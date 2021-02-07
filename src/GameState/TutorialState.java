package GameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import TileMap.Background;
import TileMap.TileMap;
import entities.*;
import entities.enemies.*;
import entities.player.*;
import main.GamePanel;

public class TutorialState extends GameState{
	
	private Background bg;
	
	//tile map lets load in the level blocks and tiles
	private TileMap tileMap;
	//our character
	private Link link;
		
	//array of enemies and whatnot
	private ArrayList <Enemy> enemies;
	//these are the explosions for enemies when they die
	private ArrayList<Explosion> explosions;
	
	private healthHUD hhud;
	
	public TutorialState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	//overriden methods
	public void init() {
		//size of each tile is 30 px
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/tutorial.map");
		tileMap.setPosition(0, 0);
		//tween is smooth camera panning
		tileMap.setTween(0.07);
				
		//setting background
		bg = new Background("/backgrounds/gamebg.gif", 0.1);
				
		//placing person on map
		link = new Link(tileMap);
		link.setPosition(100,100);
				
		//adding in enemies 
		populateEnemies();
				
		explosions = new ArrayList<Explosion>();
		
		hhud = new healthHUD(link);
	}
	
	//helper method
	private void populateEnemies() {
		enemies = new ArrayList<Enemy>();			
		Slugger s = new Slugger(tileMap);
		s.setPosition(400, 100);
		enemies.add(s);
		Jar j = new Jar(tileMap);
		j.setPosition(500, 100);
		enemies.add(j);
	}
	
	public void update() {
		link.update();
		tileMap.setPosition(GamePanel.WIDTH / 2 - link.getx(), GamePanel.HEIGHT/2 - link.gety());
		
		//check if dead
		if(link.isDead()) {
			gsm.setState(GameStateManager.DEADSTATE);
		}
		
		//background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		//attacking enemies
		link.checkAttack(enemies);
		//updating enemies
		for(int i = 0; i<enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(),e.gety()));
			}
		}
		//updating explosions
		for(int i = 0; i<explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
	}
	public void draw(Graphics2D g) {
		//drawing the background
		bg.draw(g);
		//drawing tilemap
		tileMap.draw(g);
		//draw link
		link.draw(g);
		//drawing hud
		hhud.draw(g);
		//draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		//draw explosions
		for(int i = 0; i<explosions.size(); i++) {
			explosions.get(i).draw(g);
			explosions.get(i).setMapPosition((int)tileMap.getx(), (int)tileMap.gety());
		}
	
	}
		
	//keylistener whatnot
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) { link.setLeft(true);} 
		if(k == KeyEvent.VK_RIGHT) {link.setRight(true); }
		if(k == KeyEvent.VK_UP) {link.setUp(true); }
		if(k == KeyEvent.VK_DOWN) {link.setDown(true);} 
		if(k == KeyEvent.VK_SPACE) {link.setJumping(true);} 
		if(k == KeyEvent.VK_CONTROL) {link.setSwinging();}
		if(k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_ENTER) {
			gsm.setState(GameStateManager.INTERMEDIATESTATE);
		}
	}
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) {link.setLeft(false); }
		if(k == KeyEvent.VK_RIGHT) {link.setRight(false);}
		if(k == KeyEvent.VK_UP) {link.setUp(false); }
		if(k == KeyEvent.VK_DOWN) {link.setDown(false); }
		if(k == KeyEvent.VK_SPACE) {link.setJumping(false);} 
	}
	
}
