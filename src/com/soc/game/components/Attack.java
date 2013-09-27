package com.soc.game.components;

import com.artemis.Component;
import com.soc.game.graphics.AttackRenderer;

public class Attack extends Component{
	public AttackRenderer renderer;
	public int damage;
	
	public Attack(AttackRenderer renderer, int damage){
		this.renderer = renderer;
		this.damage = damage;
	}
}
