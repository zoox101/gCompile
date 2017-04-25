package FrontEnd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * This program allows the user to input google doc urls so that they can compile
 * code from google docs locally and get a console output without having to copy and 
 * paste code back and forth from google doc to their local machine.
 */
public class FrontEnd extends Application{
	final int APPWIDTH = 400;
	final int APPHEIGHT = 500;
	Button plusButton;
	GridPane grid;
	AtomicInteger numAddedFiles;
	AtomicInteger numPresetFilesToAdd;
	Stage mainStage;
	Scene outputScene;
	Accordion accordion;
	TextArea outputText;
	File selectedDirectory;

	public File getSelectedDirectory() {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		selectedDirectory = directoryChooser.showDialog(mainStage);
	    if (selectedDirectory != null) {
	    	selectedDirectory.getAbsolutePath();
	    }
		return selectedDirectory;
	}

	public void setSelectedDirectory(File selectedDirectory) {
		this.selectedDirectory = selectedDirectory;
	}

	protected TextArea getOutputText() {
		return outputText;
	}

	public void setOutputText(String outputs) {
		this.outputText = new TextArea(outputs); 
	}

	public static void main(String args[]) throws IOException {
		
		launch(args);
		   
	}

	/**
	 *  This is for the UI so that the user can add google documents and compile them to get 
	 *  a console output.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Create the main pane that the user will interact with
		FlowPane outputPane = new FlowPane();
		outputScene = new Scene(outputPane, APPWIDTH, APPHEIGHT);
	
		/*
		 * Allow the user to add documents and compile them to get an output
		 */
		ScrollPane sp = new ScrollPane();
		TitledPane tps = new TitledPane("Add Google Documents", sp);
	    accordion = new Accordion ();  
	    accordion.getPanes().addAll(tps);
		accordion.setMinWidth(APPWIDTH);
		accordion.setMaxHeight(APPHEIGHT);
		
		outputPane.getChildren().add(accordion);
	        
		grid = new GridPane(); 
		plusButton = new Button("+");
		plusButton.setTooltip(new Tooltip("Add more Google documents to compile"));
		numAddedFiles = new AtomicInteger(); //initializes to 0
		numPresetFilesToAdd = new AtomicInteger();
		plusButton.setOnAction(e -> pressButton(e));
		plusButton.setMaxHeight(10);
		plusButton.setMaxWidth(10);
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(2);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setPrefHeight(APPHEIGHT);
		grid.setPrefWidth(APPWIDTH);
		 
		Button compileBtn = new Button("Compile");
		compileBtn.setOnAction(e -> pressButton(e));
		compileBtn.setMaxWidth(200);;
		grid.add(compileBtn, 1, 0, 2, 1);
		
		
		Label doc1Label = new Label("Google Doc 1:");
		grid.add(doc1Label, 0, numPresetFilesToAdd.incrementAndGet());

		TextField doc1Text = new TextField();
		grid.add(doc1Text, 1, numPresetFilesToAdd.get());
		grid.add(plusButton, 3, numPresetFilesToAdd.get());
	
		TextField doc2Text = new TextField();
		grid.add(doc2Text, 1, numPresetFilesToAdd.get());
	
		sp.setPrefHeight(APPHEIGHT-50);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setPrefWidth(accordion.getWidth());
		sp.setContent(grid);
		
		/**
		 * Create area for the output to print to
		 */
		Pane consolePane = new Pane();
		outputText = new TextArea("Output");
		outputText.setMinSize(APPWIDTH, APPHEIGHT-25);
		outputText.setMaxSize(APPWIDTH, APPHEIGHT-25);
		consolePane.getChildren().add(outputText);
		consolePane.setMaxSize(APPWIDTH, APPHEIGHT-25);
		outputPane.getChildren().add(consolePane);

		
		//Set the stage to the UI
		mainStage = primaryStage;
	    mainStage.setScene(outputScene);
	    mainStage.setResizable(false);
	    mainStage.show();
	}

	/**
	 * These are listeners that allow the user to dynamically add more google docs
	 * 
	 */
    public void pressButton(ActionEvent e){
    	if(e.getSource().toString().contains("+")){
    		Button tempAddBtn = new Button("+");
    		tempAddBtn.setOnAction(e2 -> pressDynamicButton (e2));
    		grid.add(tempAddBtn, 3, numPresetFilesToAdd.get() + numAddedFiles.incrementAndGet());
    		grid.add(new Label("Google Doc " + (numPresetFilesToAdd.get() + numAddedFiles.get()) + ":"),
    				0, numPresetFilesToAdd.get()+ numAddedFiles.get());
    		grid.add(new TextField(), 1, numPresetFilesToAdd.get() + numAddedFiles.get());
    	}else if(e.getSource().toString().contains("Compile")){
    		System.out.println("pressing");
    		
    		mainStage.setScene(outputScene);
    		mainStage.show();
    	}
    
    }
    public void pressDynamicButton(ActionEvent e2){
    	if(e2.getSource().toString().contains("+")){
    		Button tempAddBtn = new Button("+");
    		tempAddBtn.setOnAction(e -> pressButton(e));
    		grid.add(tempAddBtn, 3, numPresetFilesToAdd.get() + numAddedFiles.incrementAndGet());
    		grid.add(new Label("Google Doc " + (2 + numAddedFiles.get()) + ":"), 
    				0, numPresetFilesToAdd.get() + numAddedFiles.get());
    		grid.add(new TextField(), 1, numPresetFilesToAdd.get() + numAddedFiles.get());
    	}
    }
    

}

