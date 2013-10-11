package com.soc.ai;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Velocity;

public class ZombiAI implements AI{
	
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
		if(Math.abs(dstx) < 32){
			pos.direction.x = 0;
		}
		else if(Math.abs(dsty) < 32) {
			pos.direction.y = 0;
		}
		if(Math.abs(dsty) < 32 && Math.abs(dstx) < 32){
			state.state=State.ATTACK;
		}else{
			state.state=State.WALK;
		}
		
	}

	@Override
	public void death(Entity e) {
		Position p = SoC.game.positionmapper.get(e);
		Entity spawned = EntityFactory.createPoisonCloud(p, SoC.game.boundsmapper.get(e));
		SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMY_ATTACKS);
		SoC.game.groupmanager.add(spawned, Constants.Groups.MAP_BOUND);
		SoC.game.levelmanager.setLevel(spawned, Constants.Groups.LEVEL +(p.z+1));
	}
}
