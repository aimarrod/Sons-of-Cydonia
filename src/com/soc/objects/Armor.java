package com.soc.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.core.SoC;
import com.soc.game.components.Stats;

public class Armor extends Item{
	public float gainArmor;
	
	public Armor(String name,String iconPath ,String tooltip, float gainArmor){
		super(name, iconPath,tooltip);
		this.gainArmor=gainArmor;
	}
	public void equip(){
		Stats stats=SoC.game.statsmapper.get(SoC.game.player);
		stats.armor+=gainArmor;
		SoC.game.playermapper.get(SoC.game.player).armor=this;
		SoC.game.playermapper.get(SoC.game.player).removeFromInventary(this);
	}
	public void remove(){
		Stats stats=SoC.game.statsmapper.get(SoC.game.player);
		if(SoC.game.playermapper.get(SoC.game.player).numFreeSlots()>0){
			stats.armor-=gainArmor;
			SoC.game.playermapper.get(SoC.game.player).addToInventary(this);
			SoC.game.playermapper.get(SoC.game.player).armor=null;
		}
	}
}
