package com.soc.core;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.soc.core.Constants.Attributes;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Character;
import com.soc.game.components.Damage;
import com.soc.game.components.Delay;
import com.soc.game.components.Enemy;
import com.soc.game.components.Expires;
import com.soc.game.components.Feet;
import com.soc.game.components.Player;
import com.soc.game.components.Position;
import com.soc.game.components.Spawner;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.game.map.Map;
import com.soc.game.spells.ChargeSpell;
import com.soc.game.spells.DaggerThrowSpell;
import com.soc.game.spells.PunchSpell;
import com.soc.game.spells.SlashSpell;
import com.soc.game.spells.Spell;
import com.soc.game.systems.AttackDelaySystem;
import com.soc.game.systems.AttackProcessingSystem;
import com.soc.game.systems.CameraSystem;
import com.soc.game.systems.DamageProcessingSystem;
import com.soc.game.systems.EnemyActuatorSystem;
import com.soc.game.systems.CollisionSystem;
import com.soc.game.systems.EntitySpawningTimerSystem;
import com.soc.game.systems.ExpiringSystem;
import com.soc.game.systems.PushProcessingSystem;
import com.soc.game.systems.RenderSystem;
import com.soc.game.systems.MovementSystem;
import com.soc.game.systems.PlayerInputSystem;
import com.soc.hud.HudSystem;
import com.soc.screens.GameOverScreen;
import com.soc.screens.SplashScreen;
import com.soc.utils.EffectsPlayer;
import com.soc.utils.LevelManager;
import com.soc.utils.MapLoader;
import com.soc.utils.MusicPlayer;
import com.soc.utils.SpawnerManager;

public class SoC extends Game {
	public static final int FRAME_WIDTH = 1440;
	public static final int FRAME_HEIGHT = 900;
	
	public static SoC game;
	public OrthographicCamera camera;
	public Entity player;
	public World world;
	public Spell[] spells;
	
	public ComponentMapper<Position> positionmapper;
	public ComponentMapper<Feet> feetmapper;
	public ComponentMapper<Bounds> boundsmapper;
	public ComponentMapper<Stats> statsmapper;
	public ComponentMapper<Player> playermapper;
	public ComponentMapper<Enemy> enemymapper;
	public ComponentMapper<Expires> expiresmapper;
	public ComponentMapper<Character> charactermapper;
	public ComponentMapper<State> statemapper;
	public ComponentMapper<Velocity> velocitymapper;
	public ComponentMapper<Delay> delaymapper;
	public ComponentMapper<Attack> attackmapper;
	public ComponentMapper<Damage> damagemapper;
	public ComponentMapper<Spawner> spawnermapper;
	
	public GroupManager groupmanager;
	public TagManager tagmanager;
	public LevelManager levelmanager;
	public SpawnerManager spawnermanager;
	
	public HudSystem hudSystem;
	public RenderSystem renderSystem;
	public CameraSystem cameraSystem;
	public InputMultiplexer inputMultiplexer;
	
	public Map map;
	
	public Stack<Screen> screens;
		
	
	
	@Override
	public void create() {		
		SoC.game = this;
		
		world = new World();
		positionmapper = world.getMapper(Position.class);
		feetmapper = world.getMapper(Feet.class);
		boundsmapper = world.getMapper(Bounds.class);
		statsmapper = world.getMapper(Stats.class);
		playermapper = world.getMapper(Player.class);
		expiresmapper = world.getMapper(Expires.class);
		charactermapper = world.getMapper(Character.class);
		statemapper = world.getMapper(State.class);
		velocitymapper = world.getMapper(Velocity.class);
		delaymapper = world.getMapper(Delay.class);
		attackmapper = world.getMapper(Attack.class);
		damagemapper = world.getMapper(Damage.class);
		spawnermapper = world.getMapper(Spawner.class);
		enemymapper = world.getMapper(Enemy.class);
		
		inputMultiplexer=new InputMultiplexer();
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		spells = new Spell[Constants.Spells.SPELL_NUMBER];
		spells[Constants.Spells.DAGGER_THROW] = new DaggerThrowSpell(); 
		spells[Constants.Spells.PUNCH]=new PunchSpell();
		spells[Constants.Spells.SLASH]=new SlashSpell();
		spells[Constants.Spells.CHARGE]=new ChargeSpell();
		
		camera = new OrthographicCamera();
		
		player = EntityFactory.createCharacter(1400, 3000, 1, 200, 0, Constants.Classes.WARRIOR);

		groupmanager = new GroupManager();
		tagmanager = new TagManager();
		levelmanager = new LevelManager();
		spawnermanager = new SpawnerManager();
		
		world.setManager(spawnermanager);
		world.setManager(groupmanager);
		world.setManager(tagmanager);
		world.setManager(levelmanager);
		
	    world.setSystem(new PlayerInputSystem());
		world.setSystem(new AttackDelaySystem());
		world.setSystem(new AttackProcessingSystem());
	    world.setSystem(new EnemyActuatorSystem());
	    world.setSystem(new EntitySpawningTimerSystem());
	    world.setSystem(new CollisionSystem());	
	    world.setSystem(new DamageProcessingSystem());
	    world.setSystem(new PushProcessingSystem());
	    world.setSystem(new MovementSystem());
	    world.setSystem(new ExpiringSystem());
	    
		cameraSystem = SoC.game.world.setSystem( new CameraSystem(camera), true);
		renderSystem = SoC.game.world.setSystem( new RenderSystem(camera), true );
		hudSystem = SoC.game.world.setSystem(new HudSystem(camera));
		
		SoC.game.world.initialize();
		
		//Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, true);
		screens=new Stack<Screen>();
		MusicPlayer.initialize();
		EffectsPlayer.initialize();
		load("initial");
		//GameManager.instance.openSplashScreen();
		//GameManager.instance.closeSplashScreen();
		
	}
	
	public void openMenu(){
		SoC.game.screens.push(SoC.game.getScreen());
		//TODO: Open Menu
	}
	
	public void changeMap(String name){
		MapLoader.loadMap(name);
	}
	
	public void resetWorld(){
		ImmutableBag<Entity> enemies = world.getManager(GroupManager.class).getEntities(Constants.Groups.MAP_BOUND);
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).deleteFromWorld();
		}
	}
	
	public void closeMenu(){
		Screen menu = getScreen();
		setScreen(screens.pop());
		menu.dispose();
	}
	
	public void openSplashScreen(){
		setScreen(new SplashScreen(this));
	}
	public void closeSplashScreen(){
		Screen splash = getScreen();
		setScreen(screens.pop());
		splash.dispose();
	}
	public void openGameOverScren(){
		game.setScreen(new GameOverScreen(game));
	}
	
	public void load(String level){
		String map = "";
		String music = "";
		JsonReader json = new JsonReader();
		JsonValue node = json.parse(Gdx.files.internal(Constants.Configuration.LEVEL_DIR + level + ".json")).child;
		while(node != null){
			if(node.name.equals(Attributes.MAP)){
				map = node.asString();
			}
			if(node.name.equals(Attributes.MUSIC)){
				music = node.asString();
			}
			node = node.next;
		}
		MusicPlayer.instance.reset();
		setScreen(new GameScreen(map));
	}

	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = FRAME_WIDTH;
		cfg.height = FRAME_HEIGHT;
		cfg.useGL20 = true;
		//cfg.resizable = false;
		cfg.title = "GameXYZ";
		new LwjglApplication(new SoC(), cfg);
	}

}
