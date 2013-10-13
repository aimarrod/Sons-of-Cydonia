package com.soc.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Manager;
import com.badlogic.gdx.utils.Array;
import com.soc.core.SoC;
import com.soc.game.components.Position;

public class LevelManager extends Manager{
	public Map<String, ArrayList<Entity>> entitiesByLevel;
    private Map<Entity, String> levelByEntity;
	private Comparator<Entity> entityComparator;

	@Override
	protected void initialize() {
		entityComparator = new Comparator<Entity>() {
			@Override
			public int compare(Entity o1, Entity o2) {
				ComponentMapper<Position> pm = world.getMapper(Position.class);
				return (int) Math.signum(pm.get(o2).y - pm.get(o1).y);
			}
		};
		entitiesByLevel = new HashMap<String, ArrayList<Entity>>();
		levelByEntity = new HashMap<Entity, String>();
	}
	
    public void setLevel(Entity e, String level) {
    	if(levelByEntity.containsKey(e)) entitiesByLevel.get(levelByEntity.get(e)).remove(e);
        levelByEntity.put(e, level);
        ArrayList<Entity> entities = entitiesByLevel.get(level);
        if(entities == null) {
                entities = new ArrayList<Entity>();
                entitiesByLevel.put(level, entities);
        }
        entities.add(e);
    }
    
    public ArrayList<Entity> getEntitiesOfLevel(String level) {
        ArrayList<Entity> entities = entitiesByLevel.get(level);
        if(entities == null) {
                entities = new ArrayList<Entity>();
                entitiesByLevel.put(level, entities);
        }
        Collections.sort(entities, entityComparator);
        return entities;
    }
    
    @Override
    public void deleted(Entity e) {
    	String removedLevel = levelByEntity.remove(e);
    	if(removedLevel != null) {
    		entitiesByLevel.get(removedLevel).remove(e);
    	}
    }
}
