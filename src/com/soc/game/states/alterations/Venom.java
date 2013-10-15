package com.soc.game.states.alterations;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Damage;

public class Venom implements Alteration{

	float timer;
	float interval;
	
	public Venom(){
		timer = Constants.Alteration.VENOM_DURATION;
		interval = Constants.Alteration.VENOM_TICK_INTERVAL;
	}
	
	@Override
	public void process(Entity e) {
		timer -= SoC.game.world.delta;
		interval -= SoC.game.world.delta;
		if(interval <= 0){
			if(SoC.game.damagemapper.has(e)){
				SoC.game.damagemapper.get(e).pureDamage+=Constants.Alteration.VENOM_DAMAGE;
			}else{
				e.addComponent(new Damage(Constants.Alteration.VENOM_DAMAGE, 0, 1, 0, true));
				e.changedInWorld();
			}
		interval = Constants.Alteration.VENOM_TICK_INTERVAL;
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


