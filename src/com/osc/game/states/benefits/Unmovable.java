package com.osc.game.states.benefits;


import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Debuff;
import com.soc.game.components.Position;
import com.soc.game.states.alterations.Push;
import com.soc.utils.FloatingText;

public class Unmovable implements Benefit{

	@Override
	public void process(Entity e) {
		if(!SoC.game.debuffmapper.has(e)) return;
		Debuff d = SoC.game.debuffmapper.get(e);
		if(d != null && d.debuffClasses.contains(Push.class)){
			d.removeDebuff(Push.class,e);
			Position pos = SoC.game.positionmapper.get(e);
			SoC.game.renderSystem.texts.add(new FloatingText("Unmovable!", Constants.Configuration.LABEL_DURATION, pos.x+20, pos.y+20, Constants.Configuration.LABEL_SPEED));
		}
	}

	@Override
	public void delete(Entity e) {
		// TODO Auto-generated method stub
		
	}

}
