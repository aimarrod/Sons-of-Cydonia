package com.soc.ai;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Delay;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.spells.Spell;
import com.soc.utils.EffectsPlayer;

public class BallistaAI implements AI{
	
	float time;
	
	public BallistaAI(){
		time = Constants.Spells.BALLISTA_FIRE_RATE;
	}

	@Override
	public void process(Entity e) {
		Position pos =	SoC.game.positionmapper.get(e);
		Position playerpos = SoC.game.positionmapper.get(SoC.game.player);
		State state=SoC.game.statemapper.get(e);
		
		pos.direction.x = Math.signum(playerpos.x-pos.x);
		pos.direction.y = Math.signum(playerpos.y-pos.y);
				
		if(time <= 0){
			time = Constants.Spells.BALLISTA_FIRE_RATE;
			Spell spell = SoC.game.spells[Constants.Spells.PUNCH];
			state.state = spell.state;
			EffectsPlayer.play("skeleton-attack.ogg");
			e.addComponent(new Delay(Constants.Groups.ENEMY_ATTACKS,spell.cast, spell.blocking, Constants.Spells.PUNCH));
			e.changedInWorld();
		} else {
			time -= SoC.game.world.delta;
		}
	}

}
