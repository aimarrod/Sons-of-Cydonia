package com.soc.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.soc.utils.Globals;

public class ActionBar extends Actor{

	public Texture slot;
	int[] spells;
	
	public ActionBar(){
		slot = new Texture(Gdx.files.internal("resources/spell-container.png"));
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		spells = Globals.playerStats.spells;
		for(int i = 0; i < spells.length; i++){
			batch.draw(Globals.spells[spells[i]].icon, getX()+(i*slot.getHeight()+2)+3, getY()+5);
			batch.draw(slot, getX()+(i*slot.getHeight()+2), getY());
		}
	}
}
