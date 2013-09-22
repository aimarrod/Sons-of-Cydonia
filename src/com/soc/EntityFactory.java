package com.soc;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.soc.components.Attack;
import com.soc.components.Movement;
import com.soc.components.Bounds;
import com.soc.components.Player;
import com.soc.components.Position;
import com.soc.components.Sprite;
import com.soc.components.State;
import com.soc.components.Velocity;

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

	public Entity createArcher(float px, float py){
		Entity e = entityWorld.createEntity();
	    e.addComponent(new Position(px,py));
	    e.addComponent(new Player());
	    e.addComponent(new Velocity(0,0));
	    e.addComponent(new Bounds(32, 32));
	    e.addComponent(new State(0,0));
	    
	    Movement movement = new Movement();
	    
	   	AnimationLoader.loadHumanoidSpriteSheet(new Texture(Gdx.files.internal("resources/archer-walk.png")), movement, 1.0f);
	   	e.addComponent(movement);
	    
	   	Attack attack = new Attack();
	   	AnimationLoader.loadHumanoidSpriteSheet(new Texture(Gdx.files.internal("resources/archer-attack.png")), attack, 0.4f);
	   	e.addComponent(attack);
	   	
	    e.addToWorld();
	    
	    
	    return e;
	}
	
	public Entity createWarrior(float px, float py){
		Entity e = entityWorld.createEntity();
	    e.addComponent(new Position(px,py));
	    e.addComponent(new Player());
	    e.addComponent(new Velocity(0,0));
	    e.addComponent(new Bounds(32, 32));
	    e.addComponent(new State(0,0));
	    
	    Movement movement = new Movement();
	    
	   	AnimationLoader.loadHumanoidSpriteSheet(new Texture(Gdx.files.internal("resources/warrior-walk.png")), movement, 1.0f);
	   	e.addComponent(movement);
	    
	   	Attack attack = new Attack();
	   	AnimationLoader.loadHumanoidSpriteSheet(new Texture(Gdx.files.internal("resources/warrior-attack.png")), attack, 0.2f);
	   	e.addComponent(attack);
	   	
	    e.addToWorld();
	    
	    
	    return e;
	}
	

}
