package com.soc.game.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Mapper;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.soc.core.SoC;
import com.soc.game.components.Position;

public class CameraSystem extends VoidEntitySystem{
	@Mapper ComponentMapper<Position> pm;
	
	float lerp = 0.2f;
	public Rectangle viewport;
		
	public CameraSystem(OrthographicCamera camera) {
		super();
		this.camera = camera;
		this.viewport = new Rectangle();
	}

	private OrthographicCamera camera;

	@Override
	protected void processSystem() {
		Position pos = SoC.game.positionmapper.get(SoC.game.player);
		Vector3 position = camera.position;
		
		position.x += (pos.x - position.x) * lerp;
		position.y += (pos.y - position.y) * lerp;
		viewport.set(pos.x-camera.viewportWidth*0.5f, pos.y-camera.viewportHeight*0.5f, camera.viewportWidth, camera.viewportHeight);
		camera.update();
	}
	
	public boolean isVisible(Position pos){
		return viewport.contains(new Vector2(pos.x, pos.y));
	}

}
