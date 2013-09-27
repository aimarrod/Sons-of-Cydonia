package com.soc.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.soc.game.components.Player;
import com.soc.game.components.Position;

public class CameraSystem extends EntityProcessingSystem{
	@Mapper ComponentMapper<Position> pm;
		
	@SuppressWarnings("unchecked")
	public CameraSystem(OrthographicCamera camera) {
		super(Aspect.getAspectForAll(Player.class, Position.class));
		this.camera = camera;
	}

	private OrthographicCamera camera;

	@Override
	protected void process(Entity e) {
		Position pos = pm.get(e);
		camera.position.set(pos.x, pos.y, 0);
	}
}
