package com.soc.game.alterations;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Damage;

public class Burn implements Alteration{

	float timer;
	float interval;
	
	public Burn(){
		timer = Constants.Alteration.BURN_DURATION;
		interval = Constants.Alteration.BURN_TICK_INTERVAL;
	}
	
	@Override
	public void process(Entity e) {
		timer -= SoC.game.world.delta;
		interval -= SoC.game.world.delta;
		if(interval <= 0){
			interval = Constants.Alteration.BURN_TICK_INTERVAL;
			e.addComponent(new Damage(Constants.Alteration.BURN_DAMAGE, 1, 0 ,0));
			e.changedInWorld();
		}
		if(timer <= 0){
			SoC.game.debuffmapper.get(e).removeDebuff(this);
		}
	}

}
