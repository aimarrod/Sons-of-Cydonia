package com.soc.utils;

import java.util.HashMap;
import java.util.Map;

import com.artemis.Entity;
import com.artemis.Manager;
import com.artemis.utils.Bag;
import com.soc.core.SoC;
import com.soc.game.components.Spawner;

public class WallManager extends Manager{
	public Map<Entity, Bag<Entity>> wallsByBoss;
    private Map<Entity, Entity> bossByWall;

	@Override
	protected void initialize() {
		wallsByBoss = new HashMap<Entity, Bag<Entity>>();
		bossByWall = new HashMap<Entity, Entity>();
	}
	
    public void block(Entity boss, Entity wall) {
    	Bag<Entity> walls = wallsByBoss.get(boss);
    	if(walls == null){
    		walls = new Bag<Entity>();
    		wallsByBoss.put(boss, walls);
    	}
    	walls.add(wall);
    	bossByWall.put(wall, boss);	
    }
    
    @Override
    public void deleted(Entity e) {
    	Bag<Entity> walls = wallsByBoss.get(e);
    	if(walls != null){
    		for(int i = 0; i < walls.size(); i++){
    			walls.get(i).deleteFromWorld();
    			bossByWall.remove(walls.get(i));
    		}
    	}
    }
    
    public void clear(){
    	wallsByBoss.clear();
    	bossByWall.clear();
    }
}
