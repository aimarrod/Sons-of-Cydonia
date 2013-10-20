package com.soc.game.attacks.spells;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.utils.GraphicsLoader;

public class FireLionSpell extends Spell{
	
	public FireLionSpell(){
		this.icon = GraphicsLoader.load("firelion-icon.png");
		this.tooltip = "\nFIRE LION\n\n30 mana\n\nSummon a lion of fire to scorch and push your foes.\n Scales with intelligence.";
		this.cast = 0.1f;
		this.blocking = 0.1f + Constants.Spells.FIRELION_DELAY*2;
		this.state = State.ATTACK;
		this.mana = 30;
	}
	@Override
	public void create(Entity source, String group, Position pos, Stats stats) {
		Entity e = EntityFactory.createFirelion(group, pos, (int) (stats.intelligence*1.5));
	    SoC.game.groupmanager.add(e, group);
	    SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
	    SoC.game.levelmanager.setLevel(e, Constants.Groups.LEVEL+pos.z);
	   	e.addToWorld();
	}

}
