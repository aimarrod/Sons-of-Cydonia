package com.soc.components;

import com.artemis.Component;
import com.soc.graphics.AttackRenderer;

public class Attack extends Component{
	public AttackRenderer attack;
	public int damage;
	
	public Attack(AttackRenderer attack, int damage){
		this.attack = attack;
		this.damage = damage;
	}
}
