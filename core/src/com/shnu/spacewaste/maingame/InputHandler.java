package com.shnu.spacewaste.maingame;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {

	private World world; //Instance of World
	
	public InputHandler(World w){
		world = w;
	}
	
	/**
	 * Called when a key goes down.
	 * Moves the player left or right depending on the key they press.
	 * Left arrow or A = move left
	 * Right arrow or D = move right
	 */
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.LEFT || keycode == Keys.A)
			world.player.moveLeft();
		else if(keycode == Keys.RIGHT || keycode == Keys.D)
			world.player.moveRight();
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
	 * Move the player when they touch on the game screen. Meant for mobile.
	 * Left side of the screen = move left,
	 * Right side of the screen = move right
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//Temp- uses whole screen for touch events
		//TODO: implement buttons for movement
		if(screenX <= Gdx.graphics.getWidth()/2)
			world.player.moveLeft();
		else
			world.player.moveRight();
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

}
