package com.soc.ai;

import java.util.Random;

import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.utils.ImmutableBag;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.attacks.spells.Spell;
import com.soc.game.components.Delay;
import com.soc.game.components.Enemy;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.utils.EffectsPlayer;

public abstract class AI {
	
	public AIModule[] modules;
	public static Random rng = new Random();
	
	public abstract void process(Entity e);
	public abstract void death(Entity e);
	protected void processModules(Entity e){
		for(int i = 0; i < modules.length; i++){
			modules[i].process(e);
		}
	}
}
