package com.soc.game.states.alterations;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Damage;
import com.soc.utils.EffectsPlayer;

public class Burn implements Alteration{

	float timer;
	float interval;
	int damage;
	
	public Burn(){
		timer = Constants.Alteration.BURN_DURATION;
		interval = Constants.Alteration.BURN_TICK_INTERVAL;
		damage = Constants.Alteration.BURN_DAMAGE;
	}
	
	@Override
	public void process(Entity e) {
		timer -= SoC.game.world.delta;
		interval -= SoC.game.world.delta;
		if(interval <= 0){
			EffectsPlayer.play("foom.ogg");
			interval = Constants.Alteration.BURN_TICK_INTERVAL;
			if(SoC.game.damagemapper.has(e)){
				SoC.game.damagemapper.get(e).pureDamage+=Constants.Alteration.BURN_DAMAGE;
			}else{
				e.addComponent(new Damage(damage, 1, 0, 0, true));
				e.changedInWorld();
			}
		}
		if(timer <= 0){
			SoC.game.debuffmapper.get(e).removeDebuff(this,e);
		}
	}
	
	public void increaseDamage(){
		damage += 2;
		timer = Constants.Alteration.BURN_DURATION;
	}

	@Override
	public void delete(Entity e) {
		// TODO Auto-generated method stub
		
	}

}
