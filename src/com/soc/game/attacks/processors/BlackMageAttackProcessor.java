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
import com.soc.ai.AI;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Buff;
import com.soc.game.components.Damage;
import com.soc.game.components.Debuff;
import com.soc.game.components.Delay;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Velocity;
import com.soc.game.states.alterations.Push;
import com.soc.game.states.benefits.Inmune;

public class BlackMageAttackProcessor implements AttackProcessor{

	public Bag<Entity> hit;
	public Circle hitbox;
	public Rectangle enemy;
	public float tickInterval;
	public float duration;
	public Entity source;
	
	@Mapper
	ComponentMapper<Damage> dm = SoC.game.world.getMapper(Damage.class);
	public BlackMageAttackProcessor(Entity source) {
		this.source = source;
		Buff.addbuff(source, new Inmune());
		this.hit = new Bag<Entity>();
		this.duration = AI.rng.nextFloat()*4f + 5f;
		this.hitbox = new Circle();
		this.enemy = new Rectangle();
	}

	@Override 
	public void process(Entity attack) {
		if(SoC.game.statemapper.get(source).state == State.SPINNING){
			Position p = SoC.game.positionmapper.get(attack);
			Position pos = SoC.game.positionmapper.get(source);
			Bounds bon = SoC.game.boundsmapper.get(source);
			duration-= SoC.game.world.delta;
			tickInterval -= SoC.game.world.delta;
			if(tickInterval <= 0){
				hit.clear();
				tickInterval = 0.5f;
			}
			if(duration>0){
				p.x = pos.x + bon.width*0.5f;
				p.y = pos.y + bon.height*0.5f;
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
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position victimpos = SoC.game.positionmapper.get(victim);
		Bounds attackbounds = SoC.game.boundsmapper.get(attack);
		Bounds victimbounds = SoC.game.boundsmapper.get(victim);
		enemy.set(victimpos.x, victimpos.y, victimbounds.width, victimbounds.height);
		hitbox.set(attackpos.x, attackpos.y, (attackbounds.width+attackbounds.height)*0.5f);
		
		System.out.println(attackpos.y);
		
		return (!hit.contains(victim) && Intersector.overlaps(hitbox, enemy));
	}

	@Override
	public void handle(Entity attack, Entity victim) {
		Attack a = SoC.game.attackmapper.get(attack);
		hit.add(victim);
		if(SoC.game.damagemapper.has(victim)){
			SoC.game.damagemapper.get(victim).damage+=a.damage;
		}else{
			victim.addComponent(new Damage(a.damage, false));
			victim.changedInWorld();
		}
	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {
		
	}

	@Override
	public void delete() {
		SoC.game.buffmapper.get(source).removebuff(Inmune.class, source);
		SoC.game.statemapper.get(source).state = State.IDLE;
		SoC.game.velocitymapper.get(source).vx = 0;
		SoC.game.velocitymapper.get(source).vy = 0;
		SoC.game.charactermapper.get(source).renderers[State.ATTACK].time = 0;
		SoC.game.positionmapper.get(source).direction.y = 0;
	}

}
