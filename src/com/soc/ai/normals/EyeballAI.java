package com.soc.ai.normals;

import com.artemis.Entity;
import com.soc.ai.AI;
import com.soc.ai.modules.AIModule;
import com.soc.ai.modules.BasicAttack;
import com.soc.ai.modules.BasicFollowing;
import com.soc.ai.modules.BasicVision;

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
		// TODO Auto-generated method stub
		
	}

}
