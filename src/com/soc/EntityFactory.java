package com.soc;

import com.artemis.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.soc.components.Bounds;
import com.soc.components.Player;
import com.soc.components.Position;
import com.soc.components.Sprite;
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

	public Entity createPlayer(float px, float py){
		Entity e = entityWorld.createEntity();
	    e.addComponent(new Position(px,py));
	    e.addComponent(new Sprite());
	    e.addComponent(new Player());
	    e.addComponent(new Velocity(0,0));
	    e.addComponent(new Bounds(32, 32));
	    e.addToWorld();
	    
	    return e;
	}
}
