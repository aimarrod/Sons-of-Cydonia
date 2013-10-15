package com.soc.game.attacks.spells;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.utils.GraphicsLoader;

public class ArrowSpell extends Spell{

	
	public ArrowSpell(){
		this.icon = GraphicsLoader.load("arrow-icon.png");
		this.tooltip = "Shoot an arrow";
		this.cast = 0.3f;
		this.blocking = 0.4f;
		this.state = State.ATTACK;
		this.mana = 0;
	}

	@Override
	public void create(Entity source, String group, Position pos, Stats stats) {
		Entity e = EntityFactory.createArrow(group, pos, (int) (stats.agility*0.5f), pos.direction);
	    SoC.game.groupmanager.add(e, group);
	    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
	    SoC.game.groupmanager.add(e, Constants.Groups.PROJECTILES);
	    SoC.game.groupmanager.add(e, Constants.Groups.DESTROYABLE_PROJECTILES);
	    SoC.game.levelmanager.setLevel(e, Constants.Groups.LEVEL+pos.z);
	   	e.addToWorld();
	}
}
