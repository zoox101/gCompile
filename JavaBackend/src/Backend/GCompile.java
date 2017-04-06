package Backend;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
		String code = doc.select("meta[property = og:description]").attr("content");
		
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
