
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Pong extends Application{
	
	/*
	 * Hovedmeny med: "1 Player" og "2 Player"
	 * 		Ball som spretter i bakgrunnen
	 * 		"PONG" i stor tydelig font
	 * HiScore
	 * Score foran ball :((
	 * Ball plassering ikke random :((
	 */

	//TODO Sometimes on start ball passes through player, pls fix

	// {sceneWidth, sceneHeight, rectWidth, rectHeight, playerSpeed, player1X, player1Y, ballState}
	double[] info = {600, 400, 10, 100, 3, 30, 30, 0};
	boolean hasStarted = false, hasEnded = false;
	
	Group root = new Group();
	VBox startPane = new VBox();
	Text txtPoints = new Text();
	Text endText = new Text();
	Text titleText = new Text("PONG");
	Button startBtn = new Button("1 Player");
	BorderPane border = new BorderPane();
	Scene scene = new Scene(root, info[0], info[1]);
	Player player1 = new Player(1, info);
	Ball ball = new Ball(info, txtPoints, border);
	// Frame
	Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), e -> {
		
		hasEnded = ball.ended();
		if (hasEnded) {
			itEnded();
		}
		player1.frame();
		ball.frame();
	}));
	
	public void start(Stage primaryStage){		
		timeline.setCycleCount(Animation.INDEFINITE);
		//timeline.play();
		
		scene.setOnKeyPressed(e -> {
			player1.keyPressed(e);
		});
		scene.setOnKeyReleased(e -> {
			player1.keyReleased(e);
		});
		
		startBtn.setOnMouseClicked(e -> {
			hasStarted = true;
			info[7] = 1;
			titleText.setVisible(false);
			startBtn.setVisible(false);		
			ball.start = true;
			root.getChildren().addAll(border, player1.returnNode());
		});
		
		border.setCenter(txtPoints);
		border.setLayoutX(225);
		
		txtPoints.setStyle("-fx-font-size: 300px");
		txtPoints.setFill(Color.WHITE);
		txtPoints.setStroke(Color.BLACK);
		
		itStarted();
		
		
		primaryStage.setResizable(false);
		primaryStage.setTitle("Pong");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void itStarted(){
		titleText.setStyle("-fx-font-size: 80px; -fx-font-weight: bold;");
		titleText.setX((info[0] / 2) - 100);
		//titleText.setY(100);
		
		startBtn.setLayoutX((info[0] / 2) - 55);
		//startBtn.setLayoutY(150);
		startBtn.setStyle("-fx-background-color: linear-gradient(#f2f2f2, #d6d6d6), linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%), linear-gradient(#dddddd 0%, #f6f6f6 50%); "
				+ "-fx-background-radius: 8,7,6;-fx-background-insets: 0,1,2;-fx-text-fill: black; "
				+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); -fx-font-size: 30px");
		
		VBox.setMargin(startBtn, new Insets(50, 0, 50, 0));
		startPane.setLayoutX(info[0] / 3);
		startPane.getChildren().addAll(titleText, startBtn);
		startPane.setAlignment(Pos.CENTER);
		
		root.getChildren().addAll(ball.returnNode(), startPane);
		timeline.play();
	}
	
	public void itEnded(){
		timeline.stop();
		endText.setText("You got " + ball.points + " points!");
		endText.setStyle("-fx-font-size: 30px");
		endText.setX((info[0] / 2) - 100);
		endText.setY(info[1] / 2);
		
		ball.returnNode().setVisible(false);
		player1.returnNode().setVisible(false);
		txtPoints.setVisible(false);
		
		root.getChildren().addAll(endText);
	}
	
	public static void main(String[] args){
		launch(args);
	}
}


