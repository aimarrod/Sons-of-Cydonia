package com.soc.ai;

import com.artemis.Entity;
import com.soc.core.SoC;
import com.soc.game.components.State;

public class GoldKnightAI extends AI{
	
	public GoldKnightAI() {
		modules = new AIModule[2];
		modules[0] = new BasicPathfinding(32, true, false, false);
		modules[1] = new BasicAttack(200, 2f);
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
