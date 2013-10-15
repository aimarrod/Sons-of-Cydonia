package com.soc.ai;

import java.util.ArrayList;

import com.artemis.Entity;
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
		modules = new AIModule[2];
		modules[0] = new BasicPathfinding(32, false, true, false);
		modules[1] = new BasicAttack(32, 1f);
	}
	public void process(Entity e) {
		

		State state = SoC.game.statemapper.get(e);
		
		if(state.state == State.DYING) return;
		
		processModules(e);
		

						
	}


	@Override
	public void death(Entity e) {
		Position p = SoC.game.positionmapper.get(e);
		Entity spawned = EntityFactory.createPoisonCloud(p, SoC.game.boundsmapper.get(e));
		SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMY_ATTACKS);
		SoC.game.groupmanager.add(spawned, Constants.Groups.MAP_BOUND);
		SoC.game.levelmanager.setLevel(spawned, Constants.Groups.LEVEL +(p.z+1));
	}
}
