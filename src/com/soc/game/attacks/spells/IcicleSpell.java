package com.soc.game.attacks.spells;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.utils.GraphicsLoader;

public class IcicleSpell extends Spell {
	public IcicleSpell(){
		this.icon = GraphicsLoader.load("arrow-icon.png");
		this.tooltip = "Throws a fireball!";
		this.cast = 0.3f;
		this.blocking = 0.4f;
		this.state = State.ATTACK;
		this.mana = 0;
	}

	@Override
	public void create(Entity source, String group, Position pos, Stats stats) {
		Entity e = EntityFactory.createIcicle(group, pos, (int) (stats.intelligence));
	    SoC.game.groupmanager.add(e, group);
	    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
	    SoC.game.groupmanager.add(e, Constants.Groups.PROJECTILES);
	    SoC.game.levelmanager.setLevel(e, Constants.Groups.LEVEL+pos.z);
	   	e.addToWorld();

	}
}
