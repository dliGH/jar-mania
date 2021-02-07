package TileMap;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

import main.GamePanel;

//just something that lets background loop and move
public class Background {

	//buffered image = loading behind screen
	private BufferedImage image;
	
	//positions
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	//if looping -> can move
	private double moveScale;
	
	public Background(String s, double ms) {
		//loading image
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));
			moveScale = ms;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//naming variables the same just for convinience
	//will need to use this keyword though
	public void setPosition(double x, double y) {
		this.x = (x * moveScale) % GamePanel.WIDTH;
		this.y = (y * moveScale) % GamePanel.HEIGHT;
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update() {
		x += dx;
		y += dy;
	}
	
	public void draw(Graphics2D g) {
		
		g.drawImage(image, (int)x, (int) y, null);
		//2d sidescrolling function below
		//draws second panels to make sure no stuttering or anything
		if(x < 0) {
			g.drawImage(image, (int)x + GamePanel.WIDTH, (int)y, null);
		}
		if(x > 0) {
			g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);
		}
	}
}
