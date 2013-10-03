package com.soc.core;

import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.artemis.managers.PlayerManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.soc.game.attacks.DaggerThrowProcessor;
import com.soc.game.attacks.IcicleProcessor;
import com.soc.game.attacks.PunchProcessor;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Character;
import com.soc.game.components.Enemy;
import com.soc.game.components.Feet;
import com.soc.game.components.Flying;
import com.soc.game.components.Player;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.utils.GraphicsLoader;


public class EntityFactory {
	

	public static Entity createCharacter(float px, float py, float range, int damage, int type){
		Entity e = SoC.game.world.createEntity();
		Character animations = new Character();
		
	    e.addComponent(new Position(px,py));
	    e.addComponent(new Player());
	    e.addComponent(new Velocity(0,0,200));
	    e.addComponent(new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT));
	    e.addComponent(new Stats(100, 50, 0, 100, 100, 0, 1, 1, 1, 1, 1, Constants.Spells.DAGGER_THROW, new int[]{}));
	    e.addComponent(new State(0));
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
	    
	    return e;
	}
	
	
	public static Entity createSkeleton(float px, float py, int damage, float range){
		Entity e = SoC.game.world.createEntity();
	    e.addComponent(new Position(px,py));
	    e.addComponent(new Velocity(0,0,100));
	    e.addComponent(new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT));
	    e.addComponent(new State(1));
	    e.addComponent(new Stats(10, 0, 0, 10, 0, 0, 1, 1, 1, 1, 1, Constants.Spells.PUNCH, new int[]{}));
	    e.addComponent(new Feet(32, 10));
	    e.addComponent(new Enemy(600,10));
	    
	    Character animations = new Character();
	    animations.idle = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/skeleton-walk.png")), 1.0f, 64, 64, false);
	    animations.movement = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/skeleton-walk.png")), 1.0f, 64, 64, true);
	    animations.attack = GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/skeleton-attack.png")), 1.0f, 64, 64, false);
	    animations.death=GraphicsLoader.loadCharacterSpriteSheet(new Texture(Gdx.files.internal("resources/skeleton-death.png")), 1.0f, 64, 64, false);
	    e.addComponent(animations);
	    
	    return e;
	}
	
	public static Entity createDaggerThrow(String group, Position pos, int damage, int range, Vector2 dir){
		Entity e=SoC.game.world.createEntity();
		
		e.addComponent( new Position(pos.x,pos.y) );
		e.addComponent( new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT) );
		e.addComponent( new Velocity(Constants.Spells.DAGGER_SPEED*dir.x, Constants.Spells.DAGGER_SPEED*dir.y,900) );
	   	e.addComponent( new Flying());
	   	e.addComponent( new Attack(new DaggerThrowProcessor(range, pos), damage) );
	   	
	   	return e;
	}
	
	public static Entity createIcicle(String group ,Position pos, int damage, int range, Vector2 dir){
		Entity e=SoC.game.world.createEntity();
				
		e.addComponent( new Position(pos.x,pos.y) );
		e.addComponent( new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT) );
		e.addComponent( new Velocity(300*dir.x, 300*dir.y, Constants.Spells.DAGGER_SPEED) );
	   	e.addComponent( new Flying());
	   	e.addComponent( new Attack(new IcicleProcessor(dir, range), damage ) );
	   	
	   	return e;
	}
	
	public static Entity createFireball(String group, Position pos, int damage, int range, Vector2 dir){
		Entity e=SoC.game.world.createEntity();
				
		e.addComponent( new Position(pos.x, pos.y) );
		e.addComponent( new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT) );
		e.addComponent( new Velocity(300*dir.x, 300*dir.y, Constants.Spells.DAGGER_SPEED) );
	   	e.addComponent( new Flying());
	   	e.addComponent( new Attack(new IcicleProcessor(dir, range), damage) );
	   	
	   	return e;
	}
	
	public static Entity createPunch(String group, Position pos, int damage, int range, Vector2 dir){
		Entity e=SoC.game.world.createEntity();
				
		e.addComponent( new Position(pos.x, pos.y) );
		e.addComponent( new Bounds(Constants.Characters.WIDTH, Constants.Characters.HEIGHT) );
		e.addComponent( new Velocity(0, 0, Constants.Spells.DAGGER_SPEED) );
	   	e.addComponent( new Attack(new PunchProcessor(dir, range), damage) );
	   	
	   	return e;
	}
}