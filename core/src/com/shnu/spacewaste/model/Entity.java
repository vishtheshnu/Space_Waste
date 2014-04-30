package com.shnu.spacewaste.model;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Represents an entity in the game world. Has location, size,
 * velocity, and a hitbox. Also has a modelInstance to represent
 * its 3D animation, and an animationController to control animation.
 * @author Vishnu
 *
 */
public class Entity {
	
	//Ratio of model movement in relation to translation
	public static final float MODEL_MOVE_RATIO = 1f;

	//Representation in Space
	protected Vector2 location;
	protected float width, height;
	
	//Velocity and hitbox
	protected Vector2 velocity;
	protected Rectangle hitbox;
	
	//Graphics
	protected ModelInstance model;
	protected AnimationController animation;
	
	//Constructor
	public Entity(float x, float y, float width, float height){
		location = new Vector2(x, y);
		this.width = width;
		this.height = height;
		
		hitbox = new Rectangle(x, y, width, height);
		velocity = new Vector2(0, 0);
	}
	
	/**
	 * Called to get this entity to self-update.
	 * Updates location and hitbox, and animation
	 * @param delta
	 */
	public void update(float delta){
		Vector2 vtemp = velocity.cpy().scl(delta);
		location.add(vtemp);
		hitbox.set(location.x, location.y, width, height);
	}
	
	/**
	 * Checks if this entity intersects with the parameter entity.
	 * @param e Entity to check collision with
	 * @return whether there's collision- true if there is, false otherwise
	 */
	public boolean intersects(Entity e){
		if(e.hitbox.overlaps(hitbox))
			return true;
		else
			return false;
	}
	
	/**
	 * Returns this Entity's model
	 * @return
	 */
	public ModelInstance getModel(){
		return model;
	}
	
	/**
	 * Gets this Entity's location
	 * @return location of this entity
	 */
	public Vector2 getLocation(){
		return location;
	}
	
	/**
	 * Gets this Entity's height
	 * @return height of this entity
	 */
	public float getHeight(){
		return height;
	}
	
	/**
	 * Begins the animation with the name in the parameter
	 * @param anim animation to set the modelInstance to.
	 */
	public void startAnimation(String anim){
		animation.setAnimation(anim);
	}
	
	/**
	 * Called to update the animation
	 * @param delta
	 */
	public void updateAnimation(float delta){
		animation.update(delta);
	}
	
	/**
	 * Gets this Entity's width
	 * @return width of this entity
	 */
	public float getWidth(){
		return width;
	}
	
	/**
	 * Frees object references in this object so the garbage
	 * collector can free it from memory.
	 */
	public void dispose(){
		model = null;
		velocity = null;
		hitbox = null;
	}
}