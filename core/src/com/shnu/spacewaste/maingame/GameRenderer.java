package com.shnu.spacewaste.maingame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.math.Vector3;
import com.shnu.spacewaste.Resources;
import com.shnu.spacewaste.SpaceWaste;
import com.shnu.spacewaste.model.Asteroid;
import com.shnu.spacewaste.model.Entity;

public class GameRenderer {
	
	private static final float pcamy = 0f, pcamz = 3f; //Camera Y and Z location
	private static final float MAX_TIME = 4; //Base rate at which the stars in tha background move in

	//3D
	protected OrthographicCamera ocam, bcam; //Orthographic Cameras for rendering background and 3D models
											 //ocam = 3D models, bcam = background
	private Environment environment; //Environment Object- stores the lighting
	private ModelBatch modelBatch; //Batch used to render 3D models
	private FillViewport viewport; //Viewport to fit the current screen
	
	//Background
	private ShapeRenderer shaperender; //Used to draw hitboxes for debugging
	private SpriteBatch spriteBatch; //Draws the background
	private float time, time2; //Records the progress of each layer of stars in the background
	
	//HUD
	private float gameTime; //How long the game has progressed
	
	//Other
	private World world; //Instance of the game world to render
	private boolean gameOver = false; //Whether the game is over
	
	public GameRenderer(World world){
		this.world = world;
		
		//Init Batches
		modelBatch = new ModelBatch();
		spriteBatch = new SpriteBatch();
		
		//Init primitives to 0
		time = 0;
		time2 = 0;
		gameTime = 0;
		
		//Environment
		environment = new Environment(); //Instantiate
		//New ambient light (the minimum lighting)
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight,
				0.4f, 0.4f, 0.4f, 1f));
		//Directional Light- light from a source location
		environment.add(new DirectionalLight().set(.8f, .8f, .8f, -1f, -.8f, -.2f));
		
		//Camera
		float width = SpaceWaste.SCREEN_WIDTH;
		float height = SpaceWaste.SCREEN_HEIGHT;
		
		//Init the 3D Camera
		ocam = new OrthographicCamera(width, height);
		ocam.zoom /= 30;
		//ocam.translate(width/2, 0);
		ocam.position.set(width/2, pcamy, pcamz);
		ocam.lookAt(width/2, pcamy, pcamz);
		ocam.near = 1f;
		ocam.far = 300f;
		ocam.update();
		
		//Init the background camera
		bcam = new OrthographicCamera();
		bcam.setToOrtho(false, width, height);
		bcam.update();
		spriteBatch.setProjectionMatrix(bcam.combined);
		
		//Viewport
		viewport = new FillViewport(width, height, ocam);
		viewport.update();
		
		//Shape Renderer
		shaperender = new ShapeRenderer();
		shaperender.setColor(Color.GREEN);
	}
	/**
	 * Renders the game
	 * @param delta time since last call to render
	 */
	public void render(float delta){
		//Clear the viewport, some weird OpenGL that I don't know :P
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		//Draw Background
		renderBackground(delta);
		
		//Draw Player
		renderEntity(world.player);
		
		//Draw Asteroids
		for(Asteroid a : world.asteroids)
			renderEntity(a);
		
		//Draw HUD
		renderHUD(delta);
	}
	
	/**
	 * Draw and update the background elements.
	 * Includes space image, and stars
	 * @param delta Time since last call to this method in seconds
	 */
	private void renderBackground(float delta){
		//Increment stars location variable
		time += delta;
		time2 += delta*2;
		if(time >= MAX_TIME)
			time %= MAX_TIME;
		if(time2 >= MAX_TIME)
			time2 %= MAX_TIME;
		
		spriteBatch.begin();
		
		//Draw background1
		spriteBatch.draw(Resources.background1, 0, 0);
		
		//Draw Stars/background2
		spriteBatch.draw(Resources.background2, 0, -SpaceWaste.SCREEN_HEIGHT*time/MAX_TIME);
		spriteBatch.draw(Resources.background2, 0, -SpaceWaste.SCREEN_HEIGHT*(time/MAX_TIME-1));
		
		//Draw Stars layer 2 (note, it's flipped on the X and Y axis to not look like the same image twice)
		spriteBatch.draw(Resources.background2, 0, -SpaceWaste.SCREEN_HEIGHT*(time2/MAX_TIME-1),
				SpaceWaste.SCREEN_WIDTH, SpaceWaste.SCREEN_HEIGHT, 0, 0, Resources.background2.getWidth(),
				Resources.background2.getHeight(), true, true);
		spriteBatch.draw(Resources.background2, 0, -SpaceWaste.SCREEN_HEIGHT*time2/MAX_TIME,
				SpaceWaste.SCREEN_WIDTH, SpaceWaste.SCREEN_HEIGHT, 0, 0, Resources.background2.getWidth(),
				Resources.background2.getHeight(), true, true);
		
		spriteBatch.end();
	}
	
	/**
	 * Draw the HUD and score
	 * @param delta time since last call to this method in seconds
	 */
	private void renderHUD(float delta){
		//Update game time used for score, only if it isn't game over yet
		if(!gameOver)
			gameTime += delta;
		
		spriteBatch.begin();
		
		//Draw HUD
		spriteBatch.draw(Resources.hud, 0, 0);
		
		//Draw Text
		String toPrint = "Score: "+(int)gameTime;
		
		Resources.font.draw(spriteBatch, toPrint,
				(SpaceWaste.SCREEN_WIDTH - Resources.font.getBounds(toPrint).width)/2,
				(SpaceWaste.SCREEN_HEIGHT/4 - Resources.font.getLineHeight())/2);
		
		spriteBatch.end();
		
		
	}
	
	/**
	 * Updates an Entity object's model and renders it.
	 * @param ent Entity to update and render
	 */
	public void renderEntity(Entity ent){
		//Entity's location as 3D vector
		Vector3 ploc = new Vector3(ent.getLocation().x, ent.getLocation().y, 0); 
		//Subtract Height offset/difference
		ploc.sub(0, SpaceWaste.SCREEN_HEIGHT/2-SpaceWaste.MENU_SIZE-ent.getHeight()/2, 0);
		//Subtract center width & height
		ploc.sub(SpaceWaste.SCREEN_WIDTH/2, pcamy, 0);
		//Scale change from center for the camera
		ploc.scl(ocam.zoom); //Player's location in relation to camera
		//Add center width & height back
		ploc.add(SpaceWaste.SCREEN_WIDTH/2, pcamy, 0);
		//Add model y-offset
		ploc.add(0, ent.getHeight()/2*ocam.zoom, 0);
		
		//Transform the model
		ent.getModel().transform.setTranslation(ploc);
				
		//Draw Batch
		modelBatch.begin(ocam);
		modelBatch.render(ent.getModel(), environment);
		modelBatch.end();
	}
	
	/**
	 * Called when the game is over. Sets the gameOver boolean to true
	 */
	public void gameOver(){
		gameOver = true;
	}
	
	/**
	 * Disposes the components loaded in this class
	 * Disposes all batches (modelBatch and spriteBatch)
	 */
	public void dispose(){
		modelBatch.dispose();
		spriteBatch.dispose();
	}
}
