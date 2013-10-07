package com.soc.game.spells;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.Stats;

public class ChargeSpell extends Spell{
	
	public ChargeSpell(){
		this.icon = new TextureRegion(new Texture(Gdx.files.internal("resources/dagger.png")), 64, 64);
		this.tooltip = "Charges Forward";
		this.cast = 0.2f;
	}

	@Override
	public void create(String group, Position pos, Stats stats) {
		Entity e = EntityFactory.createCharge(group, pos, stats.strength, 600, pos.direction);	
	    SoC.game.groupmanager.add(e, group);
	    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
	   	e.addToWorld();
		
	}
	
	
}
