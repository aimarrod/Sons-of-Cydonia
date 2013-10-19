package com.soc.game.objects;

import com.soc.core.SoC;
import com.soc.game.components.Stats;

public class Potion extends Item{
	public int gainHealth;
	public int gainMana;
	String iconPath;

	public Potion(int num, String name, String iconPath, String tooltip,int gainHealth,int gainMana) {
		super(name, iconPath, tooltip,num);
		this.gainHealth=gainHealth;
		this.gainMana=gainMana;
		this.iconPath=iconPath;
	}
	public void use(){
		Stats stats=SoC.game.statsmapper.get(SoC.game.player);
		stats.health+=gainHealth;
		if(stats.health>stats.maxHealth)
			stats.health=stats.maxHealth;
		stats.mana+=gainMana;
		if(stats.mana>stats.maxMana)
			stats.mana=stats.maxMana;
		SoC.game.playermapper.get(SoC.game.player).removeFromInventary(this);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Potion clone=new Potion(this.num,this.name,this.iconPath,this.tooltip,this.gainHealth,this.gainMana);
		return clone;
	}

}
