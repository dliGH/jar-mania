package entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import entities.player.Link;

//currently showing hp only
public class healthHUD {

	//need to be able to get hp
	private Link link;
	//for loading the hud
	private BufferedImage image;
	private Font font;
	
	//constructor
	public healthHUD(Link l) {
		link = l;
		//loading in hud
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.gif"));
			font = new Font("Arial", Font.PLAIN, 14);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		//drawing and showing how much hp character hass
		g.drawImage(image, 0, 10, null);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(link.getHealth() + "/" + link.getMaxHealth(), 30, 25);
	}

}
