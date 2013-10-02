package com.soc.game.systems;

import java.util.Iterator;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.soc.algorithms.AStar;
import com.soc.core.Constants;
import com.soc.core.Constants.World;
import com.soc.core.SoC;
import com.soc.game.components.Bounds;
import com.soc.game.components.Enemy;
import com.soc.game.components.Feet;
import com.soc.game.components.Flying;
import com.soc.game.components.Player;
import com.soc.game.components.Position;
import com.soc.game.components.Velocity;
import com.soc.utils.Gate;
import com.soc.utils.MapLoader;
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
	@Mapper
	ComponentMapper<Feet> fm;
	@Mapper 
	ComponentMapper<Player> plm;
	
	private Tile[][] tiles;
	
	@SuppressWarnings("unchecked")
	public MapCollisionSystem() {
		super(Aspect.getAspectForAll(Position.class, Bounds.class, Velocity.class, Feet.class).exclude(Flying.class));
	}
	
	public void loadTiles(TiledMap map){
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("background");
		Iterator<MapObject> collision = map.getLayers().get("collision").getObjects().iterator();
		Iterator<MapObject> border = map.getLayers().get("border").getObjects().iterator();
		Bag<Rectangle> obstacles = new Bag<Rectangle>();
		Bag<RectangleMapObject> entrances = new Bag<RectangleMapObject>();

		
		while(collision.hasNext()) obstacles.add( (((RectangleMapObject)collision.next()).getRectangle()) );
		while(border.hasNext()) entrances.add( (((RectangleMapObject)border.next())) );
		
		tiles = new Tile[layer.getWidth()][layer.getHeight()];
		for(int i = 0; i < layer.getWidth(); i++){
			for(int j = 0; j < layer.getHeight(); j++){
				
				Vector2 pos = new Vector2( (i*World.TILE_SIZE),(j*World.TILE_SIZE));
				Rectangle tile = new Rectangle(pos.x, pos.y, World.TILE_SIZE, World.TILE_SIZE);
				
				for(int m = 0; m < obstacles.size(); m++){
					if(tile.overlaps(obstacles.get(m))){
						tiles[i][j] = new Tile(World.TILE_OBSTACLE);
						break;
					}
				}
				
				for(int m = 0; m < entrances.size(); m++){
					if(tile.overlaps(entrances.get(m).getRectangle())){
						MapProperties obj = entrances.get(m).getProperties();
						tiles[i][j] = new Gate(obj.get("map",String.class), Integer.parseInt(obj.get("x", String.class)), Integer.parseInt(obj.get("y", String.class)), false);
						break;
					}
				}
				
				if(tiles[i][j] == null){
					tiles[i][j] = new Tile(Constants.World.TILE_WALKABLE);
				}
				
			}
		}
		AStar.initialize(tiles);
	}

	@Override
	protected void process(Entity e) {
		
		Position pos = pm.get(e);
		Velocity v = vm.get(e);
		Feet feet = fm.get(e);
		
		//IMPROVE
		
		int nextleft = (int) ( (pos.x + v.vx*world.delta + feet.width) * World.TILE_FACTOR);
		int nextright = (int) ( (pos.x + v.vx*world.delta) * World.TILE_FACTOR);
		int nextup = (int) ( (pos.y + v.vy*world.delta + feet.heigth) * World.TILE_FACTOR);
		int nextdown = (int) ( (pos.y + v.vy*world.delta) * World.TILE_FACTOR);
		
		int up = (int) ( (pos.y + feet.heigth) * World.TILE_FACTOR);
		int rigth = (int) ( (pos.x + feet.width) * World.TILE_FACTOR);
		int left = (int) ( (pos.x) * World.TILE_FACTOR);
		int down = (int) ( (pos.y) * World.TILE_FACTOR);
		
		int centerx = (int) ( (pos.x + feet.width*0.5) * World.TILE_FACTOR);
		int centery = (int) ( (pos.y + feet.heigth*0.5) * World.TILE_FACTOR);
				
		if(tiles[nextright][up].type == World.TILE_OBSTACLE || tiles[nextleft][up].type == World.TILE_OBSTACLE || tiles[nextright][down].type == World.TILE_OBSTACLE || tiles[nextleft][down].type == World.TILE_OBSTACLE){
 			v.vx = 0; 
		}
		if(tiles[rigth][nextup].type == World.TILE_OBSTACLE || tiles[left][nextup].type == World.TILE_OBSTACLE || tiles[rigth][nextdown].type == World.TILE_OBSTACLE || tiles[left][nextdown].type == World.TILE_OBSTACLE){
			v.vy = 0;
		}
		
		if(plm.has(e)){
			if(tiles[centerx][centery].type == World.TILE_MAP_CHANGE){
				SoC.game.getScreen().pause();
				Gate gate = (Gate) tiles[centerx][centery];
				SoC.game.resetWorld();
				pos.x = gate.x * Constants.World.TILE_SIZE;
				pos.y = gate.y * Constants.World.TILE_SIZE;
			}
		}
		
	}

}
	
