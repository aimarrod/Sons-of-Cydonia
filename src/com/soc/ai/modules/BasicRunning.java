package com.soc.ai.modules;

import com.artemis.Entity;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Velocity;

public class BasicRunning implements AIModule{

		public float dstx, dsty, range;
		public boolean stops, diagonal;
		
		
		public BasicRunning(float range, boolean stops, boolean diagonal){

			this.dsty = 0;
			this.dstx = 0;
			this.range = range;
			
			this.stops = stops;
			this.diagonal = diagonal;
		}
		
		@Override
		public boolean process(Entity e) {
			
			State state = SoC.game.statemapper.get(e);
			Velocity vel = SoC.game.velocitymapper.get(e);
			Position pos = SoC.game.positionmapper.get(e);
			Position playerPos = SoC.game.positionmapper.get(SoC.game.player);

			
			if(state.state == State.DYING || state.state == State.FALLING){
				vel.vx = 0;
				vel.vy = 0;
				return true;
			}
			
			dstx = playerPos.x - pos.x;
			dsty = playerPos.y - pos.y;
						
			if(state.state >= State.BLOCKED && stops){

				if(diagonal){
					if(Math.abs(dstx) > Math.abs(dsty)){
						pos.direction.x = Math.signum(dstx);
						pos.direction.y = Math.signum(dsty)*(Math.abs(dsty)/Math.abs(dstx));
					} else {
						pos.direction.y = Math.signum(dsty);
						pos.direction.x = Math.signum(dstx)*(Math.abs(dstx)/Math.abs(dsty));
					}
				} else {
					pos.direction.x = Math.signum(dstx);
					if(Math.abs(dstx) < 32){
						pos.direction.x = 0;
						return false;
					}
					if(Math.abs(dstx) > Math.abs(dsty)){
						pos.direction.y = 0;
					} else {
						pos.direction.y = Math.signum(dsty);
					}
				}
				
				if(state.state != State.CHARGING && state.state != State.SPINNING){
					vel.vx = 0;
					vel.vy = 0;
				}
				return true;
			}
				
			if(Math.abs(dstx) < range){
				vel.vx = -Math.signum(dstx)*vel.speed; 
			} else {
				vel.vx = 0;
			}
					
			if(Math.abs(dsty) < range){
				vel.vy = -Math.signum(dsty)*vel.speed;			
			} else {
				vel.vy = 0;
			}
			
			
			if(state.state < State.BLOCKED){
				if(vel.vx != 0 || vel.vy != 0){
					state.state = State.WALK;
				} else {
					state.state = State.IDLE;
				}
			}
				
			if(vel.vx == 0){
				pos.direction.x = 0;
			} else {
				pos.direction.x = -Math.signum(dstx);
			}
			
			if(vel.vy == 0){
				pos.direction.y = 0;
			} else {
				pos.direction.y = -Math.signum(dsty);
			}
			
			return false;
		}
}
