package com.soc.ai;

import java.util.Random;

import com.artemis.Entity;
import com.soc.core.Constants.World;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Bounds;
import com.soc.game.components.Position;

public class GaiaDarkAI extends AI{

	public float timer;
	public Random r;
	public boolean init;
	
	public GaiaDarkAI(){
		timer = 0;
		r = new Random();
		init = false;
	}
	
	@Override
	public void process(Entity e) {

		Position playerPos = SoC.game.positionmapper.get(SoC.game.player);

		if(!init && (int)(playerPos.x*World.TILE_FACTOR) > 12 && (int)(playerPos.y*World.TILE_FACTOR) > 181 && (int)(playerPos.y*World.TILE_FACTOR) < 186){
			EntityFactory.createWall(e, 12, 185, 0).addToWorld();
			EntityFactory.createWall(e, 12, 184, 0).addToWorld();
			EntityFactory.createWall(e, 12, 183, 0).addToWorld();
			EntityFactory.createWall(e, 12, 182, 0).addToWorld();
			init = true;
			return;
		}
		timer -= SoC.game.world.delta;
		if(timer > 0) return;	
		timer = 3.0f;		
		Entity circle = EntityFactory.createCircle(playerPos.x+Constants.Characters.WIDTH*0.5f, playerPos.y+Constants.Characters.HEIGHT*0.5f, playerPos.z);
		SoC.game.levelmanager.setLevel(circle, Constants.Groups.LEVEL + playerPos.z);
		circle.addToWorld();

	}

	@Override
	public void death(Entity e) {
		SoC.game.progress.gaiaDarkDefeated=true;
	}

}
