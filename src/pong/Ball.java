package pong;

import javax.swing.ImageIcon;

public class Ball extends Sprite {
	
	protected double xDir = 2;
	protected double yDir = -2;
	protected String ball = "newball.png";
	
	public Ball(){
		ImageIcon ballImg = new ImageIcon(this.getClass().getResource(ball));
		image = ballImg.getImage();
		
		width = image.getWidth(null);
		height = image.getHeight(null);
		
		resetLocation();
	}
	
	public void move(){
	    x += xDir;
	    y += yDir;
	    
	    if(y < 10 || y > Constants.BALL_BOTTOM){
	    	setYDir(-yDir);
	    	}
	    }
	
	public void resetLocation(){
		x = 400;
		y = 600;
	}
	
	public void setXDir(double d){
		xDir = d;
	}
	
	public void setYDir(double y){
		yDir = y;
	}
	
	public double getYDir(){
		return yDir;
	}
	
	public double getXDir(){
		return xDir;
	}

}
