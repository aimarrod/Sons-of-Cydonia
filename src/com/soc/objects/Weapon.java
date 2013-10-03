package com.soc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.core.SoC;
import com.soc.game.components.Stats;

public class Weapon {
	public TextureRegion icon;
	public String tooltip;
	public int gainStrenght;
	public int gainAgility;
	public int gainIntelligence;
	public String name;
	private Stats stats;
	
	public Weapon(String name,String iconPath ,String tooltip, int gainStrenght, int gainAgility, int gainIntelligence){
		this.name=name;
		this.icon=new TextureRegion(new Texture(Gdx.files.internal(iconPath)), 64, 64);
		this.tooltip=tooltip;
		this.gainStrenght=gainStrenght;
		this.gainIntelligence=gainIntelligence;
		this.gainAgility=gainAgility;
		stats=SoC.game.statsmapper.get(SoC.game.player);
	}
	public void equip(){
		stats.strength+=gainStrenght;
		stats.intelligence+=gainIntelligence;
		stats.agility+=gainAgility;
	}
	public void remove(){
		stats.strength-=gainStrenght;
		stats.intelligence-=gainIntelligence;
		stats.agility-=gainAgility;
	}
}
