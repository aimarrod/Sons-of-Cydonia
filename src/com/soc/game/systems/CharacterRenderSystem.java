package com.soc.game.systems;

import java.util.HashMap;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.physics.box2d.Shape;
import com.soc.game.components.Attacker;
import com.soc.game.components.Bounds;
import com.soc.game.components.CharacterAnimations;
import com.soc.game.components.Movement;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.graphics.DirectionalAnimatedRenderer;
import com.soc.game.graphics.DirectionalRenderer;

public class CharacterRenderSystem extends EntityProcessingSystem{
	@Mapper ComponentMapper<CharacterAnimations> am;
	@Mapper ComponentMapper<State> sm;
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Bounds> bm;

	
	
	@SuppressWarnings("unchecked")
	public CharacterRenderSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(CharacterAnimations.class, State.class, Position.class));
		this.batch = new SpriteBatch();
		this.camera = camera;
		shape = new ShapeRenderer();
	}
	
	@Override
	protected void begin() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeType.Filled);
	}

	@Override
	protected void process(Entity e) {
		State state = sm.get(e);
		Position pos = pm.get(e);
		Bounds bon = bm.get(e);
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
//		shape.setColor(0, 0, 0, 1);
//		shape.ellipse(pos.x, pos.y+5, bon.width, 10);

		batch.setColor(r.r, r.g, r.b, r.a);
		batch.draw(r.frame(world.delta), pos.x+r.ox, pos.y+r.oy);
	}
	
	@Override
	protected void end() {
		batch.end();
		shape.end();
	}

	private SpriteBatch batch;
	private ShapeRenderer shape;
	private OrthographicCamera camera;
}
