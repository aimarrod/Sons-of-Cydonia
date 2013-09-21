package com.soc;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.soc.components.Player;
import com.soc.components.Position;
import com.soc.components.Sprite;
import com.soc.components.Velocity;
import com.soc.systems.CameraSystem;
import com.soc.systems.Map;
import com.soc.systems.MovementSystem;
import com.soc.systems.PlayerInputSystem;
import com.soc.systems.SpriteRenderSystem;

public class GameSOC implements Screen {

	private OrthographicCamera camera;
	private Game game;
	private com.artemis.World entityWorld;
	private com.badlogic.gdx.physics.box2d.World physicsWorld;
	private SpriteRenderSystem spriteRenderSystem;
	private CameraSystem cameraSystem;
	private Map map;
	
	public GameSOC(Game game) {
		this.camera = new OrthographicCamera();
		this.game=game;
		
		physicsWorld = new com.badlogic.gdx.physics.box2d.World(new Vector2(0,0), true);
		
		//Regular Systems
		entityWorld = new World();
	    entityWorld.setSystem(new PlayerInputSystem(camera));
	    entityWorld.setSystem(new MovementSystem());
	    
	    //Specially treated systems
		spriteRenderSystem = entityWorld.setSystem( new SpriteRenderSystem(camera), true );
		cameraSystem = entityWorld.setSystem( new CameraSystem(camera), true);
		
		map = new Map("initial", camera);
		
		entityWorld.initialize();
		
		camera.setToOrtho(false, 1280, 900);
		
		EntityFactory.createPlayer(entityWorld, physicsWorld);
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();
		 entityWorld.setDelta(delta);
	     entityWorld.process();
	     map.render();
	     spriteRenderSystem.process();
	     cameraSystem.process();
	}

	@Override
	public void resize(int width, int height) {
		System.out.println("Resize");

	}

	@Override
	public void show() {
		System.out.println("Show");
	}

	@Override
	public void hide() {
		System.out.println("Hide");
	}

	@Override
	public void pause() {
		System.out.println("Pause");
	}

	@Override
	public void resume() {
		System.out.println("Resume");
	}

	@Override
	public void dispose() {
		System.out.println("Dispose");
	}

}
