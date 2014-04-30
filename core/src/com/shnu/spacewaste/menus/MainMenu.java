package com.shnu.spacewaste.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.shnu.spacewaste.Resources;
import com.shnu.spacewaste.SpaceWaste;
import com.shnu.spacewaste.maingame.GameScreen;

/**
 * Represents the main menu of the game.
 * @author Vishnu
 *
 */
public class MainMenu implements Screen {

	private SpaceWaste game;
	
	//Rendering
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private FitViewport viewport;
	
	private String todisp; //The "Do whatever to begin" String
	private float alpha; //Fade Effect alpha
	
	public MainMenu(SpaceWaste game){
		this.game = game;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, SpaceWaste.SCREEN_WIDTH, SpaceWaste.SCREEN_HEIGHT);
		viewport = new FitViewport(SpaceWaste.SCREEN_WIDTH, SpaceWaste.SCREEN_HEIGHT, cam);
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		viewport.update();
		
		Resources.font.setColor(Color.WHITE);
		alpha = 0;
		
		if(Gdx.app.getVersion() > 0)
			todisp = "< click anywhere to begin >";
		else
			todisp = "< press any key to continue >";
	}
	
	/**
	 * Renders the background and text
	 */
	public void render(float delta) {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		if(alpha < 1)
			alpha += delta;
		if(alpha > 1)
			alpha = 1;
		
		batch.setColor(1, 1, 1, alpha);
		batch.begin();
		batch.draw(Resources.mainmenu, 0, 0);
		Resources.font.draw(batch, todisp,
				(SpaceWaste.SCREEN_WIDTH - Resources.font.getBounds(todisp).width)/2,
				(SpaceWaste.SCREEN_HEIGHT - Resources.font.getLineHeight())/2);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	/**
	 * Called when the main menu is switched to.
	 * Sets the font color to yellow and initializes the game screen.
	 */
	public void show() {
		Resources.font.setColor(Color.YELLOW);
		SpaceWaste.game = new GameScreen(game);
		Gdx.input.setInputProcessor(new MenuInputHandler(game, this, SpaceWaste.game));
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
