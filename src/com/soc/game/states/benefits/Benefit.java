package com.soc.game.states.benefits;

import com.artemis.Entity;

	public interface Benefit {
		public void process(Entity e);
		public void delete(Entity e);
	}

