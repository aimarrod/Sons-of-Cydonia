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
import com.soc.components.WeaponAttack;

public class AnimationAttackSystem extends EntityProcessingSystem{
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<WeaponAttack> wam;
	@Mapper
	ComponentMapper<Bounds> bm; 
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	@SuppressWarnings("unchecked")
	public AnimationAttackSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForOne(WeaponAttack.class));
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
			WeaponAttack weaponAttack=wam.get(e);		
			TextureRegion frame = null;
				frame = weaponAttack.animations[0].getKeyFrame(weaponAttack.time, true);
			batch.setColor(weaponAttack.r, weaponAttack.g, weaponAttack.b, weaponAttack.a);
			batch.draw(frame, position.x, position.y);
			weaponAttack.time += world.delta;
		}
	}
	
	@Override
	protected void end() {
		batch.end();
	}
	
}