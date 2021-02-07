package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import GameState.GameStateManager;
import java.awt.event.KeyEvent;
import java.awt.event.*;

public class GamePanel extends JPanel implements Runnable, KeyListener{

	//creating dimensions
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	
	//threads that the gameloop will run on
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000/FPS;
	
	//images
	private BufferedImage image;
	private Graphics2D g;
	
	//game state manager (allows switching between menu and levels)
	private GameStateManager gsm;
	
	public GamePanel() {
		//modifying jpanel properties so that it shows in the frame
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.setFocusable(true);
		this.requestFocus();
	}
	
	public void addNotify() {
		//let's computer know that game is running
		//will start the threads (inbuilt java function to prioritize computing)
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			//can do this bc we implemented keylistener
			this.addKeyListener(this);
			thread.start();
		}
	}
	
	//initializing method
	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		gsm = new GameStateManager(); 
	}
	
	public void run() {
		
		init();
		
		//timer variables
		long start;
		long elapsed;
		long wait;
		
		//game loop starts in here
		while(running) {
			
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed/1000000;
			//was pulling exception so here's something
			if(wait<0) {
				wait = 5;
			}		
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	//goes from this class to GSM for orgainization
	private void update() {
		gsm.update();
	}
	private void draw() {
		gsm.draw(g);
	}
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		//panel wasn't full screen but fixed it here
		g2.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		g2.dispose();
	}
	
	//Overridden methods for the keyListener
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}
	public void keyTyped(KeyEvent e) {}
}
