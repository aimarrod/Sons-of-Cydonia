package com.soc.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.core.SoC;
import com.soc.game.components.Player;
import com.soc.utils.GraphicsLoader;

public abstract class Item implements Cloneable{
	public TextureRegion icon;
	public String tooltip;
	public String name;
	public int num;
	
	public Item(String name, String iconPath, String tooltip,int num){
		this.name=name;
		this.icon=new TextureRegion(GraphicsLoader.load("items/"+iconPath));
		this.tooltip=tooltip;
		this.num=num;
	}
	
	public abstract void use();
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return null;
	}
}
