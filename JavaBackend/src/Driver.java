import java.awt.Scrollbar;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
		GridPane grid = new GridPane();
		
		Button plusButton = new Button("+");
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
		
		
		Text scenetitle = new Text("Select Documents to compile.");
		scenetitle.setFont(Font.font("", FontWeight.NORMAL, 14));
		grid.add(scenetitle, 0, 0, 2, 1);
		grid.add(plusButton, 3,2);
		Label doc1Label = new Label("Google Doc 1:");
		grid.add(doc1Label, 0, 1);

		TextField doc1Text = new TextField();
		grid.add(doc1Text, 1, 1);

		Label doc2Label = new Label("Google Doc 2:");
		grid.add(doc2Label, 0, 2);

		TextField doc2Text = new TextField();
		grid.add(doc2Text, 1, 2);
		ScrollBar s = new ScrollBar();
		s.setOrientation(Orientation.VERTICAL);
		
		Button compileBtn = new Button("Compile");
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
        });
//		primaryStage.setScene(scene);
//		 Button btn = new Button();
//	        btn.setText("Compile");
//	        
//	        
//	        
//	        btn.setOnAction(new EventHandler<ActionEvent>() {
//	 
//	            @Override
//	            public void handle(ActionEvent event) {
//	                System.out.println("Hello1 World!");
//	            }
//	        });
//	        
//	      
//	        root.getChildren().add(btn);
//	       btn.setLayoutX(170);
//	       btn.setLayoutY(140);
//	       
//
//	 Scene scene = new Scene(root, 400, 200);
//
//	        primaryStage.setTitle("HaWillJavaCompile");
//	        primaryStage.setScene(scene);
//	        
//	        Label label1 = new Label("GoogleDoc URL:");
//	        label1.setLayoutX(60);
//	        label1.setLayoutY(100);
//	        TextField textField = new TextField ("Ello");
//	      
//	      //  HBox hb = new HBox();
//	        root.getChildren().addAll(label1, textField);
//	     //   hb.setSpacing(10);
//	        textField.setLayoutX(180);
//	        textField.setLayoutY(100);
	      
	    /*    
	        GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        grid.setHgap(10);
	        grid.setVgap(10);
	        grid.setPadding(new Insets(25, 25, 25, 25));

	        Text scenetitle = new Text("Welcome");
	        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        grid.add(scenetitle, 0, 0, 2, 1);

	        Label userName = new Label("User Name:");
	        grid.add(userName, 0, 1);

	        TextField userTextField = new TextField();
	        grid.add(userTextField, 1, 1);

	        Label pw = new Label("Password:");
	        grid.add(pw, 0, 2);

	        PasswordField pwBox = new PasswordField();
	        
	        
	        Button btn1 = new Button("Sign in");
	        HBox hbBtn = new HBox(10);
	        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
	        hbBtn.getChildren().add(btn1);
	        grid.add(hbBtn, 1, 4);
	        
	      //  final Text actiontarget = new Text();
	       // grid.add(actiontarget, 1, 6);
	        
	   
	        grid.add(pwBox, 1, 2);*/
	       
	        primaryStage.setScene(scene);
	        primaryStage.show();
	}

}

