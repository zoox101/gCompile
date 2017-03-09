package Backend;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Driver {

	public static void main(String args[]) throws IOException {

		System.out.println("Running");
		System.out.println(compileAndRun("1ULCRSEaBhw7oJ4Way2GQU0dDM62IiQUmO5LARWWZydQ"));

	}
	
	public static String compileAndRun(String documentlink) throws IOException {
		
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

		//Runs the file
		Bash.run("javac " + title + ".java");
		String output = Bash.run("java " + title);
		String error = Bash.checkError();
		
		//Return error if it breaks, return output if it doesn't
		if(error.equals("")) {return output;}
		else {return error;}		
	}

}
