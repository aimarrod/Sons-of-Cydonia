package com.soc.game.attacks;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Delay;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Velocity;

public class ChargeProcessor implements AttackProcessor{

	public Bag<Entity> hit;
	public float range;
	public Entity source;
	
	@Mapper
	ComponentMapper<Damage> dm = SoC.game.world.getMapper(Damage.class);
	public ChargeProcessor(Entity source, float duration) {
		this.source = source;
		hit = new Bag<Entity>();
		this.range = duration;
	}

	@Override 
	public void process(Entity attack) {
		Velocity vAttack = SoC.game.velocitymapper.get(attack);
		Position p = SoC.game.positionmapper.get(attack);
		Velocity vSource = SoC.game.velocitymapper.get(source);
		range -= SoC.game.world.delta;
		if(range>0){
			vAttack.vx=vAttack.speed*p.direction.x;
			vAttack.vy=vAttack.speed*p.direction.y;
			vSource.vx=vAttack.speed*p.direction.x;
			vSource.vy=vAttack.speed*p.direction.y;
		}else{
			attack.deleteFromWorld();
		}
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position victimpos = SoC.game.positionmapper.get(victim);
		Bounds attackbounds = SoC.game.boundsmapper.get(attack);
		Bounds victimbounds = SoC.game.boundsmapper.get(victim);
		State state = SoC.game.statemapper.get(victim);
		
		return (!hit.contains(victim) && state.state !=State.DYING && attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y);
	}

	@Override
	public void handle(Entity attack, Entity victim) {
		Attack a = SoC.game.attackmapper.get(attack);
		hit.add(victim);
		if(SoC.game.damagemapper.has(victim)){
			SoC.game.damagemapper.get(victim).damage+=a.damage;
		}else{
			victim.addComponent(new Damage(a.damage));
			victim.changedInWorld();
		}
		
	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {
		
	}

	@Override
	public void delete() {
		source.removeComponent(Delay.class);
		source.changedInWorld();
		SoC.game.statemapper.get(source).state = State.IDLE;
		SoC.game.charactermapper.get(source).renderers[State.ATTACK].time = 0;
	}

}
