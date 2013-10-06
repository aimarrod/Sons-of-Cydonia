package com.soc.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Manager;
import com.artemis.utils.Bag;
import com.soc.core.SoC;
import com.soc.game.components.Position;

public class SpawnerManager extends Manager{
	public Map<Entity, Bag<Entity>> entitiesBySpawner;
    private Map<Entity, Entity> spawnersByEntity;

	@Override
	protected void initialize() {
		entitiesBySpawner = new HashMap<Entity, Bag<Entity>>();
		spawnersByEntity = new HashMap<Entity, Entity>();
	}
	
    public void spawn(Entity spawner, Entity spawned) {
    	Bag<Entity> entities = entitiesBySpawner.get(spawner);
    	if(entities == null){
    		entities = new Bag<Entity>();
    		entitiesBySpawner.put(spawner, entities);
    	}
    	entities.add(spawned);
    	spawnersByEntity.put(spawned, spawner);	
    }
    
    @Override
    public void deleted(Entity e) {
    	Entity spawner = spawnersByEntity.remove(e);
    	if(spawner != null){
    		entitiesBySpawner.get(spawner).remove(e);
    		SoC.game.spawnermapper.get(spawner).max += 1;
    	}     	
    }
    
    public void clear(){
    	entitiesBySpawner.clear();
    	spawnersByEntity.clear();
    }
}
