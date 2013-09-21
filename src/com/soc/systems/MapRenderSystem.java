package com.soc.systems;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapRenderSystem {
	
	private final static float UNITSCALE = 1/1f;

	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	public MapRenderSystem(TiledMap map, OrthographicCamera camera){
		this.renderer = new OrthogonalTiledMapRenderer(map, UNITSCALE);
		this.camera = camera;
	}
	
	public void render() {
		renderer.setView(camera);
		renderer.render();
	}
}
