
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Ball{
	
	Circle ball = new Circle();
	boolean ended = false, start = false;
	double[] info;
	Text txtPoints;
	BorderPane border;
	int points = 0;
	int dir;
	int radius = 20;
	double ballSpeed = 2; 
	
	public Ball(double[] info, Text txtPoints, BorderPane border){
		this.info = info;
		this.txtPoints = txtPoints;
		this.border = border;

        // Sets random direction of ball
		dir = ((int)(Math.random() * 4));
		
		ball.setRadius(radius);
        // Set coordinates to something random within scene
		ball.setCenterX((int)((Math.random() * (info[0] - (radius*2))) + radius));
		ball.setCenterY((int)((Math.random() * (info[1] - (radius*2))) + radius));
	}
	
	public void frame(){

		if (start){
			dir = 0;
			start = false;
		}
		
		if (dir == 0){
            // If ball touches left edge, bounce
			if (ball.getCenterX() - radius >= info[0]){
				dir = 3;
			}
            // If ball touches bottom edge, bounce
			if (ball.getCenterY() - radius >= info[1]){
				dir = 1;
			}
			ball.setCenterX(ball.getCenterX() + ballSpeed);
			ball.setCenterY(ball.getCenterY() + ballSpeed);
		}
		if (dir == 1){
            // If ball touches right edge, bounce
			if (ball.getCenterX() - radius >= info[0]){
				dir = 2;
			}
            // If ball touches top edge, bounce
			if (ball.getCenterY() <= radius){
				dir = 0;
			}
			ball.setCenterX(ball.getCenterX() + ballSpeed);
			ball.setCenterY(ball.getCenterY() - ballSpeed);
		}
		if (dir == 2){
            // If ball touches left edge, lose, unless game has not started
			if (ball.getCenterX() <= radius){
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
				dir = 1;
				ballSpeed += ballSpeed < 10 ? 1 : 0;
				info[4] += ballSpeed < 10 ? 1 : 0;
				points++;
				txtPoints.setText("" + points);
				if (points == 10){
					border.setLayoutX(150);
				}
				System.out.println("Points: " + points + "\nBallSpeed: " + ballSpeed);
			}
			ball.setCenterX(ball.getCenterX() - ballSpeed);
			ball.setCenterY(ball.getCenterY() - ballSpeed);
		}
		if (dir == 3){
            // If ball touches left edge, lose, unless games has not started
			if (ball.getCenterX() <= radius){
				if (info[7] == 0){
					dir = 0;	
				}else{
					ended = true;
				}
			}
            // If ball touches right edge, bounce
			if (ball.getCenterY() - radius >= info[1]){
				dir = 2;
			}
            // If ball touches within rectangle bounce, increase speed and add points
			if (ball.getCenterY() + radius >= info[6] && ball.getCenterY() - radius <= info[6] + info[3]
					&& ball.getCenterX() - radius <= info[5] + info[2] && ball.getCenterX() - radius >= info[5]
                    && info[7] != 0){
				dir = 0;
				ballSpeed += ballSpeed < 10 ? 1 : 0;
				info[4] += ballSpeed < 10 ? 1 : 0;
				points++;
				txtPoints.setText("" + points);
				if (points == 10){
					border.setLayoutX(150);
				}
				System.out.println("Points: " + points + "\nBallSpeed: " + ballSpeed);
			}
			ball.setCenterX(ball.getCenterX() - ballSpeed);
			ball.setCenterY(ball.getCenterY() + ballSpeed);
		}
	}
	
	public Circle returnNode(){
		return ball;
	}
	
	public boolean ended(){
		return ended;
	}
}
