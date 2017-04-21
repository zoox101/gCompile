package Backend;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GCompile {

	//The file separator for the local machine
	public final static String fsep = File.separator;
	
	//Compiles the google docs and runs the main method
	public static String compileAndRun(FileStorage storage) throws IOException {
		return compileAndRun(storage, storage.src, storage.bin);}

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

	//Compiles the code from the given link returns the name of the class
	public static String compile(String documentlink, File src, File bin) throws IOException {

		String link = "https://docs.google.com/document/d/" + documentlink + "/export?format=txt";

		//Connecting to the server and getting the data
		String code = Bash.run("curl " + link); Bash.checkError();

		//Looking for header
		String title = ""; String[] split = code.split(" ");
		for(int i=0; i<split.length; i++) {
			if(split[i].equals("class")) {
				title = split[i+1]; break;}
		}

		//Sanitizing the returned code
		StringBuffer newcode = new StringBuffer();
		for(int i=0; i<code.length(); i++) {
			if(code.charAt(i) != '\ufeff') {
				newcode.append(code.charAt(i));}}
		code = newcode.toString();

		//Creating a java file to hold the file
		File file;
		if(src == null) {file = new File(title + ".java");}
		else {file = new File(src.getAbsolutePath() + fsep + title + ".java");}

		//Writing the code to a file
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(code); writer.close();

		//Compile the code to the correct location
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
