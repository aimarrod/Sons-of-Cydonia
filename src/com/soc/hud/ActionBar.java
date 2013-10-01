package com.soc.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.soc.utils.Globals;

public class ActionBar extends Actor{

	public Texture slot;
	
	public ActionBar(){
		slot = new Texture(Gdx.files.internal("resources/spell-contaner.png"));
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		for(int i = 0; i < Globals.playerControls.spells.length; i++){
			batch.draw(slot, getX()+(i*slot.getHeight()+2), getY());
		}
	}
}
