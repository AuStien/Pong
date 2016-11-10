
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Ball{
	
	Circle ball = new Circle();
	Text txtPoints;
	BorderPane border;
	
	boolean ended = false, start = false;
	double[] info;
	double ballSpeed = 2; 
	int points = 0, dir, radius = 20, maxSpeed = 10;

	// Constructor of class Ball
	public Ball(double[] info, Text txtPoints, BorderPane border){
		this.info = info;
		this.txtPoints = txtPoints;
		this.border = border;

        // Sets random direction of ball
		dir = ((int)(Math.random() * 4));
		
		// Set radius
		ball.setRadius(radius);
        
		// Set coordinates to something random within scene
		ball.setCenterX((int)((Math.random() * (info[0] - (radius*2))) + radius));
		ball.setCenterY((int)((Math.random() * (info[1] - (radius*2))) + radius));
	}
	
	public void frame(){
		// If game starts set start to false and dir = 0
		if (start){
			dir = 0;
			start = false;
		}
		
		// Ball moves south east
		if (dir == 0){
            // If ball touches right edge, bounce
			if (ball.getCenterX() + radius - 10 >= info[0]){
				dir = 3;
			}
            // If ball touches bottom edge, bounce
			if (ball.getCenterY() + radius - 10 >= info[1]){
				dir = 1;
			}
			ball.setCenterX(ball.getCenterX() + ballSpeed);
			ball.setCenterY(ball.getCenterY() + ballSpeed);
		}
		
		// Ball moves north east
		if (dir == 1){
            // If ball touches right edge, bounce
			if (ball.getCenterX() + radius - 10 >= info[0]){
				dir = 2;
			}
            // If ball touches top edge, bounce
			if (ball.getCenterY() <= radius){
				dir = 0;
			}
			ball.setCenterX(ball.getCenterX() + ballSpeed);
			ball.setCenterY(ball.getCenterY() - ballSpeed);
		}
		
		// Ball moves north west
		if (dir == 2){
            // If ball touches left edge, lose, unless game has not started
			if (ball.getCenterX() <= radius){
				// If ball hits left edge and game has started, end game. If not, bounce
				if (info[7] == 0){
					dir = 1;
				}else{
					ended = true;
				}
			}
            // If ball touches top edge, bounce
			if (ball.getCenterY() <= radius){
				dir = 3;
			}
			// If ball touches within rectangle bounce, increase speed and add points
			if (ball.getCenterY() + radius >= info[6] && ball.getCenterY() - radius <= info[6] + info[3]
					&& ball.getCenterX() - radius <= info[5] + info[2] && ball.getCenterX() - radius >= info[5]
                    && info[7] != 0){
                // Set direction of ball to north east
				dir = 1;
				// If ballSpeed is less than maxSpeed, increment by 1
				ballSpeed += ballSpeed < maxSpeed ? 1 : 0;
				// Increment points by 1
				points++;
				// Update txtPoints
				txtPoints.setText("" + points);
				// If points = 10, adjust border to fit screen
				if (points == 10){
					border.setLayoutX(150);
				}
				// If points = 100, adjust border to fit screen
				if (points == 100){
					border.setLayoutX(60);
				}
				System.out.println("Points: " + points + "\nBallSpeed: " + ballSpeed);
			}
			ball.setCenterX(ball.getCenterX() - ballSpeed);
			ball.setCenterY(ball.getCenterY() - ballSpeed);
		}
		
		// Ball moves south west
		if (dir == 3){
            // If ball touches left edge, lose, unless games has not started
			if (ball.getCenterX() <= radius){
				// If ball hits left edge and game has started, end game. If not, bounce
				if (info[7] == 0){
					dir = 0;
				}else{
					ended = true;
				}
			}
            // If ball touches bottom edge, bounce
			if (ball.getCenterY() + radius - 10 >= info[1]){
				dir = 2;
			}
            // If ball touches within rectangle bounce, increase speed and add points
			if (ball.getCenterY() + radius >= info[6] && ball.getCenterY() - radius <= info[6] + info[3]
					&& ball.getCenterX() - radius <= info[5] + info[2] && ball.getCenterX() - radius >= info[5]
                    && info[7] != 0){
                // Set direction of ball to south east
				dir = 0;
				// If ballSpeed is less than maxSpeed, increment by 1
				ballSpeed += ballSpeed < maxSpeed ? 1 : 0;
				// Increment points by 1
				points++;
				// Update txtPoints
				txtPoints.setText("" + points);
				// If points = 10, adjust border to fit screen
				if (points == 10){
					border.setLayoutX(150);
				}
				// If points = 100, adjust border to fit screen
				if (points == 100){
					border.setLayoutX(60);
				}
				System.out.println("Points: " + points + "\nBallSpeed: " + ballSpeed);
			}
			ball.setCenterX(ball.getCenterX() - ballSpeed);
			ball.setCenterY(ball.getCenterY() + ballSpeed);
		}
	}
	
	// Returns Circle object
	public Circle returnNode(){
		return ball;
	}
	
	// Return check if ended
	public boolean ended(){
		return ended;
	}
}
