package com.soc.ai;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Position;

public class BallistaAI implements AI{
	
	float time;
	
	public BallistaAI(){
		time = Constants.Spells.BALLISTA_FIRE_RATE;
	}

	@Override
	public void process(Entity e) {
		Position pos =	SoC.game.positionmapper.get(e);
		Position playerpos = SoC.game.positionmapper.get(SoC.game.player);
		
		pos.direction.x = Math.signum(playerpos.x-pos.x);
		pos.direction.y = Math.signum(playerpos.y-pos.y);
				
		if(time <= 0){
			time = Constants.Spells.BALLISTA_FIRE_RATE;
		} else {
			time -= SoC.game.world.delta;
		}
	}

}
