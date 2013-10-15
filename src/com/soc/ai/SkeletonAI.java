package com.soc.ai;

import java.util.ArrayList;

import com.artemis.Entity;
import com.artemis.managers.TagManager;
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
		modules = new AIModule[2];
		modules[0] = new BasicPathfinding(600, false, true, true);
		modules[1] = new BasicAttack(600, 2f);
	}

	@Override
	public void process(Entity e) {		
		processModules(e);
	}


	@Override
	public void death(Entity e) {
		Position pos = SoC.game.positionmapper.get(e);
		EntityFactory.createItem(Constants.Items.ANTIDOTE, pos.x, pos.y, pos.z).addToWorld();
	}
}
