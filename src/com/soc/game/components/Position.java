package com.soc.game.components;

import com.artemis.Component;

public class Position extends Component {
	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Position() {
		this(0, 0);
	}

	public float x, y;
}
