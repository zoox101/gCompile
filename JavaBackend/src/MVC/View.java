package MVC;

import java.util.ArrayList;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

 
public class View extends Application {
	Stage mainStage;
	
	Scene welcomeScene;
	Scene gameScene;
	Scene graphScene;
	Scene loserScene;
	
	Pane loserPane;
	Pane gamePane;
	Pane graphPane;
	Button startBtn;
	Button scoreBtn;
	Button newGameBtn;
	String numOfGamesInput = "";
	Double xOfBubbles[]; 
	Double yOfBubbles[]; 
	Double xClick[]; 
	Double circleSize[];
	ArrayList<Double> x;
	ArrayList<Double> y ;

	Double yClick[] ;
	Timeline timeline;
	
	
    public static void main(String args[]) {
    	launch(args);
    	
    	
    }
    

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
	    Group root = new Group();
        welcomeScene = new Scene(root, 800, 600, Color.WHITE);
        
     
        mainStage = primaryStage;
        
        
		
		//start game button
        Button compileBtn = new Button("Compile");
	
		
		
		Pane frontPane = new Pane();
		frontPane.getChildren().add(compileBtn);
		
		

		root.getChildren().add(frontPane);
        mainStage.setScene(welcomeScene);
       
        
        mainStage.show();		
	}
  
}