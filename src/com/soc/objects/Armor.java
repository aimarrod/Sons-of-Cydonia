package com.soc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.core.SoC;
import com.soc.game.components.Stats;

public class Armor {
	public TextureRegion icon;
	public String tooltip;
	public float gainArmor;
	public String name;
	private Stats stats;
	
	public Armor(String name,String iconPath ,String tooltip, float gainArmor){
		this.name=name;
		this.icon=new TextureRegion(new Texture(Gdx.files.internal(iconPath)), 64, 64);
		this.tooltip=tooltip;
		this.gainArmor=gainArmor;
		stats=SoC.game.statsmapper.get(SoC.game.player);
	}
	public void equip(){
		stats.armor+=gainArmor;
	}
	public void remove(){
		stats.armor-=gainArmor;
	}
}
