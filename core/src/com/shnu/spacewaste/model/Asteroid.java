package com.shnu.spacewaste.model;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.shnu.spacewaste.Resources;

/**
 * Represents an asteroid in the game world. Has a predefined
 * size and animation/model. Velocity and location are determined
 * at initialization.
 * @author Vishnu
 *
 */
public class Asteroid extends Entity{

	private static final float WIDTH = 50, HEIGHT = 50;
	
	public Asteroid(float x, float y, Vector2 velocity) {
		super(x, y, WIDTH, HEIGHT);
		this.velocity = velocity.cpy();
		
		//Set Animation
		model = new ModelInstance(Resources.asteroid);
		model.transform.rotate(1, 0, 0, 90);
		model.transform.rotate(0, 1, 0, 180);
		
		animation = new AnimationController(model);
		//System.out.println(model.animations.get(0).id);
		//animation.setAnimation(model.animations.get(0).id);
		//startAnimation("spin");
	}
	
	/**
	 * Update this Asteroid. Also updates animation and rotates itself
	 * besides Entity's update.
	 */
	public void update(float delta){
		super.update(delta);
		animation.update(delta);
		model.transform.rotate(1, 1, 1, delta*100);
	}

}
