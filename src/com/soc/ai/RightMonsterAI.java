package com.soc.ai;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Feet;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;

public class RightMonsterAI extends AI{
	float timerCast;
	float limitXLeft;
	float limitXRight;
	float limitYBottom;
	float limitYUp;
	boolean rightSpawned;
	boolean playerInside;
	float timerStomp;
	boolean stompMode;
	
	public RightMonsterAI(){
		timerCast=0;
		limitXLeft=63*Constants.World.TILE_SIZE;
		limitXRight=99*Constants.World.TILE_SIZE;
		limitYBottom=55*Constants.World.TILE_SIZE;
		limitYUp=83*Constants.World.TILE_SIZE;
		playerInside=false;
		rightSpawned=false;
		timerStomp=10f;
		stompMode=false;
		
	}
	@Override
	public void process(Entity e) {
		Position pos = SoC.game.positionmapper.get(e);
		Velocity vel = SoC.game.velocitymapper.get(e);
		Stats stats=SoC.game.statsmapper.get(e);
		State state = SoC.game.statemapper.get(e);
		Entity player = SoC.game.player;
		Position playerPos = SoC.game.positionmapper.get(player);
		if(state.state == State.DYING) return;
		if(!rightSpawned){
			EntityFactory.createWall(e, 45, 54, 0).addToWorld();
			EntityFactory.createWall(e, 46, 54, 0).addToWorld();
			EntityFactory.createWall(e, 47, 54, 0).addToWorld();
			EntityFactory.createWall(e, 48, 54, 0).addToWorld();
			EntityFactory.createWall(e, 49, 54, 0).addToWorld();
			EntityFactory.createWall(e, 50, 54, 0).addToWorld();
			rightSpawned=true;
		}
		if( !playerInside && playerPos.x>limitXLeft && playerPos.x<limitXRight && playerPos.y>limitYBottom && playerPos.y<limitYUp){
			EntityFactory.createWall(e, 78, 55, 0).addToWorld();
			EntityFactory.createWall(e, 79, 55, 0).addToWorld();
			EntityFactory.createWall(e, 80, 55, 0).addToWorld();
			EntityFactory.createWall(e, 81, 55, 0).addToWorld();
			EntityFactory.createWall(e, 82, 55, 0).addToWorld();
			EntityFactory.createWall(e, 83, 55, 0).addToWorld();
			playerInside=true;
		}
		if((playerPos.x<limitXLeft || playerPos.x>limitXRight) || (playerPos.y<limitYBottom || playerPos.y>limitYUp)){
			state.state=State.IDLE;
			vel.vx=0;
			vel.vy=0;
			return;
		}
		timerStomp-=SoC.game.world.delta;
		if(state.state==State.ATTACK){
			timerCast+=SoC.game.world.delta;
			if(timerCast>=2f){
				Feet feet=SoC.game.feetmapper.get(e);
				Entity stomp = EntityFactory.createStomp(pos.x+feet.width*0.5f, pos.y+feet.heigth*0.5f, pos.z,stats.strength);
			    SoC.game.groupmanager.add(stomp, Constants.Groups.ENEMY_ATTACKS);
			    SoC.game.groupmanager.add(stomp, Constants.Groups.MAP_BOUND);
			    SoC.game.levelmanager.setLevel(stomp, Constants.Groups.LEVEL+pos.z);
			    stomp.addToWorld();
			    timerCast=0f;
			    state.state=State.IDLE;
			    SoC.game.charactermapper.get(e).renderers[State.ATTACK].time=0;
			}else{
				return;
			}
		}
		
		float dsty = playerPos.y - pos.y;
		float dstx = playerPos.x - pos.x;
		
		pos.direction.x = Math.signum(dstx);
		pos.direction.y = Math.signum(dsty); 
		
		vel.vx = vel.speed * pos.direction.x;
		vel.vy = vel.speed * pos.direction.y;
		
		state.state = State.WALK;
		if(Math.abs(dstx) < 32) pos.direction.x = 0;
		else if(Math.abs(dsty) < 10) pos.direction.y = 0;
		
		if(timerStomp<=0){
			timerStomp=10f;
			state.state=State.ATTACK;
			vel.vx=0;
			vel.vy=0;
		}
	}

	@Override
	public void death(Entity e) {
		SoC.game.progress.rightMonsterDefetead=true;
	}

}
