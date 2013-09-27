package com.soc.game.systems;

import java.util.Iterator;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
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



public class MapCollisionSystem extends EntityProcessingSystem {
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Bounds> bm;
	@Mapper
	ComponentMapper<Velocity> vm;
	@Mapper
	ComponentMapper<Enemy> em;
		
	private MapLayers layers;
	private Bag<Rectangle> obstacles;
	private int[][] tiles;
	
	@SuppressWarnings("unchecked")
	public MapCollisionSystem(TiledMap map) {
		super(Aspect.getAspectForAll(Position.class, Bounds.class, Velocity.class).exclude(Flying.class));
		this.layers = map.getLayers();
		this.obstacles = new Bag<Rectangle>();
		retrieveCollisionObjects(layers.get("collision"));
		getTiles(map);
	}
	
	protected void getTiles(TiledMap map){
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("background");
		tiles = new int[layer.getWidth()][layer.getHeight()];
		for(int i = 0; i < layer.getWidth(); i++){
			for(int j = 0; j < layer.getHeight(); j++){
				Vector2 pos = new Vector2( (i*World.TILE_SIZE)+(World.TILE_SIZE/2),(j*World.TILE_SIZE)+(World.TILE_SIZE/2));
				for(int m = 0; m < obstacles.size(); m++){
					Rectangle rect = obstacles.get(m);
					Rectangle tile = new Rectangle(pos.x, pos.y, World.TILE_SIZE, World.TILE_SIZE);
					if(rect.overlaps(tile)){
						tiles[i][j]=1;
						break;
					}
				}
			}
		}
		System.out.println(tiles[87][65]);
		AStar.initialize(tiles);
	}
	
	protected void retrieveCollisionObjects(MapLayer layer){
		Iterator<MapObject> i = layer.getObjects().iterator();
		while(i.hasNext()){			
			obstacles.add( (((RectangleMapObject)i.next()).getRectangle()) );
		}
	}

	@Override
	protected void process(Entity e) {
		
		Position pos = pm.get(e);
		Velocity v = vm.get(e);
		Enemy enemy = em.getSafe(e);
		
		float px = pos.x + v.vx*world.delta;
		float py = pos.y + v.vy*world.delta;
				
		if(tiles[(int)(px*World.TILE_FACTOR)][(int)(pos.y*World.TILE_FACTOR)]>0){
 			v.vx = 0; 
		}
		if(tiles[(int)(pos.x*World.TILE_FACTOR)][(int)(py*World.TILE_FACTOR)]>0){
			v.vy = 0;
		}
	}
}
	
