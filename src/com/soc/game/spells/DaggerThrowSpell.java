package com.soc.game.spells;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.game.components.Position;
import com.soc.game.components.Stats;
import com.soc.utils.EntityFactory;

public class DaggerThrowSpell extends Spell{
	
	public DaggerThrowSpell(){
		this.icon = new TextureRegion(new Texture(Gdx.files.internal("resources/dagger.png")), 64, 64);
		this.tooltip = "Hurls a dagger to the front, which returns to the character after a certain distance is traveled";
	}

	@Override
	public void create(Position pos, Stats stats) {
		int damage = stats.strength;
	}
}
