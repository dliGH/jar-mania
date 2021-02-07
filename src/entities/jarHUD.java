package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entities.player.Link;

public class jarHUD {
	//need to be able to get hp
		private Link link;
		//for loading the hud
		private BufferedImage image;
		private Font font;
		
		//constructor
		public jarHUD(Link l) {
			link = l;
			//loading in hud
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/HUD/jar hud.gif"));
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
			g.drawString(link.getJarsKiller() + "!", 15, 25);
		}
}
