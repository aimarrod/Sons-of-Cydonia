package com.soc.ai.normals;

import com.artemis.Entity;
import com.soc.ai.AI;
import com.soc.ai.modules.AIModule;
import com.soc.ai.modules.BasicAttack;
import com.soc.ai.modules.BasicFollowing;
import com.soc.ai.modules.BasicVision;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;

public class MeleeSkeletonAI extends AI{
	
	public MeleeSkeletonAI() {
		modules = new AIModule[3];
		modules[0] = new BasicVision(1000, 0, false);
		modules[1] = new BasicFollowing(32, false, true, false);
		modules[2] = new BasicAttack(50, 1f);
	}

	@Override
	public void process(Entity e) {
		State state = SoC.game.statemapper.get(e);
		if(state.state == State.DYING) return;
		processModules(e); 
	}

	@Override
	public void death(Entity e) {
		Position pos = SoC.game.positionmapper.get(e);
		if(AI.rng.nextFloat() < 0.1){
			EntityFactory.createItem(Constants.Items.HEALTH_POTION, pos.x, pos.y, pos.z).addToWorld();
		}
		if(AI.rng.nextFloat() < 0.1){
			EntityFactory.createItem(Constants.Items.MANA_POTION, pos.x, pos.y, pos.z).addToWorld();
		}	
	}
	
}
