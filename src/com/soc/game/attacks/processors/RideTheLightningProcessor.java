package com.soc.game.attacks.processors;


import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Debuff;
import com.soc.game.components.Delay;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Velocity;
import com.soc.game.graphics.Renderer;
import com.soc.game.states.alterations.Push;
import com.soc.utils.GraphicsLoader;

public class RideTheLightningProcessor implements AttackProcessor{

	public Bag<Entity> hit;
	public float duration;
	public Entity source;

	public float radius;
	
	
	@Mapper
	ComponentMapper<Damage> dm = SoC.game.world.getMapper(Damage.class);
	public RideTheLightningProcessor(Entity source) {
		this.source = source;
		this.hit = new Bag<Entity>();
		this.duration = Constants.Spells.RIDE_THE_LIGHTNING_DURATION;
	}

	@Override 
	public void process(Entity attack) {
		SoC.game.effectSystem.addSound(attack, "thunder.ogg");
		
		if(SoC.game.statemapper.get(source).state == State.CHARGING){
			Velocity vAttack = SoC.game.velocitymapper.get(attack);
			Position p = SoC.game.positionmapper.get(attack);
			Velocity vSource = SoC.game.velocitymapper.get(source);
			duration-= SoC.game.world.delta;
			if(duration>0){
				vAttack.vx=vAttack.speed*p.direction.x;
				vAttack.vy=vAttack.speed*p.direction.y;
				vSource.vx=vAttack.speed*p.direction.x;
				vSource.vy=vAttack.speed*p.direction.y;
			}else{
				attack.deleteFromWorld();
				delete();
			}
		} else {
			attack.deleteFromWorld();
			delete();
		}
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		return false;
	}

	@Override
	public void handle(Entity attack, Entity victim) {

	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {
	}

	@Override
	public void delete() {
		Entity e = EntityFactory.createAirBlast(source ,SoC.game.positionmapper.get(source), SoC.game.statsmapper.get(source).intelligence);
	    SoC.game.groupmanager.add(e, Constants.Groups.PLAYER_ATTACKS);
	    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
	    SoC.game.levelmanager.setLevel(e, Constants.Groups.LEVEL+SoC.game.positionmapper.get(source).z);
	   	e.addToWorld();
		source.removeComponent(Delay.class);
		source.changedInWorld();
		SoC.game.statemapper.get(source).state = State.IDLE;
		SoC.game.charactermapper.get(source).renderers[State.ATTACK].time = 0;
	}

}
