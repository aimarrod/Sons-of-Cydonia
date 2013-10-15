package com.soc.game.attacks.processors;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.soc.core.SoC;
import com.soc.game.states.alterations.Burn;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Debuff;
import com.soc.game.components.Position;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.utils.GraphicsLoader;

public class FlameProcessor implements AttackProcessor{
	public AnimatedRenderer renderer;
	public float timer;
	public float lastHit;
	public FlameProcessor() {
  		this.renderer = GraphicsLoader.loadFlame();
  		this.timer=4f;
  		this.lastHit=4f;
	}

	@Override 
	public void process(Entity attack) {
		timer-=SoC.game.world.delta;
		if(timer<=0){
			attack.deleteFromWorld();
		}
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position victimpos = SoC.game.positionmapper.get(victim);
		Bounds attackbounds = SoC.game.boundsmapper.get(attack);
		Bounds victimbounds = SoC.game.boundsmapper.get(victim);
		return ((lastHit-timer)>0.5 && timer<=3f && attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y);
	

	}

	@Override
	public void handle(Entity attack, Entity victim) {
		lastHit=timer;
		int damage = (int) (SoC.game.attackmapper.get(attack).damage);
		if(SoC.game.damagemapper.has(victim)){
			SoC.game.damagemapper.get(victim).damage+=damage;
		}else{
			victim.addComponent(new Damage(damage, false));
			victim.changedInWorld();
		}
		Debuff.addDebuff(victim, new Burn());
	}

	@Override
	public void frame(Entity attack, SpriteBatch batch) {
		Position pos = SoC.game.positionmapper.get(attack);
		batch.draw(renderer.frame(SoC.game.world.delta), pos.x, pos.y);
	}

	@Override
	public void delete() {

	}

}
