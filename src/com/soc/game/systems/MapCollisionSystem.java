package com.soc.game.systems;

import java.util.Iterator;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.soc.algorithms.AStar;
import com.soc.game.components.Bounds;
import com.soc.game.components.Enemy;
import com.soc.game.components.Flying;
import com.soc.game.components.Position;
import com.soc.game.components.Velocity;
import com.soc.utils.Constants.World;
import com.soc.utils.Tile;



public class MapCollisionSystem extends EntityProcessingSystem {
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Bounds> bm;
	@Mapper
	ComponentMapper<Velocity> vm;
	@Mapper
	ComponentMapper<Enemy> em;
		
	private Tile[][] tiles;
	
	@SuppressWarnings("unchecked")
	public MapCollisionSystem(TiledMap map) {
		super(Aspect.getAspectForAll(Position.class, Bounds.class, Velocity.class).exclude(Flying.class));
		loadTiles(map);
	}
	
	protected void loadTiles(TiledMap map){
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("background");
		Iterator<MapObject> collision = map.getLayers().get("collision").getObjects().iterator();
		Iterator<MapObject> border = map.getLayers().get("border").getObjects().iterator();
		Bag<Rectangle> obstacles = new Bag<Rectangle>();
		Bag<Rectangle> entrances = new Bag<Rectangle>();

		
		while(collision.hasNext()) obstacles.add( (((RectangleMapObject)collision.next()).getRectangle()) );
		while(border.hasNext()) entrances.add( (((RectangleMapObject)border.next()).getRectangle()) );
		
		tiles = new Tile[layer.getWidth()][layer.getHeight()];
		for(int i = 0; i < layer.getWidth(); i++){
			for(int j = 0; j < layer.getHeight(); j++){
				
				Vector2 pos = new Vector2( (i*World.TILE_SIZE)+(World.TILE_SIZE/2),(j*World.TILE_SIZE)+(World.TILE_SIZE/2));
				Rectangle tile = new Rectangle(pos.x, pos.y, World.TILE_SIZE, World.TILE_SIZE);
				tiles[i][j]=new Tile(World.TILE_WALKABLE);
				
				for(int m = 0; m < obstacles.size(); m++){
					if(tile.overlaps(obstacles.get(m))){
						tiles[i][j].type = World.TILE_OBSTACLE;
						break;
					}
				}
				
				for(int m = 0; m < entrances.size(); m++){
					if(tile.overlaps(entrances.get(m))){
						tiles[i][j].type = World.TILE_MAP_CHANGE;
						break;
					}
				}
				
			}
		}
		AStar.initialize(tiles);
	}

	@Override
	protected void process(Entity e) {
		
		Position pos = pm.get(e);
		Velocity v = vm.get(e);
		
		float px = pos.x + v.vx*world.delta;
		float py = pos.y + v.vy*world.delta;
				
		if(tiles[(int)(px*World.TILE_FACTOR)][(int)(pos.y*World.TILE_FACTOR)].type > 0){
 			v.vx = 0; 
		}
		if(tiles[(int)(pos.x*World.TILE_FACTOR)][(int)(py*World.TILE_FACTOR)].type > 0){
			v.vy = 0;
		}
	}

}
	
