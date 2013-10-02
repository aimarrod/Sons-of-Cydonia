package com.soc.game.attacks;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.game.graphics.AttackRenderer;
import com.soc.utils.Globals;
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
		
		Position p = Globals.positionmapper.get(attack);
		Velocity v = Globals.velocitymapper.get(attack);
		
		range -= Math.abs(v.speed*Globals.world.delta);
		
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
		Position attackpos = Globals.positionmapper.get(attack);
		Position victimpos = Globals.positionmapper.get(victim);
		Bounds attackbounds = Globals.boundsmapper.get(attack);
		Bounds victimbounds = Globals.boundsmapper.get(victim);
		State state = Globals.statemapper.get(victim);
		
		return (!hit.contains(victim) && state.state != State.DYING && attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y);
	}

	@Override
	public void handle(Entity attack, Entity victim) {
		
		Attack a = Globals.attackmapper.get(attack);
		hit.add(victim);
		if(Globals.damagemapper.has(victim)){
			Globals.damagemapper.get(victim).damage+=a.damage;
		}else{
			victim.addComponent(new Damage(a.damage));
			victim.changedInWorld();
		}
	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {
		Position pos = Globals.positionmapper.get(attack);
		Bounds bounds = Globals.boundsmapper.get(attack);

		sprite.draw(renderer.frame(Globals.world.delta),pos.x,pos.y,bounds.width, bounds.height);
		
	}
}
