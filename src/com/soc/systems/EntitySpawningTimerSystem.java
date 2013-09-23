package com.soc.systems;

import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.Timer;
import com.soc.EntityFactory;

public class EntitySpawningTimerSystem extends VoidEntitySystem{
	private Timer timer;
	public EntitySpawningTimerSystem(){
		//Delay and repeat
		  timer = new Timer(2, true) {
			   @Override
			   public void execute() {
			    EntityFactory.getInstance().createSkeleton(2000,300,10,10);
			   }
			  };
	}
	@Override
	protected void processSystem() {
		timer.update(world.delta);
		
	}
}
