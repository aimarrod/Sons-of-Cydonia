package com.soc.game.map;

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
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Enemy;
import com.soc.game.components.Feet;
import com.soc.game.components.Flying;
import com.soc.game.components.Player;
import com.soc.game.components.Position;
import com.soc.game.components.Velocity;
import com.soc.utils.MapLoader;



public class Map{
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
	@Mapper 
	ComponentMapper<Attack> am;
	
	
	public Tile[][][] tiles;
}
	
