package com.soc.ai;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.State;

public class KnightCaptainAI extends AI{
	
	public KnightCaptainAI(){
		modules = new AIModule[2];
		modules[0] = new BasicPathfinding(32, false, true, true);
		modules[1] = new SpellAttack(new int[]{Constants.Spells.SLASH, Constants.Spells.WHIRLBLADE, Constants.Spells.QUAKEBLADE}, new float[]{64, 200, 400}, new float[]{0.5f, 2.0f, 3.0f});
	}

	@Override
	public void process(Entity e) {
		State state = SoC.game.statemapper.get(e);
		if(state.state == State.DYING) return;
		processModules(e); 
	}

	@Override
	public void death(Entity e) {
		SoC.game.progress.knightCaptainDefeated = true;
	}
}
