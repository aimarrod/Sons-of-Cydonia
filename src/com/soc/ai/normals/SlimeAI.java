package com.soc.ai.normals;

import java.util.Random;

import com.artemis.Entity;
import com.soc.ai.AI;
import com.soc.ai.modules.AIModule;
import com.soc.ai.modules.BasicFollowing;
import com.soc.ai.modules.BasicVision;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Velocity;

	public class SlimeAI extends AI{
		
		public SlimeAI(){
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
			Position p = SoC.game.positionmapper.get(e);

			Random r = new Random();
			int maggots = r.nextInt(5) + 3;
			for(int i = 1; i < maggots+1; i++){
				Entity spawned = EntityFactory.createMaggot(r.nextInt(50)+p.x-25, r.nextInt(50)+p.y-25, p.z);	
				SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMY_ATTACKS);
				SoC.game.groupmanager.add(spawned, Constants.Groups.MAP_BOUND);
				SoC.game.levelmanager.setLevel(spawned, Constants.Groups.LEVEL +p.z);
				SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMIES);
				spawned.addToWorld();
			}
			
			Position pos = SoC.game.positionmapper.get(e);
			if(AI.rng.nextFloat() < 0.1){
				EntityFactory.createItem(Constants.Items.MIX_POTION, pos.x, pos.y, pos.z).addToWorld();
			}
		}
}
