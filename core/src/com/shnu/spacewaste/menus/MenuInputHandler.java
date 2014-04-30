package com.shnu.spacewaste.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.shnu.spacewaste.SpaceWaste;

/**
 * Controls switching between 2 screens using a fade effect.
 * @author Vishnu
 *
 */
public class MenuInputHandler implements InputProcessor, Screen {

	public static final int GAME = 1, RESTART = 2;
	
	private SpaceWaste game; //Game instance
	
	private float alpha; //Alpha value for fading
	private boolean alphaIncrease; //Whether teh alpha is increasing or decreasing
	
	//Screens to switch- from screen1 to screen2
	private Screen screen1, screen2;
	
	//Draws a black rectangle over the screen to represent fading
	private ShapeRenderer shape;
	
	public MenuInputHandler(SpaceWaste game, Screen s1, Screen s2){
		this.game = game;
		alpha = 0;
		alphaIncrease = true;
		
		screen1 = s1;
		screen2 = s2;
		
		shape = new ShapeRenderer();
	}
	
	/**
	 * Sets the screen to this screen
	 */
	private void changeScreen(){
		game.setScreen(this);
	}
	
	/**
	 * When the player presses a button, the game
	 * switches to this screen for the transition process
	 */
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		changeScreen();
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * When the player presses the mouse, the game
	 * switches to this screen for the transition process
	 */
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		changeScreen();
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Draws this screen- Either screen 1 or screen 2,
	 * with a black fade effect over it.
	 */
	public void render(float delta) {
		//Draw the current Screen
		if(alphaIncrease)
			screen1.render(delta);
		else
			screen2.render(delta);
		
		//Draw Fade Box
		shape.setColor(0, 0, 0, alpha);
		Gdx.gl.glEnable(GL20.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    
	    shape.begin(ShapeType.Filled);
		shape.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shape.end();
		
		//Update alpha
		if(alphaIncrease)
			alpha += delta;
		else
			alpha -= delta;
		
		//Check if should switch things
		if(alpha > 1f){
			alpha = 1f;
			alphaIncrease = false;
			screen2.show();
		}else if(alpha < 0f){
			alpha = 0;
			//Change screen
			game.setScreen(screen2);
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
