package com.soc.ai.modules;

import com.artemis.Entity;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Velocity;

public class BasicVision implements AIModule{
	
	float range, pursuitRange;
	boolean spotted, hasMemory;
	
	public BasicVision(float range, float pursuitRange, boolean hasMemory){
		this.spotted = false;
		this.hasMemory = hasMemory;
		this.range = range;
		this.pursuitRange = pursuitRange;
	}
	
	@Override
	public boolean process(Entity e) {
		
		State state = SoC.game.statemapper.get(e);
		
		if(state.state == State.FALLING || state.state == State.DYING) return true;
		
		Position pos = SoC.game.positionmapper.get(e);
		Position playerPos = SoC.game.positionmapper.get(SoC.game.player);
		
		if(pos.z != playerPos.z) return true;
		
		float dst = (float) Math.hypot(Math.abs(pos.x - playerPos.x), Math.abs(pos.y - playerPos.y));
		
		if(!spotted){
			if(dst < range){
				spotted = true;
				return false;
			} else {
				return true;
			}
		} else {
			if(hasMemory){
				if(dst > pursuitRange){
					spotted = false;
					state.state = State.IDLE;
					Velocity vel = SoC.game.velocitymapper.get(e);
					vel.vx = 0;
					vel.vy = 0;
					return true;
				} else {
					return false;
				}
			} else {
				if(dst > range){
					state.state = State.IDLE;
					Velocity vel = SoC.game.velocitymapper.get(e);
					vel.vx = 0;
					vel.vy = 0;
					spotted = false;
					return true;
				} else {
					return false;
				}
			}
		}
		
		
		
	}
	
	
	 
	
}
