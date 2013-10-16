package com.soc.ai;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.attacks.spells.Spell;
import com.soc.game.components.Delay;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.systems.AttackDelaySystem;

public class BasicAttack implements AIModule{

	public float range, interval, timer;
	
	public BasicAttack(float range, float interval) {
		this.range = range;
		this.interval = interval;
		this.timer = 0;
	}

	@Override
	public void process(Entity e) {
		State state = SoC.game.statemapper.get(e);
		if(state.state >= State.BLOCKED) return;
		
		timer -= SoC.game.world.delta;
		if(timer > 0) return;
				
		timer = interval;
		Position pos = SoC.game.positionmapper.get(e);
		Position playerPos = SoC.game.positionmapper.get(SoC.game.player);
				
		if(Math.abs(playerPos.x - pos.x) < range && Math.abs(playerPos.y - pos.y) < range){
			Stats stats = SoC.game.statsmapper.get(e);
			Spell spell = SoC.game.spells[stats.attack];
			e.addComponent(new Delay(Constants.Groups.ENEMY_ATTACKS, spell.cast, spell.blocking, stats.attack));
			e.changedInWorld();
			state.state = spell.state;
		}
	}

}
