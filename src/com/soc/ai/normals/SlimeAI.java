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
		
		float timer = 0.2f;
		
		public SlimeAI(){
			modules = new AIModule[2];
			modules[0] = new BasicVision(1000, 0, false);
			modules[1] = new BasicFollowing(0, true, false, false);
		}

		@Override
		public void process(Entity e) {
			if(SoC.game.statemapper.get(e).state == State.DYING){
				timer -= SoC.game.world.delta;
				if(timer <= 0){
					timer = 0.2f;
					Position pos = SoC.game.positionmapper.get(e);
					Entity spawned = EntityFactory.createMaggot(pos.x, pos.y, pos.z);	
					SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMY_ATTACKS);
					SoC.game.groupmanager.add(spawned, Constants.Groups.MAP_BOUND);
					SoC.game.levelmanager.setLevel(spawned, Constants.Groups.LEVEL +pos.z);
					SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMIES);
					spawned.addToWorld();
				}
			} else {
				processModules(e);
			}
		}

		@Override
		public void death(Entity e) {			
			Position pos = SoC.game.positionmapper.get(e);
			if(AI.rng.nextFloat() < 0.1f){
				EntityFactory.createItem(Constants.Items.MIX_POTION, pos.x, pos.y, pos.z).addToWorld();
			}
			if(AI.rng.nextFloat() < 0.1f){
				EntityFactory.createItem(Constants.Items.ANTIDOTE, pos.x, pos.y, pos.z).addToWorld();
			}
		}
}
