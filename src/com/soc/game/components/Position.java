package com.soc.game.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class Position extends Component {
	
	public Position(){
		direction = new Vector2(0, -1);
	}
	
	public Position(float x, float y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		direction = new Vector2(0, -1);
	}

	public Position(float x, float y, int z, Vector2 direction) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.direction = direction.cpy();
	}

	public float x, y;
	public int z;
	public Vector2 direction;
}
