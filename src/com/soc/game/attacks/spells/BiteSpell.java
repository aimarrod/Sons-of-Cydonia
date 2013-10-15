package com.soc.game.attacks.spells;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.utils.GraphicsLoader;

public class BiteSpell extends Spell{
	public BiteSpell(){
		//this.icon = GraphicsLoader.load("punch-icon.png");
		this.tooltip = "Bite inf the body!!";
		this.cast = 1f;
		this.sound = "";
		this.blocking = 1f;
		this.state = State.ATTACK;
		//this.sounddelay = 0.2f;
		this.mana = 0;
	}
	@Override
	public void create(Entity source, String group, Position pos, Stats stats) {
		Entity e = EntityFactory.createBite(group, pos, stats.strength, 16);	
	    SoC.game.groupmanager.add(e, group);
	    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
	   	e.addToWorld();
	}
}
