package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import TileMap.Background;

public class IntermediateState extends GameState{
	
	private Background bg;
		
	//options in menu
	private int currentChoice = 0;
	private String[] options = {"Menu","Tutorial","Infinite Mode","Campaign"};
	
	//fonts and colors and stuff for menu screen
	private Font optionFont;
	
	public IntermediateState(GameStateManager gsm) {
		this.gsm = gsm;
		try {
			//sets background
			bg = new Background("/backgrounds/intermediate menu bg.gif", 1);
			//doing cool cosmetic stuff
			optionFont = new Font ("Algerian", Font.PLAIN, 30);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	//overriden methods
	public void init() {}
	public void update() {
		bg.update();
	}
	public void draw(Graphics2D g) {
		//drawing background.
		bg.draw(g);
		
		//draw menu options
		g.setFont(optionFont);
		for(int i = 0; i<options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.YELLOW);
			}else {
				g.setColor(Color.WHITE);
			}
			g.drawString(options[i], 20,  50 + i * 40);
		}
	}

	private void select() {
		//start button
		if(currentChoice == 0) {
			//sends them back to menu
			gsm.setState(GameStateManager.MENUSTATE);
		}
		//tutorial button
		if(currentChoice == 1) {
			gsm.setState(GameStateManager.TUTORIALSTATE);
		}
		//nifinite mode
		if(currentChoice == 2) {
			gsm.setState(GameStateManager.INFINITESTATE);
		}
		//level one
		if(currentChoice == 3) {
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
	}
	
	//method used to traverse buttons in menu
	//using up down and enter buttons
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) {
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length-1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
	public void keyReleased(int k) {}
}

