package com.soc.game.components;

import com.artemis.Component;
import com.soc.game.graphics.DirectionalAnimatedRenderer;

public class Attacker extends Component{
	public float range;
	public int damage;
	public int attack;
	
	public Attacker(float range, int damage, int attack) {
		this.range=range;
		this.damage=damage;
		this.attack = attack;
	}
}
