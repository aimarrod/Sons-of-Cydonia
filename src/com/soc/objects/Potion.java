package com.soc.objects;

import com.soc.core.SoC;
import com.soc.game.components.Stats;

public class Potion extends Item{
	int gainHealth;
	int gainMana;
	public Potion(String name, String iconPath, String tooltip,int gainHealth,int gainMana) {
		super(name, iconPath, tooltip);
		this.gainHealth=gainHealth;
		this.gainMana=gainMana;
	}
	public void use(){
		Stats stats=SoC.game.statsmapper.get(SoC.game.player);
		stats.health+=gainHealth;
		if(stats.health>stats.maxHealth)
			stats.health=stats.maxHealth;
		stats.mana+=gainMana;
		if(stats.mana>stats.maxMana)
			stats.mana=stats.maxMana;
	}

}
