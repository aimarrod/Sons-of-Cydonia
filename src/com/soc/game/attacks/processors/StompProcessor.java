package com.soc.game.attacks.processors;

import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Debuff;
import com.soc.game.components.Enemy;
import com.soc.game.components.Position;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.game.states.alterations.Push;
import com.soc.utils.GraphicsLoader;

public class StompProcessor implements AttackProcessor {
	AnimatedRenderer renderer;
	Entity hit;
	public Circle hitbox;
	public float radius;
	private Rectangle enemy;
	private float duration;
	
	public StompProcessor(){
		this.hit = null;
		this.hitbox = new Circle();
		this.enemy = new Rectangle();
		this.radius = Constants.Spells.STOMP_RADIUS;
		this.renderer = GraphicsLoader.loadStomp();
		this.duration=2f;
	}
	@Override
	public void process(Entity attack) {
		duration-=SoC.game.world.delta;
		if(duration<=0)
		attack.deleteFromWorld();
		
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position pos = SoC.game.positionmapper.get(victim);
		Bounds bon = SoC.game.boundsmapper.get(victim);
		
		enemy.set(pos.x, pos.y, bon.width, bon.height);
		hitbox.set(attackpos.x, attackpos.y, radius);
		
		return (hit==null && Intersector.overlaps(hitbox, enemy));
	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {
		Position pos = SoC.game.positionmapper.get(attack);
		sprite.draw(renderer.frame(SoC.game.world.delta), pos.x-128, pos.y-64);
		
	}

	@Override
	public void handle(Entity attack, Entity victim) {
		hit=victim;
		Attack a = SoC.game.attackmapper.get(attack);
		if(SoC.game.damagemapper.has(victim)){
			SoC.game.damagemapper.get(victim).damage+=a.damage;
		}else{
			victim.addComponent(new Damage(a.damage, false));
			victim.changedInWorld();
		}
		Position pos1 = SoC.game.positionmapper.get(attack);
		Position pos2 = SoC.game.positionmapper.get(victim);
		Push p = new Push(new Vector2(), 50, 400);
		p.direction.x = Math.signum(pos2.x - pos1.x);
		p.direction.y = Math.signum(pos2.y - pos1.y);

		Debuff.addDebuff(victim, p);
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

}
