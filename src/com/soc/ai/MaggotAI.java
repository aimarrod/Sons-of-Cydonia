package com.soc.ai;


import com.artemis.Entity;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Velocity;

public class MaggotAI extends AI{

	@Override
	public void process(Entity e) {
		Position pos = SoC.game.positionmapper.get(e);
		Position playerPos = SoC.game.positionmapper.get(SoC.game.player);
		Velocity vel = SoC.game.velocitymapper.get(e);
		State state = SoC.game.statemapper.get(e);
		
		if(state.state == State.DYING) return;
		
		float dsty = playerPos.y - pos.y;
		float dstx = playerPos.x - pos.x;
		
		pos.direction.x = Math.signum(dstx);
		pos.direction.y = Math.signum(dsty); 
		
		vel.vx = vel.speed * pos.direction.x;
		vel.vy = vel.speed * pos.direction.y;
		
		if(Math.abs(dstx) < 32) pos.direction.x = 0;
		else if(Math.abs(dsty) < 32) pos.direction.y = 0;
		
	}

	@Override
	public void death(Entity e) {
		// TODO Auto-generated method stub
		
	}

}
