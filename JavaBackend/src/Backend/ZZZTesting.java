package Backend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ZZZTesting {

	public static void main(String[] args) throws IOException {
		
		//Document doc = Jsoup.connect("https://docs.google.com/document/d/1ULCRSEaBhw7oJ4Way2GQU0dDM62IiQUmO5LARWWZydQ/edit").get();
		//test(doc.body().toString());
		
		File file = new File("/Users/WilliamBooker/Desktop/AAAGDocOutputs/");
		System.out.println(file.isDirectory());
		
		
		
	}
	
	private static void test(String string) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("test.txt")));
		writer.write(string);
		writer.close();
	}
}
