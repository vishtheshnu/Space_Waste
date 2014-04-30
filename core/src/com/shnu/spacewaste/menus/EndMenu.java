package com.shnu.spacewaste.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.shnu.spacewaste.Resources;
import com.shnu.spacewaste.SpaceWaste;
import com.shnu.spacewaste.maingame.GameScreen;

/**
 * Represents the menu shown when the player loses. Displays their score,
 * counting up from 0. Then allows the player to return to the main menu
 * by pressing/clicking anywhere/anything.
 * @author Vishnu
 *
 */
public class EndMenu implements Screen{

	private SpaceWaste game;
	
	//Display
	private int finalScore = 0; //What to add score up to
	private float displayScore = 0; //Current float to display
	private String contString;
	
	private OrthographicCamera cam;
	private SpriteBatch batch;
	
	//Transition
	private float alpha = 0;
	private boolean endTransition;
	private boolean finished;
	
	public EndMenu(SpaceWaste game){
		this.game = game;
		
		//init vars
		finalScore = 0;
		displayScore = 0;
		alpha = 0;
		endTransition = false;
		finished = false;
		if(Gdx.app.getVersion() > 0)
			contString = "< Click Anywhere to Continue >";
		else
			contString = "< Press Anything to Continue >";
		
		//Set Batch & Camera
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, SpaceWaste.SCREEN_WIDTH, SpaceWaste.SCREEN_HEIGHT);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
	}
	
	/**
	 * Renders the background, sore, and message to go back to main menu
	 */
	public void render(float delta) {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		//Update Alpha
		if(!endTransition){
			alpha += delta;
			if(alpha > 1f){
				alpha = 1f;
				endTransition = true;
			}
		}
		
		//Draw fade-in background
		batch.setColor(1, 1, 1, alpha);
		if(!endTransition){
			batch.begin();
			batch.draw(Resources.endmenu, 0, 0);
			batch.end();
			return;
		}
		
		//Draw regular background & score
		batch.begin();
		//Render Background
		batch.draw(Resources.endmenu, 0, 0);
		//Render Score
		float xloc = (SpaceWaste.SCREEN_WIDTH-Resources.font.getBounds("Final Score").width)/2;
		float yloc = (SpaceWaste.SCREEN_HEIGHT + Resources.font.getLineHeight())/2;
		Resources.font.draw(batch, "Final Score", xloc, yloc);
		
		xloc = (SpaceWaste.SCREEN_WIDTH-Resources.font.getBounds(""+(int)displayScore).width)/2;
		yloc = (SpaceWaste.SCREEN_HEIGHT + Resources.font.getLineHeight())/2 - Resources.font.getLineHeight();
		Resources.font.draw(batch, ""+(int)displayScore, xloc, yloc);
		
		//Display "Press anywhere to continue"
		if(finished){
			xloc = (SpaceWaste.SCREEN_WIDTH-Resources.font.getBounds(contString).width)/2;
			yloc = (SpaceWaste.SCREEN_HEIGHT/2 + Resources.font.getLineHeight())/2 - Resources.font.getLineHeight();
			Resources.font.draw(batch, contString, xloc, yloc);
		}
		batch.end();
		
		//Increment Score
		displayScore += (delta*finalScore/3);
		if(displayScore > finalScore){
			displayScore = finalScore;
			
			//Create way to go back to main menu
			finished = true;
			Gdx.input.setInputProcessor(new MenuInputHandler(game, this, SpaceWaste.mainmenu));
		}
	}
	
	/**
	 * Sets the final score of the player
	 * @param score what to set the score to
	 */
	public void setScore(int score){
		finalScore = score;
		displayScore = 0;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Called when the game switches to this screen.
	 * Plays the main menu music, sets the text color
	 * to white, and disposes of the game screen.
	 */
	@Override
	public void show() {
		Resources.gamemusic.stop();
		Resources.titlemusic.setLooping(true);
		Resources.titlemusic.play();
		Resources.font.setColor(Color.WHITE);
		
		//Dispose game, go back to main menu
		SpaceWaste.game.dispose();
		SpaceWaste.game = new GameScreen(game);
		System.gc();
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
