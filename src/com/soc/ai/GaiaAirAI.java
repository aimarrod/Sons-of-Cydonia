package com.soc.ai;

import java.util.Random;

import com.artemis.Entity;
import com.soc.core.Constants.World;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Bounds;
import com.soc.game.components.Position;

public class GaiaAirAI implements AI{

	public float timer;
	public Random r;
	
	public GaiaAirAI(){
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
		
		Entity tornado = EntityFactory.createTornado(posx, posy, pos.z, pos.direction.cpy(), 1.1f);
	    SoC.game.groupmanager.add(tornado, Constants.Groups.ENEMY_ATTACKS);
	    SoC.game.groupmanager.add(tornado, Constants.Groups.MAP_BOUND);
	    SoC.game.groupmanager.add(tornado, Constants.Groups.PROJECTILES);
	    SoC.game.levelmanager.setLevel(tornado, Constants.Groups.LEVEL+pos.z);
	    tornado.addToWorld();
		timer = 1f;
	}

	@Override
	public void death(Entity e) {
		SoC.game.progress.gaiaAirDefeated=true;
	}

}
