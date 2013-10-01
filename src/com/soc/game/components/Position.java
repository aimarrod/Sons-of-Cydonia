package com.soc.game.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class Position extends Component {
	public Position(float x, float y) {
		this.x = x;
		this.y = y;
		direction = new Vector2(0, 1);
	}

	public Position() {
		this(0, 0);
	}

	public float x, y;
	public Vector2 direction;
}
