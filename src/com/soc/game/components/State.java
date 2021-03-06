package com.soc.game.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class State extends Component{
	
	public State(int state){
		this.state = state;
		this.nextAttack=0;
		this.statetime = 0;	
	}
	
	public int state;
	public float statetime;
	public int nextAttack;
	
	final public static int IDLE = 0;
	final public static int WALK = 1;
	final public static int RUN = 2;
	final public static int ATTACK = 3;
	final public static int DYING = 4;
	final public static int CHARGING = 5;
	final public static int SPINNING = 6;
	final public static int FALLING = 7;
	
	final public static int STATENUM = 8;
	
	
	final public static int BLOCKED = 3;
}
