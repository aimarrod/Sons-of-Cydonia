package com.gamexyz.systems;

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
import com.gamexyz.components.Player;
import com.gamexyz.components.Velocity;


	public class PlayerInputSystem extends EntityProcessingSystem implements InputProcessor {
		 @Mapper ComponentMapper<Velocity> vm;
		  
		 private OrthographicCamera camera;
		 private Vector3 mouseVector;
		  
		 private int ax, ay;
		 private int thruster = 400;
		 private float drag = 0.4f;
		  
		 @SuppressWarnings("unchecked")
		 public PlayerInputSystem(OrthographicCamera camera) {
		  super(Aspect.getAspectForAll(Velocity.class, Player.class));
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
		   
		  vel.vx += (ax - drag * vel.vx) * world.getDelta();
		  vel.vy += (ay - drag * vel.vy) * world.getDelta();
		 
		 }
		 
		 @Override
		 public boolean keyDown(int keycode) {
		  if (keycode == Input.Keys.UP) ay = thruster;
		  if (keycode == Input.Keys.DOWN) ay = -thruster;
		  if (keycode == Input.Keys.RIGHT) ax = thruster;
		  if (keycode == Input.Keys.LEFT) ax = -thruster; 
		  return false;
		 }
		 
		 @Override
		 public boolean keyUp(int keycode) {
		  if (keycode == Input.Keys.UP) ay = 0;
		  if (keycode == Input.Keys.DOWN) ay = 0;
		  if (keycode == Input.Keys.RIGHT) ax = 0;
		  if (keycode == Input.Keys.LEFT) ax = 0; 
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

