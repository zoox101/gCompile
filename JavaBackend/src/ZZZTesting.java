import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class ZZZTesting {
	
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		
		URL url = new URL("https://docs.google.com/document/d/1ULCRSEaBhw7oJ4Way2GQU0dDM62IiQUmO5LARWWZydQ/edit?usp=sharing");

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
		    for (String line; (line = reader.readLine()) != null;) {
		        System.out.println(line);
		    }
		}
		
		//System.out.println(Bash.run("echo testing12345")[1]);
		//String[] io = Bash.run("curl https://docs.google.com/document/d/1ULCRSEaBhw7oJ4Way2GQU0dDM62IiQUmO5LARWWZydQ/edit?usp=sharing");
		//String[] io = Bash.run("curl https://docs.google.com/document/d/1ULCRSEaBhw7oJ4Way2GQU0dDM62IiQUmO5LARWWZydQ/edit?usp=sharing");
		//Bash.run("javac Test.java");
		//String[] io = Bash.run("java Test");
		//Bash.run("jar cfm Test.jar Manifest.txt *.class");
		//String[] io = Bash.run("java -cp Test.jar");
		
		//System.out.println(io[0]);

		
	}

}
