package com.soc.game.states.benefits;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Damage;
import com.soc.game.components.Position;
import com.soc.utils.FloatingText;

public class Inmune implements Benefit{

	@Override
	public void process(Entity e) {
		if(SoC.game.damagemapper.has(e)){
			Position pos = SoC.game.positionmapper.get(e);
			Damage damage = SoC.game.damagemapper.get(e);
			damage.damage = 0;
			damage.pureDamage = 0;
			damage.r = 0.5f;
			damage.g= 0.5f;
			damage.b = 0.5f;
			SoC.game.renderSystem.texts.add(new FloatingText("Inmune", Constants.Configuration.LABEL_DURATION, pos.x+20, pos.y+20, Constants.Configuration.LABEL_SPEED));

		}
	}

	@Override
	public void delete(Entity e) {
		// TODO Auto-generated method stub
		
	}

}
