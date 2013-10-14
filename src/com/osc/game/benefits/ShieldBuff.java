package com.osc.game.benefits;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Stats;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.utils.GraphicsLoader;

public class ShieldBuff implements Benefit{
	public float timer;
	public float gainHealth;
	public boolean buffAdded;
	public int initialHealth;
	public AnimatedRenderer renderer;
	public ShieldBuff(){
		timer=Constants.Buff.SHIELD_DURATION;
		gainHealth=Constants.Buff.SHIELD_GAINHEALTH;
		buffAdded=false;
		initialHealth=0;
		renderer=GraphicsLoader.loadLifeShield();
	}
	@Override
	public void process(Entity e) {
		timer -= SoC.game.world.delta;
		Stats stats=SoC.game.statsmapper.get(e);
		if(timer>0 && !buffAdded){
			initialHealth=stats.health;
			stats.maxHealth+=gainHealth;
			stats.health+=gainHealth;
			buffAdded=true;
		}else{
			if(timer>0 && buffAdded){
				if(stats.health<=initialHealth){
					stats.maxHealth-=gainHealth;
					SoC.game.buffmapper.get(e).removebuff(e,this);
				}
			}else{
				stats.maxHealth-=gainHealth;
				stats.health=initialHealth;
				SoC.game.buffmapper.get(e).removebuff(e,this);
			}
		}
		
	}
	@Override
	public void delete(Entity e) {
		// TODO Auto-generated method stub
		
	}
}
