package com.shnu.spacewaste;

import com.badlogic.gdx.Game;
import com.shnu.spacewaste.maingame.GameScreen;
import com.shnu.spacewaste.menus.*;

/**
 * The game class. Stores global static variables and screen instances.
 * @author Vishnu
 *
 */
public class SpaceWaste extends Game {
	
	//Logging
	public static final String LOG = "Space Waste Log";
	
	//Screen Sizing
	public static final float SCREEN_WIDTH = 400; //Width of game world
	public static final float SCREEN_HEIGHT = 600; //Height of game world
	public static final int MENU_SIZE = 100; //Size of the HUD, where score is displayed
	
	//Game Screens
	public static SplashScreen splashscreen;
	public static LoadScreen loadscreen;
	public static MainMenu mainmenu;
	public static GameScreen game;
	public static EndMenu endmenu;
	
	/**
	 * Default contructor
	 */
	public SpaceWaste(){
		super();
	}
	
	@Override
	/**
	 * Initializes the non-game screens and sets the screens
	 * and sets the current screen to the load screen to
	 * load resources.
	 */
	public void create() {
		loadscreen = new LoadScreen(this);
		splashscreen = new SplashScreen(this);
		endmenu = new EndMenu(this);
		setScreen(loadscreen);
		
	}

	@Override
	/**
	 * Disposes all of the screens which have dispose
	 * methods, if they aren't null.
	 */
	public void dispose() {
		super.dispose();
		loadscreen.dispose();
		if(game != null)
			game.dispose();
		if(mainmenu != null)
			mainmenu.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
