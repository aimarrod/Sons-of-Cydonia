package com.soc.game.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class State extends Component{
	
	public State(int state, int direction){
		this.state = state;
		this.statetime = 0;	
	}
	
	public int state;
	public float statetime;
	
	final public static int IDLE = 0;
	final public static int WALK = 1;
	final public static int ATTACK = 2;
	
	final public static int NORTH = 0;
	final public static int WEST = 1;
	final public static int SOUTH = 2;
	final public static int EAST = 3;
	
	final public static int BLOCKED = 2;
}
