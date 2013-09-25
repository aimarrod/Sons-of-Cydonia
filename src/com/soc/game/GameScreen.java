package com.soc.game;

import org.lwjgl.Sys;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.PlayerManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
<<<<<<< HEAD:src/com/soc/game/GameScreen.java
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTile.BlendMode;
import com.soc.game.systems.AttackCollisionSystem;
import com.soc.game.systems.AttackRenderSystem;
import com.soc.game.systems.CameraSystem;
import com.soc.game.systems.CharacterRenderSystem;
import com.soc.game.systems.EntitySpawningTimerSystem;
import com.soc.game.systems.MapCollisionSystem;
import com.soc.game.systems.MapRenderSystem;
import com.soc.game.systems.MovementSystem;
import com.soc.game.systems.PlayerInputSystem;
import com.soc.utils.EntityFactory;
=======
import com.soc.systems.AttackRenderSystem;
import com.soc.systems.AttackCollisionSystem;
import com.soc.systems.CameraSystem;
import com.soc.systems.CharacterRenderSystem;
import com.soc.systems.EnemyActuatorSystem;
import com.soc.systems.EntitySpawningTimerSystem;
import com.soc.systems.MapCollisionSystem;
import com.soc.systems.MapRenderSystem;
import com.soc.systems.MovementSystem;
import com.soc.systems.PlayerInputSystem;
>>>>>>> cd07d7dfa7cb144638952edcacfc8c0e5294c97c:src/com/soc/GameSOC.java
import com.soc.utils.MapLoader;

public class GameScreen implements Screen {

	private OrthographicCamera camera;
	private Game game;
	private World world;
	private CharacterRenderSystem characterRenderSystem;
	private AttackRenderSystem animationAttackSystem;
	private CameraSystem cameraSystem;
	private MapRenderSystem mapRenderSystem;
	
	public GameScreen(Game game, String mapName) {
		
		this.camera = new OrthographicCamera();
		this.game=game;
		
		world = new World();
		
		//CreateMap/home/obssidian/Development
		TiledMap map = MapLoader.loadMap(mapName);

		world.setManager(new GroupManager());
		world.setManager(new PlayerManager());
		
		//Regular Systems
	    world.setSystem(new PlayerInputSystem());
	    world.setSystem(new MapCollisionSystem(map));
	    world.setSystem(new MovementSystem());
	    world.setSystem(new EntitySpawningTimerSystem());
	    world.setSystem(new AttackCollisionSystem());
	    world.setSystem(new EnemyActuatorSystem());
	    
	    //Specially treated systems
	    
	    characterRenderSystem = world.setSystem( new CharacterRenderSystem(camera) , true );
		mapRenderSystem = world.setSystem( new MapRenderSystem(map, camera), true );
		cameraSystem = world.setSystem( new CameraSystem(camera), true);
		animationAttackSystem=world.setSystem(new AttackRenderSystem(camera),true);
		
		world.initialize();
		
		camera.setToOrtho(false, 1280, 900);
		
		EntityFactory.initialize(world);
		EntityFactory.instance.createWarrior(2000, 300,10,10);
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
