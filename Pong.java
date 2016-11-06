
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Pong extends Application{
    /*
     * 4 player (one up, one down OR/AND one on each edge)
     * Settings?
     *      Key Assignment
     *      Custom speed on ball and player
     *      Color
     * Score on each players side
     */
    //TODO Fix so ball radius can safely vary
	//TODO Save high score

	// {0sceneWidth, 1sceneHeight, 2rectWidth, 3rectHeight, 4playerSpeed, 5player1X, 6player1Y, 7ballState}
	double[] info = {600, 400, 10, 100, 3, 30, 30, 0};

    //boolean hasStarted = false; currently not used

    boolean hasEnded = false;
	
	Group root = new Group();
	VBox startPane = new VBox();
	Text txtPoints = new Text();
	Text endText = new Text();
	Text titleText = new Text("PONG");
	Button startBtn = new Button("1 Player");
	BorderPane border = new BorderPane();
	Scene scene = new Scene(root, info[0], info[1]);
	Player player1 = new Player(1, info);
    Player player2 = new Player(2, info);
	Ball ball = new Ball(info, txtPoints, border);

    // Frame in which all animation happen
	Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), e -> {
		// Checks every frame if ball class has set end to true
        hasEnded = ball.ended();

        // If hasEnded is true start the itEnded() method
        if (hasEnded) {
			itEnded();
		}

		// Does all the checks for each object
		player1.frame();
		ball.frame();
	}));
	
	public void start(Stage primaryStage){		
		// Set timeline to run indefinite and start it
        timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

        // Events to control player
		scene.setOnKeyPressed(e -> player1.keyPressed(e));
		scene.setOnKeyReleased(e -> player1.keyReleased(e));

        // Events to happen when startBtn is pressed
		startBtn.setOnMouseClicked(e -> {
			info[7] = 1;
			titleText.setVisible(false);
			startBtn.setVisible(false);		
			ball.start = true;
            root.getChildren().removeAll(startPane, ball.returnNode());
			root.getChildren().addAll(border, ball.returnNode(), player1.returnNode());
		});

        /* DEBUG: Place ball on mouse click
        scene.setOnMouseClicked(c -> {
            ball.returnNode().setCenterX(c.getX());
            ball.returnNode().setCenterY(c.getY());
            ball.dir = 2;
        });
        */

        // Place border which holds text points on right place
		border.setCenter(txtPoints);
		border.setLayoutX(225);

        // Fix up the points text
		txtPoints.setStyle("-fx-font-size: 300px");
		txtPoints.setFill(Color.WHITE);
		txtPoints.setStroke(Color.BLACK);

        // Initiate itStarted() method
		itStarted();

		primaryStage.setResizable(false);
		primaryStage.setTitle("Pong");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void itStarted(){
		// Fix up title text and place
        titleText.setStyle("-fx-font-size: 80px; -fx-font-weight: bold;");
		titleText.setX((info[0] / 2) - 100);

        // Fix up start button and place
		startBtn.setLayoutX((info[0] / 2) - 55);
		startBtn.setStyle("-fx-background-color: linear-gradient(#f2f2f2, #d6d6d6), "
                + "linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%), linear-gradient(#dddddd 0%, #f6f6f6 50%); "
				+ "-fx-background-radius: 8,7,6;-fx-background-insets: 0,1,2;-fx-text-fill: black; "
				+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); -fx-font-size: 30px");

        // Set margin between nodes on start menu
		VBox.setMargin(startBtn, new Insets(50, 0, 50, 0));

        // Place pane in middle of screen
        startPane.setLayoutX(info[0] / 3);

		startPane.getChildren().addAll(titleText, startBtn);
		startPane.setAlignment(Pos.CENTER);
		
		root.getChildren().addAll(ball.returnNode(), startPane);
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

	// main class launches JavaFX
	public static void main(String[] args){
		launch(args);
	}
}


