package pong;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Paddle extends Sprite {
	protected int dy;
	protected int dy2;
	protected String paddle = "newpaddle.png";
	
	public Paddle(){
		ImageIcon paddleImg = new ImageIcon(this.getClass().getResource(paddle));
		image = paddleImg.getImage();
		
		width = image.getWidth(null);
		height = image.getHeight(null);
		
		resetLocation();
	}
	
	public void moveLeft(){
		y += dy;
	
		if (y <= 2){
			y = 2;
		}
		
		if ( y >= Constants.PADDLE_TOP){
			y = Constants.PADDLE_TOP;
		}
	}
	
	public void moveRight(){
		y += dy2;
		
		if (y <= 2){
			y = 2;
		}
		
		if ( y >= Constants.PADDLE_TOP){
			y = Constants.PADDLE_TOP;
		}
	}
	
	public void moveUp(){
		y -= 2.5;
	}
	
	public void moveDown(){
		y += 2.5;
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_UP:
			dy = Math.min(-5, dy);
			dy -= .5;
			break;
		case KeyEvent.VK_DOWN:
			dy = Math.max(5, dy);
			dy += .5;
			break;
		case KeyEvent.VK_W:
			dy2 = Math.min(-5, dy2);
			dy2 -= .5;
			break;
		case KeyEvent.VK_S:
			dy2 = Math.max(5, dy2);
			dy2 += .5;
			break;
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP) dy = 0;
		
		if(key == KeyEvent.VK_DOWN) dy = 0;
		
		if(key == KeyEvent.VK_W) dy2 = 0;
		
		if(key == KeyEvent.VK_S) dy2 = 0;
		
	}
	
	public void resetLocation(){
		x = 50;
		y = 400;
		
	}
	
	public int getLPadVel(){
		if(dy > 0){
			return dy;
		}else{
			return 1;
		}
	}
	
	public int getRPadVel(){
		if(dy > 0){
			return dy;
		}else{
			return 1;
		}
	}
}
