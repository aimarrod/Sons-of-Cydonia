package com.soc.utils;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.PlayerManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.soc.game.attacks.DaggerThrow;
import com.soc.game.attacks.Icicle;
import com.soc.game.attacks.Punch;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Character;
import com.soc.game.components.Enemy;
import com.soc.game.components.Expires;
import com.soc.game.components.Feet;
import com.soc.game.components.Flying;
import com.soc.game.components.Movement;
import com.soc.game.components.Player;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.game.systems.CharacterRenderSystem;


public class EntityFactory {
	
	public static EntityFactory instance;
	private World world;
	
	private EntityFactory(World world){
		this.world = world;
		world.getSystem(CharacterRenderSystem.class);
	}
	
	public static EntityFactory initialize(com.artemis.World world){
		instance = new EntityFactory(world);
		return instance;
	}
	
	public static EntityFactory getInstance(){
		return instance;
	}

	public void createCharacter(float px, float py, float range, int damage, int type){
		Entity e = world.createEntity();
		Character animations = new Character();
		
	    e.addComponent(new Position(px,py));
	    e.addComponent(new Player());
	    e.addComponent(new Velocity(0,0,200));
	    e.addComponent(new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT));
	    e.addComponent(new Stats(100, 50, 0, 100, 100, 0, 1, 1, 1, 1, 1, Constants.Spells.DAGGER_THROW, new int[]{}));
	    e.addComponent(new State(0));
	    e.addComponent(new Movement());
	    e.addComponent(new Feet(32, 10));
	    e.addComponent(animations);
	    
	    if(type == Constants.Classes.HUNTER){
	    	animations.idle = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/archer-walk.png")), 1.0f, 64, 64, false);
		    animations.movement = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/archer-walk.png")), 1.0f, 64, 64, true);
		    animations.attack = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/archer-attack.png")), 0.4f, 64, 64, false);
	    } else if(type == Constants.Classes.WARRIOR){
	    	GraphicsLoader.loadWarrior(animations);
	    } else if(type == Constants.Classes.MAGE){
	    	animations.idle = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/mage-walk.png")), 1.0f, 64, 64, false);
	 	    animations.movement = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/mage-walk.png")), 1.0f, 64, 64, true);
	 	    animations.attack = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/mage-attack.png")), 0.4f, 64, 64, false);
	    } else {
	    	
	    }
	    
	    e.addToWorld();
	    world.getManager(PlayerManager.class).setPlayer(e, Constants.Groups.PLAYERS);
	    Globals.playerPosition = e.getComponent(Position.class);
	    Globals.playerStats = e.getComponent(Stats.class);
	    Globals.playerControls = e.getComponent(Player.class);
	    Globals.initializeSpells();
	}
	
	
	public Entity createSkeleton(float px, float py, int damage, float range){
		Entity e = world.createEntity();
	    e.addComponent(new Position(px,py));
	    e.addComponent(new Velocity(0,0,100));
	    e.addComponent(new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT));
	    e.addComponent(new State(1));
	    e.addComponent(new Stats(10, 0, 0, 10, 0, 0, 1, 1, 1, 1, 1, Constants.Spells.PUNCH, new int[]{}));
	    e.addComponent(new Movement());
	    e.addComponent(new Enemy(600,10));
	    
	    Character animations = new Character();
	    animations.idle = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/skeleton-walk.png")), 1.0f, 64, 64, false);
	    animations.movement = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/skeleton-walk.png")), 1.0f, 64, 64, true);
	    animations.attack = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/skeleton-attack.png")), 1.0f, 64, 64, false);
	    animations.death=GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/skeleton-death.png")), 1.0f, 64, 64, false);
	    e.addComponent(animations);
	    
	    e.addToWorld();
	    world.getManager(GroupManager.class).add(e, Constants.Groups.ENEMIES);
	    world.getManager(GroupManager.class).add(e, Constants.Groups.SKELETONS);
	    
	    return e;
	}
	
	public Entity createDaggerThrow(String group, Position pos, int damage, int range, Vector2 dir){
		Entity e=world.createEntity();
		
		e.addComponent( new Position(pos.x,pos.y) );
		e.addComponent( new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT) );
		e.addComponent( new Velocity(Constants.Spells.DAGGER_SPEED*dir.x, Constants.Spells.DAGGER_SPEED*dir.y,900) );
	   	e.addComponent( new Flying());
	   	e.addComponent( new Attack(new DaggerThrow(range, pos), damage) );
 
	    world.getManager(GroupManager.class).add(e, group);
	   	e.addToWorld();
	   	
	   	return e;
	}
	
	public Entity createIcicle(String group ,Position pos, int damage, int range, Vector2 dir){
		Entity e=world.createEntity();
				
		e.addComponent( new Position(pos.x,pos.y) );
		e.addComponent( new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT) );
		e.addComponent( new Velocity(300*dir.x, 300*dir.y, Constants.Spells.DAGGER_SPEED) );
	   	e.addComponent( new Flying());
	   	e.addComponent( new Attack(new Icicle(dir, range), damage ) );
 
	    world.getManager(GroupManager.class).add(e, group);
	   	e.addToWorld();
	   	
	   	return e;
	}
	
	public Entity createFireball(String group, Position pos, int damage, int range, Vector2 dir){
		Entity e=world.createEntity();
				
		e.addComponent( new Position(pos.x, pos.y) );
		e.addComponent( new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT) );
		e.addComponent( new Velocity(300*dir.x, 300*dir.y, Constants.Spells.DAGGER_SPEED) );
	   	e.addComponent( new Flying());
	   	e.addComponent( new Attack(new Icicle(dir, range), damage) );
 
	    world.getManager(GroupManager.class).add(e, group);
	   	e.addToWorld();
	   	
	   	return e;
	}
	
	public Entity createPunch(String group, Position pos, int damage, int range, Vector2 dir){
		Entity e=world.createEntity();
				
		e.addComponent( new Position(pos.x, pos.y) );
		e.addComponent( new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT) );
		e.addComponent( new Velocity(0, 0, Constants.Spells.DAGGER_SPEED) );
	   	e.addComponent( new Attack(new Punch(dir, range), damage) );
	   	
	    world.getManager(GroupManager.class).add(e, group);
	   	e.addToWorld();
	   	
	   	return e;
	}
}
