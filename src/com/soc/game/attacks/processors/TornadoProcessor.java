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
import com.osc.game.states.benefits.Inmune;
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
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.game.states.alterations.Push;
import com.soc.utils.GraphicsLoader;

public class TornadoProcessor implements AttackProcessor{
	public AnimatedRenderer renderer;
	public Entity hit;
	public float distance;


	
	public TornadoProcessor() {
		this.hit = null;
		this.distance = Constants.Spells.TORNADO_RANGE;
		this.renderer = GraphicsLoader.loadTornado();
	}

	@Override 
	public void process(Entity attack) {
		if(hit != null){
			Velocity veloc = SoC.game.velocitymapper.get(attack);
			distance -= SoC.game.world.delta*(Math.abs(veloc.vx)+Math.abs(veloc.vy));
			if(distance <= 0){
				attack.deleteFromWorld();
				delete();
			}
			Velocity vel = SoC.game.velocitymapper.get(hit);
			Velocity torvel = SoC.game.velocitymapper.get(attack);
			vel.vx = torvel.vx;
			vel.vy = torvel.vy;
		}
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		if(hit != null) return false;
		if(SoC.game.debuffmapper.has(victim) && SoC.game.debuffmapper.get(victim).debuffClasses.contains(Push.class)) return false;
		if(SoC.game.buffmapper.has(victim) && SoC.game.buffmapper.get(victim).buffClasses.contains(Inmune.class)) return false;
		
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position victimpos = SoC.game.positionmapper.get(victim);
		Bounds attackbounds = SoC.game.boundsmapper.get(attack);
		Bounds victimbounds = SoC.game.boundsmapper.get(victim);
		
		
		return (attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y);
	

	}

	@Override
	public void handle(Entity attack, Entity victim) {
		hit = victim;
		Position pos1 = SoC.game.positionmapper.get(victim);
		Position pos2 = SoC.game.positionmapper.get(attack);

		pos1.x = pos2.x;
		pos1.y = pos2.y + 2;
		victim.removeComponent(Delay.class);
		victim.changedInWorld();
		Buff.addbuff(victim, new Inmune());
		SoC.game.statemapper.get(victim).state = State.SPINNING;
	}

	@Override
	public void frame(Entity attack, SpriteBatch batch) {
		Position pos = SoC.game.positionmapper.get(attack);
		batch.draw(renderer.frame(SoC.game.world.delta), pos.x+renderer.ox, pos.y+renderer.oy);
	}

	@Override
	public void delete() {
		if(hit != null){
			SoC.game.buffmapper.get(hit).removebuff(Inmune.class,hit);
			SoC.game.statemapper.get(hit).state = State.IDLE;
			int damage = (int) (SoC.game.statsmapper.get(hit).maxHealth*0.1);
			if(SoC.game.damagemapper.has(hit)){
				SoC.game.damagemapper.get(hit).pureDamage+=damage;
			}else{
				hit.addComponent(new Damage(damage, true));
				hit.changedInWorld();
			}
		}
	}
}
