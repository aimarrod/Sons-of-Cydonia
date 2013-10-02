package com.soc.game.attacks;

import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.SoC;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.game.graphics.AttackRenderer;
import com.soc.game.components.Attack;
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
		range -= Math.abs(SoC.game.velocitymapper.get(attack).speed*SoC.game.world.delta);
		if(range < 0){
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
		
		return (!hit.contains(victim) && state.state != State.DYING && attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y);
	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {
		Position pos = SoC.game.positionmapper.get(attack);
		Bounds bounds = SoC.game.boundsmapper.get(attack);

		sprite.draw(renderer.frame(SoC.game.world.delta),pos.x,pos.y,bounds.width, bounds.height);
		
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

}
