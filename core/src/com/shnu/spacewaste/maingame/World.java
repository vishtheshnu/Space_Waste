package com.shnu.spacewaste.maingame;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.shnu.spacewaste.SpaceWaste;
import com.shnu.spacewaste.model.*;

/**
 * Represents the world which the game takes place in.
 * Stores list of asteroids and the player.
 * Updates all items accordingly
 * @author Vishnu
 *
 */
public class World {
	
	//Entities
	protected ArrayList<Asteroid> asteroids;
	protected Player player;
	
	//HUD items
	protected float time;
	
	//Game Over
	protected boolean isGameOver;
	
	//Iterator
	private Iterator<Asteroid> asteroidIterator;
	
	public World(){
		//Init variables
		asteroids = new ArrayList<Asteroid>();
		player = new Player();
		time = 0;
		isGameOver = false;
	}
	
	/**
	 * Used to update the world and all entities within
	 * @param delta time since last call to update in seconds
	 */
	public void update(float delta){
		//Update player
		player.update(delta);
		
		//Update Time
		time += delta;
		
		//Update asteroids
		Asteroid a;
		asteroidIterator = asteroids.iterator();
		while(asteroidIterator.hasNext()){
			a = asteroidIterator.next();
			a.update(delta);
			//Check lose conditions- asteroid intersects with player
			if(player.intersects(a)){
				//End Game condition
				isGameOver = true;
				Gdx.app.log(SpaceWaste.LOG, "Asteroid Collision, Game Over");
			}
			
			//If asteroid has passed the player, remove it.
			if(a.getLocation().y < -a.getHeight()-SpaceWaste.MENU_SIZE){
				a.dispose();
				asteroidIterator.remove();
			}
		}
	}
	
	/**
	 * Disposes resources.
	 * Currently disposes player and asteroids.
	 */
	public void dispose(){
		//Dispose Asteroids
		for(Asteroid a : asteroids)
			a.dispose();
		asteroids.clear();
		asteroids = null;
		asteroidIterator = null;
		
		//Dispose Player
		player.dispose();
		player = null;
	}
}
