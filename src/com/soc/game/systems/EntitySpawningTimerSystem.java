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
	
	
	@SuppressWarnings("unchecked")
	public EntitySpawningTimerSystem() {
		super(Aspect.getAspectForAll(Spawner.class));
	}

	@Override
	protected void process(Entity spawner) {
		Spawner spawn = sm.get(spawner);
		Position pos = pm.get(spawner);
		Bounds boun = bm.get(spawner);
		Position playerpos = pm.get(SoC.game.player);
		
		if(spawn.max > 0 && playerpos.z == pos.z && Math.hypot(pos.x-playerpos.x, pos.y-playerpos.y) <= spawn.range){
			if(spawn.time <= 0){
				spawn.time = spawn.interval;
				spawn.max -= 1;
				Entity spawned=null;
				if(spawn.type.equals(Constants.Groups.SKELETONS)){
					spawned = EntityFactory.createSkeleton(pos.x, pos.y, pos.z,10);
					SoC.game.groupmanager.add(spawned, Constants.Groups.CHARACTERS);
				}
				else if(spawn.type.equals(Constants.Groups.BALLISTAS)){
					spawned = EntityFactory.createBallista(pos.x, pos.y, pos.z,10);
					SoC.game.groupmanager.add(spawned, Constants.Groups.CHARACTERS);
				}
				else if(spawn.type.equals(Constants.Groups.MAGGOTS)){
					spawned = EntityFactory.createMaggot(pos.x, pos.y, pos.z);
					SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMY_ATTACKS);
				}
				else if(spawn.type.equals(Constants.Groups.SLIMES)){
					spawned = EntityFactory.createSlime(pos.x, pos.y, pos.z);
					SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMY_ATTACKS);
				}else if(spawn.type.equals(Constants.Groups.ZOMBIES)){
					spawned = EntityFactory.createZombie(pos.x, pos.y, pos.z);
					SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMY_ATTACKS);
				}
				SoC.game.groupmanager.add(spawned, Constants.Groups.MAP_BOUND);
				SoC.game.levelmanager.setLevel(spawned, Constants.Groups.LEVEL +pos.z);
				SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMIES);
				SoC.game.spawnermanager.spawn(spawner, spawned);
				spawned.addToWorld();
			} else {
				spawn.time -= world.delta;
			}
		}
	}
}
