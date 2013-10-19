package com.soc.game.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.core.SoC;
import com.soc.game.components.Stats;

public class Armor extends Item{
	public float gainArmor;
	public String iconPath;
	
	public Armor(int num,String name,String iconPath ,String tooltip, float gainArmor){
		super(name, iconPath,tooltip,num);
		this.gainArmor=gainArmor;
		this.iconPath=iconPath;
	}
	
	public void use(){
		if(SoC.game.playermapper.get(SoC.game.player).armor != null){
			Armor current = SoC.game.playermapper.get(SoC.game.player).armor.remove();
			if(current != this){
				equip();
			}
		} else {
			equip();
		}
	}

	private void equip(){
		Stats stats=SoC.game.statsmapper.get(SoC.game.player);
		stats.armor+=gainArmor;
		SoC.game.playermapper.get(SoC.game.player).armor=this;
		SoC.game.playermapper.get(SoC.game.player).removeFromInventary(this);
	}
	public Armor remove(){
		Stats stats=SoC.game.statsmapper.get(SoC.game.player);
		if(SoC.game.playermapper.get(SoC.game.player).numFreeSlots()>0){
			stats.armor-=gainArmor;
			SoC.game.playermapper.get(SoC.game.player).addToInventary(this);
			SoC.game.playermapper.get(SoC.game.player).armor=null;
			return this;
		}
		return null;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Armor clone=new Armor(this.num,this.name,this.iconPath,this.tooltip,this.gainArmor);
		return clone;
	}
}
