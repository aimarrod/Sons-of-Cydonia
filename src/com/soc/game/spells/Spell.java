package com.soc.game.spells;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.game.components.Position;
import com.soc.game.components.Stats;

public abstract class Spell {
	
	public TextureRegion icon;
	public String tooltip;
	
	public abstract void create(Entity source, Position pos, Stats stats);
}
