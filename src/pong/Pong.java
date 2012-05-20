package pong;

import javax.swing.JFrame;

/* ============================================================ *
 *  Created by: Ben Vest | May 13, 2012                         *
 *                                                              *
 * Purpose: Sets up the parameters for the panel which displays * 
 * the game. Also includes the main method.                     * 
 * ============================================================ */

public class Pong extends JFrame {

    public Pong(){
        add(new Board());
        setTitle("Breakout");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Constants.WIDTH, Constants.HEIGHT);
        setLocationRelativeTo(null);
        setIgnoreRepaint(true);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Pong();
    }
}