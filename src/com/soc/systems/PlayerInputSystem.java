package com.soc.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.soc.components.Player;
import com.soc.components.State;
import com.soc.components.Velocity;


	public class PlayerInputSystem extends EntityProcessingSystem implements InputProcessor {
		 @Mapper ComponentMapper<Velocity> vm;
		 @Mapper ComponentMapper<State> sm;

		  
		 private OrthographicCamera camera;
		 private Vector3 mouseVector;
		  
		 private int vx, vy;
		 private int movement = 10000;
		  
		 @SuppressWarnings("unchecked")
		 public PlayerInputSystem(OrthographicCamera camera) {
		  super(Aspect.getAspectForAll(Velocity.class, Player.class, State.class));
		  this.camera=camera;
		 }
		  
		 @Override
		 protected void initialize() {
		  Gdx.input.setInputProcessor(this);
		 }
		 
		 @Override
		 protected void process(Entity e) {
		  mouseVector = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
		  camera.unproject(mouseVector);
		   
		  Velocity vel = vm.get(e);
		   
		  vel.vx = vx * world.getDelta();
		  vel.vy = vy * world.getDelta();
		  
		  State state = sm.get(e);
		  if(vx < 0 && state.state != State.WALK_WEST){
			  state.state = State.WALK_WEST;
			  state.statetime = 0;
		  }
		  if(vx > 0 && state.state != State.WALK_EAST){
			  state.state = State.WALK_EAST;
			  state.statetime = 0;
		  }
		  if(vy > 0 && state.state != State.WALK_NORTH ){
			  state.state = State.WALK_NORTH;
			  state.statetime = 0;
		  }
		  if(vy < 0 && state.state != State.WALK_SOUTH){
			  state.state = State.WALK_SOUTH;
			  state.statetime = 0;
		  }
		  if(vx == 0 && vy == 0 && state.state > 3){
			  state.state -= 4;
			  System.out.println(state.state);
		  }
		  
		 }
		 
		 @Override
		 public boolean keyDown(int keycode) {
		  if (keycode == Input.Keys.UP) vy = movement;
		  if (keycode == Input.Keys.DOWN) vy = -movement;
		  if (keycode == Input.Keys.RIGHT) vx = movement;
		  if (keycode == Input.Keys.LEFT) vx = -movement; 
		  return false;
		 }
		 
		 @Override
		 public boolean keyUp(int keycode) {
		  if (keycode == Input.Keys.UP) vy = 0;
		  if (keycode == Input.Keys.DOWN) vy = 0;
		  if (keycode == Input.Keys.RIGHT) vx = 0;
		  if (keycode == Input.Keys.LEFT) vx = 0; 
		  return false;
		 }

		@Override
		public boolean keyTyped(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			// TODO Auto-generated method stub
			return false;
		}
		 
		}

