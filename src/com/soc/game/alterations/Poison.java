package com.soc.game.alterations;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Damage;


public class Poison implements Alteration{

	float timer;
	float interval;
	
	public Poison(){
		timer = Constants.Alteration.POISON_DURATION;
		interval = Constants.Alteration.POISON_TICK_INTERVAL;
	}
	
	@Override
	public void process(Entity e) {
		timer -= SoC.game.world.delta;
		interval -= SoC.game.world.delta;
		if(interval <= 0){
			if(SoC.game.damagemapper.has(e)){
				SoC.game.damagemapper.get(e).damage+=Constants.Alteration.POISON_DAMAGE;
			}else{
				e.addComponent(new Damage(Constants.Alteration.POISON_DAMAGE));
				e.changedInWorld();
			}
		interval = Constants.Alteration.POISON_TICK_INTERVAL;
		}
		if(timer <= 0){
			SoC.game.debuffmapper.get(e).removeDebuff(this);
		}
	}
	
}
