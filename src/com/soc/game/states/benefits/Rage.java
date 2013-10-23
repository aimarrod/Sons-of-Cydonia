package com.soc.game.states.benefits;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Stats;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.utils.GraphicsLoader;

public class Rage implements Benefit{
	public float timer;
	public float gainAttr;
	public int initialAttr;
	boolean buffAdded;
	public float lastBuff;
	public AnimatedRenderer renderer; 
	public Rage(){
		timer=Constants.Buff.RAGE_DURATION;
		initialAttr=0;
		gainAttr=Constants.Buff.GAIN_STRENGTH;
		buffAdded=false;
		lastBuff=Constants.Buff.RAGE_DURATION;
		renderer=GraphicsLoader.loadRageAura();
	}
	@Override
	public void process(Entity e) {
		timer -= SoC.game.world.delta;
		Stats stats=SoC.game.statsmapper.get(e);
		if(!buffAdded && timer>0){
			if(stats.clazz.equals(Constants.Characters.WARRIOR)){
				initialAttr=stats.strength;
			} else if(stats.clazz.equals(Constants.Characters.MAGE)){
				initialAttr = stats.intelligence;
			}
			lastBuff=timer;
			buffAdded=true;
		}else{
			if(timer>0){
				if((lastBuff-timer)>=1){
					if(stats.clazz.equals(Constants.Characters.WARRIOR)){
						stats.strength+=Constants.Buff.GAIN_STRENGTH;
					} else if(stats.clazz.equals(Constants.Characters.MAGE)){
						stats.intelligence+=Constants.Buff.GAIN_STRENGTH;
					}
					lastBuff=timer;
				}
			}else{
				if(stats.clazz.equals(Constants.Characters.WARRIOR)){
					stats.strength=initialAttr;
				} else if(stats.clazz.equals(Constants.Characters.MAGE)){
					stats.intelligence=initialAttr;
				}
				SoC.game.buffmapper.get(e).removebuff(e,this);
			}
		}
		
	}
	@Override
	public void delete(Entity e) {
		Stats stats=SoC.game.statsmapper.get(e);
		if(stats.clazz.equals(Constants.Characters.WARRIOR)){
			stats.strength=initialAttr;
		} else if(stats.clazz.equals(Constants.Characters.MAGE)){
			stats.intelligence=initialAttr;
		}		
	}

}
