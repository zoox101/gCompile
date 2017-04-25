package MiddleEarth;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import Backend.*;
import FrontEnd.FrontEnd;

public class Controller {
	
	FileStorage storage;
	FrontEnd view;
	
	//Constructor for the controller object
	public Controller(FrontEnd view) {
		this.view = view;
		storage = new FileStorage();
	}
	
	//Compiles and runs the list of files
	public void compileAndRun(ArrayList<String> files) {
		
		//Gets the directory from the view if is does not already exist
		if(!storage.hasDirectory()) {
			File directory = view.getDirectory();
			storage.setDirectory(directory);
		}
		
		//Setting the storage file
		storage.clear();
		storage.addAll(files);
		String outputs = "";
		
		//Compiles the code and returns the outputs
		try {outputs = GCompile.compileAndRun(storage);} 
		catch (IOException error) {}
		
		//Passes the outputs to the view
		view.setOutputs(outputs);
	}
	

}
