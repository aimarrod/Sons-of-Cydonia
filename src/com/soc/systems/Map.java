package com.soc.systems;

import java.util.List;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.soc.components.Position;
import com.soc.components.Sprite;


public class Map {

	private final static String BASE_DIR = "resources/maps/";
	private final static String EXTENSION_TMX = ".tmx";
	private final static float UNITSCALE = 1/0.5f;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private MapLayers layers;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	public Map(String name, OrthographicCamera camera) {
		this.map = new TmxMapLoader().load(BASE_DIR + name + EXTENSION_TMX);
		this.layers = map.getLayers();
		System.out.println(layers.get("collision").getObjects().get(1));
		this.camera = camera; 
		this.renderer = new OrthogonalTiledMapRenderer(map, UNITSCALE);
	}

	public void render() {
		renderer.setView(camera);
		renderer.render();
	}
}
