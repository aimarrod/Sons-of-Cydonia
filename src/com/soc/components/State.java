package com.soc.components;

import com.artemis.Component;

public class State extends Component{
	
	public State(int state){
		this.state = state;
		this.statetime = 0;
	}
	
	public int state;
	public float statetime;
	
	final public static int IDLE_NORTH = 0;
	final public static int IDLE_WEST = 1;
	final public static int IDLE_SOUTH = 2;
	final public static int IDLE_EAST = 3;
	final public static int WALK_NORTH = 4;
	final public static int WALK_WEST = 5;
	final public static int WALK_SOUTH = 6;
	final public static int WALK_EAST = 7;

	
}
