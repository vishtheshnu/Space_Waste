package com.shnu.spacewaste.maingame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.shnu.spacewaste.Resources;
import com.shnu.spacewaste.SpaceWaste;

public class GameScreen implements Screen {

	private SpaceWaste game;
	
	private World world; //Represents the game world, stores all entities (player, asteroid) and updates positions
	private AsteroidAdder adder; //Controls adding asteroids to the world
	private GameRenderer renderer; //Draws the world to the screen
	private InputHandler inputhandler; //Handles user input
	
	//Transition Effects
	private float alpha = 0; //Alpha variable used in transition
	private ShapeRenderer tbatch; //draws rectangle with increasing alpha
	
	public GameScreen(SpaceWaste game){
		this.game = game;
		
		//Init components of the GameScreen
		world = new World();
		renderer = new GameRenderer(world);
		adder = new AsteroidAdder(world);
		inputhandler = new InputHandler(world);
		
		//Init shape batch for transition
		tbatch = new ShapeRenderer();
		tbatch.setColor(0, 0, 0, 0);
	}
	@Override
	public void render(float delta) {
		//Update components
		adder.update(delta);
		world.update(delta);
		renderer.render(delta);
		
		//Game Over?
		if(world.isGameOver){
			if(world.player.animController == null){
				//Set up animation Controller
				world.player.animController = new AnimationController(world.player.getModel());
				world.player.animController.setAnimation(world.player.getModel().animations.get(0).id);
				//Play crash sound
				Resources.crash.play(1f);
				//Disable Player Input
				Gdx.input.setInputProcessor(null);
				//Set Renderer's score display to stop
				renderer.gameOver();
				//Set End Menu's final score
				SpaceWaste.endmenu.setScore((int)world.time);
			}
			adder.isFinished = true;
			//Start the player's animation, update it by delta*10 per update cycle
			world.player.animController.update(delta*10);
			
			//Fade Animation
			alpha += delta;
			if(alpha > 1f)
				alpha = 1f;
			tbatch.setColor(0, 0, 0, alpha);
			
			//openGL stuff needed for this to work, IDK what this is again :P
			Gdx.gl.glEnable(GL20.GL_BLEND);
		    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			
		    //Draw a black rectangle over the screen to simulate a fade effect
			tbatch.begin(ShapeType.Filled);
			tbatch.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			tbatch.end();
			if(alpha == 1f){
				//Switch Screen when fade is finished
				game.setScreen(SpaceWaste.endmenu);
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		//Set font color to black
		Resources.font.setColor(Color.BLACK);
		Gdx.app.log(SpaceWaste.LOG, "Entering Main Game Screen");
		//Set input Processor to local one
		Gdx.input.setInputProcessor(inputhandler);
		Gdx.app.log(SpaceWaste.LOG, "Playing gamemusic");
		//Stop the title music if it's playing
		if(Resources.titlemusic.isPlaying())
			Resources.titlemusic.stop();
		
		//Play the game music at full volume! (It's really silent)
		Resources.gamemusic.setVolume(1f);
		Resources.gamemusic.setLooping(true);
		Resources.gamemusic.play();
		
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
	
	/**
	 * Disposes game components.
	 * Includes content in the world and the renderer
	 */
	@Override
	public void dispose() {
		world.dispose();
		renderer.dispose();
	}

}
