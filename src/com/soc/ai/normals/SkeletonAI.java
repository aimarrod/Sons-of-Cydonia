package com.soc.ai.normals;

import java.util.ArrayList;

import com.artemis.Entity;
import com.artemis.managers.TagManager;
import com.soc.ai.AI;
import com.soc.ai.Node;
import com.soc.ai.modules.AIModule;
import com.soc.ai.modules.BasicAttack;
import com.soc.ai.modules.BasicFollowing;
import com.soc.ai.modules.BasicVision;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.attacks.spells.Spell;
import com.soc.game.components.Delay;
import com.soc.game.components.Enemy;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.utils.EffectsPlayer;

public class SkeletonAI extends AI {
	
	ArrayList<Node> path;
	
	public SkeletonAI(){
		path = new ArrayList<Node>();
		modules = new AIModule[3];
		modules[0] = new BasicVision(1000, 0, false);
		modules[1] = new BasicFollowing(600, false, true, true);
		modules[2] = new BasicAttack(600, 2f);
	}

	@Override
	public void process(Entity e) {		
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
		if(AI.rng.nextFloat() < 0.05){
			EntityFactory.createItem(Constants.Items.STONE_AXE, pos.x, pos.y, pos.z);
		}
		if(AI.rng.nextFloat() < 0.05){
			EntityFactory.createItem(Constants.Items.WOODEN_SHIELD, pos.x, pos.y, pos.z);
		}
	}
}
