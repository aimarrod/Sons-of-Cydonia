package com.soc.ai;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.Push;

public class ZombieAI implements AI{

	@Override
	public void process(Entity e) {
		Position pos = SoC.game.positionmapper.get(e);
		Position playerPos = SoC.game.positionmapper.get(SoC.game.player);
		
		pos.direction.y = Math.signum(playerPos.y - pos.y);
		pos.direction.x = Math.signum(playerPos.x - pos.x);
	}

}
