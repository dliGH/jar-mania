package entities;

import java.awt.image.BufferedImage;

//lets frames update and look cool
//each class that appears as an entity will have an 'animation' associated with it
//allows the game to have the flowing function
public class Animation {
	
	//array to hold all frames
	private BufferedImage[] frames;
	//keep track of current frame
	private int currentFrame;
	
	//these are just some time mechanisms that will be used for the "flowy" aspect of things
	private long startTime;
	private long delay;
	
	//knowing if the animation has occured
	//mainly for attack animation
	private boolean playedOnce;
	
	//initializing variable
	public Animation() {
		playedOnce = false;
	}
	
	//allows us to load in sprites from the sprite sheet for each individual entity
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	
	//modifier methods
	public void setDelay(long d) {delay = d;}
	//allows manual setting of frame
	public void setFrame(int i) {currentFrame = i;}
	
	//changes the frames thru the thing
	public void update() {
		//no animation going on
		if(delay == -1) {
			return;
		}
		//converting into milliseconds
		long elapsed = (System.nanoTime() - startTime)/ 1000000;
		//frequency in updating frames
		if(elapsed>delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		//making sure frames doesn't go past bounds
		//don't want oob exception here
		if(currentFrame == frames.length) {
			currentFrame = 0;
			playedOnce = true;
		}
	}
	
	//accessor methods
	public int getFrame() { return currentFrame; } 
	public BufferedImage getImage() { return frames[currentFrame]; } 
	public boolean hasPlayedOnce() { return playedOnce; }

}

