package com.soc.components;

public class WeaponAttack extends AnimatedComponent{
	float range;
	int damage;
	public WeaponAttack(float range, int damage){
		this.range=range;
		this.damage=damage;
	}
}
