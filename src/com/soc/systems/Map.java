package com.soc.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.soc.components.Position;
import com.soc.components.Sprite;


public class Map {

	private final static String BASE_DIR = "resources/maps/";
	private final static String EXTENSION_TMX = ".tmx";
	private final static float UNITSCALE = 1/32f;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	public Map(String name, OrthographicCamera camera) {
		this.map = new TmxMapLoader().load(BASE_DIR + name + EXTENSION_TMX);
		this.camera = camera; 
		//this.camera.setToOrtho(false, 20, 20);
		this.renderer = new OrthogonalTiledMapRenderer(map, UNITSCALE);
	}

	public void render() {
		renderer.setView(camera);
		renderer.render();
	}
}
