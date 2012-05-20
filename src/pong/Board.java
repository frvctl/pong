package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/* =========================================================== *
 *  Created by: Ben Vest | May 12, 2012                        *
 *                                                             *
 * Purpose: Initializes the game states and draws all of the   *
 * components, also performs collision detection.              *
 * =========================================================== *
 * TODO: - Add levels / increase of ball speed
 *       - Power-Ups!                                          *
 * =========================================================== */
public class Board extends JPanel implements Constants{

    Image ii;
    Timer timer;
    String gameOverMessage = "Game Over";
    String titleMessage = "Pong Game";
    String title2Message = "Created By: Ben Vest";
    Ball ball;
    Ball balls[];
    Paddle paddleRight;
    Paddle paddleLeft;
    
    int leftScore = 0;
    int rightScore = 0;
    
    private static JLabel rightScoreLabel, leftScoreLabel;
    public JButton startButton, settingsButton, exitButton;

    boolean inGame = false;
    boolean gameOver = false;
    boolean inMenu = true;
    int timerId;

    public Board(){
    	setLayout(null);
        addKeyListener(new TAdapter());
        setFocusable(true);
        setDoubleBuffered(true);
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 10);
        
		rightScoreLabel = new JLabel(" 0 ");
		leftScoreLabel = new JLabel(" 0 ");

		rightScoreLabel.setForeground(Color.WHITE);
		leftScoreLabel.setForeground(Color.WHITE);

		rightScoreLabel.setFont(new Font("Serif", Font.PLAIN, 75));
		leftScoreLabel.setFont(new Font("Serif", Font.PLAIN, 75));

		rightScoreLabel.setBounds(550, 25, 250, 100);
		leftScoreLabel.setBounds(150, 25, 250, 100);
		
		startButton = new JButton("Start Game");
		settingsButton = new JButton("Settings");
		exitButton = new JButton("Exit Game");
		
       	startButton.setBounds(300, 400, 200, 50);   	
    	settingsButton.setBounds(300, 500, 200, 50);    	
    	exitButton.setBounds(300, 600, 200, 50);
    	
    	startButton.addActionListener(new ButtonListener());
    	settingsButton.addActionListener(new ButtonListener());
    	exitButton.addActionListener(new ButtonListener());
    }

        public void addNotify(){
            super.addNotify();
            gameInit();
        }
        
    /* ================================================== *
     * Function: keyReleased | Return Type: Void          *
     * ================================================== *
     * Purpose: Initializes the game by instantiating a   *
     * ball object and two paddle objects as well as      *
     * setting the right paddles location and boolean hit *
     * to false                                           *
     * ================================================== */     
    public void gameInit(){
        ball = new Ball();
        paddleRight = new Paddle();
        paddleLeft = new Paddle();
        paddleRight.setX(700);
     }

    /* =================================================== *
     * Function: paint | Return Type: Void                 *
     * =================================================== *
     * Purpose: Paints the every component onto the fame   *
     * every time the timer is fired, is called as         *
     * repaint() within the run() method.                  *
     * =================================================== *
     * Parameters: A Graphics object, arbitrarily declared *
     * in this case it is "g"                              *
     * =================================================== */ 
    public void paint(Graphics g){
        super.paint(g);
        super.setBackground(Color.BLACK);
        
        if(inMenu){
        	
          	Font font = new Font("Verdana", Font.BOLD, 72);
          	Font fontSub = new Font("Verdana", Font.BOLD, 32);
            FontMetrics metr = this.getFontMetrics(font);
            FontMetrics metrSub = this.getFontMetrics(fontSub);

            g.setColor(Color.WHITE);
            g.setFont(font);
            g.drawString(titleMessage, 
            		(Constants.WIDTH - metr.stringWidth(titleMessage)) / 2,
            		200);
            
            g.setColor(Color.WHITE);
            g.setFont(fontSub);
            g.drawString(title2Message, 
            		(Constants.WIDTH - metrSub.stringWidth(title2Message)) / 2,
            		300);
            
            add(startButton);
            add(settingsButton);
            add(exitButton);
            
 
        }else if(inGame){
        	
        	ball.move();
        	
            g.drawImage(ball.getImage(), ball.getX(), ball.getY(),
                        ball.getWidth(), ball.getHeight(), this);
            g.drawImage(paddleLeft.getImage(), paddleLeft.getX(), paddleLeft.getY(),
                        paddleLeft.getWidth(), paddleLeft.getHeight(), this);
            g.drawImage(paddleRight.getImage(), paddleRight.getX(), paddleRight.getY(),
                        paddleRight.getWidth(), paddleRight.getHeight(), this);
            
    		add(rightScoreLabel);
    		add(leftScoreLabel);

    		for (int i = 10; i < 800; i += 100) {
    			g.setColor(Color.WHITE);
    			g.fillRect(400, i, 10, 50);
    		}
    		
    		startButton.setVisible(false);
    		settingsButton.setVisible(false);
    		exitButton.setVisible(false);

        }
        
        if(gameOver){
        	Font font = new Font("Verdana", Font.BOLD, 18);
            FontMetrics metr = this.getFontMetrics(font);

            g.setColor(Color.WHITE);
            g.setFont(font);
            g.drawString(gameOverMessage,
                         (Constants.WIDTH - metr.stringWidth(gameOverMessage)) / 2,
                         Constants.WIDTH / 2);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    
    /* ==================================================== *
     * Class TAdapter | Extends: KeyAdapter                 *
     * ==================================================== *
     * Purpose: Allows user input to occur by corresponding *
     * with the keyListener.                                *
     * ==================================================== */
    private class TAdapter extends KeyAdapter{
        /* ================================================== *
         * Function: keyReleased | Return Type: Void          *
         * ================================================== *
         * Purpose: Calls the keyReleased method within the   *
         * Paddle class which allows user input to take place.*
         * When UP, DOWN, W, or S is pressed the paddles      *
         * respond accordingly                                *
         * ================================================== */ 
		public void keyReleased(KeyEvent e){
				paddleLeft.keyReleased(e);
				paddleRight.keyReleased(e);			
        }
		
        /* ================================================== *
         * Function: keyPressed | Return Type: Void           *
         * ================================================== *
         * Purpose: Sets the paddle speed to 0 when the key   *
         * creating motion is released                        *
         * ================================================== */ 
        public void keyPressed(KeyEvent e){
				paddleLeft.keyPressed(e);
				paddleRight.keyPressed(e);
        }        
    }

    /* =================================================== *
     * Class ScheduleTask | Extends TimerTask              *
     * =================================================== *
     * Purpose: Allows the timer to fire every 10ms        *
     * effectivly creating the main game looping mechanism *
     * =================================================== */
    class ScheduleTask extends TimerTask {
        /* ================================================== *
         * Function: run | Return Type: Void                  *
         * ================================================== *
         * Purpose: The main game loop, calls every move fun  *
         * ction and other functions every time the timer     *
         * fires, in the case of this timer that is every     *
         * 10 milli-seconds.                                  *
         * ================================================== *
         * Return: No return                                  *
         * ================================================== *
         * Parameters: No parameters                          *
         * ================================================== */ 
        public void run() {        	
        	paddleRight.moveRight();
        	paddleLeft.moveLeft();
        	handleAIPaddle();
        	handleCollision();
            handleScore();
            repaint();
        }
    }
    
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == startButton){
				inMenu = false;
				inGame = true;
			}else if(e.getSource() == settingsButton){
				System.out.println("settings");
			}else if(e.getSource() == exitButton){
				System.exit(0);
			}
		}
	}
    
    /* ================================================== *
     * Function: resetBall | Return Type: Void            *
     * ================================================== *
     * Purpose: Resets the ball in a random direction if  *
     * it is called by handleScore. The paddle that corre *
     * sonds with the random direction is then set to     *
     * not being hit, allowing a collision to occur corr  *
     * ectly                                              *
     * ================================================== *
     * Return: No return                                  *
     * ================================================== *
     * Parameters: No parameters                          *
     * ================================================== */ 
    public void resetBall(){
    	ball.setX(400);
    	ball.setY(400);
    	ball.setXDir(2);
    	ball.setYDir(-2);
    }
    
    /* ================================================== *
     * Function: handleScore | Return Type: Void          *
     * ================================================== *
     * Purpose: Allows the score to be kept by checking   *
     * if the ball exceeds the bounds of the game.        *
     * ================================================== *
     * Return: No return                                  *
     * ================================================== *
     * Parameters: No parameters                          *
     * ================================================== */ 
	public void handleScore() {
		if (ball.getX() > Constants.WIDTH+200 
				|| ball.getX() > Constants.WIDTH+200) {
			rightScore ++;
			leftScoreLabel.setText(" " + rightScore);
			resetBall(); // resets the ball
		}
		if (ball.getX() < -10 || ball.getX() < -10) {
			leftScore ++;
			rightScoreLabel.setText(" " + leftScore);
			resetBall(); // resets the ball
		}
	}
	
    /* ================================================== *
     * Function: stopGame | Return Type: Void             *
     * ================================================== *
     * Purpose: Stops the game by canceling the timer and *
     * setting the boolean variable ingame to false       * 
     * ================================================== *
     * Return: No return                                  *
     * ================================================== *
     * Parameters: No parameters                          *
     * ================================================== */ 
    public void stopGame() {
    	inGame = false;
        gameOver = true;
        timer.cancel();
    }
    
    /* ================================================== *
     * Function: checkCollision | Return Type: Void       *
     * ================================================== *
     * Purpose: If detectCollision is called to true      *
     * handleCollision determines all of the various ball *
     * responses to the collision. In pong the ball does  * 
     * not *bounce* off of the paddles in a natural way   *
     * the angle of reflection is determined instead by   *
     * where on the paddle the collision occurs. Thus if  *
     * the ball hits on the top of the paddle the x-direc *
     * tion is reversed and the y-direction is also       *
     * reversed. If the ball hits the middle of the       *
     * paddle the x-direction is reversed and the y-dir   *
     * ection is set to 0.                                *      
     * ================================================== *
     * Return: No return                                  *
     * ================================================== *
     * Parameters: No parameters                          *
     * ================================================== */ 
    public void handleCollision(){
    	
        int paddleRPos = paddleRight.getY();
	    int paddleLPos = paddleLeft.getY();
	    int ballPos = ball.getY();
	    
	    int firstL = paddleLPos + 8;
	    int secondL = paddleLPos + 16;
	    int thirdL = paddleLPos + 24;
	    int fourthL = paddleLPos + 32;
	    
	    int firstR = paddleRPos + 8;
	    int secondR = paddleRPos + 16;
	    int thirdR = paddleRPos + 24;
	    int fourthR = paddleRPos + 32;
	    
	    if(rightScore > 9 || leftScore > 9){
	    	stopGame();
	    }
    	
    	if(detectCollision(ball.getX(), paddleLeft.getX())){
	    	if (ballPos < firstL) {
	    		System.out.println("first");
	    		ball.setXDir(2);
	    		ball.setYDir(-2);
	    	}

	    	if (ballPos >= firstL && ballPos < secondL) {
	    		System.out.println("second");
	    		ball.setXDir(2);
	    		ball.setYDir(-2);    	
	    		}
	    	if (ballPos >= secondL && ballPos < thirdL) {
	    		System.out.println("thid");
	    	    ball.setXDir(2);
	    	    ball.setYDir(0);
	    	}

	    	if (ballPos >= thirdL && ballPos < fourthL) {
	    		System.out.println("fourth");
	    		ball.setXDir(2);
	    		ball.setYDir(2);  
	    	}

	    	if (ballPos > fourthL) {
	    		System.out.println("fifth");
	    		ball.setXDir(2);
	    		ball.setYDir(2);  
	    	}
    	}
    	
    	if(detectCollision(ball.getX(), paddleRight.getX())){
	    	if (ballPos < firstR) {
	    		ball.setXDir(-2);
	    		ball.setYDir(-2);
	    	}

	    	if (ballPos >= firstR && ballPos < secondR) {
	    		ball.setXDir(-2);
	    		ball.setYDir(-2);    	
	    		}
	    	if (ballPos >= secondR && ballPos < thirdR) {
	    		ball.setXDir(-2);
	    	    ball.setYDir(0);
	    	}

	    	if (ballPos >= thirdR && ballPos < fourthR) {
	    		ball.setXDir(-2);
	    		ball.setYDir(2);  
	    	}

	    	if (ballPos > fourthR) {
	    		ball.setXDir(-2);
	    		ball.setYDir(2);  
	    	}
    	}
    }

    /* ================================================== *
     * Function: detectCollision | Return Type: Boolean   *
     * ================================================== *
     * Purpose: Determines if a collision occurs using    *
     * the distance formula, bounds and a boolean to      *
     * assure that double hits do not occur. The class    *
     * accepts two arguments: x1 - being the x-coordinate *
     * of the ball, and x2 - being the x - coordinate     *
     * of the paddle. The function returns true if a      *
     * collision occurs and false if one does not.        *
     * ================================================== *
     * Return: Boolean true if collision occurs and false *
     * if there is not collision                          *
     * ================================================== *
     * Parameters: Two values must be inputed: x1 is the  *
     * balls x - coordinate and x2 is the paddles         *
     * x - coordinate                                     *
     * ================================================== */
	public boolean detectCollision(double x1, double x2){
		double xTerms = Math.pow((x2 - x1), 2);
	    double distance = Math.sqrt(xTerms);

	    if(distance < 10 && ball.getY() > paddleLeft.getY() && 
	        ball.getY() < (paddleLeft.getY() + 40) && 
	        ball.getX() < (Constants.WIDTH/2)){
	    	return true;
	    }
	    if(distance < 5 && ball.getY() > paddleRight.getY() &&
	        ball.getY() < (paddleRight.getY() + 40) && 
	        ball.getX() > (Constants.WIDTH/2)){
	    	return true;
	    }else{
	    	return false;
	    }
	}
	
	public void handleAIPaddle(){
		if(ball.getX() < 400 && ball.getY() < paddleLeft.getY() + 10){
			paddleLeft.moveUp();
		}
		
		if(ball.getX() < 400 && ball.getY() > paddleLeft.getY() + 10){
			paddleLeft.moveDown();
		}
	}
}



