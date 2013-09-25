package com.soc.game.components;

import com.artemis.Component;

public class Velocity extends Component{
	 public float vx, vy;
	 public int speed;
	  
	 public Velocity(float vx, float vy, int speed) {
	  this.vx = vx;
	  this.vy = vy;
	  this.speed=speed;
	 }
	  
	 public Velocity() {
	  this(0,0,0);
	 }
}
