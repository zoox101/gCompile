import java.awt.Scrollbar;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Driver extends Application{
	Button plusButton;
	GridPane grid;
	AtomicInteger numAddedFiles;
	AtomicInteger numPresetFilesToAdd;
	
	
	public static void main(String args[]) throws IOException {

		System.out.println("Running");

		Document doc = Jsoup.connect("https://docs.google.com/document/d/1ULCRSEaBhw7oJ4Way2GQU0dDM62IiQUmO5LARWWZydQ/edit?usp=sharing").get();
		String code = doc.select("meta[property = og:description]").attr("content");
		StringBuffer newcode = new StringBuffer();

		for(int i=0; i<code.length(); i++) {
			if(code.charAt(i) != '\u000b') {
				newcode.append(code.charAt(i));
			}
			
		}
		
		code = newcode.toString();


		File file = new File("Test.java");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(code); writer.close();

		Bash.run("javac Test.java");
		String[] io = Bash.run("java Test");
		System.out.println(io[0]);
		
		launch(args);
		   
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Group root = new Group(); //create the root
		ScrollPane sp = new ScrollPane();
		grid = new GridPane(); 
		
		
		plusButton = new Button("+");
		numAddedFiles = new AtomicInteger(); //initializes to 0
		numPresetFilesToAdd = new AtomicInteger();
		plusButton.setOnAction(e -> pressButton(e));
		plusButton.setMaxHeight(10);
		plusButton.setMaxWidth(10);
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(2);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		//sp.fitToHeightProperty();
		
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setContent(grid);
		Scene scene = new Scene(sp, 370, 275);
		
		Button compileBtn = new Button("Compile");
		compileBtn.setMaxWidth(200);;
		grid.add(compileBtn, 1, 0, 2, 1);
		//Text scenetitle = new Text("Select Documents to compile.");
		//scenetitle.setFont(Font.font("", FontWeight.NORMAL, 14));
		//grid.add(scenetitle, 0, 0, 2, 1);
		
		Label doc1Label = new Label("Google Doc 1:");
		grid.add(doc1Label, 0, numPresetFilesToAdd.incrementAndGet());

		TextField doc1Text = new TextField();
		grid.add(doc1Text, 1, numPresetFilesToAdd.get());
		
		Label doc2Label = new Label("Google Doc 2:");
		grid.add(doc2Label, 0, numPresetFilesToAdd.incrementAndGet());
		grid.add(plusButton, 3, numPresetFilesToAdd.get());
		
		TextField doc2Text = new TextField();
		grid.add(doc2Text, 1, numPresetFilesToAdd.get());
		ScrollBar s = new ScrollBar();
		s.setOrientation(Orientation.VERTICAL);
		
		
/*		Button compileBtn = new Button("Compile");
		HBox hbBtn = new HBox(5);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(compileBtn);
		grid.add(hbBtn, 1, 4);
		final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        compileBtn.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Compile button pressed");
            }
        });*/
    
	       
	        primaryStage.setScene(scene);
	        primaryStage.show();
	}
    
    public void pressButton(ActionEvent e){
    	if(e.getSource().toString().contains("+")){
    		Button tempAddBtn = new Button("+");
    		tempAddBtn.setOnAction(e2 -> pressDynamicButton (e2));
    		grid.add(tempAddBtn, 3, numPresetFilesToAdd.get() + numAddedFiles.incrementAndGet());
    		grid.add(new Label("Google Doc " + (numPresetFilesToAdd.get() + numAddedFiles.get()) + ":"),
    				0, numPresetFilesToAdd.get()+ numAddedFiles.get());
    		grid.add(new TextField(), 1, numPresetFilesToAdd.get() + numAddedFiles.get());

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

