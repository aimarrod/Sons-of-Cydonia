package com.soc.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.soc.EntityFactory;
import com.soc.components.Attacker;
import com.soc.components.Player;
import com.soc.components.Position;
import com.soc.components.State;
import com.soc.components.Velocity;


	public class PlayerInputSystem extends EntityProcessingSystem{
		 @Mapper ComponentMapper<Velocity> vm;
		 @Mapper ComponentMapper<State> sm;
		 @Mapper ComponentMapper<Position>pm;
		 @Mapper ComponentMapper<Attacker>am;

		  
		 private OrthographicCamera camera;
		 private Vector3 mouseVector;
		 private State state;
		 
		 private int vx, vy;
		 private int movement = 15000;
		  
		 @SuppressWarnings("unchecked")
		 public PlayerInputSystem(OrthographicCamera camera) {
			 super(Aspect.getAspectForAll(Velocity.class, Player.class, State.class,Position.class));
			 this.camera=camera;
			 this.state = null;
		 }
		  
/*		 @Override
		 protected void initialize() {
		  //Gdx.input.setInputProcessor(this);
		 }
	*/	 
		 @Override
		 protected void process(Entity e) {
			 mouseVector = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
			 camera.unproject(mouseVector);
		   
			 Velocity vel = vm.get(e);
		   
			 vel.vx = vx * world.getDelta();
			 vel.vy = vy * world.getDelta();
			 
			 state = sm.get(e);
			 Position p=pm.get(e);
			 
			 if(state.state < State.BLOCKED){
				
				if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
					if(state.state != State.ATTACK){
						am.get(e).time = 0;
						EntityFactory.getInstance().createAttack(p.x,p.y,0,10,10,state.getDirVector());
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
		 
/*		 //@Override
		 public boolean keyDown(int keycode) {
			 if(state != null){
				 if (keycode == Input.Keys.UP){ 
					 state.direction = State.NORTH;
					 state.statetime = 0;
				 }
				 if (keycode == Input.Keys.DOWN){ 
					 vy = -movement;
					 state.direction = State.SOUTH;
					 state.statetime = 0;
				 }
				 if (keycode == Input.Keys.RIGHT){
					 vx = movement;
					 state.direction = State.EAST;
					 state.statetime = 0;
				 }
				 if (keycode == Input.Keys.LEFT){
					 vx = -movement; 
					 state.direction = State.WEST;
					 state.statetime = 0;
				 }
			 }
			 return false;
		 }
		 
		 //@Override
		 public boolean keyUp(int keycode) {
			 if(state != null){
				 if (keycode == Input.Keys.UP){
					 vy = 0;
				 }
				 if (keycode == Input.Keys.DOWN){
					 vy = 0;
				 }
				 if (keycode == Input.Keys.RIGHT){
					 vx = 0;
				 }
				 if (keycode == Input.Keys.LEFT){
					 vx = 0;
				 }
			 }
			 return false;

		 }

		//@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		//@Override
		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {
			// TODO Auto-generated method stub
			return false;
		}

		//@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		//@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}

		//@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

		//@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}
		 
		}
*/
