package com.soc.game.attacks.processors;

import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.game.components.Attack;
import com.soc.utils.EffectsPlayer;
import com.soc.utils.GraphicsLoader;

public class IcicleProcessor implements AttackProcessor {

	public boolean hit, sounded;
	public AnimatedRenderer renderer;
	public float distance;
	
	public IcicleProcessor(Vector2 dir) {
		this.hit = false;
		this.renderer = GraphicsLoader.loadIcicle(dir);
		this.distance = Constants.Spells.ICICLE_RANGE;
	}

	@Override
	public void process(Entity attack) {
		Velocity vel = SoC.game.velocitymapper.get(attack);
		if(!sounded){
			EffectsPlayer.play("shimmer.ogg");
			sounded = true;
		}
		distance -= vel.speed * SoC.game.world.delta;
		if(distance <= 0){
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
		
		return (!hit && attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y);
	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {
		Position pos = SoC.game.positionmapper.get(attack);
		sprite.draw(renderer.frame(SoC.game.world.delta),pos.x+renderer.ox,pos.y+renderer.oy,64, 64);
	}

	@Override
	public void handle(Entity attack, Entity victim) {
		Attack a = SoC.game.attackmapper.get(attack);
		hit=true;
		if(SoC.game.damagemapper.has(victim)){
			SoC.game.damagemapper.get(victim).damage+=a.damage;
		}else{
			victim.addComponent(new Damage(a.damage, false));
			victim.changedInWorld();
		}
		attack.deleteFromWorld();
		delete();

	}

	@Override
	public void delete() {
	}

}
