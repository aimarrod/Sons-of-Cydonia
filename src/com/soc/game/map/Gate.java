package com.soc.game.map;

import com.soc.core.Constants;

public class Gate extends Tile{
	
	public String destination;
	public float x, y;
	
	public Gate(String destination, float x, float y, boolean level){
		super((level)?Constants.World.TILE_LEVEL_CHANGE:Constants.World.TILE_MAP_CHANGE);
		this.x = x;
		this.y = y;
		this.destination = destination;
	}
}
