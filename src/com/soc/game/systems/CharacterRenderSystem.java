package com.soc.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.soc.game.components.Bounds;
import com.soc.game.components.Character;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.graphics.DirectionalAnimatedRenderer;
import com.soc.game.graphics.DirectionalRenderer;

public class CharacterRenderSystem extends EntitySystem{
	@Mapper ComponentMapper<Character> am;
	@Mapper ComponentMapper<State> sm;
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Bounds> bm;
	Bag<Entity>sortedEntities;
	
	
	@SuppressWarnings("unchecked")
	public CharacterRenderSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(Character.class, State.class, Position.class));
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
	 public void processEntities(ImmutableBag<Entity> entities) {
		for(int i=entities.size()-1;i>=0;i--){
			this.process(entities.get(i));
		}
	 }

	protected void process(Entity e) {
		State state = sm.get(e);
		Position pos = pm.get(e);
		Bounds bon = bm.get(e);
		Character animations = am.get(e);
		
		DirectionalRenderer r = null;
		if(state.state == State.ATTACK){
			r = animations.attack;
			if(((DirectionalAnimatedRenderer)r).isFinished()){
				r.time = 0;
			}
		}
		if(state.state == State.WALK){
			r = animations.movement;
		}
		
		if(state.state == State.IDLE){
			r = animations.idle;
		}
		if(state.state==State.DYING){
			r=animations.death;
		}
		else{
		float angle = pos.direction.angle();
		if(angle%90 != 0){
			if(angle<90f || angle>270f){
				angle=0f;
			} else {
				angle=180f;
			}
		}
		r.direction = ((int) angle/90 + 3) % 4;
		}

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


	@Override
	protected boolean checkProcessing() {
		return true;
	}

}
