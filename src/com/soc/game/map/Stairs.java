package com.soc.game.map;

import com.soc.core.Constants;

public class Stairs extends Tile{

	public int level;
	
	public Stairs(int level) {
		super(Constants.World.TILE_STAIRS);
		this.level = level;
	}

}
