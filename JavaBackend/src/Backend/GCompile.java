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

	public static void main(String args[]) throws IOException {
		System.out.println("Running");	
		File directory = new File("/Users/WilliamBooker/Desktop/AAAGDocOutputs/");
		//File directory = null;
		ArrayList<String> testing = new ArrayList<String>();
		testing.add("https://docs.google.com/document/d/1ULCRSEaBhw7oJ4Way2GQU0dDM62IiQUmO5LARWWZydQ/edit?usp=sharing");
		testing.add("https://docs.google.com/document/d/1fk3NDJ6m_p5woQkW03mkYmpHuC1knuRPcHmMGXTwUrk/edit?usp=sharing");
		System.out.println(compileAndRun(testing, directory));
	}

	public static String compileAndRun(ArrayList<String> googledocs, File directory) throws IOException {

		//Setting static variables
		String prefix = "https://docs.google.com/document/d/";
		int ID_LENGTH = 44;
		ArrayList<String> doclinks = new ArrayList<String>();

		//Getting the id from the URLs
		for(String url: googledocs) {
			doclinks.add(url.substring(prefix.length(), prefix.length() + ID_LENGTH));}

		//Compiling the IDs
		String main = compile(doclinks.get(0), directory);
		for(int i=1; i<doclinks.size(); i++) {compile(doclinks.get(i), directory);}

		//Returning the outputs 
		String outputs = run(main, directory);

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
		//System.out.println(unsandata);

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
				String dummytab = "u000b";
				String equals = "u003d";
				if(unsandata.charAt(i) == 'n') {returnbuffer.append("\n");}
				else if(unsandata.charAt(i) == '"') {returnbuffer.append("\"");}
				else if(unsandata.substring(i, i+dummytab.length()).equals(dummytab)) {i+=dummytab.length()-1;}
				else if(unsandata.substring(i, i+equals.length()).equals(equals)) {
					i+=equals.length()-1; returnbuffer.append("=");}
			}
			else if(unsandata.charAt(i) == '"') {break;}
			else {returnbuffer.append(unsandata.charAt(i));}
		}

		//Returning the string
		return returnbuffer.toString();
	}

	//Compiles the code from the given link returns the name of the class
	public static String compile(String documentlink, File directory) throws IOException {

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
		if(directory == null) {file = new File(title + ".java");}
		else {file = new File(directory.getAbsolutePath() + File.separator + title + ".java");}

		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(code); writer.close();

		if(directory != null) {
			String dir = directory.getAbsolutePath();
			Bash.run("javac -sourcepath " + dir + " -d " + dir + " " +  dir + File.separator + title + ".java");
		}
		else {Bash.run("javac " + title + ".java");}

		//Return the name of the class
		return title;
	}

	//Runs the file associated with the given title
	public static String run(String title, File directory) {

		String output; String error;
		
		//Runs the file
		if(directory != null) {
			output =  Bash.run("java -classpath " + directory.getAbsolutePath() + " " + title);
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
