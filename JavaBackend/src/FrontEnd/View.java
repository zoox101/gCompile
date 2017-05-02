package FrontEnd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import MiddleEarth.Controller;
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
import model.Model;

/**
 * This program allows the user to input google doc urls so that they can compile
 * code from google docs locally and get a console output without having to copy and 
 * paste code back and forth from google doc to their local machine.
 */
public class View extends Application{
	
	// Set size of the Frame
	public final static int APPWIDTH = 400;
	public final static int APPHEIGHT = 500;
	
	// Initialize Model and Controller
	private Model m = new Model();
	private Controller c = new Controller();
	
	// Items for GUI
	private Button plusButton;
	private Accordion accordion;
	private TextArea outputText;

	// Main Method
	public static void main(String args[]){
		launch(args);
	}

	/**
	 *  This is for the UI so that the user can add google documents and compile 
	 *  them to get a console output.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		// Create the main pane that the user will interact with
		FlowPane outputPane = new FlowPane();
		
		// Set the output Scene with the pane and Frame dimensions
		Scene outputScene = new Scene(outputPane, APPWIDTH, APPHEIGHT);
	
		/******************** Add documents to compile ************************/	
		
		// Allow user to scroll when user has an un-godly number of files to compile
		ScrollPane sp = new ScrollPane();
		
		// Create accordion where user can modify files to compile
		TitledPane tps = new TitledPane("Add Google Documents", sp);
	    accordion = new Accordion ();  
	    accordion.getPanes().addAll(tps);
		accordion.setMinWidth(APPWIDTH);
		accordion.setMaxHeight(APPHEIGHT);
		outputPane.getChildren().add(accordion);
	    
		// Add the plus button that allows user to add more files
		plusButton = new Button("+");
		plusButton.setTooltip(new Tooltip("Add more Google documents to compile"));
		plusButton.setMaxHeight(10);
		plusButton.setMaxWidth(10);
		plusButton.setOnAction( e ->c.pressButton(e, m));
		
		// Create compile button which prompts user to select a directory if 
		// one has not yet been set
		Button compileBtn = new Button("Compile");
		compileBtn.setTooltip(new Tooltip("Select a directory to save to"));
		compileBtn.setOnAction(e -> c.pressButton(e, m));
		compileBtn.setMaxWidth(200);
		
		// Add the help button that assists users
		Button helpButton = new Button("?");
		helpButton.setTooltip(new Tooltip("Click here to get more information "
				+ "on how to use tool"));
		helpButton.setMaxHeight(10);
		helpButton.setMaxWidth(10);
		helpButton.setOnAction( e ->c.pressButton(e, m));
				
		// Create grid for compile button, plus button, labels, and text Area
		GridPane grid = new GridPane(); 
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(2);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		grid.setPrefHeight(APPHEIGHT);
		grid.setPrefWidth(APPWIDTH);
		grid.add(compileBtn, 1, 0, 2, 1);
		grid.add(helpButton, 3, 0);
		
		// Increment the numbers of file labels that are being added to the GUI
		m.setNumPresetFilesToAdd(m.getNumPresetFilesToAdd() + 1);
		
		// Add input doc label
		Label doc1Label = new Label("Google Doc 1:");
		grid.add(doc1Label, 0, m.getNumPresetFilesToAdd());

		// Add text field to grid
		TextField doc1Text = new TextField();
		grid.add(doc1Text, 1, m.getNumPresetFilesToAdd());
		grid.add(plusButton, 3,m.getNumPresetFilesToAdd());
		
		// Update grid pane to model
		m.setGridpane(grid);
	
		// Limit the scroll bar 
		sp.setPrefHeight(APPHEIGHT-50);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setPrefWidth(accordion.getWidth());
		sp.setContent(m.getGridpane());
		
		/***************** Create area for the output to print to ***************/	
		Pane consolePane = new Pane();
		m.setOutputText("Console output");
		outputText = new TextArea(m.getOutputText());
		outputText.setMinSize(APPWIDTH, APPHEIGHT-25);
		outputText.setMaxSize(APPWIDTH, APPHEIGHT-25);
		
		consolePane.getChildren().add(outputText);	
		consolePane.setMaxSize(APPWIDTH, APPHEIGHT-25);
		m.setConsolePane(consolePane);
		outputPane.getChildren().add(m.getConsolePane());
		
		
		// Set the stage to the UI
		Stage mainStage = primaryStage;
	    mainStage.setScene(outputScene);
	    mainStage.setResizable(false);
	    m.setMainStage(mainStage);
	    
	    // Show the result!
	    m.getMainStage().show();

	}

}

