package com.soc.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.soc.utils.Globals;

public class StatusBar extends Actor{
	
	public final float baroffsetx = 73f;
	public final float baroffsety = 8f;
	public final float spacey = 20f;
	
	public Texture container;
	public Texture bars;
	
	public StatusBar(){
		bars = new Texture(Gdx.files.internal("resources/hp-mp.png"));
		container = new Texture(Gdx.files.internal("resources/bar-hp-mp.png"));
	}

	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
        batch.draw(container, getX(),  getY());
		float proportion = (float)Globals.playerStats.health / (float)Globals.playerStats.maxHealth;
        batch.draw(bars, getX() +3, getY() + 34, 0, 0, (int)(bars.getWidth()*proportion), (int)(bars.getHeight()*0.5));
		proportion = (float)Globals.playerStats.mana / (float)Globals.playerStats.maxMana;
        batch.draw(bars, getX() +3, getY() + 9, 0, (int)(0+bars.getHeight()*0.5f), (int)(bars.getWidth()*proportion), (int)(bars.getHeight()*0.5));
	}
}
