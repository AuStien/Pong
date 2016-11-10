
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Pong extends Application{
    /*
     * 4 player (one up, one down OR/AND one on each edge)
     * Settings
     *      Key Assignment?
     * Score on each players side
     */
	//TODO Save high score
	
	// {0sceneWidth, 1sceneHeight, 2rectWidth, 3rectHeight, 4playerSpeed, 5player1X, 6player1Y, 7ballState}
	double[] info = {600, 400, 10, 100, 8, 30, 30, 0};
    //boolean hasStarted = false; currently not used
    boolean hasEnded = false;
    String btnStyle = "-fx-background-color: linear-gradient(#f2f2f2, #d6d6d6), "
                + "linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%), linear-gradient(#dddddd 0%, #f6f6f6 50%); "
				+ "-fx-background-radius: 8,7,6;-fx-background-insets: 0,1,2;-fx-text-fill: black; "
				+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); -fx-font-size: 30px";
    
    // Declare Panes
	Group root = new Group();
	VBox startPane = new VBox();
	BorderPane border = new BorderPane();
	GridPane setPane = new GridPane();
	
	// Declare TextFields
	TextField radiusFld = new TextField("Radius of ball");
	TextField startBallSpeedFld = new TextField("Starting Speed of Ball");
	TextField maxBallSpeedFld = new TextField("Max speed of ball");
	TextField colorFld = new TextField("Color of objects");
	TextField playerSpeedFld = new TextField("Speed of player");
	TextField playerHeightFld = new TextField("Height of player");
	
	// Declare Text
	Text txtPoints = new Text();
	Text endText = new Text();
	Text titleText = new Text("PONG");
	Text setText = new Text("SETTINGS");
	
	// Declare Buttons
	Button startBtn = new Button("1 Player");
	Button setBtn = new Button("Settings");
	Button exitBtn = new Button("Exit");

	// Declare Scene
	Scene scene = new Scene(root, info[0], info[1]);

	// Declare players and ball
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

		// Style and place title in Settings menu
		setText.setStyle("-fx-font-size: 80px; -fx-font-weight: bold;");
		setText.setLayoutX((info[0] / 3) - 70);
		setText.setLayoutY(80);
		
		// Style buttons
		setBtn.setStyle(btnStyle);
		exitBtn.setStyle(btnStyle);
		
		// Set max width of all TextFields
		radiusFld.setMaxWidth(150);
		maxBallSpeedFld.setMaxWidth(150);
		startBallSpeedFld.setMaxWidth(150);
		colorFld.setMaxWidth(150);
		playerSpeedFld.setMaxWidth(150);
		playerHeightFld.setMaxWidth(150);

		// Fix and place Settings BorderPane
		setPane.setLayoutX((info[0] / 3) - 70);
		setPane.setLayoutY(120);
		setPane.setAlignment(Pos.CENTER);
		setPane.setVgap(20);
		setPane.setHgap(20);
		
		// Set all objects in Settings menu invisible
		setPane.setVisible(false);
		setText.setVisible(false);
		radiusFld.setVisible(false);
		maxBallSpeedFld.setVisible(false);
		startBallSpeedFld.setVisible(false);
		colorFld.setVisible(false);
		playerSpeedFld.setVisible(false);
		playerHeightFld.setVisible(false);
		exitBtn.setVisible(false);
		
		// Place border which holds text points on right place
		border.setCenter(txtPoints);
		border.setLayoutX(225);

		// Fix up the points text
		txtPoints.setStyle("-fx-font-size: 300px");
		txtPoints.setFill(Color.WHITE);
		txtPoints.setStroke(Color.BLACK);
		
        // Events to happen when startBtn is pressed
		startBtn.setOnMouseClicked(e -> {
			// Set ballState to 1, game has started
			info[7] = 1;
			// Set titleText and startButton invisible
			titleText.setVisible(false);
			startBtn.setVisible(false);
			// Set ballState to start
			ball.start = true;
			// Remove and add nodes to root
            root.getChildren().removeAll(startPane, ball.returnNode());
			root.getChildren().addAll(border, ball.returnNode(), player1.returnNode());
		});
		
		// Events to happen when setBtn is pressed
		setBtn.setOnMouseClicked(e -> {
			settings();
		});

		/*
        // DEBUG: Place ball on mouse click
        scene.setOnMouseClicked(c -> {
            ball.returnNode().setCenterX(c.getX());
            ball.returnNode().setCenterY(c.getY());
            ball.dir = 3;
        });
        */
		
		// Events to happen when radiusFld is changed and Enter is pressed
		radiusFld.setOnAction(e -> {
			// Try to set radius of ball = value of TextField
			try{
				// Get int from TextField
				int radius = Integer.parseInt(radiusFld.getCharacters().toString());
				// If value is bigger than 0 and smaller than half the height, set radius = value
				if (radius > 0 && radius < info[1]/2){
					ball.radius = (radius);
					ball.returnNode().setRadius(radius);
					System.out.println("Radius " + ball.radius);
				}else{
					System.out.println("Radius out of bounds");
				}
			}catch(Exception ex){
				// Print exception message
				System.out.println(ex.getMessage());
			}
		});
		
		// Events to happen when maxBallSpeedFld is changed and Enter is pressed
		maxBallSpeedFld.setOnAction(e -> {
			// Try to set max speed of ball = value of TextField
			try{
				// Get int from TextField
				int speed = Integer.parseInt(maxBallSpeedFld.getCharacters().toString());
				// If value is bigger than 0, set max speed = value
				if (speed > 0){
					ball.maxSpeed = speed;
					System.out.println("Ball Max Speed " + ball.maxSpeed);
				}else{
					System.out.println("Ball Speed put of bounds");
				}
			}catch(Exception ex){
				// Print exception message
				System.out.println(ex.getMessage());
			}
		});
		
		// Events to happen when startBallSpeedFld is changed and Enter is pressed
		startBallSpeedFld.setOnAction(e -> {
			// Try to set start speed of ball = value of TextField
			try{
				// Get int from TextField
				int speed = Integer.parseInt(startBallSpeedFld.getCharacters().toString());
				// If value is bigger than 0, set start speed = value
				if (speed > 0){
					ball.ballSpeed = speed;
					System.out.println("Ball Starting Speed " + ball.ballSpeed);
				}else{
					System.out.println("Ball Speed put of bounds");
				}
			}catch(Exception ex){
				// Print exception message
				System.out.println(ex.getMessage());
			}
		});
		
		// Events to happen when colorFld is changed and Enter is pressed
		colorFld.setOnAction(e -> {
			// Try to set color of objects = value of TextField
			try{
				// Get color from TextField
				Color color = Color.valueOf(colorFld.getCharacters().toString());
				// Set color of objects to value
				ball.returnNode().setFill(color);
				player1.returnNode().setFill(color);
				System.out.println("Color set to " + colorFld.getCharacters().toString());
			}catch(Exception ex){
				// Print exception message
				System.out.println(ex.getMessage());
			}
			
			
		});
		
		// Events to happen when playerSpeedFld is changed and Enter is pressed
		playerSpeedFld.setOnAction(e -> {
			// Try to set speed of player = value of TextField
			try{
				// Get int from TextField
				int speed = Integer.parseInt(playerSpeedFld.getCharacters().toString());
				// If value is bigger than 0, set speed of player = value
				if (speed > 0){
					info[4] = speed;
					System.out.println("Player Speed " + info[4]);
				}else{
					System.out.println("Player Speed out of bounds");
				}
			}catch(Exception ex){
				// Print exception message
				System.out.println(ex.getMessage());
			}
		});
		
		// Events to happen when playerHeightFld is changed and Enter is pressed
		playerHeightFld.setOnAction(e -> {
			// Try to set height of player= value of TextField
			try{
				// Get int from TextField
				int height = Integer.parseInt(playerHeightFld.getCharacters().toString());
				// If value is bigger than 0 and smaller than height of scene, set player height = value
				if (height > 0 && height < info[1]){
					player1.returnNode().setHeight(height);
					info[3] = height;
					System.out.println("Player Hight " + info[3]);
				}else{
					System.out.println("Height out of bounds");
				}
			}catch(Exception ex){
				// Print exception message
				System.out.println(ex.getMessage());
			}
		});
		
		// Events to happen when exitBtn is pressed
		exitBtn.setOnMouseClicked(e -> {
			// Set nodes in Settings menu invisible and Main Menu nodes visible
			radiusFld.setVisible(false);
			maxBallSpeedFld.setVisible(false);
			startBallSpeedFld.setVisible(false);
			colorFld.setVisible(false);
			playerSpeedFld.setVisible(false);
			playerHeightFld.setVisible(false);
			exitBtn.setVisible(false);
			setPane.setVisible(false);
			setText.setVisible(false);
			titleText.setVisible(true);
			startBtn.setVisible(true);
			setBtn.setVisible(true);
		});       

        

        // Initiate itStarted() method
		itStarted();

		// Add nodes to Settings pane and place in grid
		setPane.getChildren().addAll(radiusFld, maxBallSpeedFld, startBallSpeedFld, 
				colorFld,  playerSpeedFld, playerHeightFld, exitBtn);
		GridPane.setConstraints(radiusFld, 0, 0);
		GridPane.setConstraints(maxBallSpeedFld, 0, 1);
		GridPane.setConstraints(startBallSpeedFld, 0, 2);
		GridPane.setConstraints(colorFld, 1, 2);
		GridPane.setConstraints(playerSpeedFld, 1, 0);
		GridPane.setConstraints(playerHeightFld, 1, 1);
		GridPane.setConstraints(exitBtn, 1, 3);
		
		
		root.getChildren().addAll(setPane, setText);
		
		primaryStage.setResizable(false);
		primaryStage.setTitle("Pong");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void itStarted(){
		// Fix up title text and place
        titleText.setStyle("-fx-font-size: 80px; -fx-font-weight: bold;");

        // Fix up start button
		startBtn.setStyle(btnStyle);

		// Fix up settings button
		setBtn.setStyle(btnStyle);
		
        // Set margin between nodes on start menu
		VBox.setMargin(startBtn, new Insets(50, 0, 50, 0));

        // Place pane in middle of screen
        startPane.setLayoutX(info[0] / 3);

        // Set alignment of Main Menu pane to center
		startPane.setAlignment(Pos.CENTER);
		
		// Add nodes to panes
		startPane.getChildren().addAll(titleText, startBtn, setBtn);
		root.getChildren().addAll(ball.returnNode(), startPane);
	}
	
	// Method to see Settings menu
	public void settings(){
		// Set nodes in Settings menu visible and Main Menu nodes invisible
		setPane.setVisible(true);
		radiusFld.setVisible(true);
		maxBallSpeedFld.setVisible(true);
		startBallSpeedFld.setVisible(true);
		colorFld.setVisible(true);
		playerSpeedFld.setVisible(true);
		playerHeightFld.setVisible(true);
		setText.setVisible(true);
		exitBtn.setVisible(true);
		titleText.setVisible(false);
		startBtn.setVisible(false);
		setBtn.setVisible(false);
	}
	
	public void itEnded(){
		// Stop animation
		timeline.stop();
		
		// Show score and place
		endText.setText("You got " + ball.points + " points!");
		endText.setStyle("-fx-font-size: 30px");
		endText.setX((info[0] / 2) - 100);
		endText.setY(info[1] / 2);
		
		// Make objects invisible
		ball.returnNode().setVisible(false);
		player1.returnNode().setVisible(false);
		txtPoints.setVisible(false);
		
		// Add endText to root
		root.getChildren().add(endText);
	}

	// main class launches JavaFX
	public static void main(String[] args){ 
		launch(args); 
	}
}