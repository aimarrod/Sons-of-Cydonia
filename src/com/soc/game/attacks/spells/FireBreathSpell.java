package com.soc.game.attacks.spells;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;

public class FireBreathSpell extends Spell {
	public FireBreathSpell(){
		//this.icon = GraphicsLoader.load("punch-icon.png");
		this.tooltip = "FireBreathSpell";
		this.cast = 3f;
		this.sound = "";
		this.blocking = 3f;
		this.state = State.ATTACK;
		//this.sounddelay = 0.2f;
		this.mana = 0;
	}
	@Override
	public void create(Entity source, String group, Position pos, Stats stats) {
		Entity e = EntityFactory.createFireBreath( pos.x,pos.y,pos.z, stats.intelligence*4, SoC.game.positionmapper.get(source).direction);	
	    SoC.game.groupmanager.add(e, group);
	    SoC.game.groupmanager.add(e, Constants.Groups.PROJECTILES);
	    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
	    SoC.game.levelmanager.setLevel(e, Constants.Groups.LEVEL+pos.z);
	   	e.addToWorld();
	}
}
