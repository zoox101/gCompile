import java.io.IOException;
import java.io.InputStream;

public class RunJavaInJava {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		runJar("TestJar.jar");
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

}