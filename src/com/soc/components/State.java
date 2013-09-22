package com.soc.components;

import com.artemis.Component;

public class State extends Component{
	
	public State(int state, int direction){
		this.state = state;
		this.direction = direction;
		this.statetime = 0;
	}
	
	public int state, direction;
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
