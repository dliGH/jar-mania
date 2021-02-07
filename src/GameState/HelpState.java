package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import TileMap.Background;

public class HelpState extends GameState{
	
	private Background bg;
	
	public HelpState(GameStateManager gsm) {
		this.gsm = gsm;
		try {
			//sets background
			bg = new Background("/backgrounds/help screen.gif", 1);
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
	}
		
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
		if(k == KeyEvent.VK_ESCAPE) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}
	public void keyReleased(int k) {}
	
}
