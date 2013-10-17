package com.soc.ai.modules;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.attacks.spells.Spell;
import com.soc.game.components.Delay;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;

public class SpellAttack implements AIModule{

	int [] spells;
	float [] ranges;
	float [] intervals;
	float timer;
	
	public SpellAttack(int [] spells, float [] ranges, float[] intervals){
		this.spells = spells;
		this.ranges = ranges;
		this.timer = 0;
		this.intervals = intervals;
	}
	
	@Override
	public boolean process(Entity e) {
		State state = SoC.game.statemapper.get(e);
		if(state.state >= State.BLOCKED) return false;
		
		timer -= SoC.game.world.delta;
		if(timer > 0) return false;
				
		Position pos = SoC.game.positionmapper.get(e);
		Position playerPos = SoC.game.positionmapper.get(SoC.game.player);
				
		for(int i =0; i < spells.length; i++){
			if(Math.abs(playerPos.x - pos.x) < ranges[i] && Math.abs(playerPos.y - pos.y) < ranges[i]){
				Stats stats = SoC.game.statsmapper.get(e);
				Spell spell = SoC.game.spells[spells[i]];
				e.addComponent(new Delay(Constants.Groups.ENEMY_ATTACKS, spell.cast, spell.blocking, spells[i]));
				state.state = spell.state;
				e.changedInWorld();
				timer = intervals[i];
				return false;
			}
		}
		return false;
	}

}
