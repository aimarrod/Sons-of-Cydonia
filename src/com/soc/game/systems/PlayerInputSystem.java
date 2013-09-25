package com.soc.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.soc.game.components.Attacker;
import com.soc.game.components.Player;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Velocity;
import com.soc.utils.EntityFactory;


	public class PlayerInputSystem extends EntityProcessingSystem{
		 @Mapper ComponentMapper<Velocity> vm;
		 @Mapper ComponentMapper<State> sm;
		 @Mapper ComponentMapper<Position>pm;
		 @Mapper ComponentMapper<Attacker>am;

		 private int vx, vy;
		 private int movement = 15000;
		  
		 @SuppressWarnings("unchecked")
		 public PlayerInputSystem() {
			 super(Aspect.getAspectForAll(Velocity.class, Player.class, State.class,Position.class));
		 }
		   
		 @Override
		 protected void process(Entity e) {
		   
			 Velocity vel = vm.get(e);
		   
			 vel.vx = vx * world.getDelta();
			 vel.vy = vy * world.getDelta();
			 
			 State state = sm.get(e);
			 Position p=pm.get(e);
			 
			 if(state.state < State.BLOCKED){
				
				if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
					if(state.state != State.ATTACK){
						EntityFactory.getInstance().createDaggerThrow(p.x,p.y,0,10,10,state.getDirVector());
						state.state = State.ATTACK;
						vx = 0;
						vy = 0;
					}
					return;
				}
				 
				boolean moving = false;
				 
			 	if(Gdx.input.isKeyPressed(Input.Keys.UP)){
				 	vy = movement;
				 	state.direction = State.NORTH;
				 	moving = true;
			 	} else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
				 	vy = -movement;
				 	state.direction = State.SOUTH;
				 	moving = true;
			 	} else {
				 	vy = 0;
			 	}
			 
			 	if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
				 	vx = -movement;
				 	state.direction = State.WEST;
				 	moving = true;
			 	} else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
				 	vx = movement;
				 	state.direction = State.EAST;
				 	moving = true;
			 	} else {
				 	vx = 0;
			 	}
			 
			 	if(moving){
				 	state.state = State.WALK;
			 	} else {
				 	state.state = State.IDLE;
			 	}
			 }
			 			 
			 
		 }
	}
