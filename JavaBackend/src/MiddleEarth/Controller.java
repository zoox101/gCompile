package MiddleEarth;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import Backend.*;
import FrontEnd.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    	
    	if(e.getSource().toString().contains("?")){
    	
    		final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(m.getMainStage());
            
            VBox dialogVbox = new VBox();
            
            //Titled pane one
            TitledPane tps0 = new TitledPane();
            tps0.setText("Where do I find the google doc link?");
            
            ScrollPane sp = new ScrollPane();
            // Load image
            Image image = new Image("Share.png");
            
    
            // resizes the image to have width of 100 while preserving the ratio and using
            // higher quality filtering method and is cached to improve performance
            ImageView iv0 = new ImageView();
            
            iv0.setImage(image);
            iv0.setFitWidth(300);
            iv0.setPreserveRatio(true);
            iv0.setSmooth(true);
            iv0.setCache(true);
            
            //Grid for instructions
            GridPane grid = new GridPane();
            
    		grid.setAlignment(Pos.TOP_LEFT);
    		grid.setVgap(10);
    		
    		//First instruction
    		grid.setPrefHeight(300);
    		grid.setPrefWidth(500);
    		TextArea instr0 = new TextArea();
    		instr0.setText("1. On the Google doc page, click on the 'Share' button,"
    				+ " located on the top right corner of the page.");
    		instr0.setWrapText(true);
    		instr0.setEditable(false);
    		instr0.setMaxHeight(iv0.getFitHeight());
    		instr0.setMinHeight(115);
  
    		instr0.setMaxWidth(500);
    		grid.add(instr0, 0, 0);
    		
    		grid.add(iv0, 1, 0);
    		
    		
    		//Second instruction
    		TextArea instr1 = new TextArea();
    		instr1.setText("2. Click on the option where everyone with the link can view"
    				+ " the document.");
    		instr1.setWrapText(true);
    		instr1.setEditable(false);
    		instr1.setMaxHeight(iv0.getFitHeight());
    		instr1.setMinHeight(210);
  
    		instr1.setMaxWidth(500);
    		grid.add(instr1, 0, 1);
    		
		    ImageView iv1 = new ImageView();
		    Image image1 = new Image("CanView.png");
            iv1.setImage(image1);
            iv1.setFitWidth(300);
            iv1.setPreserveRatio(true);
            iv1.setSmooth(true);
            iv1.setCache(true);
    		grid.add(iv1, 1, 1);
    	
    		//Third instruction
    		TextArea instr2 = new TextArea();
    		instr2.setText("3. Copy the link in this text field to paste into the compile tool. ");
    		instr2.setWrapText(true);
    		instr2.setEditable(false);
    		//instr2.setMaxHeight(iv.getFitHeight());
    		instr2.setMinHeight(50);
  
    		instr1.setMaxWidth(500);
    		grid.add(instr2, 0, 2);
    		
		    ImageView iv2 = new ImageView();
		    Image image2 = new Image("CopyLink.png");
            iv2.setImage(image2);
            iv2.setFitWidth(300);
            iv2.setPreserveRatio(true);
            iv2.setSmooth(true);
            iv2.setCache(true);
            grid.add(iv2, 1, 2);
    		
    		
            sp.setContent(grid);
            tps0.setContent(sp);
            
            //Titled pane two
            TitledPane tps1 = new TitledPane();
            tps1.setText("Why does the compile button make me choose a file?");
            tps1.setWrapText(true);
      
            TextArea ta1 = new TextArea();
            ta1.setWrapText(true);
            ta1.setText("The program needs a way to look for the java file locally"
                    		+ " so the program prompts you to choose a file to save to.");
            ta1.setEditable(false);
            
            ta1.setMaxWidth(500);
            ta1.setMinHeight(100);
            ta1.setMaxHeight(300);
            tps1.setContent(ta1);
            
            //Titled pane three
            TitledPane tps2 = new TitledPane();
            tps2.setWrapText(true);
            tps2.setText("I accidentally added too many google doc fields to compile. "
            		+ "What do I do?");
            
            
            TextArea ta2 = new TextArea();
            ta2.setWrapText(true);
            ta2.setText("Don't worry. The program will only look at the google doc "
            		+ "fields that are populated to run the program.");
            ta2.setEditable(false);
            
            ta2.setMaxWidth(500);
            ta2.setMinHeight(100);
            ta2.setMaxHeight(300);
            tps2.setContent(ta2);
            
            
            Accordion a = new Accordion();
            a.getPanes().addAll(tps0,tps1,tps2);
         
            
            dialogVbox.getChildren().add(a);
            Scene dialogScene = new Scene(dialogVbox, 520, 300);
            dialog.setScene(dialogScene);
            dialog.show();
    		
    		
    	}
    	// Check if the action is a plus button
    	else if(e.getSource().toString().contains("+")){
    		
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
    		m.setOutputText(m.getOutputText());
    		
    		// Show the output!
    		Pane consolePane = m.getConsolePane();    		
    		TextArea outputText = new TextArea(m.getOutputText());
    		outputText.setEditable(false);
    		outputText.setMinSize(View.APPWIDTH, View.APPHEIGHT-25);
    		outputText.setMaxSize(View.APPWIDTH, View.APPHEIGHT-25);
    		
    		consolePane.getChildren().clear();
    		consolePane.getChildren().add(outputText);
    		
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
    	
    	}
    }

}
