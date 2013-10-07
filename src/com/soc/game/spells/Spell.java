package com.soc.game.spells;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.game.components.Position;
import com.soc.game.components.Stats;

public abstract class Spell {
	
	public TextureRegion icon;
	public String tooltip;
	public float cast;
	public float blocking;
	public float sounddelay;
	public int state;
	public String sound;
	
	public abstract void create(Entity source, String group, Position pos, Stats stats);
}
