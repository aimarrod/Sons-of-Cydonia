package com.soc.ai;

import com.artemis.Entity;
import com.soc.core.SoC;
import com.soc.game.components.Position;

public class BallistaAI implements AI{

	@Override
	public void process(Entity e) {
		Position pos =	SoC.game.positionmapper.get(e);
		Position playerpos = SoC.game.positionmapper.get(SoC.game.player);
		
		pos.direction.x = pos.x - playerpos.x;
		pos.direction.y = pos.y - playerpos.y;
	}

}
