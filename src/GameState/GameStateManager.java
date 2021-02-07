package GameState;

import java.util.*;
import java.awt.*;

//this class determines switches between levels considered "states" in the game
//helps organize functions
public class GameStateManager {

	private ArrayList<GameState> gameStates;
	private int currentState;
	//just knows states will always be index value
	//enumerated just to determine for later usage
	public static final int MENUSTATE = 0;
	public static final int HELPSTATE = 1;
	public static final int INTERMEDIATESTATE = 2;	
	public static final int DEADSTATE = 3;		
	public static final int TUTORIALSTATE = 4;
	public static final int INFINITESTATE = 5;	
	public static final int LEVEL1STATE = 6;
	
	public GameStateManager() {
		//creating place to store the levels.
		gameStates = new ArrayList<GameState>();
		//starts at menu
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this));
		gameStates.add(new HelpState(this));
		gameStates.add(new IntermediateState(this));
		gameStates.add(new DeathScreenState(this));
		gameStates.add(new TutorialState(this));
		gameStates.add(new InfiniteState(this));	
		gameStates.add(new LevelOneState(this));
	}
	
	public void setState(int state) {
		currentState = state;
		gameStates.get(currentState).init();
	}
	
	public void update() {
		gameStates.get(currentState).update();
	}
	
	public void draw(Graphics2D g) {
		gameStates.get(currentState).draw(g);
	}
	
	public void keyPressed(int k) {
		gameStates.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates.get(currentState).keyReleased(k);
	}
	
}
