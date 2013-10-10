package com.soc.ai;

import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.artemis.utils.ImmutableBag;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Delay;
import com.soc.game.components.Enemy;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.game.spells.Spell;
import com.soc.utils.EffectsPlayer;

public interface AI {
	public void process(Entity e);
	public void death(Entity e);
}
