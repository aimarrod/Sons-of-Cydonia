package com.soc.objects;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.core.SoC;
import com.soc.game.components.Stats;

public class Weapon extends Item {
	public int gainStrenght;
	public int gainAgility;
	public int gainIntelligence;
	
	public Weapon(String name,String iconPath ,String tooltip, int gainStrenght, int gainAgility, int gainIntelligence){
		super(name,iconPath,tooltip);
		this.gainStrenght=gainStrenght;
		this.gainIntelligence=gainIntelligence;
		this.gainAgility=gainAgility;
		
	}
	public void equip(){
		Stats stats=SoC.game.statsmapper.get(SoC.game.player);
		stats.strength+=gainStrenght;
		stats.intelligence+=gainIntelligence;
		stats.agility+=gainAgility;
		SoC.game.playermapper.get(SoC.game.player).removeFromInventary(this);
		SoC.game.playermapper.get(SoC.game.player).weapon=this;
	}
	public void remove(){
		Stats stats=SoC.game.statsmapper.get(SoC.game.player);
		stats.strength-=gainStrenght;
		stats.intelligence-=gainIntelligence;
		stats.agility-=gainAgility;
		SoC.game.playermapper.get(SoC.game.player).addToInventary(this);
		SoC.game.playermapper.get(SoC.game.player).weapon=null;
	}
}
