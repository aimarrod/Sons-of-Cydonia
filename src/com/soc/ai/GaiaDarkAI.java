package com.soc.ai;

import java.util.Random;

import com.artemis.Entity;
import com.soc.core.Constants.World;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Bounds;
import com.soc.game.components.Position;

public class GaiaDarkAI implements AI{

	public float timer;
	public Random r;
	
	public GaiaDarkAI(){
		timer = 0;
		r = new Random();
	}
	
	@Override
	public void process(Entity e) {
		timer -= SoC.game.world.delta;
		if(timer > 0) return;	
		Position pos = SoC.game.positionmapper.get(e);
		Position playerPos = SoC.game.positionmapper.get(SoC.game.player);
		if(Math.hypot(playerPos.x-pos.x, playerPos.y - pos.y) > 3000) return;
		
		timer = 2.0f;		
		Entity circle = EntityFactory.createCircle(playerPos.x, playerPos.y, playerPos.z);
		SoC.game.levelmanager.setLevel(circle, Constants.Groups.LEVEL + playerPos.z);
		circle.addToWorld();

	}

	@Override
	public void death(Entity e) {
		SoC.game.progress.gaiaDarkDefeated=true;
	}

}
