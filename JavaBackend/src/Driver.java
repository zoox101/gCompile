import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Driver {

	public static void main(String args[]) throws IOException {

		System.out.println("Running");

		Document doc = Jsoup.connect("https://docs.google.com/document/d/1ULCRSEaBhw7oJ4Way2GQU0dDM62IiQUmO5LARWWZydQ/edit?usp=sharing").get();
		String code = doc.select("meta[property = og:description]").attr("content");
		StringBuffer newcode = new StringBuffer();

		for(int i=0; i<code.length(); i++) {
			if(code.charAt(i) != '\u000b') {
				newcode.append(code.charAt(i));
			}
		}
		
		code = newcode.toString();


		File file = new File("Test.java");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(code); writer.close();

		Bash.run("javac Test.java");
		String[] io = Bash.run("java Test");
		System.out.println(Bash.check);
		System.out.println(io[0]);

	}

}
