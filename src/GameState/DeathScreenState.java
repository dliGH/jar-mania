package GameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import TileMap.Background;

//something if player dies
public class DeathScreenState extends GameState{

	private Background bg;
	
	public DeathScreenState(GameStateManager gsm) {
		this.gsm = gsm;
		try {
			//sets background
			bg = new Background("/backgrounds/death.gif", 1);
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
		if(k == KeyEvent.VK_ENTER || k == KeyEvent.VK_ESCAPE) {
			gsm.setState(GameStateManager.INTERMEDIATESTATE);
		}
	}
	public void keyReleased(int k) {}
	
}

