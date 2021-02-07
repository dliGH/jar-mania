package entities;

import java.awt.*;
import TileMap.*;
import main.GamePanel;

//holds all functionality that other objects are gonna need
public abstract class MapObject {
	
	//tiles for each class that extends
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	//position in map
	protected double x;
	protected double y;
	//moving/change in movement variables
	protected double dx;
	protected double dy;
	
	//dimensions of sprite sheet
	protected int width;
	protected int height;
	
	//collision box for later interactions
	//c stands for character
	protected int cwidth;
	protected int cheight;
	
	//collision more
	protected int currRow;
	protected int currCol;
	//positions for collision
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	//animation of standing/walking/whatever it does
	protected Animation animation;
	//whichever state currently in
	protected int currentAction;
	protected int previousAction;
	protected boolean facingRight;
	
	//movement to see what object is doing
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	
	//movement physics whatnot 
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	
	//constructor 
	public MapObject(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize();
	}
	
	//collision method using rectangles w other objects
	public boolean intersects(MapObject other) {
		Rectangle r1 = this.getRectangle();
		Rectangle r2 = other.getRectangle();
		return r1.intersects(r2);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle((int)x - cwidth, (int)y-cheight,cwidth,cheight);
	}
	
	public void calculateCorners(double x, double y) {
		
		//doing what method name says
		int leftTile = (int) (x-cwidth/2) / tileSize;
		int rightTile = (int) (x+cwidth/2-1) / tileSize;
		int topTile = (int) (y-cheight/2) / tileSize;
		int bottomTile = (int) (y+cheight/2-1) / tileSize;
		
		
		//making sure that the tile actually exists and we arent calculating something offscreen
		if(topTile < 0 || bottomTile >= tileMap.getNumRows() ||
			leftTile < 0 || rightTile >= tileMap.getNumCols()) {
			topLeft = topRight = bottomLeft = bottomRight = false;
			return;
		}
		
		//actual corners
		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);
		
		//assigning if can't make past place w block enum
		topLeft = (tl == Tile.BLOCKED);
		topRight = (tr == Tile.BLOCKED);
		bottomLeft = (bl == Tile.BLOCKED);
		bottomRight = (br == Tile.BLOCKED);
	}
	
	//collision w map
	public void checkTileMapCollision() {
		
		currCol = (int) x/tileSize;
		currRow = (int) y/tileSize;
		
		//looking at where character will be next
		xdest = x + dx;
		ydest = y + dy;
		
		//just storing into temp for manipulation
		xtemp = x;
		ytemp = y;
		
		calculateCorners(x,ydest);
		
		//caracter is moving up
		if(dy < 0) {
			//checking if hitting anything above self
			if(topLeft || topRight) {
				//set movement in y direction to 0
				dy = 0;
				//sets position right below tile that hits
				ytemp = currRow*tileSize + cheight/2;
			}else {
				//if theres nothing blocking, position will continue
				ytemp += dy;
			}
		}
		//if landing on tile below
		if(dy > 0) {
			if(bottomLeft || bottomRight) {
				dy = 0;
				falling = false;
				ytemp = (currRow + 1) * tileSize - cheight/2;
			}else {
				ytemp += dy;
			}
		}
		
		//know checking horizontal
		calculateCorners(xdest,y);
		
		//moving left
		if(dx < 0) {
			if(topLeft || bottomLeft) {
				dx = 0;
				xtemp = currCol * tileSize + cwidth/2;
			}else {
				xtemp += dx;
			}
		}
		//moving right
		if(dx > 0) {
			if(topRight || bottomRight) {
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth/2;
			}else {
				xtemp += dx;
			}
		}
		
		//check if in falling motion
		if(!falling) {
			calculateCorners(x, ydest + 1);
			if(!bottomLeft && !bottomRight) {
				falling = true;
			}
		}
	}
	
	//accessor methods for others
	//maintain info hiding
	public int getx() { return (int)x; }
	public int gety() { return (int)y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getCWidth() { return cwidth; }
	public int getCHeight() { return cheight; }
	
	
	//one position is for character on the 320*240 screen
	//other is position on the actual map itself -> tells us where to put the image
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	public void setMapPosition() {
		xmap = tileMap.getx();
		ymap = tileMap.gety();
	}
	
	//more info hiding
	//modifier methods
	public void setLeft(boolean b) { left = b; }
	public void setRight(boolean b) { right = b; }
	public void setUp(boolean b) { up = b; }
	public void setDown(boolean b) { down = b; }
	public void setJumping(boolean b) { jumping = b; }
	
	//lets us decide to only draw whats on screen
	//save some cpu power
	public boolean notOnScreen() {
		//beyond left side, beyond right side, beyond top, beyond bottom
		return x + xmap + width < 0 || x + xmap - width > GamePanel.WIDTH||y + ymap + height < 0||y + ymap - height > GamePanel.HEIGHT;
	}
	
	public void draw(Graphics2D g) {
		//this just helps in knowing if we need to draw our sprite facing left or right
		if(facingRight) {
			 g.drawImage(animation.getImage(),(int)(x + xmap - width / 2),(int)(y + ymap - height / 2), null);
	    }
	    else {
	    	 g.drawImage(animation.getImage(),(int)(x + xmap - width / 2 + width),(int)(y + ymap - height / 2),-width,height,null);   
	    }         
	}
}
