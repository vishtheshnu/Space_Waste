package com.shnu.spacewaste.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.shnu.spacewaste.Resources;
import com.shnu.spacewaste.SpaceWaste;

/**
 * The splash screen. Displays company name and fade effect.
 * @author Vishnu
 *
 */
public class SplashScreen implements Screen{

	private SpaceWaste game;
	
	//Drawing
	private FitViewport viewport;
	private OrthographicCamera cam;
	private SpriteBatch batch;
	
	//Splash Screen
	private Texture splashscreen;
	
	//Tween Attempting
	private float alpha;
	private boolean finishedLoading;
	
	public SplashScreen(SpaceWaste game){
		this.game = game;
		
		//Viewport
		viewport = new FitViewport(SpaceWaste.SCREEN_WIDTH, SpaceWaste.SCREEN_HEIGHT);
		viewport.update();
		
		//Batch
		batch = new SpriteBatch();
		
		//Camera
		cam = new OrthographicCamera();
		cam.setToOrtho(false, SpaceWaste.SCREEN_WIDTH, SpaceWaste.SCREEN_HEIGHT);
		
		viewport.setCamera(cam);
		batch.setProjectionMatrix(cam.combined);
		viewport.update();
		
		//Splash Screen
		splashscreen = new Texture("data/splashscreen.png");
		
		//tween attempt
		alpha = 0;
		finishedLoading = false;
	}
	
	@Override
	public void render(float delta) {
		//Clear openGL
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		//Draw screen & darkness
		batch.begin();
		batch.draw(splashscreen, 0, 0);
		batch.end();
		
		//Update darkness
		if(!finishedLoading && alpha < 1f){
			alpha += delta;
			if(alpha > 1f){
				alpha = 1f;
				finishedLoading = true;
			}
		}else{
			alpha -= delta;
			if(alpha < 0){
				//Change Screens
				alpha = 0;
				game.setScreen(SpaceWaste.mainmenu);
			}
		}
		
		//Set sbatch's color and alpha
		batch.setColor(1, 1, 1, alpha);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * When the game switches to this screen, play
	 * the main menu music.
	 */
	public void show() {
		Resources.titlemusic.setLooping(true);
		Resources.titlemusic.play();
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
