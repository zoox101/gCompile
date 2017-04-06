package Backend;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GCompile {

	//The file separator for the local machine
	public final static String fsep = File.separator;

	//Compiles the google docs and runs the main method
	public static String compileAndRun(ArrayList<String> googledocs, File src, File bin) throws IOException {

		//Setting static variables
		String prefix = "https://docs.google.com/document/d/";
		int ID_LENGTH = 44;
		ArrayList<String> doclinks = new ArrayList<String>();

		//Getting the id from the URLs
		for(String url: googledocs) {
			doclinks.add(url.substring(prefix.length(), prefix.length() + ID_LENGTH));}

		//Compiling the IDs
		for(int i=1; i<doclinks.size(); i++) {compile(doclinks.get(i), src, bin);}
		String main = compile(doclinks.get(0), src, bin);

		//Returning the outputs 
		String outputs = run(main, bin);

		return outputs;
	}

	//Gets the google doc data from a document
	private static String fetchData(Document doc) throws IOException {

		//Getting the correct elements from the document
		Elements elements = doc.select("script[type = text/javascript]");

		//Getting the target element
		Element targetelement = null;
		String target = "DOCS_modelChunk =";
		for(Element element: elements) {
			if(element.data().length() > target.length() && 
					element.data().substring(0, target.length()).equals(target)) {
				targetelement = element; 
				break;
			}
		}

		//Getting the unsanitized data from the HTML page
		String unsandata = targetelement.data();
		//System.out.println(unsandata); //TESTING

		String expectedstart = "DOCS_modelChunk = [{\"ty\":\"is\",\"ibi\":1,\"s\":";

		//Checks to make sure the data is correct
		if(!unsandata.substring(0, expectedstart.length()).equals(expectedstart)) {
			throw new IOException("ODD INFO FROM GOOGLE EXCEPTION");}

		//Chop off the first part of the data
		unsandata = unsandata.substring(expectedstart.length());

		//Getting the string
		StringBuffer returnbuffer = new StringBuffer();
		for(int i=1; i<unsandata.length(); i++) {
			if(unsandata.charAt(i) == '\\') {
				i++; 
				String dummytab = "u000b"; String equals = "u003d";
				if(unsandata.substring(i, i+dummytab.length()).equals(dummytab)) {i+=dummytab.length()-1;}
				else if(unsandata.substring(i, i+equals.length()).equals(equals)) {
					i+=equals.length()-1; returnbuffer.append("=");}
				else if(unsandata.charAt(i) == '\\') {returnbuffer.append("\\" + unsandata.charAt(++i));}
				else if(unsandata.charAt(i) == 't') {returnbuffer.append("\t");}
				else if(unsandata.charAt(i) == 'b') {returnbuffer.append("\b");}
				else if(unsandata.charAt(i) == 'n') {returnbuffer.append("\n");}
				else if(unsandata.charAt(i) == 'r') {returnbuffer.append("\r");}
				else if(unsandata.charAt(i) == 'f') {returnbuffer.append("\f");}
				else if(unsandata.charAt(i) == '\'') {returnbuffer.append("\'");}
				else if(unsandata.charAt(i) == '"') {returnbuffer.append("\"");}
				//else {throw new IOException("UNKNOWN_CHARACTER_EDGE_CASE: " + unsandata.charAt(i));}
			}
			else if(unsandata.charAt(i) == '"') {break;}
			else {returnbuffer.append(unsandata.charAt(i));}
		}

		//Returning the string
		return returnbuffer.toString();
	}

	//Compiles the code from the given link returns the name of the class
	public static String compile(String documentlink, File src, File bin) throws IOException {

		//Connecting to the server and getting the data
		Document doc = Jsoup.connect("https://docs.google.com/document/d/" + documentlink + "/edit?usp=sharing").get();
		String code = fetchData(doc);

		//Sanitizing the returned code
		StringBuffer newcode = new StringBuffer();
		for(int i=0; i<code.length(); i++) {
			if(code.charAt(i) != '\u000b') {
				newcode.append(code.charAt(i));}}
		code = newcode.toString();

		//Getting the name of the class
		String title = doc.title();
		int endbezel = new String(" - Google Docs").length();
		title = title.substring(0, title.length()-endbezel);

		//Creating a java file to hold the file
		File file;
		if(src == null) {file = new File(title + ".java");}
		else {file = new File(src.getAbsolutePath() + fsep + title + ".java");}

		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(code); writer.close();

		if(src == null) {
			Bash.run("javac " + title + ".java");}
		else {
			String srcdir = src.getAbsolutePath();
			String bindir = bin.getAbsolutePath();
			Bash.run("javac -sourcepath " + srcdir + " -d " + bindir + " " + srcdir + fsep + title + ".java");}

		//Return the name of the class
		return title;
	}

	//Runs the file associated with the given title
	public static String run(String title, File bin) {

		String output; String error;

		//Runs the file
		if(bin != null) {
			output =  Bash.run("java -classpath " + bin.getAbsolutePath() + " " + title);
			error = Bash.checkError();
		}
		else {
			output = Bash.run("java " + title);
			error = Bash.checkError();
		}

		//Return error if it breaks, return output if it doesn't
		if(error.equals("")) {return output;}
		else {return error;}
	}
}
