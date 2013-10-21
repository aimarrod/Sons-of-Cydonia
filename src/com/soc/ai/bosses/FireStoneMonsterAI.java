package com.soc.ai.bosses;

import java.util.Random;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.soc.ai.AI;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;

public class FireStoneMonsterAI extends AI {
	public float posYStone;
	public float timer;
	public float timer2;
	public Random r;
	public FireStoneMonsterAI(){
		timer = 0f;
		r = new Random();
		posYStone=0f;
		timer2=0f;
	}

	@Override
	public void process(Entity e) {
		Position pos=SoC.game.positionmapper.get(e);
		Position pPlayer=SoC.game.positionmapper.get(SoC.game.player);
		float dsty = pPlayer.y - pos.y;
		float dstx = pPlayer.x - pos.x;
		if(dstx>0){
			pos.direction.x=1;
		}else{
			if(dstx<0){
				pos.direction.x=-1;
			}
		}	
		if(dsty>0){
			pos.direction.y=1;
		}else{
			if(dsty<0){
				pos.direction.y=-1;
			}
		}
		//float tileX=pPlayer.x*Constants.World.TILE_FACTOR;
		float tileY=pPlayer.y*Constants.World.TILE_FACTOR;
		if(tileY>200-117 && tileY<200-90 && timer<=0){
			posYStone=(97+r.nextInt(13))*Constants.World.TILE_SIZE;
			Entity fireStone = EntityFactory.createFireStone(pos.x, posYStone, pos.z, new Vector2(Math.signum(dstx),0),false);
		    SoC.game.groupmanager.add(fireStone, Constants.Groups.ENEMY_ATTACKS);
		    SoC.game.groupmanager.add(fireStone, Constants.Groups.MAP_BOUND);
		    SoC.game.groupmanager.add(fireStone, Constants.Groups.PROJECTILES);
		    SoC.game.levelmanager.setLevel(fireStone, Constants.Groups.LEVEL+pos.z);
		    fireStone.addToWorld();
		    timer=1.5f;
		}else{
			timer-=SoC.game.world.delta;
		}
		if(tileY>200-100 && tileY<200-74 && timer2<=0){
			posYStone=(115+r.nextInt(12))*Constants.World.TILE_SIZE;
			Entity fireStone = EntityFactory.createFireStone(pos.x, posYStone, pos.z, new Vector2(Math.signum(dstx),0),false);
		    SoC.game.groupmanager.add(fireStone, Constants.Groups.ENEMY_ATTACKS);
		    SoC.game.groupmanager.add(fireStone, Constants.Groups.MAP_BOUND);
		    SoC.game.groupmanager.add(fireStone, Constants.Groups.PROJECTILES);
		    SoC.game.levelmanager.setLevel(fireStone, Constants.Groups.LEVEL+pos.z);
		    fireStone.addToWorld();
		    timer2=1.5f;
		}else{
			timer2-=SoC.game.world.delta;
		}

	}

	@Override
	public void death(Entity e) {

	}

}
