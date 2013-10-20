package com.soc.game.attacks.spells;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;

public class VenomSwordSpell extends Spell {
	public VenomSwordSpell() {
		// this.icon = GraphicsLoader.load("punch-icon.png");
		this.tooltip = "Venom!";
		this.cast = 0.2f;
		this.sound = "";
		this.blocking = 1f;
		this.state = State.ATTACK;
		// this.sounddelay = 0.2f;
		this.mana = 0;
	}

	@Override
	public void create(Entity source, String group, Position pos, Stats stats) {
		Entity e = EntityFactory.createVenomSword(group, pos, stats.strength, 16);
		SoC.game.groupmanager.add(e, group);
		SoC.game.groupmanager.add(e, Constants.Groups.MAP_BOUND);
		e.addToWorld();
	}
}
