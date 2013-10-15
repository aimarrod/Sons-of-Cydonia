package com.soc.game.objects;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.core.SoC;
import com.soc.game.components.Stats;

public class Weapon extends Item {
	public int gainStrenght;
	public int gainAgility;
	public int gainIntelligence;
	
	public Weapon(int num,String name,String iconPath ,String tooltip, int gainStrenght, int gainAgility, int gainIntelligence){
		super(name,iconPath,tooltip,num);
		this.gainStrenght=gainStrenght;
		this.gainIntelligence=gainIntelligence;
		this.gainAgility=gainAgility;
		
	}
	
	public void use(){
		if(SoC.game.playermapper.get(SoC.game.player).weapon != null){
			Weapon current = SoC.game.playermapper.get(SoC.game.player).weapon.remove();
			if(current != this){
				equip();
			}
		} else {
			equip();
		}
	}

	private void equip(){
		Stats stats=SoC.game.statsmapper.get(SoC.game.player);
		stats.strength+=gainStrenght;
		stats.intelligence+=gainIntelligence;
		stats.agility+=gainAgility;
		SoC.game.playermapper.get(SoC.game.player).removeFromInventary(this);
		SoC.game.playermapper.get(SoC.game.player).weapon=this;
	}
	public Weapon remove(){
		Stats stats=SoC.game.statsmapper.get(SoC.game.player);
		if(SoC.game.playermapper.get(SoC.game.player).numFreeSlots()>0){
			stats.strength-=gainStrenght;
			stats.intelligence-=gainIntelligence;
			stats.agility-=gainAgility;
			SoC.game.playermapper.get(SoC.game.player).addToInventary(this);
			SoC.game.playermapper.get(SoC.game.player).weapon=null;
			return this;
		}
		return null;
	}
}
