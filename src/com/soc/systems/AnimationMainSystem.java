package com.soc.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.components.Attacker;
import com.soc.components.Movement;
import com.soc.components.Bounds;
import com.soc.components.Position;
import com.soc.components.Sprite;
import com.soc.components.State;

public class AnimationMainSystem extends EntityProcessingSystem{
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Movement> am;
	@Mapper
	ComponentMapper<Attacker> atm;
	@Mapper
	ComponentMapper<State> sm;
	@Mapper
	ComponentMapper<Bounds> bm; 
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	@SuppressWarnings("unchecked")
	public AnimationMainSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(Movement.class, Attacker.class, State.class, Position.class, Bounds.class));
		this.camera = camera;
		this.batch = new SpriteBatch();
	}
	
	@Override
	protected void begin() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}

	@Override
	protected void process(Entity e) {
		if (pm.has(e)) {
			Position position = pm.getSafe(e);
			Movement movement = am.get(e);
			Attacker attack = atm.get(e);
			State state = sm.get(e);
			Bounds bounds = bm.get(e);
			
			TextureRegion frame = null;
			if(state.state == State.WALK){
				frame = movement.animations[state.direction].getKeyFrame(movement.time, true);
				movement.time += world.delta;
			}
			if(state.state == State.IDLE){
				frame = movement.animations[state.direction].getKeyFrame(0);
			}
			if(state.state == State.ATTACK){
				frame = attack.animations[state.direction].getKeyFrame(attack.time);
				attack.time+=world.delta;
				if(attack.animations[state.direction].isAnimationFinished(attack.time)){
					state.state = State.IDLE;
				}
			}

			batch.setColor(movement.r, movement.g, movement.b, movement.a);
			batch.draw(frame, position.x, position.y);
		}
	}
	
	@Override
	protected void end() {
		batch.end();
	}
	
}
