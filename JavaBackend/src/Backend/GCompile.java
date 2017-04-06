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
		String main = compile("1ULCRSEaBhw7oJ4Way2GQU0dDM62IiQUmO5LARWWZydQ");
		compile("1fk3NDJ6m_p5woQkW03mkYmpHuC1knuRPcHmMGXTwUrk");
		System.out.println(run(main));

	}
	
	public static String compileAndRun(ArrayList<String> googledocs, File directory) {
		return null;
	}
	
	//Compiles the code from the given link returns the name of the class
	public static String compile(String documentlink) throws IOException {
		
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
		File file = new File(title + ".java");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(code); writer.close();
		
		//Return the name of the class
		return title;
		
	}
	
	
	private static String fetchData(Document doc) throws IOException {
		
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
		
		String unsandata = targetelement.data();
		String expectedstart = "DOCS_modelChunk = [{\"ty\":\"is\",\"ibi\":1,\"s\":";
		
		if(!unsandata.substring(0, expectedstart.length()).equals(expectedstart)) {
			throw new IOException("ODD INFO FROM GOOGLE EXCEPTION");}
		
		unsandata = unsandata.substring(expectedstart.length());
				
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
		
		return returnbuffer.toString();
	}
	
	//Runs the file associated with the given title
	public static String run(String title) {

		//Runs the file
		Bash.run("javac " + title + ".java");
		String output = Bash.run("java " + title);
		String error = Bash.checkError();
		
		//Return error if it breaks, return output if it doesn't
		if(error.equals("")) {return output;}
		else {return error;}	
	}
	
}
