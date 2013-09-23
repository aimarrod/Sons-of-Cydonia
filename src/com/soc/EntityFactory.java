package com.soc;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.soc.components.Attacker;
import com.soc.components.Bounds;
import com.soc.components.Movement;
import com.soc.components.Player;
import com.soc.components.Position;
import com.soc.components.State;
import com.soc.components.Velocity;
import com.soc.components.WeaponAttack;

public class EntityFactory {
	
	public static EntityFactory instance;
	private com.artemis.World entityWorld;
	
	private EntityFactory(com.artemis.World entityWorld){
		this.entityWorld = entityWorld;
	}
	
	public static EntityFactory initialize(com.artemis.World entityWorld){
		instance = new EntityFactory(entityWorld);
		return instance;
	}
	
	public static EntityFactory getInstance(){
		return instance;
	}

	public Entity createArcher(float px, float py, float range, int damage){
		Entity e = entityWorld.createEntity();
	    e.addComponent(new Position(px,py));
	    e.addComponent(new Player());
	    e.addComponent(new Velocity(0,0));
	    e.addComponent(new Bounds(32, 32));
	    e.addComponent(new State(0,0));
	    
	    Movement movement = new Movement();
	    
	   	AnimationLoader.loadHumanoidSpriteSheet(new Texture(Gdx.files.internal("resources/archer-walk.png")), movement, 1.0f);
	   	e.addComponent(movement);
	    
	   	Attacker attack = new Attacker(range, damage);
	   	AnimationLoader.loadHumanoidSpriteSheet(new Texture(Gdx.files.internal("resources/archer-attack.png")), attack, 0.4f);
	   	e.addComponent(attack);
	   	
	    e.addToWorld();
	    
	    
	    return e;
	}
	
	public Entity createWarrior(float px, float py, int damage, float range){
		Entity e = entityWorld.createEntity();
	    e.addComponent(new Position(px,py));
	    e.addComponent(new Player());
	    e.addComponent(new Velocity(0,0));
	    e.addComponent(new Bounds(32, 32));
	    e.addComponent(new State(0,0));
	    
	    Movement movement = new Movement();
	    
	   	AnimationLoader.loadHumanoidSpriteSheet(new Texture(Gdx.files.internal("resources/warrior-walk.png")), movement, 1.0f);
	   	e.addComponent(movement);
	    
	   	Attacker attack = new Attacker(range,damage);
	   	AnimationLoader.loadHumanoidSpriteSheet(new Texture(Gdx.files.internal("resources/warrior-attack.png")), attack, 0.2f);
	   	e.addComponent(attack);
	   	
	    e.addToWorld();
	    
	    
	    return e;
	}
	public Entity createAttack(float x, float y, int attackType, int damage, float range){
		Entity e=entityWorld.createEntity();
		WeaponAttack weaponAttack=new WeaponAttack(range,damage);
		e.addComponent(weaponAttack);
		Position position=new Position(x,y);
		e.addComponent(position);
		Velocity v=new Velocity(100,100);
		e.addComponent(v);
		Bounds b=new Bounds(10,10);
		e.addComponent(b); 
	   	AnimationLoader.loadHumanoidSpriteSheet(new Texture(Gdx.files.internal("resources/dague-attack.png")), weaponAttack, 0.2f);		
	   	e.addToWorld();
	   	return e;
	}
	

}
