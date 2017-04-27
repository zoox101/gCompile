package model;

import java.io.File;
import java.util.ArrayList;


import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class Model {
	private String outputText;
	private ArrayList<String> files;
	private File selectedDirectory;
	private int numAddedFiles;
	private int numPresetFilesToAdd;
	private GridPane gridpane;
	private Stage mainStage;
	
	public Model(){
		this.outputText = "Console output";
		this.selectedDirectory = null;
		this.files = new ArrayList<String>();

		this.numAddedFiles = 0;
		this.numPresetFilesToAdd = 0;

	}
	
	/**
	 *  If the user has not specified a directory, force the
	 *  user to select a directory.
	 * 
	 */
	public File getSelectedDirectory() {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
		selectedDirectory = directoryChooser.showDialog(mainStage);
	    if (selectedDirectory != null) {
	    	selectedDirectory.getAbsolutePath();
	    }
		return selectedDirectory;
	}

	/******** A whole bunch of getters & setters, complements of MVC *********/
	
	public void setSelectedDirectory(File selectedDirectory) {
		this.selectedDirectory = selectedDirectory;
	}

	public int getNumAddedFiles() {
		return numAddedFiles;
	}

	public void setNumAddedFiles(int numAddedFiles) {
		this.numAddedFiles = numAddedFiles; 
	}
	public int getNumPresetFilesToAdd() {
		return numPresetFilesToAdd;
	}

	public void setNumPresetFilesToAdd(int numPresetFilesToAdd) {
		this.numPresetFilesToAdd = numPresetFilesToAdd;
	}

	public GridPane getGridpane() {
		return gridpane;
	}

	public void setGridpane(GridPane gridpane) {
		this.gridpane = gridpane;
	}

	public Stage getMainStage() {
		return mainStage;
	}

	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}

	public ArrayList<String> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<String> files) {
		this.files = files;
	}
	
	public String getOutputText() {
		return outputText;
	}

	public void setOutputText(String outputText) {
		this.outputText = outputText; 
	}



}
