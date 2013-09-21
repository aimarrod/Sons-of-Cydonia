package com.soc.utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class MapLoader {
	private final static String BASE_DIR = "resources/maps/";
	private final static String EXTENSION_TMX = ".tmx";

	public static TiledMap loadMap(String name){
		return new TmxMapLoader().load(BASE_DIR + name + EXTENSION_TMX);
	}
}
