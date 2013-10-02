package com.soc.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Position;
import com.soc.game.components.Velocity;

public class AttackProcessingSystem extends EntityProcessingSystem{

	@Mapper ComponentMapper<Attack> am;
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Bounds> bm;
	@Mapper ComponentMapper<Velocity> vm;

	
	@SuppressWarnings("unchecked")
	public AttackProcessingSystem() {
		super(Aspect.getAspectForAll(Attack.class));
	}

	@Override
	protected void process(Entity e) {		
		am.get(e).processor.process(e);
	}

}
