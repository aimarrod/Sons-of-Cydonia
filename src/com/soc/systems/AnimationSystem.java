package com.soc.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.components.Animated;
import com.soc.components.Bounds;
import com.soc.components.Position;
import com.soc.components.Sprite;
import com.soc.components.State;

public class AnimationSystem extends EntityProcessingSystem{
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Animated> am;
	@Mapper
	ComponentMapper<State> sm;
	@Mapper
	ComponentMapper<Bounds> bm; 
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	@SuppressWarnings("unchecked")
	public AnimationSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(Animated.class, State.class, Position.class, Bounds.class));
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
			Animated animated = am.get(e);
			State state = sm.get(e);
			Bounds bounds = bm.get(e);
						
			TextureRegion frame = animated.animations[state.state].getKeyFrame(state.statetime, true); 
			state.statetime += world.delta;

			batch.setColor(animated.r, animated.g, animated.b, animated.a);
			batch.draw(frame, position.x, position.y);
		}
	}
	
	@Override
	protected void end() {
		batch.end();
	}
	
}
