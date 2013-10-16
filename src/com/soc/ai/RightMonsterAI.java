package com.soc.ai;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;

public class RightMonsterAI extends AI{
	float timer;
	float limitXLeft;
	float limitXRight;
	float limitYBottom;
	float limitYUp;
	boolean rightSpawned;
	boolean playerInside;
	
	public RightMonsterAI(){
		timer=0;
		limitXLeft=63*Constants.World.TILE_SIZE;
		limitXRight=99*Constants.World.TILE_SIZE;
		limitYBottom=54*Constants.World.TILE_SIZE;
		limitYUp=83*Constants.World.TILE_SIZE;
		playerInside=false;
		rightSpawned=false;
		
	}
	@Override
	public void process(Entity e) {
		Position pos = SoC.game.positionmapper.get(e);
		Velocity vel = SoC.game.velocitymapper.get(e);
		Stats stats=SoC.game.statsmapper.get(e);
		State state = SoC.game.statemapper.get(e);
		Entity player = SoC.game.player;
		Position playerPos = SoC.game.positionmapper.get(player);
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
			EntityFactory.createWall(e, 84, 55, 0).addToWorld();
			playerInside=true;
		}
		if((playerPos.x<limitXLeft || playerPos.x>limitXRight) || (playerPos.y<limitYBottom || playerPos.y>limitYUp)){
			state.state=State.IDLE;
			vel.vx=0;
			vel.vy=0;
			return;
		}
		
	}

	@Override
	public void death(Entity e) {
		
	}

}
