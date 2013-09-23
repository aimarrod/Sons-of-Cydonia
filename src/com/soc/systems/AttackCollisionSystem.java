package com.soc.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.soc.components.Bounds;
import com.soc.components.Health;
import com.soc.components.Position;

public class AttackCollisionSystem extends EntitySystem{
	@Mapper ComponentMapper<Position> pm;
	 @Mapper ComponentMapper<Bounds> bm;
	 @Mapper ComponentMapper<Health> hm;
	public AttackCollisionSystem(Aspect aspect) {
		super(Aspect.getAspectForAll(Position.class, Bounds.class));		
	}

	@Override
	protected boolean checkProcessing() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> arg0) {
		// TODO Auto-generated method stub
		
	}

}
