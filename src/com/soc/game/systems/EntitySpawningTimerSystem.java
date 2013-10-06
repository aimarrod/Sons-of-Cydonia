package com.soc.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.Timer;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Bounds;
import com.soc.game.components.Position;
import com.soc.game.components.Spawner;

public class EntitySpawningTimerSystem extends EntityProcessingSystem{
	@Mapper ComponentMapper<Spawner> sm;
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Bounds> bm;
	
	
	public EntitySpawningTimerSystem() {
		super(Aspect.getAspectForAll(Spawner.class));
	}

	@Override
	protected void process(Entity e) {
		Spawner spawn = sm.get(e);
		Position pos = pm.get(e);
		Bounds boun = bm.get(e);
		Position playerpos = pm.get(SoC.game.player);
		
		if(spawn.max > 0 && Math.hypot(pos.x-playerpos.x, pos.y-playerpos.y) <= spawn.range){
			if(spawn.time <= 0){
				spawn.time = spawn.interval;
				
				Entity spawned = EntityFactory.createSkeleton(pos.x, pos.y, 0,10,10);
			    SoC.game.groupmanager.add(spawned, Constants.Groups.MAP_BOUND);
			    SoC.game.groupmanager.add(spawned, Constants.Groups.SKELETONS);
			    SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMIES);
			    SoC.game.levelmanager.setLevel(spawned, Constants.Groups.LEVEL +pos.z);
				SoC.game.groupmanager.add(spawned, Constants.Groups.CHARACTERS);
			    spawned.addToWorld();
			} else {
				System.out.println(spawn.time);
				spawn.time -= world.delta;
			}
		}
		

	}
	

	
}
