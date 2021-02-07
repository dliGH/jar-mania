package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import entities.enemies.*;
import TileMap.*;
import entities.*;
import entities.player.Link;
import main.GamePanel;

public class LevelOneState extends GameState{

	//tile map lets load in the level blocks and tiles
	private TileMap tileMap;
	//background for the background
	private Background bg;
	
	//our character
	private Link link;
	
	private ArrayList <Enemy> enemies;
	private ArrayList<Explosion> explosions;
	
	private healthHUD hhud;
	
	Random rand = new Random();
	
	public LevelOneState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/test3.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(0.07);
		
		bg = new Background("/backgrounds/gamebg.gif", 0.1);
		
		link = new Link(tileMap);
		link.setPosition(100,100);
		
		populateEnemies();
		
		explosions = new ArrayList<Explosion>();
		
		hhud = new healthHUD(link);
	
	}
	
	private void populateEnemies() {
		enemies = new ArrayList<Enemy>();
		
		Slugger s;
		Point[] points = new Point[] {new Point(860,200), new Point(1525,200), new Point(1680,200), new Point(1800,200)};
		
		for(int i = 0; i<points.length; i++) {
			s = new Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		
		Spider spider;
		Point[] spiderpoints = new Point[] {
			new Point(1190, rand.nextInt(75)),
			new Point(1235, rand.nextInt(75)),
			new Point(2530, rand.nextInt(100)),
			new Point(2600, rand.nextInt(100)),
			new Point(2565, rand.nextInt(100))
		};
		for(int i = 0; i < spiderpoints.length; i++) {
			spider = new Spider(tileMap, spiderpoints[i].x, 00);
			spider.setPosition(spiderpoints[i].x, spiderpoints[i].y);
			enemies.add(spider);
		}
		
	}
	
	public void update() {
		link.update();
		tileMap.setPosition(GamePanel.WIDTH / 2 - link.getx(), GamePanel.HEIGHT/2 - link.gety());
		//background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		//attacking enemies
		link.checkAttack(enemies);
		//check if dead
		if(link.isDead()) {
			gsm.setState(GameStateManager.DEADSTATE);
		}
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
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) link.setLeft(true); 
		if(k == KeyEvent.VK_RIGHT) link.setRight(true); 
		if(k == KeyEvent.VK_UP) link.setUp(true); 
		if(k == KeyEvent.VK_DOWN) link.setDown(true); 
		if(k == KeyEvent.VK_SPACE) link.setJumping(true); 
		if(k == KeyEvent.VK_CONTROL) link.setSwinging();
		if(k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_ENTER) {
			gsm.setState(GameStateManager.INTERMEDIATESTATE);
		}
	}
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) link.setLeft(false); 
		if(k == KeyEvent.VK_RIGHT) link.setRight(false);
		if(k == KeyEvent.VK_UP) link.setUp(false); 
		if(k == KeyEvent.VK_DOWN) link.setDown(false); 
		if(k == KeyEvent.VK_SPACE) link.setJumping(false); 
	}
}
