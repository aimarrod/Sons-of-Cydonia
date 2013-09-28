package com.soc.game.attacks;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Position;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;

public interface AttackProcessor {
	public void process(Entity e, Position p, Bounds b, Velocity v, float delta);
	public boolean collision(Entity other, Position myp, Bounds myb, Position otherp, Bounds otherb);
	public TextureRegion frame(float delta);
	public void handle(Entity e, Attack a, Stats s);
}
