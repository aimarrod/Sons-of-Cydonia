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

public class EyeballAI extends AI{
	public EyeballAI(){
		modules = new AIModule[3];
		modules[0] = new BasicVision(1000, 0, false);
		modules[1] = new BasicFollowing(600, false, false, true);
		modules[2] = new BasicAttack(600, 2f);
	}
	@Override
	public void process(Entity e) {
		processModules(e);
	}

	@Override
	public void death(Entity e) {
		Position pos = SoC.game.positionmapper.get(e);
		if(AI.rng.nextFloat() < 0.15f){
			EntityFactory.createItem(Constants.Items.MIX_POTION, pos.x, pos.y, pos.z).addToWorld();
		}		
	}

}
