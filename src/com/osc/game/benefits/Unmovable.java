package com.osc.game.benefits;


import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.alterations.Push;
import com.soc.game.components.Debuff;
import com.soc.game.components.Position;
import com.soc.utils.FloatingText;

public class Unmovable implements Benefit{

	@Override
	public void process(Entity e) {
		Debuff d = SoC.game.debuffmapper.get(e);
		if(d != null && d.debuffClasses.contains(Push.class)){
			d.removeDebuff(Push.class);
			Position pos = SoC.game.positionmapper.get(e);
			SoC.game.renderSystem.texts.add(new FloatingText("Unmovable!", Constants.Configuration.LABEL_DURATION, pos.x+20, pos.y+20, Constants.Configuration.LABEL_SPEED));
		}
	}

}
