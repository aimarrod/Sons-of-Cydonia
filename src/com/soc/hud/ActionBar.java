package com.soc.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.soc.core.SoC;

public class ActionBar extends Actor{

	public Texture slot;
	int[] spells;
	
	public ActionBar(){
		slot = new Texture(Gdx.files.internal("resources/spell-container.png"));
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		spells = SoC.game.statsmapper.get(SoC.game.player).spells;
		for(int i = 0; i < spells.length; i++){
			batch.draw(SoC.game.spells[spells[i]].icon, getX()+(i*slot.getHeight()+2)+3, getY()+5);
			batch.draw(slot, getX()+(i*slot.getHeight()+2), getY());
		}
	}
}
