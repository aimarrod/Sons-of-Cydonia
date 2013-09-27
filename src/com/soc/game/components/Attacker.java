package com.soc.game.components;

import com.artemis.Component;
import com.soc.game.graphics.DirectionalAnimatedRenderer;

public class Attacker extends Component{
	public float range;
	public int damage;
	public int type;
	
	public Attacker(float range, int damage, int type) {
		this.range=range;
		this.damage=damage;
		this.type = type;
	}
}
