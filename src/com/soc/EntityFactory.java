package com.soc;

import com.artemis.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.soc.components.Player;
import com.soc.components.Position;
import com.soc.components.Sprite;
import com.soc.components.Velocity;

public class EntityFactory {
	
	static Entity createPlayer(com.artemis.World entityWorld, com.badlogic.gdx.physics.box2d.World physicsWorld){
		Entity e = entityWorld.createEntity();
	    e.addComponent(new Position(150,150));
	    e.addComponent(new Sprite());
	    e.addComponent(new Player());
	    e.addComponent(new Velocity(0,0));
	    e.addToWorld();
	    
	    BodyDef bodyDef = new BodyDef();
	    bodyDef.type = BodyType.KinematicBody;
	    //Hay que tener en cuenta la escala
	    bodyDef.position.set(150, 150);
	    Body body = physicsWorld.createBody(bodyDef);
	    body.setUserData(e);
	    //Continue
	    
	    return e;
	}
}
