package com.soc.ai;

import java.util.ArrayList;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.State;

public class GreenKnightAI extends AI{
	
	public GreenKnightAI() {
		modules = new AIModule[2];
		modules[0] = new BasicFollowing(32, false, true, true);
		modules[1] = new SpellAttack(new int[]{Constants.Spells.SLASH, Constants.Spells.CHARGE}, new float[]{64, 400}, new float[]{0.5f, 2.5f});
	}

	@Override
	public void process(Entity e) {
		State state = SoC.game.statemapper.get(e);
		if(state.state == State.DYING) return;
		processModules(e); 
	}

	@Override
	public void death(Entity e) {
		// TODO Auto-generated method stub
		
	}
}