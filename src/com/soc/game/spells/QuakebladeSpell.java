package com.soc.game.spells;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.utils.GraphicsLoader;

public class QuakebladeSpell extends Spell{
	
	public QuakebladeSpell(){
		this.icon = GraphicsLoader.load("quakeblade-icon.png");
		this.tooltip = "Send a quake forth with a swing of your sword";
		this.cast = 0.3f;
		this.blocking = 0.4f;
		this.state = State.ATTACK;
		this.sounddelay = 0.1f;
		this.mana = 70;
	}
	@Override
	public void create(Entity source, String group, Position pos, Stats stats) {
		Entity e = EntityFactory.createQuake(pos, SoC.game.feetmapper.get(source),stats.strength*2);	
	    SoC.game.groupmanager.add(e, group);
	    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
	    SoC.game.levelmanager.setLevel(e, Constants.Groups.LEVEL+pos.z);
	   	e.addToWorld();
	}
}
