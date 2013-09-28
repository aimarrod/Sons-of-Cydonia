package com.soc.game.components;

import com.artemis.Component;
import com.soc.game.attacks.AttackProcessor;
import com.soc.game.graphics.AttackRenderer;

public class Attack extends Component{
	public AttackProcessor processor;
	public int damage;
	
	public Attack(AttackProcessor processor, int damage){
		this.processor = processor;
		this.damage = damage;
	}
}
