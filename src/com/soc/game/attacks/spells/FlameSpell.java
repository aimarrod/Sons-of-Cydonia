package com.soc.game.attacks.spells;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;

public class FlameSpell extends Spell{
	public FlameSpell(){
		//this.icon = GraphicsLoader.load("punch-icon.png");
		this.tooltip = "FlameSpell";
		this.cast = 0f;
		this.sound = "";
		this.blocking = 1f;
		this.state = State.ATTACK;
		//this.sounddelay = 0.2f;
		this.mana = 0;
	}
	@Override
	public void create(Entity source, String group, Position pos, Stats stats) {
		Entity e = EntityFactory.createFlame( pos.x,pos.y,pos.z, stats.intelligence);	
	    SoC.game.groupmanager.add(e, group);
	    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
	   	e.addToWorld();
	}
}
