package com.soc.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.core.SoC;
import com.soc.game.components.Stats;

public class Armor extends Item{
	public TextureRegion icon;
	public String tooltip;
	public String name;
	public float gainArmor;
	private Stats stats;
	
	public Armor(String name,String iconPath ,String tooltip, float gainArmor){
		super(name, iconPath,tooltip);
		this.gainArmor=gainArmor;
		stats=SoC.game.statsmapper.get(SoC.game.player);
	}
	public void equip(){
		stats.armor+=gainArmor;
		player.removeFromInventary(this);
		player.armor=this;
	}
	public void remove(){
		stats.armor-=gainArmor;
		player.addToInventary(this);
		player.armor=null;
	}
}
