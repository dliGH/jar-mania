package entities;

import TileMap.*;

//super class of all enemies that will be on the map
public class Enemy extends MapObject{

	//some variables
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	//this game mechanism that doesn't allow user to hit in a specific time
	//didn't exactly get it to work on enemies
	protected boolean flinching;
	protected long flinchTimer;
	
	//constructor
	public Enemy(TileMap tm) {
		super(tm);
	}
	
	//accessor methods
	public boolean isDead() {return dead;};
	public int getDamage() {return damage;}
	public void hit(int damage)
	{
		//update variables
		if(dead||flinching) {
			return;
		}
		health-=damage;
		if(health<0) {
			health = 0;
		}
		if(health == 0) {
			dead = true;
		}
		
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	public void update() {}
}
