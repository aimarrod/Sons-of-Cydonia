package com.soc.game.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Mapper;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.soc.core.SoC;
import com.soc.game.components.Position;

public class CameraSystem extends VoidEntitySystem{
	@Mapper ComponentMapper<Position> pm;
		
	public CameraSystem(OrthographicCamera camera) {
		super();
		this.camera = camera;
	}

	private OrthographicCamera camera;

	@Override
	protected void processSystem() {
		Position pos = SoC.game.positionmapper.get(SoC.game.player);
		camera.position.set(pos.x, pos.y, 0);
		camera.update();
	}

}
