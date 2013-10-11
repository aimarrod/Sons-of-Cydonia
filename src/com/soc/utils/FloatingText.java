package com.soc.utils;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.soc.core.SoC;

public class FloatingText {
	public String text;
	public float time;
	public float posx;
	public float posy;
	public float speed;
	public float r;
	public float g;
	public float b;
	
	public FloatingText(String text, float time, float posx, float posy, float speed) {
		this.text = text;
		this.time = time;
		this.posx = posx;
		this.posy = posy;
		this.speed = speed;
		this.r = 1;
		this.g = 1;
		this.b = 1;
	}
	
	public boolean draw(SpriteBatch batch, BitmapFont font){
		font.draw(batch, text, posx, posy);
		posy += speed*SoC.game.world.delta;
		time -= SoC.game.world.delta;
		return time <= 0;
	}
} 
