package com.soc.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.soc.game.components.Bounds;
import com.soc.game.components.Position;
import com.soc.game.components.Velocity;

public class MovementSystem extends EntityProcessingSystem {
	 @Mapper ComponentMapper<Position> pm;
	 @Mapper ComponentMapper<Velocity> vm;
	 @Mapper ComponentMapper<Bounds> bm;

	 
	 @SuppressWarnings("unchecked")
	 public MovementSystem() {
	  super(Aspect.getAspectForAll(Position.class, Velocity.class));
	 }
	 
	 @Override
	 protected void process(Entity e) {
	  Position position = pm.get(e);
	  Velocity velocity = vm.get(e);
	     
	  position.x += velocity.vx*world.delta;
	  position.y += velocity.vy*world.delta;
	 }
	 
	}