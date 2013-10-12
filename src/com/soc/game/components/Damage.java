package com.soc.game.components;

import com.artemis.Component;

public class Damage extends Component{
	public int damage;
	public int pureDamage;
	public float r;
	public float g;
	public float b;
	
	public Damage(int damage, float r, float g, float b, boolean pure){
		if(pure) this.pureDamage=damage;
		else this.damage=damage;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public Damage(int damage, boolean pure){
		if(pure) this.pureDamage = damage;
		else this.damage=damage;
		this.r = 1;
		this.g = 1;
		this.b = 1;
	}
}
