package com.soc.ai.bosses;

import java.util.Random;

import com.artemis.Entity;
import com.soc.ai.AI;
import com.soc.core.Constants.World;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Bounds;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;

public class GaiaFlameAI extends AI{

	public float timer;
	public Random r;
	
	public GaiaFlameAI(){
		timer = 0;
		r = new Random();
	}
	
	@Override
	public void process(Entity e) {
		timer -= SoC.game.world.delta;
		if(SoC.game.statemapper.get(e).state==State.DYING) return;
		if(timer > 0) return;	
		Position pos = SoC.game.positionmapper.get(e);
		Position playerPos = SoC.game.positionmapper.get(SoC.game.player);
		float distance = (float) Math.hypot(playerPos.x-pos.x, playerPos.y - pos.y);
		if(distance > 2200) return;
		timer = 0.1f * ((2200-distance)/2200) + 0.2f*(distance/2200);
		
		Position p = SoC.game.positionmapper.get(e);
		

		
		int tileX = 0;
		if(distance > 700){
			tileX = r.nextInt(66) + 127;
		} else {
			tileX = r.nextInt(25) + 160;
		}
		int tileY = r.nextInt(35) + 157;
			Entity spawned = EntityFactory.createMeteor(tileX*World.TILE_SIZE+World.TILE_SIZE*0.5f, tileY*World.TILE_SIZE+World.TILE_SIZE*0.5f, p.z, 50);	
			SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMY_ATTACKS);
			SoC.game.groupmanager.add(spawned, Constants.Groups.MAP_BOUND);
			SoC.game.levelmanager.setLevel(spawned, Constants.Groups.LEVEL +(p.z+1));
			spawned.addToWorld();
	}

	@Override
	public void death(Entity e) {
		SoC.game.progress.gaiaFlameDefeated=true;		
		Position pos = SoC.game.positionmapper.get(e);
		Stats stat = SoC.game.statsmapper.get(SoC.game.player);
		
		if(stat.clazz.equals(Constants.Characters.WARRIOR)){
			EntityFactory.createItem(Constants.Items.IRON_AXE, pos.x, pos.y, pos.z);
		} else if(stat.clazz.equals(Constants.Characters.MAGE)){
		}
		EntityFactory.createItem(Constants.Items.IRON_SHIELD, pos.x, pos.y, pos.z).addToWorld();	
	}

}
