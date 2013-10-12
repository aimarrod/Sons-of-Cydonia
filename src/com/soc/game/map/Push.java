package com.soc.game.map;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.core.Constants.World;
import com.soc.game.components.Debuff;
import com.soc.game.components.Position;

public class Push extends Tile{

	public Vector2 dir;
	public float posx, posy;
	
	public Push(int dirx, int diry, float posx, float posy) {
		super(Constants.World.TILE_PUSH);
		dir = new Vector2(dirx, diry);
	}
	
	public void push(Entity e){
		Debuff.addDebuff(e, new com.soc.game.alterations.Push(dir, 32, 150));
	}

}
