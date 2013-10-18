package com.soc.ai.normals;

import java.util.ArrayList;

import com.artemis.Entity;
import com.soc.ai.AI;
import com.soc.ai.modules.AIModule;
import com.soc.ai.modules.BasicFollowing;
import com.soc.ai.modules.SpellAttack;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
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
		Position pos = SoC.game.positionmapper.get(e);
		if(AI.rng.nextFloat() < 0.2){
			EntityFactory.createItem(Constants.Items.HEALTH_POTION, pos.x, pos.y, pos.z).addToWorld();
		}		
		if(AI.rng.nextFloat() < 0.02){
			EntityFactory.createItem(Constants.Items.HEALTH_ULTRAPOTION, pos.x, pos.y, pos.z).addToWorld();
		}	
	}
}