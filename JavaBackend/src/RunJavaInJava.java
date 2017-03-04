import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RunJavaInJava {

	//Main method -- Used for testing
	public static void main(String[] args) throws IOException, InterruptedException {
		InputStream[] inputstreamarray = runJar("TestJar.jar");
		File outputtest = new File("OutputTest.txt");
		streamToFile(outputtest, inputstreamarray[0]);
		streamToFile(outputtest, inputstreamarray[1]);
	}

	//Runs a given jar file and returns the in/err streams
	public static InputStream[] runJar(String jarfile) throws IOException, InterruptedException {

		//Starting a new process
		Process proc = Runtime.getRuntime().exec("java -jar " + jarfile);
		proc.waitFor();

		//Getting the streams from the process
		InputStream in = proc.getInputStream();
		InputStream err = proc.getErrorStream();
		InputStream[] array = {in, err};

		//Returning the array
		return array;
	}

	//Converts the inputstream to a textfile in the given directory
	public static void streamToFile(File file, InputStream stream) throws IOException {

		//Creates a new file writer
		OutputStream writer = new FileOutputStream(file, false);
		
		//Creates a new input buffer
	    byte[] buffer = new byte[stream.available()];
	    stream.read(buffer);

	    //Writes to the buffer
		writer.write(buffer);
		writer.close();
	}
	
	
}