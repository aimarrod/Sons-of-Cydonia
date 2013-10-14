package com.soc.ai;

import java.util.Random;

import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.soc.core.Constants.World;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Bounds;
import com.soc.game.components.Position;

public class GaiaAirAI implements AI{

	public float timer;
	public Random r;
	public boolean init;
	
	public GaiaAirAI(){
		timer = 0;
		r = new Random();
		init = false;
	

	}
	
	@Override
	public void process(Entity e) {
		if(init){
		timer -= SoC.game.world.delta;
		if(timer > 0) return;
		Position pos = SoC.game.positionmapper.get(e);
		Bounds bon = SoC.game.boundsmapper.get(e);
		float posx;
		float posy;
		if(pos.direction.x == 0) posx = pos.x + r.nextInt((int) (Constants.World.TILE_SIZE*5)) - Constants.World.TILE_SIZE*2;
		else posx = pos.x + bon.width*0.5f;
		if(pos.direction.y == 0) posy = pos.y + r.nextInt((int) (Constants.World.TILE_SIZE*3)) - Constants.World.TILE_SIZE;
		else posy = pos.y;
		
		Entity tornado = EntityFactory.createTornado(posx, posy, pos.z, pos.direction.cpy(), 0.9f);
	    SoC.game.groupmanager.add(tornado, Constants.Groups.ENEMY_ATTACKS);
	    SoC.game.groupmanager.add(tornado, Constants.Groups.MAP_BOUND);
	    SoC.game.groupmanager.add(tornado, Constants.Groups.PROJECTILES);
	    SoC.game.levelmanager.setLevel(tornado, Constants.Groups.LEVEL+pos.z);
	    tornado.addToWorld();
		timer = 0.8f;
		} else {
			Position pos = SoC.game.positionmapper.get(SoC.game.player);
			if((int)(pos.y*World.TILE_FACTOR) > 7 && (int)(pos.x * World.TILE_FACTOR) <= 11 && (int)(pos.x * World.TILE_FACTOR) >= 5){
				EntityFactory.createWall(e, 6, 5, 0).addToWorld();
				EntityFactory.createWall(e, 7, 5, 0).addToWorld();
				EntityFactory.createWall(e, 8, 5, 0).addToWorld();
				EntityFactory.createWall(e, 9, 5, 0).addToWorld();
				EntityFactory.createWall(e, 10, 5, 0).addToWorld();
				EntityFactory.createWall(e, 11, 5, 0).addToWorld();
				EntityFactory.createWall(e, 12, 185, 0).addToWorld();
				EntityFactory.createWall(e, 12, 184, 0).addToWorld();
				EntityFactory.createWall(e, 12, 183, 0).addToWorld();
				EntityFactory.createWall(e, 12, 182, 0).addToWorld();

				
		    	init = true;
			}
		}
	}

	@Override
	public void death(Entity e) {
		SoC.game.progress.gaiaAirDefeated=true;
	}

}
