package com.soc.ai.normals;

import java.util.ArrayList;

import com.artemis.Entity;
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
import com.soc.game.components.Velocity;
import com.soc.utils.EffectsPlayer;

public class ZombiAI extends AI{
	
	ArrayList<Node> path;
	
	public ZombiAI(){
		path = new ArrayList<Node>();
		modules = new AIModule[3];
		modules[0] = new BasicVision(1000, 3000, true);
		modules[1] = new BasicFollowing(32, false, true, false);
		modules[2] = new BasicAttack(32, 1f);
	}
	public void process(Entity e) {
		processModules(e);				
	}


	@Override
	public void death(Entity e) {
		Position pos = SoC.game.positionmapper.get(e);
		Entity spawned = EntityFactory.createPoisonCloud(pos, SoC.game.boundsmapper.get(e));
		SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMY_ATTACKS);
		SoC.game.groupmanager.add(spawned, Constants.Groups.MAP_BOUND);
		spawned.addToWorld();
		
		SoC.game.levelmanager.setLevel(spawned, Constants.Groups.LEVEL +(pos.z+1));
		if(AI.rng.nextFloat() < 0.1f){
			EntityFactory.createItem(Constants.Items.ANTIDOTE, pos.x, pos.y, pos.z).addToWorld();
		}
		if(AI.rng.nextFloat() < 0.1f){
			EntityFactory.createItem(Constants.Items.MIX_POTION, pos.x, pos.y, pos.z).addToWorld();
		}
	}
}
