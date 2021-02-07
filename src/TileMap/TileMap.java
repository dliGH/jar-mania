package TileMap;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

import main.GamePanel;

public class TileMap {

	//positions
	private double x;
	private double y;
	
	//bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	//smoothscrolling in game?
	private double tween;
	
	//map
	private int [][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	//tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;
	
	//drawing
	//draws only whats on screen
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		tween = 0.07;
	}
	
	//using the tilemap image from resources to and defining the top and bottom row functions
	public void loadTiles(String s) {
		
		try {
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
					numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[2][numTilesAcross];
			
			BufferedImage subimage;
			for(int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.getSubimage(col*tileSize, 0, tileSize, tileSize);
				tiles[0][col] = new Tile(subimage, Tile.NORMAL);
				subimage = tileset.getSubimage(col*tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//reading in from txt(.map) doc.
	//first line is number of cols in level
	//second line is number of rows
	//everything else is actual map blocsk
	
	public void loadMap(String s) {
		
		try {

			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;
			
			//used later to get rid of blank space
			String delims = "\\s+";
			
			for(int row = 0; row<numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				//splitting and creating actual map to be built later
				for(int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//accessor methods
	public int getTileSize() { return tileSize; } 
	public double getx() { return x; } 
	public double gety() { return y; } 
	public int getWidth() { return width; } 
	public int getHeight() { return height; }
	
	public int getType(int row, int col) {
		int rc = map[row][col];
		int r = rc/numTilesAcross;
		int c = rc% numTilesAcross;
		return tiles[r][c].getType();
	}
	
	public void setTween(double d) { tween = d; }
	
	public void setPosition(double x, double y) {
		//smooth camera motion
		this.x += (x-this.x)*tween;
		this.y = (y-this.y)*tween;
		
		fixBounds();
		
		//checking where to start drawing map
		colOffset = (int) -this.x / tileSize;
		rowOffset = (int) -this.y / tileSize;
	}
	
	//helper to make sure character doesn't go oob
	private void fixBounds() {
		if (x < xmin) {
			x = xmin;
		}
		if(y<ymin) {
			y = ymin;
		}
		if(x > xmax) {
			x = xmax;
		}
		if(y > ymax) {
			y = ymax;
		}
	}
	
	//actually drawing the map now
	
	public void draw(Graphics2D g) {
		
		//rows
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
			
			if(row >= numRows) {
			break;
			}
			//columns
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				if (col >= numCols) {
					break;
				}
				//first tile in image file is a blank tile
				if(map[row][col] == 0) {
					continue;
				}
				int rc = map[row][col];
				int r = rc/numTilesAcross;
				int c = rc%numTilesAcross;
				
				g.drawImage(tiles[r][c].getImage(), (int)x + col * tileSize, (int)y + row * tileSize, null);
				
			}
		}	
	}
	
	//accesor methods to be used 
	public int getNumRows() { return numRows; }
	public int getNumCols() { return numCols; }
}
