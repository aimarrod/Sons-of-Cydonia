package com.soc.game.components;

import com.artemis.Component;
import com.artemis.Entity;
import com.soc.game.attacks.processors.AttackProcessor;
import com.soc.game.graphics.AnimatedRenderer;

public class Attack extends Component{
	public AttackProcessor processor;
	public int damage;
	
	public Attack(AttackProcessor processor, int damage){
		this.processor = processor;
		this.damage = damage;
	}
}
