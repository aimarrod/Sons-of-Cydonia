package com.soc.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntityProcessingSystem;
import com.soc.components.Enemy;

public class AISystem extends IntervalEntityProcessingSystem{

	public AISystem(float interval) {
		super(Aspect.getAspectForAll(Enemy.class), interval);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void process(Entity arg0) {
		// TODO Auto-generated method stub
		
	}

}
