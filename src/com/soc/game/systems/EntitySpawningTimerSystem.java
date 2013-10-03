package com.soc.game.systems;

import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.Timer;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;

public class EntitySpawningTimerSystem extends VoidEntitySystem{
	private Timer timer;
	public EntitySpawningTimerSystem(){
		//Delay and repeat
		  timer = new Timer(1.0f, false) {
			   @Override
			   public void execute() {
			    Entity e = EntityFactory.createSkeleton(2000,300,10,10);
			    SoC.game.world.getManager(GroupManager.class).add(e, Constants.Groups.ENEMIES);
			    SoC.game.world.getManager(GroupManager.class).add(e, Constants.Groups.SKELETONS);
			    e.addToWorld();
			   }
			  };
	}
	@Override
	protected void processSystem() {
		timer.update(world.delta);
		
	}
}
