package com.soc.utils;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile.BlendMode;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class MapLoader {
	private final static String BASE_DIR = "resources/maps/";

	public static TiledMap loadMap(String name){
		TiledMap map =  new TmxMapLoader().load(BASE_DIR + name);
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("background");
		for(int i = 0; i < layer.getWidth(); i++){
			for(int j = 0; j < layer.getHeight(); j++){
				layer.getCell(i,j).getTile().setBlendMode(BlendMode.NONE);
			}
		}
		return map;
	}
}
