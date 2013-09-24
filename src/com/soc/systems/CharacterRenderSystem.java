package com.soc.systems;

import java.util.HashMap;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.soc.components.Attacker;
import com.soc.components.CharacterAnimations;
import com.soc.components.Movement;
import com.soc.components.Position;
import com.soc.components.State;
import com.soc.graphics.DirectionalAnimatedRenderer;
import com.soc.graphics.DirectionalRenderer;

public class CharacterRenderSystem extends EntityProcessingSystem{
	@Mapper ComponentMapper<CharacterAnimations> am;
	@Mapper ComponentMapper<State> sm;
	@Mapper ComponentMapper<Position> pm;
	
	
	@SuppressWarnings("unchecked")
	public CharacterRenderSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(CharacterAnimations.class, State.class, Position.class));
		this.batch = new SpriteBatch();
		this.camera = camera;
	}
	
	@Override
	protected void begin() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}

	@Override
	protected void process(Entity e) {
		State state = sm.get(e);
		Position pos = pm.get(e);
		CharacterAnimations animations = am.get(e);
		
		DirectionalRenderer r = null;
		if(state.state == State.ATTACK){
			r = animations.attack;
			if(((DirectionalAnimatedRenderer)r).isFinished()){
				r.time = 0;
				state.state = State.IDLE;
			}
		}
		
		if(state.state == State.WALK){
			r = animations.movement;
		}
		
		if(state.state == State.IDLE){
			r = animations.idle;
			r.time = Float.MAX_VALUE;
		}
		
		r.direction = state.direction;
		batch.setColor(r.r, r.g, r.b, r.a);
		batch.draw(r.frame(world.delta), pos.x+r.ox, pos.y+r.oy);
	}
	
	@Override
	protected void end() {
		batch.end();
	}

	private SpriteBatch batch;
	private OrthographicCamera camera;
}
