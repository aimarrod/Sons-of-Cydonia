package com.soc.game.attacks.processors;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;

public class InfernoProcessor implements AttackProcessor {
	public float timer;
	public float timerMeteor;
	public Position []posMeteors;
	public Position pPos;
	public InfernoProcessor(){
		timer=0f;
		timerMeteor=0;
		posMeteors=new Position[8];
		pPos=SoC.game.positionmapper.get(SoC.game.player);
		posMeteors[0]=new Position(pPos.x+2*Constants.World.TILE_SIZE,pPos.y,pPos.z);
		posMeteors[1]=new Position(pPos.x-2*Constants.World.TILE_SIZE,pPos.y,pPos.z);
		posMeteors[2]=new Position(pPos.x,pPos.y+2*Constants.World.TILE_SIZE,pPos.z);
		posMeteors[3]=new Position(pPos.x,pPos.y-2*Constants.World.TILE_SIZE,pPos.z);
		posMeteors[4]=new Position(pPos.x+2*Constants.World.TILE_SIZE,pPos.y+2*Constants.World.TILE_SIZE,pPos.z);
		posMeteors[5]=new Position(pPos.x-2*Constants.World.TILE_SIZE,pPos.y-2*Constants.World.TILE_SIZE,pPos.z);
		posMeteors[6]=new Position(pPos.x+2*Constants.World.TILE_SIZE,pPos.y-2*Constants.World.TILE_SIZE,pPos.z);
		posMeteors[7]=new Position(pPos.x-2*Constants.World.TILE_SIZE,pPos.y+2*Constants.World.TILE_SIZE,pPos.z);
	}
	@Override
	public void process(Entity attack) {

		timer+=SoC.game.world.delta;
		if(timer<3.5f){
			Entity e=null;
			for(int i=0;i<posMeteors.length;i++){
				e=EntityFactory.createMeteor(posMeteors[i].x,posMeteors[i].y, posMeteors[i].z);
				SoC.game.levelmanager.setLevel(e, Constants.Groups.LEVEL + posMeteors[i].z);
				SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
				SoC.game.groupmanager.add(e, Constants.Groups.PLAYER_ATTACKS);
				e.addToWorld();
				
			}


		}
		attack.deleteFromWorld();
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		return false;
	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {

	}

	@Override
	public void handle(Entity attack, Entity enemy) {
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

}
