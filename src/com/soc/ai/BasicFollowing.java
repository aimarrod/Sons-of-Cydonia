package com.soc.ai;

import com.artemis.Entity;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Velocity;

public class BasicFollowing implements AIModule{

	public float dstx, dsty, range;
	public boolean transparent, stops, diagonal;
	
	
	public BasicFollowing(float range, boolean transparent, boolean stops, boolean diagonal){

		this.dsty = 0;
		this.dstx = 0;
		this.range = range;
		
		this.stops = stops;
		this.transparent = transparent;
		this.diagonal = diagonal;
	}
	
	@Override
	public void process(Entity e) {
		
		State state = SoC.game.statemapper.get(e);
		Velocity vel = SoC.game.velocitymapper.get(e);
		
		if(state.state == State.DYING || state.state == State.FALLING){
			vel.vx = 0;
			vel.vy = 0;
			return;
		}

		if((state.state >= State.BLOCKED && stops)){
			if(state.state != State.CHARGING && state.state != State.SPINNING){
				vel.vx = 0;
				vel.vy = 0;
			}
			return;
		}
		
		Position pos = SoC.game.positionmapper.get(e);
		Position playerPos = SoC.game.positionmapper.get(SoC.game.player);
		
		dstx = playerPos.x - pos.x;
		dsty = playerPos.y - pos.y;
			
		if(transparent || Math.abs(dstx) > range){
			vel.vx = Math.signum(dstx)*vel.speed; 
		} else {
			vel.vx = 0;
		}
				
		if(transparent || Math.abs(dsty) > range){
			vel.vy = Math.signum(dsty)*vel.speed;			
		} else {
			System.out.println("AQUI");
			vel.vy = 0;
		}
		
		

		
		if(state.state < State.BLOCKED){
			if(vel.vx != 0 || vel.vy != 0){
				state.state = State.WALK;
			} else {
				state.state = State.IDLE;
			}
		}
			
		if(diagonal){
			if(Math.abs(dstx) > Math.abs(dsty)){
				pos.direction.x = Math.signum(dstx);
				pos.direction.y = Math.signum(dsty)*(Math.abs(dsty)/Math.abs(dstx));
			} else {
				pos.direction.y = Math.signum(dsty);
				pos.direction.x = Math.signum(dstx)*(Math.abs(dstx)/Math.abs(dsty));
			}
		} else {
			if(Math.abs(dstx) < 32){
				pos.direction.x = 0;
				pos.direction.y = Math.signum(dsty);
			} else {
				pos.direction.x = Math.signum(dstx);
				pos.direction.y = 0;
			}
		}
	}
}
