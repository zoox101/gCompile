import java.io.BufferedReader;
import java.io.InputStreamReader;

//A class to make running bash scripts easier
public class Bash {
	
	//Executes a bash command and returns a string for the input and error streams
	public static String[] run(String command) {
		
		//Creates a string buffer for the output
	    StringBuffer input = new StringBuffer();
	    StringBuffer error = new StringBuffer();

	    try {
	    	//Creates a new process and waits for it to run
	        Process process = Runtime.getRuntime().exec(command);
	        process.waitFor();
	        	       
	        //Appends the input to the input variable
	        BufferedReader inputreader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        String inputline = "";
	        while ((inputline = inputreader.readLine()) != null) {input.append(inputline + "\n");}
	        	        
	        //Appends the error to the error variable
	        BufferedReader errorreader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
	        String errorline = "";
	        while ((errorline = errorreader.readLine()) != null) {error.append(errorline + "\n");}
	    } 
	    catch (Exception e) {e.printStackTrace();}
	    
	    //Returns the output
	    String[] returnstrings = new String[2];
	    returnstrings[0] = input.toString();
	    returnstrings[1] = error.toString();
	    
	    //System.out.println(input.toString());
	    //System.out.println(error.toString());

	    return returnstrings;
	}
}
