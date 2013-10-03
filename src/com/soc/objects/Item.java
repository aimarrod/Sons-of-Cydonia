package com.soc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.core.SoC;
import com.soc.game.components.Player;

public abstract class Item {
	public TextureRegion icon;
	public String tooltip;
	public String name;
	public Player player;
	
	public Item(String name, String iconPath, String tooltip){
		this.name=name;
		this.icon=new TextureRegion(new Texture(Gdx.files.internal(iconPath)), 64, 64);
		this.tooltip=tooltip;
		this.player=SoC.game.playermapper.get(SoC.game.player);
	}
}
