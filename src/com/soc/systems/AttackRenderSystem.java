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
import com.soc.components.Attacker;
import com.soc.components.Movement;
import com.soc.components.Bounds;
import com.soc.components.Position;
import com.soc.components.Sprite;
import com.soc.components.State;

public class AttackRenderSystem extends EntityProcessingSystem{
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Attack> am;
	@Mapper
	ComponentMapper<Bounds> bm; 
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	@SuppressWarnings("unchecked")
	public AttackRenderSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForOne(Attack.class));
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
			Bounds bounds = bm.get(e);
			Attack attack=am.get(e);		
			batch.draw(attack.attack.frame(world.delta), position.x, position.y, bounds.width, bounds.height);
		}
	}
	
	@Override
	protected void end() {
		batch.end();
	}
	
}