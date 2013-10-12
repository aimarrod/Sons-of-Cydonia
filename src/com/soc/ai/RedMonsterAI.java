package com.soc.ai;

import java.util.Random;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Bounds;
import com.soc.game.components.Position;

public class RedMonsterAI implements AI{


	public float timer;
	public Random r;
	
	public RedMonsterAI(){
		timer = 0;
		r = new Random();
	}
	
	@Override
	public void process(Entity e) {
		timer -= SoC.game.world.delta;
		if(timer > 0) return;
		Position pos = SoC.game.positionmapper.get(e);
		Bounds bon = SoC.game.boundsmapper.get(e);
		float posx;
		float posy;
		if(pos.direction.x == 0) posx = pos.x + r.nextInt((int) (bon.width*5)) - bon.width*2;
		else posx = pos.x + bon.width*0.5f;
		if(pos.direction.y == 0) posy = pos.y + r.nextInt((int) (bon.height*3)) - bon.height;
		else posy = pos.y;
		
		Entity flame = EntityFactory.createFlame(posx, posy, pos.z, pos.direction.cpy());
	    SoC.game.groupmanager.add(flame, Constants.Groups.ENEMY_ATTACKS);
	    SoC.game.groupmanager.add(flame, Constants.Groups.MAP_BOUND);
	    SoC.game.levelmanager.setLevel(flame, Constants.Groups.LEVEL+pos.z);
	    flame.addToWorld();
		timer = 1.0f;
	}

	@Override
	public void death(Entity e) {
		// TODO Auto-generated method stub
		
	}

}
