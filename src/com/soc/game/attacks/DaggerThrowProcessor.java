package com.soc.game.attacks;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.core.SoC;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.game.graphics.AttackRenderer;
import com.soc.utils.GraphicsLoader;

public class DaggerThrowProcessor implements AttackProcessor{
	
	public Bag<Entity> hit;
	public AttackRenderer renderer;
	public Position source;
	public float range;
	public boolean reached;
	public boolean backing;
	
	public DaggerThrowProcessor(float range, Position source) {
		this.hit = new Bag<Entity>();
		this.renderer = GraphicsLoader.loadDaggerThrow();
		this.range = range;
		this.backing = false;
		this.source = source;
		
		
	}

	@Override 
	public void process(Entity attack) {
		
		Position p = SoC.game.positionmapper.get(attack);
		Velocity v = SoC.game.velocitymapper.get(attack);
		
		range -= Math.abs(v.speed*SoC.game.world.delta);
		
		if(0 > range && !backing){
			backing = true;
			hit.clear();
			return;
		}
		if(backing){
			reached = true;
			range = source.x -p.x;
			if(Math.abs(range) > 16){
				v.vx=v.speed*Math.signum(range);
				reached = false;
			} else {
				v.vx = 0;
			}
			range = source.y - p.y;
			if(Math.abs(range) > 16){
				v.vy=v.speed*Math.signum(range);
				reached = false;
			} else {
				v.vy = 0;
			}
		}
		
		if(reached)
			attack.deleteFromWorld(); 
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position victimpos = SoC.game.positionmapper.get(victim);
		Bounds attackbounds = SoC.game.boundsmapper.get(attack);
		Bounds victimbounds = SoC.game.boundsmapper.get(victim);
		State state = SoC.game.statemapper.get(victim);
		
		return (!hit.contains(victim) && state.state != State.DYING && attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y);
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
		Position pos = SoC.game.positionmapper.get(attack);
		Bounds bounds = SoC.game.boundsmapper.get(attack);

		sprite.draw(renderer.frame(SoC.game.world.delta),pos.x,pos.y,bounds.width, bounds.height);
		
	}
}
