package com.shnu.spacewaste.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.shnu.spacewaste.Resources;
import com.shnu.spacewaste.SpaceWaste;

/**
 * Represents the Player. Has a predefined size and animation,
 * velocity and location are controlled by the player.
 * @author Vishnu
 *
 */
public class Player extends Entity {
	//Size
	private static final float WIDTH = 25, HEIGHT = 40;
	
	//Rate of velocity lost per second
	private static final float FRICTION_RATE = 3f;
	
	//Change in velocity from one click
	private static final float CLICK_CHANGE = 250;
	
	//Boundaries
	public static final float LEFT_BOUND = 50, RIGHT_BOUND = SpaceWaste.SCREEN_WIDTH-50;
	
	//Max velocity- velocity will not go above this
	private static final float MAX_VELOCITY = 500;
	
	private float currentRotation = 0; //Base rotation of the player model
	public AnimationController animController;
	
	public Player() {
		super(SpaceWaste.SCREEN_WIDTH/2, 0,
				WIDTH,
				HEIGHT);
		
		model = new ModelInstance(Resources.ship);
		model.transform.translate(location.x, location.y, 0);
		fixModelRotation();
	}

	/**
	 * Overridden to also add "friction" and slowly reduce velocity.
	 * Also updates and sets the proper rotation.
	 */
	public void update(float delta){
		super.update(delta);
		
		//Reduce velocity
		float tored = velocity.x*FRICTION_RATE * delta; //how much to reduce
		velocity.x -= tored;
		
		//Check Max velocity
		if(velocity.x > MAX_VELOCITY)
			velocity.x = MAX_VELOCITY;
		else if(-velocity.x > MAX_VELOCITY)
			velocity.x = -MAX_VELOCITY;

		//Check Screen Size/Dimensions
		if(location.x < LEFT_BOUND){
			location.x = LEFT_BOUND;
			//velocity.x = 0;
		}else if(location.x > RIGHT_BOUND){
			location.x = RIGHT_BOUND;
			//velocity.x = 0;
		}
		
		//Set rotation based on velocity- transition velocity
		float rotToSet = 0; //Rotation to set to
		//(What it should be - what it is) * delta
		rotToSet = (90*velocity.x/MAX_VELOCITY - currentRotation)*delta*20;
		
		model.transform.setToRotation(new Vector3(0, 1, 0), currentRotation+rotToSet);
		currentRotation += rotToSet;
		//Proper facing
		fixModelRotation();
	}
	
	/**
	 * Called by input handler to move left
	 */
	public void moveLeft(){
		velocity.x -= (CLICK_CHANGE);
	}
	
	/**
	 * Called by input handler to move right
	 */
	public void moveRight(){
		velocity.x += (CLICK_CHANGE);
	}
	
	/**
	 * Makes the model face the right direction
	 */
	public void fixModelRotation(){
		model.transform.rotate(0, 0, 1, 270);
		model.transform.rotate(1, 0, 0, 90);
	}

}
