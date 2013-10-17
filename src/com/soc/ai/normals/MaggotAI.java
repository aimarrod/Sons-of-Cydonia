package com.soc.ai.normals;


import com.artemis.Entity;
import com.soc.ai.AI;
import com.soc.ai.modules.AIModule;
import com.soc.ai.modules.BasicFollowing;
import com.soc.ai.modules.BasicVision;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Velocity;

public class MaggotAI extends AI{

	public MaggotAI(){
		modules = new AIModule[2];
		modules[0] = new BasicVision(1000, 0, false);
		modules[1] = new BasicFollowing(0, true, false, false);
	}

	@Override
	public void process(Entity e) {
		processModules(e);
	}

	@Override
	public void death(Entity e) {
		// TODO Auto-generated method stub
		
	}

}
