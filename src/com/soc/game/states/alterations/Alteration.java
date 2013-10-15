package com.soc.game.states.alterations;

import com.artemis.Entity;

public interface Alteration {
	public void process(Entity e);
	public void delete(Entity e);
}
