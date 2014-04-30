package com.shnu.spacewaste.maingame;

import com.badlogic.gdx.math.Vector2;
import com.shnu.spacewaste.SpaceWaste;
import com.shnu.spacewaste.model.Asteroid;

/**
 * Contains an instance of the World, and continually adds asteroids to it.
 * 
 * Rules of Adding Asteroids:
 * 
 *  -Asteroid spawns above the visible screen (Constant starting y position)
 *  -Starting x position varies between 0 and world width
 *  -x velocity within defined interval
 *  -y velocity is negative, starting with one value, increasing with difficulty
 * @author Vishnu
 *
 */
public class AsteroidAdder {

	//Static difficulty variables
	private static final float INTERVAL_RANDOM = 1.5f; //Base time between each random asteroid spawned
	private static final float INTERVAL_WAVE = 3f; //Base time between each random asteroid wave (not used)
	private static final float DIFFICULTY_INCREASE = .03f; //Scale at which difficulty float increases per second
	private static final float MAX_DIFFICULTY = .75f; //Maximum difficulty possible in game
	
	//Max value for the velocities
	public static final float MIN_X_VELOCITY = -50; //Minimum X velocity of an asteroid
	public static final float MIN_Y_VELOCITY = -250; //Minimum Y velocity of an asteroid (not used)
	
	//Max values for the velocities
	public static final float MAX_X_VELOCITY = 50; //Maximum X velocity of an asteroid
	public static final float MAX_Y_VELOCITY = -50; //Currently used as base Y velocity of an asteroid
	
	//Asteroids Per Wave
	//private static final int APW = 3;
	
	//World instance
	private World world;
	
	//Time info
	private float time; //total time
	private float intervalRandom; //time since last random asteroid addition
	private float intervalWave; //time since last asteroid wave addition
	private float difinc; //Current increased difficulty
	
	//Whether game is finished
	protected boolean isFinished;
	
	public AsteroidAdder(World w){
		world = w;
		
		//Init Variables
		time = 0;
		intervalRandom = 0;
		intervalWave = 0;
		difinc = 0;
		isFinished = false;
	}
	
	/**
	 * Used to update this class.
	 * @param delta time since last update in seconds
	 */
	public void update(float delta){
		if(isFinished) //Don't do anything if the game is finished
			return;
		
		//Increment the interval and time variables
		intervalRandom += delta;
		intervalWave += delta;
		time += delta;
		
		//Increment the difficulty increase
		difinc = time * DIFFICULTY_INCREASE;
		if(difinc > MAX_DIFFICULTY)
			difinc = MAX_DIFFICULTY;
		
		//Whether to add an asteroid to the world
		if(intervalRandom >= INTERVAL_RANDOM*(1-difinc)){
			addAsteroidRandom();
			intervalRandom %= INTERVAL_RANDOM*(1-difinc);
		}
		
		//Whether to add a wave of asteroids to the world (not used currently)
		if(intervalWave >= INTERVAL_WAVE*(1-difinc)){
			//addAsteroidWave();
			intervalWave %= INTERVAL_WAVE*(1-difinc);
		}
	}
	
	/**
	 * Adds a random asteroid to the world
	 */
	public void addAsteroidRandom(){
		//X Location
		float xl = (float)(Math.random() * SpaceWaste.SCREEN_WIDTH);
		//X Velocity
		float xv = (float)(Math.random() * (MAX_X_VELOCITY - MIN_X_VELOCITY)) + MIN_X_VELOCITY;
		//Y Velocity
		float yv = MIN_Y_VELOCITY * (1+difinc/2);
		//Instantiate new Asteroid and add to world
		Asteroid a = new Asteroid(xl, SpaceWaste.SCREEN_HEIGHT, new Vector2(xv, yv));
		world.asteroids.add(a);
	}
	
	/*
	
	Adds a wave of asteroids to the world. Not used currently because people keep losing :P
	public void addAsteroidWave(){
		float xvel, yvel;
		
		xvel = (float)(Math.random() * (MAX_X_VELOCITY));
		yvel = (float)(Math.random() * (MAX_Y_VELOCITY - MIN_Y_VELOCITY)) + MIN_Y_VELOCITY;
		
		Asteroid a;
		
		for(int i = 0; i < APW; i++){
			a = new Asteroid(i*SpaceWaste.SCREEN_WIDTH/APW, SpaceWaste.SCREEN_HEIGHT, new Vector2(xvel, yvel));
			world.asteroids.add(a);
		}
	}

	*/
}
