package GameState;

import java.awt.Graphics2D;

//making abstract bc other classes will be extending off of to create levels and menu and whatnot
public abstract class GameState {
	protected GameStateManager gsm;
	public abstract void init();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
}
