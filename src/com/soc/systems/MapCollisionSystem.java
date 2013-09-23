package com.soc.systems;

import java.util.Iterator;
import java.util.List;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.soc.EntityFactory;
import com.soc.components.Flying;
import com.soc.components.Position;
import com.soc.components.Bounds;
import com.soc.components.Sprite;
import com.soc.components.Velocity;



public class MapCollisionSystem extends EntityProcessingSystem {
	@Mapper
	ComponentMapper<Position> pm;
	@Mapper
	ComponentMapper<Bounds> bm;
	@Mapper
	ComponentMapper<Velocity> vm;
		
	private MapLayers layers;
	private Bag<Rectangle> terrains;
	
	@SuppressWarnings("unchecked")
	public MapCollisionSystem(TiledMap map) {
		super(Aspect.getAspectForAll(Position.class, Bounds.class, Velocity.class).exclude(Flying.class));
		this.layers = map.getLayers();
		this.terrains = new Bag<Rectangle>();
		retrieveCollisionObjects(layers.get("collision"));

	}
	
	protected void retrieveCollisionObjects(MapLayer layer){
		Iterator<MapObject> i = layer.getObjects().iterator();
		while(i.hasNext()){			
			terrains.add( (((RectangleMapObject)i.next()).getRectangle()) );
		}
	}

	@Override
	protected void process(Entity e) {
		
		for(int i=0; i < terrains.size(); i++){
			Position pos = pm.get(e);
			Bounds bounds = bm.get(e);
			Velocity v = vm.get(e);

			float px = pos.x + v.vx*world.delta;
			float py = pos.y + v.vy*world.delta;
			Rectangle poly = new Rectangle(px, py, bounds.height, bounds.width);
			if(Intersector.overlaps(poly, terrains.get(i))){
				System.out.println("Overlaps");
				v.vx = 0;
				v.vy = 0;
			}
		}
	}
}
	
