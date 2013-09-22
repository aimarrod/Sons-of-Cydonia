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

	public Entity createPlayer(float px, float py){
		Entity e = entityWorld.createEntity();
	    e.addComponent(new Position(px,py));
	    e.addComponent(new Player());
	    e.addComponent(new Velocity(0,0));
	    e.addComponent(new Bounds(32, 32));
	    e.addComponent(new State(0,0));
	    
	    Movement anim = new Movement();
	    anim.animations = new Animation[8];
	    //Super especifico (Tailor made)
	    Texture sheet = new Texture(Gdx.files.internal("resources/archer_walking.png"));
	   	int vframes = 4;
	   	int hframes = 9;
	   	TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth(), sheet.getHeight() / 4);
	   	for(int i = 0; i < tmp.length; i++){
	   		TextureRegion tr = tmp[i][0];
	   		TextureRegion[][] tmp2 = tr.split(tr.getRegionWidth()/9, tr.getRegionHeight());
	   		anim.animations[i] = new Animation(1.0f/9f, tmp2[0]);
	   	}
	   	//Fin especifico
	   	e.addComponent(anim);
	    
	    e.addToWorld();
	    
	    
	    return e;
	}
}
