package com.soc.game.attacks;

import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.soc.game.components.Bounds;
import com.soc.game.components.DamageReceived;
import com.soc.game.components.Position;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.game.graphics.AttackRenderer;
import com.soc.game.components.Attack;
import com.soc.utils.GraphicsLoader;

public class Icicle implements AttackProcessor {

	public Bag<Entity> hit;
	public AttackRenderer renderer;
	public float range;

	public Icicle(Vector2 dir, float range) {
		hit = new Bag<Entity>();
		this.renderer = GraphicsLoader.loadIcicle(dir);
		this.range = range;
	}

	@Override 
	public void process(Entity e, Position p, Bounds b, Velocity v, float delta) {
		range -= Math.abs(v.speed*delta);
		if(range < 0){
			e.deleteFromWorld();
		}
	}

	@Override
	public boolean collision(Entity e, Position mypos, Bounds mybounds, Position otherpos,
			Bounds otherbounds) {
		return (!hit.contains(e) && mypos.x < otherpos.x + otherbounds.width && mypos.x + mybounds.height > otherpos.x && mypos.y < otherpos.y + otherbounds.height && mypos.y + mybounds.height > otherpos.y);
	}
	@Override
	public void frame(float delta, SpriteBatch sprite, float x, float y) {
		sprite.draw(renderer.frame(delta),x,y);
	}

	@Override
	public void handle(Entity e, Attack a, Stats s) {
		hit.add(e);
		DamageReceived damageReceived=e.getComponent(DamageReceived.class);
		if(damageReceived==null){
			e.addComponent(new DamageReceived(a.damage));
		}else{
			damageReceived.damage+=a.damage;
		}
	}

}
