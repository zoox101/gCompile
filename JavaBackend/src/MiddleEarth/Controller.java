package MiddleEarth;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import Backend.*;
import FrontEnd.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import model.Model;

public class Controller{
	
	private FileStorage storage;
	
	// Constructor for the controller object
	public Controller() {
		storage = new FileStorage();
	}
	
	// Compiles and runs the list of files
	public void compileAndRun(ArrayList<String> files, Model m) {
		
		// Gets the directory from the view if is does not already exist
		if(!storage.hasDirectory()) {
			File directory = m.getSelectedDirectory();
			storage.setDirectory(directory);
		}
		
		// Setting the storage file
		storage.clear();
		storage.addAll(files);
		String outputs = "";
		
		// Compiles the code and returns the outputs
		try {outputs = GCompile.compileAndRun(storage);} 
		catch (IOException error) {}
		
		// Passes the outputs to the view
		m.setOutputText(outputs);
	}
	
	/**
	 * These are listeners that allow the user to dynamically add more google docs
	 * 
	 */
    public void pressButton(ActionEvent e, Model m){
    	
    	// Check if the action is a plus button
    	if(e.getSource().toString().contains("+")){
    		
    		// Create a new button to add right under on the grid
    		Button tempAddBtn = new Button("+");
    		tempAddBtn.setTooltip(new Tooltip("Add more Google documents to compile"));
    		
    		// Add an action listener to the newly created button
    		tempAddBtn.setOnAction(e2 -> pressDynamicButton (e2, m));
    		
    		// Increment the number of file fields needed to be checked
    		m.setNumAddedFiles(m.getNumAddedFiles() +1);
    		
    		// Get the current grid pane to update
    		GridPane grid = m.getGridpane();
    			// add the new plus button
			grid.add(tempAddBtn, 3, m.getNumPresetFilesToAdd() 
					+ m.getNumAddedFiles());
				// add new label for the input text field
    		grid.add(new Label("Google Doc " + (m.getNumPresetFilesToAdd() 
    										+ m.getNumAddedFiles()) + ":"),
    				0, 
    				m.getNumPresetFilesToAdd() + m.getNumAddedFiles());
    			// add new text field for additional google doc
    		grid.add(new TextField(), 1, m.getNumPresetFilesToAdd() + m.getNumAddedFiles());
    		
    		// Update the grid pane in model
    		m.setGridpane(grid);
    	}
    	// Check if the action is a compile button
    	else if(e.getSource().toString().contains("Compile")){
    		
    		// Get details from model
    		GridPane grid = m.getGridpane();
    		ArrayList<String> files = new ArrayList<String>();
    		
    		// Loop through text fields and get the text
    		for(Node n: grid.getChildren()){
    			if ( grid.getColumnIndex(n) == 1 && n instanceof TextField 
    					&& ((TextField)n).getText().length() > 10){
    				files.add( ((TextField)n).getText() );
    			}
    		}
    		
    		// Update model
    		m.setFiles(files);
    		
    		// Compile the google doc files
    		compileAndRun(m.getFiles(), m);
    		
    		// Show the output!
    		m.getMainStage().show();
    	}
    
    }
    
    /**
     *  This second listener allows the user to stay on the same panel while seeing
     *  updates to the UI 
     */
    public void pressDynamicButton(ActionEvent e2, Model m){
    	
    	// Check if the action is a plus button
    	if(e2.getSource().toString().contains("+")){
    		
    		// Create a new button to add right under on the grid
    		Button tempAddBtn = new Button("+");
    		tempAddBtn.setTooltip(new Tooltip("Add more Google documents to compile"));
    		tempAddBtn.setOnAction(e -> pressButton(e, m));
    		
    		// Increment the number of file fields needed to be checked
    		m.setNumAddedFiles(m.getNumAddedFiles() +1);
    		
    		// Get the current grid pane to update with the new input labels
    		GridPane grid = m.getGridpane();
    		grid.add(tempAddBtn, 3, m.getNumPresetFilesToAdd() + m.getNumAddedFiles());
    		grid.add(new Label("Google Doc " + (1 + m.getNumAddedFiles()) + ":"), 
    				0, m.getNumPresetFilesToAdd()+ m.getNumAddedFiles());
    		grid.add(new TextField(), 1, m.getNumPresetFilesToAdd() + m.getNumAddedFiles());
    		
    		// Update the grid pane in model
    		m.setGridpane(grid);
    	}
    }

}
