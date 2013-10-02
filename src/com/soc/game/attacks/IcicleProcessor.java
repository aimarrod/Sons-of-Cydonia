package com.soc.game.attacks;

import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.game.graphics.AttackRenderer;
import com.soc.game.components.Attack;
import com.soc.utils.Globals;
import com.soc.utils.GraphicsLoader;

public class IcicleProcessor implements AttackProcessor {

	public Bag<Entity> hit;
	public AttackRenderer renderer;
	public float range;

	public IcicleProcessor(Vector2 dir, float range) {
		hit = new Bag<Entity>();
		this.renderer = GraphicsLoader.loadIcicle(dir);
		this.range = range;
	}

	@Override
	public void process(Entity attack) {
		range -= Math.abs(Globals.velocitymapper.get(attack).speed*Globals.world.delta);
		if(range < 0){
			attack.deleteFromWorld();
		}		
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
	public void frame(Entity attack, SpriteBatch sprite) {
		Position pos = Globals.positionmapper.get(attack);
		Bounds bounds = Globals.boundsmapper.get(attack);

		sprite.draw(renderer.frame(Globals.world.delta),pos.x,pos.y,bounds.width, bounds.height);
		
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

}
