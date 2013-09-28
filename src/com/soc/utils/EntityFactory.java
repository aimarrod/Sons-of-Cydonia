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
import com.soc.game.components.Attack;
import com.soc.game.components.Attacker;
import com.soc.game.components.Bounds;
import com.soc.game.components.Character;
import com.soc.game.components.Enemy;
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

	public void createArcher(float px, float py, float range, int damage){
		Entity e = world.createEntity();
	    e.addComponent(new Position(px,py));
	    e.addComponent(new Player());
	    e.addComponent(new Velocity(0,0,200));
	    e.addComponent(new Bounds(Constants.Characters.WIDTH_PIXELS, Constants.Characters.HEIGHT_PIXELS));
	    e.addComponent(new State(0,0));
	    e.addComponent(new Movement());
	    e.addComponent(new Attacker(range, damage, Constants.Attacks.DAGGER_ATTACK));
	    
	    Character animations = new Character();
	    animations.idle = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/archer-walk.png")), 1.0f, 64, 64, false);
	    animations.movement = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/archer-walk.png")), 1.0f, 64, 64, true);
	    animations.attack = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/archer-attack.png")), 0.4f, 64, 64, false);
	    e.addComponent(animations);
	   	
	    e.addToWorld();
	    world.getManager(PlayerManager.class).setPlayer(e, Constants.Groups.PLAYER);
	}
	
	public void createWarrior(float px, float py, int damage, float range){
		Entity e = world.createEntity();
	    e.addComponent(new Position(px,py));
	    e.addComponent(new Player());
	    e.addComponent(new Velocity(0,0,500));
	    e.addComponent(new Bounds(Constants.Characters.WIDTH_PIXELS, Constants.Characters.HEIGHT_PIXELS));
	    e.addComponent(new State(0,0));
	    e.addComponent(new Movement());
	    e.addComponent(new Attacker(range, damage, Constants.Attacks.DAGGER_ATTACK));
	   
	    Character animations = new Character();
	    animations.idle = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/female-warrior-walk.png")), 1.0f, 64, 64, false);
	    animations.movement = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/female-warrior-walk.png")), 1.0f, 64, 64, true);
	    animations.attack = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/female-warrior-lance.png")), 0.4f, 64, 64, false);
	    e.addComponent(animations);
	    
	    e.addToWorld();
	    world.getManager(PlayerManager.class).setPlayer(e, Constants.Groups.PLAYER);
	}
	
	public Entity createMage(float px, float py, int damage, float range){
		Entity e = world.createEntity();
	    e.addComponent(new Position(px,py));
	    e.addComponent(new Player());
	    e.addComponent(new Velocity(0,0,400));
	    e.addComponent(new Bounds(Constants.Characters.WIDTH_PIXELS, Constants.Characters.HEIGHT_PIXELS));
	    e.addComponent(new State(0,0));
	    e.addComponent(new Movement());
	    e.addComponent(new Attacker(range,damage, Constants.Attacks.MAGIC_FIREBALL));
	    
	    Character animations = new Character();
	    animations.idle = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/mage-walk.png")), 1.0f, 64, 64, false);
	    animations.movement = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/mage-walk.png")), 1.0f, 64, 64, true);
	    animations.attack = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/mage-attack.png")), 0.4f, 64, 64, false);
	    e.addComponent(animations);
	    
	    e.addToWorld();
	    world.getManager(PlayerManager.class).setPlayer(e, Constants.Groups.PLAYER);
	    return e;
	}
	
	public Entity createSkeleton(float px, float py, int damage, float range){
		Entity e = world.createEntity();
	    e.addComponent(new Position(px,py));
	    e.addComponent(new Velocity(0,0,100));
	    e.addComponent(new Bounds(Constants.Characters.WIDTH_PIXELS, Constants.Characters.HEIGHT_PIXELS));
	    e.addComponent(new State(1,0));
	    e.addComponent(new Stats(10, 1, 1, 1, 1, 1, 1));
	    e.addComponent(new Movement());
	    e.addComponent(new Attacker(range,damage, Constants.Attacks.SWORD_ATTACK));
	    e.addComponent(new Enemy(600));
	    
	    Character animations = new Character();
	    animations.idle = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/skeleton-walk.png")), 1.0f, 64, 64, false);
	    animations.movement = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/skeleton-walk.png")), 1.0f, 64, 64, true);
	    animations.attack = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/skeleton-attack.png")), 0.4f, 64, 64, false);
	    e.addComponent(animations);
	    
	    e.addToWorld();
	    world.getManager(GroupManager.class).add(e, Constants.Groups.ENEMIES);
	    world.getManager(GroupManager.class).add(e, Constants.Groups.SKELETONS);
	    
	    return e;
	}
	
	public Entity createDaggerThrow(float x, float y, int damage, float range, Vector2 dir){
		Entity e=world.createEntity();
		
		e.addComponent( new Position(x,y) );
		e.addComponent( new Bounds(Constants.Characters.WIDTH_PIXELS, Constants.Characters.HEIGHT_PIXELS) );
		e.addComponent( new Velocity(Constants.Attacks.DAGGER_SPEED*dir.x, Constants.Attacks.DAGGER_SPEED*dir.y,900) );
	   	e.addComponent( new Flying());
	   	e.addComponent( new Attack(new DaggerThrow(600), damage) );
 
	    world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER_PROJECTILES);
	   	e.addToWorld();
	   	
	   	return e;
	}
	
	public Entity createIcicle(float x, float y, int damage, float range, Vector2 dir){
		Entity e=world.createEntity();
		
		e.addComponent( new Position(x,y) );
		e.addComponent( new Bounds(Constants.Characters.WIDTH_PIXELS, Constants.Characters.HEIGHT_PIXELS) );
		e.addComponent( new Velocity(300*dir.x, 300*dir.y, Constants.Attacks.DAGGER_SPEED) );
	   	e.addComponent( new Flying());
	   	e.addComponent( new Attack(new Icicle(dir, 600), damage) );
 
	    world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER_PROJECTILES);
	   	e.addToWorld();
	   	
	   	return e;
	}
	
	public Entity createFireball(float x, float y, int damage, float range, Vector2 dir){
		Entity e=world.createEntity();
		
		e.addComponent( new Position(x,y) );
		e.addComponent( new Bounds(Constants.Characters.WIDTH_PIXELS, Constants.Characters.HEIGHT_PIXELS) );
		e.addComponent( new Velocity(300*dir.x, 300*dir.y, Constants.Attacks.DAGGER_SPEED) );
	   	e.addComponent( new Flying());
	   	e.addComponent( new Attack(new Icicle(dir, 600), damage) );
 
	    world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER_PROJECTILES);
	   	e.addToWorld();
	   	
	   	return e;
	}
	
	public Entity createAttack(float x, float y, int type, int damage, float range, Vector2 dir){
		if(type==Constants.Attacks.DAGGER_ATTACK){
			return createDaggerThrow(x, y, damage, range, dir);
		}
		if(type==Constants.Attacks.MAGIC_ICICLE){
			return createIcicle(x, y, damage, range, dir);
		}
		if(type==Constants.Attacks.MAGIC_FIREBALL){
			return createFireball(x, y, damage, range, dir);
		}
		return null;
	}
	
	

}
