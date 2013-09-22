package com.soc.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.components.Attack;
import com.soc.components.Movement;
import com.soc.components.Bounds;
import com.soc.components.Position;
import com.soc.components.Sprite;
import com.soc.components.State;

public class AnimationSystem extends EntityProcessingSystem{
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Movement> am;
	@Mapper
	ComponentMapper<Attack> atm;
	@Mapper
	ComponentMapper<State> sm;
	@Mapper
	ComponentMapper<Bounds> bm; 
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	@SuppressWarnings("unchecked")
	public AnimationSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(Movement.class, Attack.class, State.class, Position.class, Bounds.class));
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
			Attack attack = atm.get(e);
			State state = sm.get(e);
			Bounds bounds = bm.get(e);
			
			TextureRegion frame = null;
			if(state.state == State.WALK){
				frame = movement.animations[state.direction].getKeyFrame(state.statetime, true);
			}
			if(state.state == State.IDLE){
				frame = movement.animations[state.direction].getKeyFrame(0);
			}
			if(state.state == State.ATTACK){
				frame = attack.animations[state.direction].getKeyFrame(state.statetime);
				if(attack.animations[state.direction].isAnimationFinished(state.statetime)){
					state.state = State.IDLE;
				}
			}
			state.statetime += world.delta;

			batch.setColor(movement.r, movement.g, movement.b, movement.a);
			batch.draw(frame, position.x, position.y);
		}
	}
	
	@Override
	protected void end() {
		batch.end();
	}
	
}
