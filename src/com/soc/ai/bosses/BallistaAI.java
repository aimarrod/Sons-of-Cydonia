package com.soc.ai.bosses;

import com.artemis.Entity;
import com.soc.ai.AI;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.attacks.spells.Spell;
import com.soc.game.components.Delay;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.utils.EffectsPlayer;

public class BallistaAI extends AI{
	
	float time;
	
	public BallistaAI(){
		time = Constants.Spells.BALLISTA_FIRE_RATE;
	}

	@Override
	public void process(Entity e) {
		Position pos =	SoC.game.positionmapper.get(e);
		Position playerpos = SoC.game.positionmapper.get(SoC.game.player);
		State state=SoC.game.statemapper.get(e);
		
		if(state.state == State.DYING) return;
		
		pos.direction.x = playerpos.x-pos.x;
		pos.direction.y = playerpos.y-pos.y;
		
		if(Math.abs(pos.direction.x) > Math.abs(pos.direction.y)){
			pos.direction.y = 0;
			pos.direction.x = Math.signum(pos.direction.x);
		} else {
			pos.direction.x = 0;
			pos.direction.y = Math.signum(pos.direction.y);
		}
				
		if(time <= 0){
			time = Constants.Spells.BALLISTA_FIRE_RATE;
			Spell spell = SoC.game.spells[Constants.Spells.ARROW];
			state.state = spell.state;
			e.addComponent(new Delay(Constants.Groups.ENEMY_ATTACKS,spell.cast, spell.blocking, Constants.Spells.ARROW));
			e.changedInWorld();
		} else {
			time -= SoC.game.world.delta;
		}
	}

	@Override
	public void death(Entity e) {
		Position pos = SoC.game.positionmapper.get(e);
		
		EntityFactory.createItem(Constants.Items.STONE_AXE, pos.x, pos.y, pos.z);
		EntityFactory.createItem(Constants.Items.WOODEN_SHIELD, pos.x, pos.y, pos.z).addToWorld();		
	}

}
