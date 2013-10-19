package com.soc.game.attacks.spells;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.utils.GraphicsLoader;

public class InfernoSpell extends Spell{
	
	public InfernoSpell(){
		this.icon = GraphicsLoader.load("arrow-icon.png");
		this.tooltip = "Inferno!";
		this.cast = 0.1f;
		this.blocking = 0.2f;
		this.state = State.IDLE;
		this.mana = 30;
	}
	@Override
	public void create(Entity source, String group, Position pos, Stats stats) {
		Entity e = EntityFactory.createInferno(group, pos, (int) (stats.intelligence));
	    SoC.game.groupmanager.add(e, group);
	    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
	    SoC.game.levelmanager.setLevel(e, Constants.Groups.LEVEL+pos.z);
	   	e.addToWorld();
	}

}
