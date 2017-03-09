import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document; 

public class ZZZTesting {
	
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		
		/*
		String fileId = "0BwwA4oUTeiV1UVNwOHItT0xfa2M";
		OutputStream outputStream = new ByteArrayOutputStream();
		driveService.files().get(fileId)
		        .executeMediaAndDownloadTo(outputStream);
		        */

		
		//System.out.println(Bash.run("echo testing12345")[1]);
		
		String input = Bash.run("curl https://docs.google.com/document/d/1ULCRSEaBhw7oJ4Way2GQU0dDM62IiQUmO5LARWWZydQ/edit?usp=sharing")[0];
		Document soup = Jsoup.parse(input);
		System.out.println(soup.text());
		
		
		//String[] io = Bash.run("curl https://www.google.com");
		//Bash.run("javac Test.java");
		//String[] io = Bash.run("java Test");
		//Bash.run("jar cfm Test.jar Manifest.txt *.class");
		//String[] io = Bash.run("java -cp Test.jar");
		
		
		//System.out.println(io[0]);

		
	}

}
