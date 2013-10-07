package com.soc.game.attacks;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Position;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;

public interface AttackProcessor {
	public void process(Entity attack);
	public boolean collision(Entity attack, Entity victim);
	public void frame(Entity attack, SpriteBatch sprite);
	public void handle(Entity attack, Entity enemy);
	public void delete();
}
