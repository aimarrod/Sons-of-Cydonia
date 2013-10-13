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
		if(Math.hypot(playerPos.x-pos.x, playerPos.y - pos.y) > 1000) return;
		System.out.println("EI");
		timer = 2.0f;
		
		Position p = SoC.game.positionmapper.get(e);
		

		
		int maggots = r.nextInt(5) + 3;
		for(int i = 1; i < maggots+1; i++){
			Entity spawned = EntityFactory.createBat(r.nextInt(50)+p.x-25, r.nextInt(50)+p.y-25, p.z);	
			SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMY_ATTACKS);
			SoC.game.groupmanager.add(spawned, Constants.Groups.MAP_BOUND);
			SoC.game.levelmanager.setLevel(spawned, Constants.Groups.LEVEL +p.z);
			SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMIES);
			spawned.addToWorld();

		}
	}

	@Override
	public void death(Entity e) {
		SoC.game.progress.gaiaDarkDefeated=true;
	}

}
