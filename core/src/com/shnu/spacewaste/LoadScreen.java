package com.shnu.spacewaste;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.shnu.spacewaste.menus.MainMenu;

/**
 * This screen is used to initialize the Resources class, which
 * in turn loads all of the game's resources.
 * @author Vishnu
 *
 */
public class LoadScreen implements Screen{

	private SpaceWaste game; //Instance of the game
	
	//Loading process
	private boolean startedLoading;
	
	public LoadScreen(SpaceWaste game){
		this.game = game;
		startedLoading = false;
		
		//Init background resources
	}
	
	/**
	 * Renders the contents of the screen and handles
	 * the game loop. Nothing is rendered in this class,
	 * but the game loop is used to wait for all resources
	 * to load, specifically the 3D asset manager which
	 * runs in its own thread.
	 * 
	 * @param delta time since last call to render in seconds
	 */
	@Override
	public void render(float delta) {
		//Start loading if haven't already
		if(!startedLoading){
			new Resources();
			startedLoading = true;
		}
		
		//Check if loading is finished
		if(Resources.isFinished()){
			Gdx.app.log(SpaceWaste.LOG, "Finished Loading Resources");
			//Call Finishing method in Resources
			Resources.finishLoading();
			//Initialize and change screen to Main Menu
			SpaceWaste.mainmenu = new MainMenu(game);
			game.setScreen(SpaceWaste.splashscreen);
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Called when the game switches to this screen. Does nothing
	 * in particular, but logs that such a transition has occured.
	 */
	@Override
	public void show() {
		Gdx.app.log(SpaceWaste.LOG, "Entering Load Screen");
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

	/** Disposes the game's external resources in the
	 * Resources class.*/
	@Override
	public void dispose() {
		Resources.dispose();
	}

}
