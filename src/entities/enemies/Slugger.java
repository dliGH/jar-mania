package entities.enemies;

import entities.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import TileMap.*;

//little snail enemy guys
public class Slugger extends Enemy{

	private BufferedImage[] sprites;
	
	//constructor
	public Slugger(TileMap tm) {
		super(tm);
		moveSpeed = 0.3;
		maxSpeed = 0.3;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		health = maxHealth = 2;
		damage = 1;
		
		//load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/enemySprites/slugger.gif"));
			sprites = new BufferedImage[3];
			for(int i = 0; i< sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(300);
		
		right = true;
		facingRight = true;
	}
	
	//this guy moves! so we need this 
	private void getNextPosition() {
	
		if(left) {
            dx -= moveSpeed;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        }
        else if(right) {
            dx += moveSpeed;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        }
		
		if(falling) {
			dy += fallSpeed;
		}
	
	}
	
	//updating 
	public void update() {
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		
		//this doesn't seem to work rn 
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer)/1000000;
			if(elapsed > 400) {
				flinching = false;
			}
		}
		
		//hits a wall, go other direction
		if(right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		}else if(left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}
		
		animation.update();
	}
	
	public void draw(Graphics2D g) {
		/*if(notOnScreen()) {
			//return;
		}*/
		setMapPosition();
		super.draw(g);
		
	}
}
