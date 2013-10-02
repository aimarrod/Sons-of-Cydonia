package com.soc.game.systems;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapRenderSystem extends VoidEntitySystem{
	
	private final static float UNITSCALE = 1/1f;

	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	public MapRenderSystem(TiledMap map, OrthographicCamera camera){
		this.renderer = new OrthogonalTiledMapRenderer(map, UNITSCALE);
		this.camera = camera;
	}
	
	public void changeMap(TiledMap map){
		this.renderer = new OrthogonalTiledMapRenderer(map, UNITSCALE);
	}

	@Override
	protected void processSystem() {
		renderer.setView(camera);
		renderer.render();
	}
}
