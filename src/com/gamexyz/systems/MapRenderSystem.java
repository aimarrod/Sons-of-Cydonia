package com.gamexyz.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.gamexyz.components.Position;
import com.gamexyz.components.Sprite;


public class MapRenderSystem extends EntityProcessingSystem{

	ComponentMapper<Sprite> sm;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private TiledMap map;
	private OrthogonalTiledMapRenderer render;

	@SuppressWarnings("unchecked")
	public MapRenderSystem(TiledMap map, OrthographicCamera camera) {
		super(Aspect.getEmpty());
		this.camera = camera; 
		this.map = map;
		this.render = new OrthogonalTiledMapRenderer(map);
		
	}

	@Override
	protected void process(Entity arg0) {
		// TODO Auto-generated method stub
		
	}
}
