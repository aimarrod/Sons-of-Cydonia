package com.soc.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class WorldCollisionSystem {

	public World world;
	
	public WorldCollisionSystem(){
		world = new World(new Vector2(0, 0), true);
	}
}
