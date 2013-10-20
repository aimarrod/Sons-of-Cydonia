package com.soc.ai.normals;

import com.artemis.Entity;
import com.soc.ai.AI;
import com.soc.ai.modules.AIModule;
import com.soc.ai.modules.BasicAttack;
import com.soc.ai.modules.BasicRunning;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
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
		Position pos = SoC.game.positionmapper.get(e);
		if(AI.rng.nextFloat() < 0.5){
			EntityFactory.createItem(Constants.Items.MANA_POTION, pos.x, pos.y, pos.z).addToWorld();
		}		
		if(AI.rng.nextFloat() < 0.2){
			EntityFactory.createItem(Constants.Items.MANA_ULTRAPOTION, pos.x, pos.y, pos.z).addToWorld();
		}			
	}
}
