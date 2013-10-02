package com.soc.game.spells;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.game.components.Position;
import com.soc.game.components.Stats;

public class PunchSpell extends Spell{
	public PunchSpell(){
		this.icon = new TextureRegion(new Texture(Gdx.files.internal("resources/punch-icon.png")), 64, 64);
		this.tooltip = "Punch in the face!";
		this.cast = 1f;
	}
	@Override
	public void create(String group, Position pos, Stats stats) {
		// TODO Auto-generated method stub
		
	}

}
