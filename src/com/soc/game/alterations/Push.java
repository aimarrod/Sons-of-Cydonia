package com.soc.game.alterations;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.SoC;
import com.soc.game.components.Velocity;

public class Push implements Alteration{
	public Vector2 direction;
	public float distance;
	public float speed;
	
	public Push(Vector2 direction, float distance, float speed){
		this.direction = direction;
		this.distance = distance;
		this.speed = speed;
	}
	
	
	public void process(Entity e){
	  Velocity vel = SoC.game.velocitymapper.get(e);
	  
	  vel.vx = speed*Math.signum(direction.x);
	  vel.vy = speed*Math.signum(direction.y);
	  
	  distance -= Math.abs(vel.vx*SoC.game.world.delta)+Math.abs(vel.vy*SoC.game.world.delta);
	  
	  if(distance <= 0){
		  SoC.game.debuffmapper.get(e).removeDebuff(this);
		  vel.vx = 0;
		  vel.vy = 0;
	  }
	}
}
