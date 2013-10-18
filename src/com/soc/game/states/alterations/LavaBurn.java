package com.soc.game.states.alterations;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Damage;
import com.soc.game.components.Stats;
import com.soc.utils.EffectsPlayer;

public class LavaBurn implements Alteration{

	float timer;
	float interval;
	
	public LavaBurn(){
		timer = Constants.Alteration.LAVA_BURN_DURATION;
		interval = Constants.Alteration.LAVA_BURN_TICK_INTERVAL;
	}
	
	@Override
	public void process(Entity e) {
		timer -= SoC.game.world.delta;
		interval -= SoC.game.world.delta;
		Stats stats=SoC.game.statsmapper.get(e);
		float damage=stats.health*Constants.Alteration.LAVA_BURN_DAMAGE;
		if(interval <= 0){
			EffectsPlayer.play("foom.ogg");
			interval = Constants.Alteration.LAVA_BURN_TICK_INTERVAL;
			if(SoC.game.damagemapper.has(e)){
				SoC.game.damagemapper.get(e).pureDamage+=damage;
			}else{
				e.addComponent(new Damage((int)damage, 1, 0, 0, true));
				e.changedInWorld();
			}
		}
		if(timer <= 0){
			SoC.game.debuffmapper.get(e).removeDebuff(this,e);
		}
	}

	@Override
	public void delete(Entity e) {
		// TODO Auto-generated method stub
		
	}

}
