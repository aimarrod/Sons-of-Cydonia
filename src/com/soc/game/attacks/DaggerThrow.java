package com.soc.game.attacks;

import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Position;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.game.graphics.AttackRenderer;
import com.soc.utils.GraphicsLoader;

public class DaggerThrow implements AttackProcessor{
	public Bag<Entity> hit;
	public AttackRenderer renderer;
	public float range;
	public float traveled;
	public boolean backing;

	public DaggerThrow(float range) {
		this.hit = new Bag<Entity>();
		this.renderer = GraphicsLoader.loadDaggerThrow();
		this.range = range;
		this.traveled = 0;;
		this.backing = false;
	}

	@Override 
	public void process(Entity e, Position p, Bounds b, Velocity v, float delta) {
		traveled += Math.abs(v.speed*delta);
		if(traveled > range && !backing){
			backing = true;
			v.vx = -v.vx;
			v.vy = -v.vy;
			hit.clear();
			traveled = 0;
		} else if(traveled > range){
			e.deleteFromWorld();
		}
	}

	@Override
	public boolean collision(Entity e, Position mypos, Bounds mybounds, Position otherpos,
			Bounds otherbounds) {
		return (!hit.contains(e) && mypos.x < otherpos.x + otherbounds.width && mypos.x + mybounds.height > otherpos.x && mypos.y < otherpos.y + otherbounds.height && mypos.y + mybounds.height > otherpos.y);
	}

	@Override
	public TextureRegion frame(float delta) {
		return renderer.frame(delta);
	}

	@Override
	public void handle(Entity e, Attack a, Stats s) {
		hit.add(e);
		s.health -= a.damage;
	}
}
