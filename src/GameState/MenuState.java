package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import TileMap.Background;

public class MenuState extends GameState{

	private Background bg;
	
	//options in menu
	private int currentChoice = 0;
	private String[] options = {"Start","Help","Quit"};
	
	//fonts and colors and stuff for menu screen
	private Font optionFont;
	
	public MenuState(GameStateManager gsm) {
		this.gsm = gsm;
		try {
			//sets background
			bg = new Background("/backgrounds/bettermenubg.gif", 1);
			//doing cool cosmetic stuff
			optionFont = new Font ("Algerian", Font.PLAIN, 23);
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
			g.drawString(options[i], 120,  155 + i *25);
		}
	}

	private void select() {
		//start button
		if(currentChoice == 0) {
			//sends them to the first level
			gsm.setState(GameStateManager.INTERMEDIATESTATE);
		}
		//help button
		if(currentChoice == 1) {
			gsm.setState(GameStateManager.HELPSTATE);
		}
		//quit
		if(currentChoice == 2) {
			System.exit(0);
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
