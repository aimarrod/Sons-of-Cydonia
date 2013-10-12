package com.soc.utils;

import java.util.Iterator;

import com.artemis.utils.Bag;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.soc.ai.AStar;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.core.Constants.World;
import com.soc.game.map.DialogTile;
import com.soc.game.map.Gate;
import com.soc.game.map.Map;
import com.soc.game.map.Push;
import com.soc.game.map.Stairs;
import com.soc.game.map.Tile;
import com.soc.game.systems.RenderSystem;

public class MapLoader {
	private final static String BASE_DIR = "resources/maps/";

	public static TiledMap loadMap(String name) {
		TiledMap map = new TmxMapLoader().load(BASE_DIR + name);
		SoC.game.map = new Map();
		SoC.game.map.name = name;
		MusicPlayer.reset();
		MusicPlayer.play(map.getProperties().get("music", String.class));
		MapLoader.loadTiles(map);
		MapLoader.loadSpawners(map);
		SoC.game.world.getSystem(RenderSystem.class).changeMap(map);
		return map;
	}
	
	public static void loadTiles(TiledMap map) {
		int layers = Integer.parseInt(map.getProperties().get("layers",
				String.class));
		int height = map.getProperties().get("height", Integer.class);
		int width = map.getProperties().get("width", Integer.class);

		SoC.game.map.tiles = new Tile[layers][width][height];

		for (int l = 0; l < layers; l++) {

			Iterator<MapObject> obstacle = map.getLayers()
					.get("level" + l + "-obstacle").getObjects().iterator();
			Iterator<MapObject> unwalkable = map.getLayers()
					.get("level" + l + "-unwalkable").getObjects().iterator();
			Iterator<MapObject> spec = map.getLayers()
					.get("level" + l + "-special").getObjects().iterator();

			Bag<Rectangle> unwalkables = new Bag<Rectangle>();
			Bag<Rectangle> obstacles = new Bag<Rectangle>();
			Bag<RectangleMapObject> special = new Bag<RectangleMapObject>();

			while (obstacle.hasNext())
				obstacles.add((((RectangleMapObject) obstacle.next())
						.getRectangle()));
			while (spec.hasNext())
				special.add((((RectangleMapObject) spec.next())));
			while (unwalkable.hasNext())
				unwalkables.add(((((RectangleMapObject) unwalkable.next())
						.getRectangle())));

			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {

					Vector2 pos = new Vector2((i * World.TILE_SIZE),
							(j * World.TILE_SIZE));
					Rectangle tile = new Rectangle(pos.x, pos.y,
							World.TILE_SIZE, World.TILE_SIZE);

					for (int m = 0; m < obstacles.size(); m++) {
						if (tile.overlaps(obstacles.get(m))) {
							SoC.game.map.tiles[l][i][j] = new Tile(
									World.TILE_OBSTACLE);
							break;
						}
					}

					for (int m = 0; m < unwalkables.size(); m++) {
						if (tile.overlaps(unwalkables.get(m))) {
							SoC.game.map.tiles[l][i][j] = new Tile(
									World.TILE_UNWALKABLE);
							break;
						}
					}

					for (int m = 0; m < special.size(); m++) {
						if (tile.overlaps(special.get(m).getRectangle())) {
							MapProperties obj = special.get(m).getProperties();
							String type = obj.get("type", String.class);
							if (type.equals("stairs")) {
								SoC.game.map.tiles[l][i][j] = new Stairs(
										Integer.parseInt(obj.get("level",
												String.class)));
							} else if (type.equals("gate")) {
								SoC.game.map.tiles[l][i][j] = new Gate(obj.get(
										"destination", String.class),
										Integer.parseInt(obj.get("x",
												String.class)),
										Integer.parseInt(obj.get("y",
												String.class)),
										Integer.parseInt(obj.get("z",
												String.class)));
							} else if (type.equals("lava")){
								SoC.game.map.tiles[l][i][j] = new Tile(World.TILE_LAVA);
							} else if (type.equals("hole")){
								SoC.game.map.tiles[l][i][j] = new Tile(World.TILE_HOLE);
							} else if (type.equals("dialog")){
								SoC.game.map.tiles[l][i][j] = new DialogTile(
										Integer.parseInt(obj.get("id", String.class))
								);
							} else if (type.equals("push")){
								SoC.game.map.tiles[l][i][j] = new Push(
										Integer.parseInt(obj.get("x", String.class)),
										Integer.parseInt(obj.get("y", String.class)),
										i*World.TILE_SIZE,
										j*World.TILE_SIZE
								);
							}
							break;
						}
					}

					if (SoC.game.map.tiles[l][i][j] == null) {
						SoC.game.map.tiles[l][i][j] = new Tile(
								Constants.World.TILE_WALKABLE);
					}

				}
			}
		}
		AStar.initialize(SoC.game.map.tiles);
	}

	public static void loadSpawners(TiledMap map) {
		//Number of levels of the map. The first map has 2 levels.
		int layers = Integer.parseInt(map.getProperties().get("layers",
				String.class));
		for (int i = 0; i < layers; i++) {
			Iterator<MapObject> spawners = map.getLayers()
					.get("level" + i + "-spawners").getObjects().iterator();
			while (spawners.hasNext()) {
				//The position and the bounds of the spawner
				RectangleMapObject spawner = (RectangleMapObject) spawners
						.next();
				Rectangle rect = spawner.getRectangle();
				EntityFactory
						.createSpawner(
								rect.getX(),
								rect.getY(),
								i,
								(int) rect.getWidth(),
								(int) rect.getHeight(),
								spawner.getProperties().get("type",
										String.class),
								Integer.parseInt(spawner.getProperties().get(
										"max", String.class)),
								Integer.parseInt(spawner.getProperties().get(
										"range", String.class)),
								Float.parseFloat(spawner.getProperties().get(
										"interval", String.class)), 
								Boolean.parseBoolean(spawner.getProperties().get("respawn",String.class)))
										.addToWorld();
				
			}
		}
	}

}
