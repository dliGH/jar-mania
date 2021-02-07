package entities.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import TileMap.TileMap;
import entities.Animation;
import entities.Enemy;

//small jar, what game was originally based off of
//try to add sound effect that is ultra satisfying when hitting it
public class Jar extends Enemy{

	private BufferedImage[] sprites;
	
	//constructor
	public Jar(TileMap tm) {
		
		super(tm);
		//setting variables in superclass
		//want it to stay still
		moveSpeed = 0.0;
		maxSpeed = 0.0;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		//size things
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		health = maxHealth = 2;
		damage = 0;
		
		//load sprites
		try {
			//need to load from resources
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/enemySprites/jar.gif"));
			sprites = new BufferedImage[1];
			sprites[0] = spritesheet.getSubimage(0, 0, width, height);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(300);
		
		right = true;
		facingRight = true;
	}

	//technically it doesn't move so we don't really need much in these methods
	
	//still need this anyways bc helps in following methods
	//just doesn't seem to do much bc the vector changes are all 0
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
	
	public void update() {
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp,ytemp);
		animation.update();
	}
	
	public void draw(Graphics2D g) {

		setMapPosition();
		super.draw(g);
		
	}
	
}
