package com.soc;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.PlayerManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.soc.components.Attack;
import com.soc.components.Attacker;
import com.soc.components.Bounds;
import com.soc.components.CharacterAnimations;
import com.soc.components.Enemy;
import com.soc.components.Flying;
import com.soc.components.Health;
import com.soc.components.Movement;
import com.soc.components.Player;
import com.soc.components.Position;
import com.soc.components.State;
import com.soc.components.Velocity;
import com.soc.systems.CharacterRenderSystem;
import com.soc.components.CharacterAnimations;;

public class EntityFactory {
	
	public static EntityFactory instance;
	private World world;
	private CharacterRenderSystem characterRenderer; 
	
	private EntityFactory(World world){
		this.world = world;
		this.characterRenderer = world.getSystem(CharacterRenderSystem.class);
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
	    e.addComponent(new Velocity(0,0));
	    e.addComponent(new Bounds(32, 32));
	    e.addComponent(new State(0,0));
	    e.addComponent(new Movement());
	    e.addComponent(new Attacker(range, damage, Constants.Attacks.DAGGER_ATTACK));
	    
	    CharacterAnimations animations = new CharacterAnimations();
	    animations.idle = AnimationLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/archer-walk.png")), 1.0f, 64, 64, false);
	    animations.movement = AnimationLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/archer-walk.png")), 1.0f, 64, 64, true);
	    animations.attack = AnimationLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/archer-attack.png")), 0.4f, 64, 64, false);
	    e.addComponent(animations);
	   	
	    e.addToWorld();
	    //world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER);  
	    //El string no sé para que sirve la verdad 
	    world.getManager(PlayerManager.class).setPlayer(e, Constants.Groups.PLAYER);
	}
	
	public void createWarrior(float px, float py, int damage, float range){
		Entity e = world.createEntity();
	    e.addComponent(new Position(px,py));
	    e.addComponent(new Player());
	    e.addComponent(new Velocity(0,0));
	    e.addComponent(new Bounds(32, 32));
	    e.addComponent(new State(0,0));
	    e.addComponent(new Movement());
	    e.addComponent(new Attacker(range,damage, Constants.Attacks.DAGGER_ATTACK));
	   
	    CharacterAnimations animations = new CharacterAnimations();
	    animations.idle = AnimationLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/warrior-walk.png")), 1.0f, 64, 64, false);
	    animations.movement = AnimationLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/warrior-walk.png")), 1.0f, 64, 64, true);
	    animations.attack = AnimationLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/warrior-attack.png")), 0.4f, 64, 64, false);
	    e.addComponent(animations);
	    
	    e.addToWorld();
//	    world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER);
	    world.getManager(PlayerManager.class).setPlayer(e, Constants.Groups.PLAYER);
	}
	
	public Entity createMage(float px, float py, int damage, float range){
		Entity e = world.createEntity();
	    e.addComponent(new Position(px,py));
	    e.addComponent(new Player());
	    e.addComponent(new Velocity(0,0));
	    e.addComponent(new Bounds(32, 32));
	    e.addComponent(new State(0,0));
	    e.addComponent(new Movement());
	    e.addComponent(new Attacker(range,damage, Constants.Attacks.DAGGER_ATTACK));
	    
	    CharacterAnimations animations = new CharacterAnimations();
	    animations.idle = AnimationLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/mage-walk.png")), 1.0f, 64, 64, false);
	    animations.movement = AnimationLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/mage-walk.png")), 1.0f, 64, 64, true);
	    animations.attack = AnimationLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/mage-attack.png")), 0.4f, 64, 64, false);
	    e.addComponent(animations);
	    
	    e.addToWorld();
//	    world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER);
	    world.getManager(PlayerManager.class).setPlayer(e, Constants.Groups.PLAYER);
	    return e;
	}
	
	
//	public Entity createAttack(float x, float y, int attackType, int damage, float range, Vector2 dir){
//		Entity e=world.createEntity();
//		WeaponAttack weaponAttack=new WeaponAttack(range,damage);
//		e.addComponent(weaponAttack);
//		Position position=new Position(x,y);
//		e.addComponent(position);
//		Velocity v=new Velocity(Constants.Attacks.DAGGER_SPEED*dir.x, Constants.Attacks.DAGGER_SPEED*dir.y);
//		e.addComponent(v);
//		Bounds b=new Bounds(10,10);
//		e.addComponent(b); 
//	   	AnimationLoader.loadProjectileSpriteSheet(new Texture(Gdx.files.internal("resources/dagger-attack.png")), weaponAttack, 0.2f, 64, 64);		
//	   	e.addComponent(new Flying());
//	   	e.addToWorld();
//	    world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER_PROJECTILES);
//
//	   	return e;
//	}
	
	public Entity createSkeleton(float px, float py, int damage, float range){
		Entity e = world.createEntity();
	    e.addComponent(new Position(px,py));
	    e.addComponent(new Velocity(10,10));
	    e.addComponent(new Bounds(32, 32));
	    e.addComponent(new State(1,0));
	    e.addComponent(new Health(10));
	    e.addComponent(new Movement());
	    e.addComponent(new Attacker(range,damage, Constants.Attacks.SWORD_ATTACK));
	    e.addComponent(new Enemy());
	    
	    CharacterAnimations animations = new CharacterAnimations();
	    animations.idle = AnimationLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/skeleton-walk.png")), 1.0f, 64, 64, false);
	    animations.movement = AnimationLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/skeleton-walk.png")), 1.0f, 64, 64, true);
	    animations.attack = AnimationLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/skeleton-attack.png")), 0.4f, 64, 64, false);
	    e.addComponent(animations);
	    
	    e.addToWorld();
	    world.getManager(GroupManager.class).add(e, Constants.Groups.ENEMIES);
	    
	    return e;
	}
	
	public void createDaggerThrow(float x, float y, int attackType, int damage, float range, Vector2 dir){
		Entity e=world.createEntity();
		
		e.addComponent( new Position(x,y) );
		e.addComponent( new Bounds(50,50) );
		e.addComponent( new Velocity(Constants.Attacks.DAGGER_SPEED*dir.x, Constants.Attacks.DAGGER_SPEED*dir.y) );
	   	e.addComponent( new Flying());
	   	e.addComponent( new Attack(AnimationLoader.loadDaggerThrow(), damage) );
 
	    world.getManager(GroupManager.class).add(e, Constants.Groups.PLAYER_PROJECTILES);
	   	e.addToWorld();
	}
	
	

}
