package com.soc.game.spells;

import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.Stats;

public class DaggerThrowSpell extends Spell{
	
	public DaggerThrowSpell(){
		this.icon = new TextureRegion(new Texture(Gdx.files.internal("resources/dagger.png")), 64, 64);
		this.tooltip = "Hurls a dagger to the front, which returns to the character after a certain distance is traveled";
		this.cast = 0.2f;
	}

	@Override
	public void create(String group, Position pos, Stats stats) {
		Entity e = EntityFactory.createDaggerThrow(group, pos, stats.strength, 600, pos.direction);
	    SoC.game.world.getManager(GroupManager.class).add(e, group);
	   	e.addToWorld();
	}
}
