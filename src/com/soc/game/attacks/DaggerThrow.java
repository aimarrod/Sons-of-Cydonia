package com.soc.game.attacks;

import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Position;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.game.graphics.AttackRenderer;
import com.soc.utils.Globals;
import com.soc.utils.GraphicsLoader;

public class DaggerThrow implements AttackProcessor{
	public Bag<Entity> hit;
	public AttackRenderer renderer;
	public Position source;
	public float range;
	public boolean reached;
	public boolean backing;
	
	public DaggerThrow(float range, Position source) {
		this.hit = new Bag<Entity>();
		this.renderer = GraphicsLoader.loadDaggerThrow();
		this.range = range;
		this.backing = false;
		this.source = source;
	}

	@Override 
	public void process(Entity e, Position p, Bounds b, Velocity v, float delta) {
		range -= Math.abs(v.speed*delta);
		if(0 > range && !backing){
			backing = true;
			hit.clear();
			return;
		}
		if(backing){
			reached = true;
			range = source.x -p.x;
			if(Math.abs(range) > 16){
				v.vx=v.speed*Math.signum(range);
				reached = false;
			} else {
				v.vx = 0;
			}
			range = source.y - p.y;
			if(Math.abs(range) > 16){
				v.vy=v.speed*Math.signum(range);
				reached = false;
			} else {
				v.vy = 0;
			}
		}
		
		if(reached)
			e.deleteFromWorld(); 
	}

	@Override
	public boolean collision(Entity e, Position mypos, Bounds mybounds, Position otherpos,
			Bounds otherbounds) {
		return (!hit.contains(e) && mypos.x < otherpos.x + otherbounds.width && mypos.x + mybounds.height > otherpos.x && mypos.y < otherpos.y + otherbounds.height && mypos.y + mybounds.height > otherpos.y);
	}

	@Override
	public void handle(Entity e, Attack a, Stats s) {
		hit.add(e);
		s.health -= a.damage;
	}

	@Override
	public void frame(float delta, SpriteBatch sprite, float x, float y) {
		sprite.draw(renderer.frame(delta),x,y);
		
	}
}
