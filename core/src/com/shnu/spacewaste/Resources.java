package com.shnu.spacewaste;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.Model;

/**
 * Static class to store and load resources
 * @author Vishnu
 *
 */
public class Resources {
	
	//Loading
	private static AssetManager assets;
	
	/*Stored Data*/
	
	//models
	public static Model asteroid, ship;
	
	//textures
	public static Texture mainmenu, endmenu;
	public static Texture background1, background2;
	public static Texture hud, button;
	
	//fonts
	public static BitmapFont font;
	
	//sound
	public static Music titlemusic, gamemusic;
	public static Sound crash;
	
	public Resources(){
		Gdx.app.log(SpaceWaste.LOG, "Initializing resources");
		//Initialize asset manager to load 3D models
		assets = new AssetManager();
		//Load 3D models
		assets.load("data/ship.g3db", Model.class);
		assets.load("data/asteroid.g3db", Model.class);
		
		//Load menu backgrounds
		mainmenu = new Texture("data/mainmenu.png");
		endmenu = new Texture("data/background1.png");
		
		//Load main game backgrounds & textures
		background1 = new Texture("data/background1.png");
		background2 = new Texture("data/background2.png");
		hud = new Texture("data/hud.png");
		
		//Load font
		font = new BitmapFont(Gdx.files.internal("data/swfont.fnt"));
		
		//Load music & sound effects
		titlemusic = Gdx.audio.newMusic(Gdx.files.internal("data/titlemusic.mp3"));
		gamemusic = Gdx.audio.newMusic(Gdx.files.internal("data/gamemusic.mp3"));
		crash = Gdx.audio.newSound(Gdx.files.internal("data/crash.ogg"));
	}

	/**
	 * Calls dispose on all resources that have a dispose method.
	 */
	public static void dispose(){
		Gdx.app.log(SpaceWaste.LOG, "Disposing resources");
		assets.dispose();
		
		mainmenu.dispose();
		endmenu.dispose();
		background1.dispose();
		background2.dispose();
		hud.dispose();
		
		font.dispose();
		
		titlemusic.dispose();
		gamemusic.dispose();
	}
	
	//Loading Process
	
	/**
	 * Returns if all resources are finished loading
	 * @return true if everything is loaded, false otherwise
	 */
	public static boolean isFinished(){
		return assets.update();
	}
	
	/**
	 * Calls get for all 3D models from the AssetManager and assigns them.
	 * Called by LoadScreen after isFinished returns true (when all 3D
	 * resources are loaded)
	 */
	public static void finishLoading(){
		ship = assets.get("data/ship.g3db", Model.class);
		//temp
		asteroid = assets.get("data/asteroid.g3db", Model.class);
	}
}
