package com.soc.ai;

import com.artemis.Entity;
import com.soc.core.SoC;
import com.soc.game.components.State;

public class GoldBowKnightAI extends AI{
	
	public GoldBowKnightAI() {
		modules = new AIModule[2];
		modules[0] = new BasicRunning(200, true, true);
		modules[1] = new BasicAttack(500, 2f);
	}

	@Override
	public void process(Entity e) {
		State state = SoC.game.statemapper.get(e);
		if(state.state == State.DYING || state.state == State.FALLING) return;
		processModules(e); 
	}

	@Override
	public void death(Entity e) {
		// TODO Auto-generated method stub
		
	}
	
}
