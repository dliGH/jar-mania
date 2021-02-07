package entities.enemies;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

import entities.Animation;

//little death animation
public class Explosion {
	
	//location on screen and on map
	//need to keep track of two conditions
	private int x;
	private int y;
	private int xmap;
	private int ymap;
	
	private int width;
	private int height;
	
	private Animation animation;
	private BufferedImage[] sprites;
	
	private boolean remove;
	
	//constructor, takes in location parameter
	public Explosion (int x,int y) {
	
		this.x = x;
		this.y = y;
		
		width = 30;
		height = 30;
		
		//load sprites
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/enemySprites/explosion.gif"));
			sprites = new BufferedImage[6];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		 
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(70);
	}
	
	//one type of animation that only runs once
	public void update() {
		animation.update();
		if(animation.hasPlayedOnce()) {
			remove = true;
		}
	}
	
	public boolean shouldRemove() {return remove;}
	
	//determine location
	public void setMapPosition(int x, int y) {
		xmap = x;
		ymap = y;
	}
	
	//draw!
	public void draw(Graphics2D g) {
		g.drawImage(animation.getImage(), x + xmap - width/2, y + ymap - height/2, null);
	}
}
