package com.soc.game.map;

import com.soc.core.Constants;

public class Gate extends Tile{
	
	public String destination;
	public float x, y;
	public int z;
	
	public Gate(String destination, float x, float y, int z){
		super(Constants.World.TILE_GATE);
		this.x = x;
		this.y = y;
		this.z = z;
		this.destination = destination;
	}
}
