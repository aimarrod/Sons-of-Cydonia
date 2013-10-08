package com.soc.game.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class Push extends Component{
	public Vector2 direction;
	public float distance;
	public float speed;
	
	public Push(Vector2 direction, float distance, float speed){
		this.direction = direction;
		this.distance = distance;
		this.speed = speed;
	}
}
