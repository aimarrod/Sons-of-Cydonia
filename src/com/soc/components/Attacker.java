package com.soc.components;

public class Attacker extends AnimatedComponent{
	float range;
	int damage;
	public Attacker(float range, int damage) {
		this.range=range;
		this.damage=damage;
	}
}
