package entities.player;

import TileMap.*;
import entities.Animation;
import entities.Enemy;
import entities.MapObject;
import entities.enemies.Jar;

import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
 
//our player/character
//based on LOZ franchise
public class Link extends MapObject {
     
    // player stuff
    private int health;
    private int maxHealth;
    private boolean dead;
    private boolean flinching;
    private long flinchTimer;
    private int jarsKilled; 
    
    // sword
    private boolean swinging;
    private int swordDamage;
    private int swordRange;
     
    // animations
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = {2, 6, 1, 1, 5};
     
    // animation actions
    //enumerated so they won't ever change
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int SWINGING = 4;
     
    //constructor
    public Link(TileMap tm) {
         
        super(tm);
        
        //assigning properties to player
        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;
         
        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;
        jarsKilled = 0;
        
        facingRight = true;
         
        health = maxHealth = 5;
         
        swordDamage = 8;
        swordRange = 40;
         
        // load sprites
        //populating array with the spritesheet
        try {
             
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/playerSprites/link spritesheet v3.gif"));
             
            sprites = new ArrayList<BufferedImage[]>();
            for(int i = 0; i < 5; i++) {
            	
            	BufferedImage[] bi = new BufferedImage[numFrames[i]];   
               
            	for(int j = 0; j < numFrames[i]; j++) {
                     
                    if(i != SWINGING) {
                        bi[j] = spritesheet.getSubimage(j * width,i * height, width, height);
                    }
                    //attacking animations are two times longer than his normal sprites
                    else { 
                        bi[j] = spritesheet.getSubimage(j * width * 2, i * height, width*2 ,height);
                    }
                     
                }                 
                sprites.add(bi);                 
            }             
        }
        catch(Exception e) {
            e.printStackTrace();
        }
         
        animation = new Animation();
        currentAction = IDLE;
        
        /*debugging
        if(animation == null) {
        	System.out.println("animation is null");
        }else if(sprites == null) {
        	System.out.println("sprties is null");
        }*/
        
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(400);
         
    }
     
    //accesor methods
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public boolean isDead() {return dead;};
    public int getJarsKiller() {return jarsKilled;}
    
    //modifier methods
    public void incrementJars() {jarsKilled++;}
    
    public void setSwinging() {
        swinging = true;
    }
     
    public void checkAttack(ArrayList<Enemy> enemies) {
        	
    	// loop through enemies
        for(int i = 0; i < enemies.size(); i++) {
             
            Enemy e = enemies.get(i);
             
            // if attack hits, then applies damage
            //checking if enemy is within the range of the sword
            if(swinging) {
                if(facingRight) {
                    if(e.getx() > x &&e.getx() < x + swordRange && e.gety() > y - height / 2 &&e.gety() < y + height / 2) {
                        e.hit(swordDamage);
                    }
                }
                else {
                    if(e.getx() < x &&e.getx() > x - swordRange &&e.gety() > y - height / 2 &&e.gety() < y + height / 2) {
                        e.hit(swordDamage);
                    }
                }
            }         
             
            // check if our guy has an enemy collision        
            if(intersects(e)) {
            	hit(e.getDamage());                  
            }    
        }
    }	
 
    public void hit(int damage) {
        if(flinching) {
        	return;
        }
        health -= damage;
        if(health < 0) {
        	health = 0;
        }
        if(health == 0) {
        	dead = true;
        }
        flinching = true;
        flinchTimer = System.nanoTime();
    }
    
    private void getNextPosition() {
    	
        // movement
    	//changing the vector by direction and magnitude depending on input from keylistener
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
        else{
            if(dx > 0) {
                dx -= stopSpeed;
                if(dx < 0) {
                    dx = 0;
                }
            }
            else if(dx < 0) {
                dx += stopSpeed;
                if(dx > 0) {
                    dx = 0;
                }
            }
        }
        
        //no moving if attacking
        //seting speed to 0
        if((currentAction == SWINGING) && !(jumping || falling)) {
            dx = 0;
        }
        
        // jumping
        if(jumping && !falling) {
            dy = jumpStart;
            falling = true; 
        }
         
        // falling
        if(falling) {

            dy += fallSpeed;
           
            if(dy > 0) {
            	jumping = false;
            }
            if(dy < 0 && !jumping) {
            	dy += stopJumpSpeed;
            }
            if(dy > maxFallSpeed) {
            	dy = maxFallSpeed;
            }             
        }
    }
     
    public void update() {
         
        // update position of player
        getNextPosition();
        //checking here if the next x and y will be out of the map
        if(ytemp > 240) {
        	dead = true;
        }
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        
        //checking if attack stopped
        if(currentAction == SWINGING) {
        	if(animation.hasPlayedOnce()) {
        		swinging = false;
        	}
        }
            
        // check done flinching
        //for some reason this works better than the mobs
        if(flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 1000) {
                flinching = false;
            }
        }
        
        // set animation to whatever is going on
        if(swinging) {
            if(currentAction != SWINGING) {
                currentAction = SWINGING;
                animation.setFrames(sprites.get(SWINGING));
                animation.setDelay(80);
                width = 60;
            }
        }
        else if(dy > 0) {
           if(currentAction != FALLING) {
                currentAction = FALLING;
                animation.setFrames(sprites.get(FALLING));
                animation.setDelay(100);
                width = 30;
            }
        }
        else if(dy < 0) {
            if(currentAction != JUMPING) {
                currentAction = JUMPING;
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(-1);
                width = 30;
            }
        }
        else if(left || right) {
            if(currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(90);
                width = 30;
            }
        }
        else {
            if(currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(400);
                width = 30;
            }
        }
         
        animation.update();
         
        // set direction
        if(currentAction != SWINGING) {
            if(right) facingRight = true;
            if(left) facingRight = false;
        }
         
    }
     
    public void draw(Graphics2D g) {
         
        setMapPosition();        
        
        // draw player
        if(flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed / 100 % 2 == 0) {
                return;
            }
        }
        //determines if the player can be right or needs to flip sides 
       super.draw(g);
    }
}