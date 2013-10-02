package com.soc.game;

import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.PlayerManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.soc.game.systems.AttackDelaySystem;
import com.soc.game.systems.AttackProcessingSystem;
import com.soc.game.systems.AttackRenderSystem;
import com.soc.game.systems.CameraSystem;
import com.soc.game.systems.CharacterRenderSystem;
import com.soc.game.systems.DamageProcessingSystem;
import com.soc.game.systems.EnemyActuatorSystem;
import com.soc.game.systems.EntityCollisionSystem;
import com.soc.game.systems.EntitySpawningTimerSystem;
import com.soc.game.systems.ExpiringSystem;
import com.soc.game.systems.MapCollisionSystem;
import com.soc.game.systems.MapRenderSystem;
import com.soc.game.systems.MovementSystem;
import com.soc.game.systems.PlayerInputSystem;
import com.soc.hud.HudSystem;
import com.soc.utils.Constants;
import com.soc.utils.EntityFactory;
import com.soc.utils.Globals;
import com.soc.utils.MapLoader;

public class GameScreen implements Screen {

	private OrthographicCamera camera;
	private Game game;
	private World world;
	private CharacterRenderSystem characterRenderSystem;
	private AttackRenderSystem animationAttackSystem;
	private CameraSystem cameraSystem;
	private MapRenderSystem mapRenderSystem;
	private HudSystem hudSystem;
	private ExpiringSystem expiringSystem;
	private FPSLogger fps;
	
	public GameScreen(Game game, String mapName) {
		
		this.camera = new OrthographicCamera();
		this.game=game;
		fps=new FPSLogger();
		world = new World();
		
		TiledMap map = MapLoader.loadMap(mapName);

		world.setManager(new GroupManager());
		world.setManager(new PlayerManager());
		world.setManager(new TagManager());
		
		world.setSystem(new AttackDelaySystem());
		world.setSystem(new AttackProcessingSystem());
	    world.setSystem(new EnemyActuatorSystem());
	    world.setSystem(new PlayerInputSystem());
	    world.setSystem(new MapCollisionSystem(map));
	    world.setSystem(new MovementSystem());
	    world.setSystem(new EntitySpawningTimerSystem());
	    world.setSystem(new EntityCollisionSystem());	
	    world.setSystem(new DamageProcessingSystem());

	    
	    expiringSystem=world.setSystem(new ExpiringSystem());
		cameraSystem = world.setSystem( new CameraSystem(camera), true);
	    characterRenderSystem = world.setSystem( new CharacterRenderSystem(camera) , true );
		mapRenderSystem = world.setSystem( new MapRenderSystem(map, camera), true );
		animationAttackSystem=world.setSystem(new AttackRenderSystem(camera),true);
		hudSystem = world.setSystem(new HudSystem(camera));
		
		world.initialize();
		Globals.initializeWorld(world);
		
		camera.setToOrtho(false, 1280, 900);
		
		EntityFactory.initialize(world);
		EntityFactory.instance.createCharacter(2000, 300, 500, 5, Constants.Classes.WARRIOR);
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		world.setDelta(delta);
	    world.process();
	     
	    cameraSystem.process();
	    mapRenderSystem.process();
	    characterRenderSystem.process();
	    animationAttackSystem.process();
	    hudSystem.process();
	    fps.log();

	}

	@Override
	public void resize(int width, int height) {
		hudSystem.setViewport(width, height);
		camera.setToOrtho(false, width, height);
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
