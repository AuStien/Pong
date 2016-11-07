import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

public class Player{
	Rectangle player = new Rectangle();
	
	KeyCode moveUp;
	KeyCode moveDown;
	
	boolean up = false, down = false;
	
	double[] info;
	
	public Player(int p, double[] info){
		// Set width and height of player to that in array
        player.setWidth(info[2]);
		player.setHeight(info[3]);

        // Add curves to player
        player.setArcHeight(10);
        player.setArcWidth(10);

		this.info = info;

        // Set variables after which player it is
		if (p == 1){
			player.setX(info[5]);
			player.setY(info[6]);
			moveUp = KeyCode.W;
			moveDown = KeyCode.S;
		}
		
		if (p == 2){
			player.setX(info[0] - 30);
			player.setY(30);
			info[0] = info[0] - 30;
			info[1] = info[1] - 30;
			
			moveUp = KeyCode.UP;
			moveDown = KeyCode.DOWN;
		}
	}
	
	public void keyPressed(KeyEvent e){
		if (e.getCode() == moveUp){
			up = true;
		}
		if (e.getCode() == moveDown){
			down = true;
		}
	}
	
	public void keyReleased(KeyEvent e){
		if (e.getCode() == moveUp){
			up = false;
		}
		if (e.getCode() == moveDown){
			down = false;
		}
	}
	
	public void frame(){
        // If up is pressed and does not hit top, move up
		if (up && info[6] >= 0){
			player.setY(info[6] - info[4]);
			info[6] = info[6] - info[4];
		}

        // If up is pressed and does not hit top, move up
		if (down && player.getY() <= info[1] - (info[3] / 2)){
			player.setY(info[6] + info[4]);
			info[6] = info[6] + info[4];
		}
		
		
	}
	
	public Rectangle returnNode(){ return player; }
}
