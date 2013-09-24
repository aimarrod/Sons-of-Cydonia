package com.soc;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.soc.systems.AttackRenderSystem;
import com.soc.systems.AttackCollisionSystem;
import com.soc.systems.CameraSystem;
import com.soc.systems.CharacterRenderSystem;
import com.soc.systems.EntitySpawningTimerSystem;
import com.soc.systems.MapCollisionSystem;
import com.soc.systems.MapRenderSystem;
import com.soc.systems.MovementSystem;
import com.soc.systems.PlayerInputSystem;
import com.soc.utils.MapLoader;

public class GameSOC implements Screen {

	private OrthographicCamera camera;
	private Game game;
	private World world;
	private CharacterRenderSystem characterRenderSystem;
	private AttackRenderSystem animationAttackSystem;
	private CameraSystem cameraSystem;
	private MapRenderSystem mapRenderSystem;
	
	public GameSOC(Game game) {
		this.camera = new OrthographicCamera();
		this.game=game;
		
		world = new World();
		
		//CreateMap
		TiledMap map = MapLoader.loadMap("initial");

		world.setManager(new GroupManager());
		
		//Regular Systems
	    world.setSystem(new PlayerInputSystem(camera));
	    world.setSystem(new MapCollisionSystem(map));
	    world.setSystem(new MovementSystem());
	    world.setSystem(new EntitySpawningTimerSystem());
	    world.setSystem(new AttackCollisionSystem());
	    
	    //Specially treated systems
	    
	    characterRenderSystem = world.setSystem( new CharacterRenderSystem(camera) , true );
		mapRenderSystem = world.setSystem( new MapRenderSystem(map, camera), true );
		cameraSystem = world.setSystem( new CameraSystem(camera), true);
		animationAttackSystem=world.setSystem(new AttackRenderSystem(camera),true);
		
		world.initialize();
		
		camera.setToOrtho(false, 1280, 900);
		
		EntityFactory.initialize(world);
		EntityFactory.instance.createMage(2000, 300,10,10);
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		 camera.update();
		 world.setDelta(delta);
	     world.process();
	     
	     mapRenderSystem.process();
	     characterRenderSystem.process();
	     cameraSystem.process();
	     animationAttackSystem.process();
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
