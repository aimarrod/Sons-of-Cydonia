package com.soc.game.attacks;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.alterations.Push;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Debuff;
import com.soc.game.components.Position;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.utils.GraphicsLoader;

public class QuakeBladeProcessor implements AttackProcessor{
	
	public AnimatedRenderer renderer;
	public Bag<Entity> hit;
	public float counter;
	public float interval;
	public Circle hitbox;
	public float radius;
	private Rectangle enemy;
	
	@Mapper
	ComponentMapper<Damage> dm = SoC.game.world.getMapper(Damage.class);
	public QuakeBladeProcessor() {
		this.hit = new Bag<Entity>();
		this.interval = 0;
		this.hitbox = new Circle();
		this.counter = 0;
		this.radius = Constants.Spells.QUAKEBLADE_RADIUS_INITIAL;
		this.renderer = GraphicsLoader.loadQuake();
	}

	@Override 
	public void process(Entity attack) {
		Position pos = SoC.game.positionmapper.get(attack);
		interval -= SoC.game.world.delta;
		if(interval <= 0){
			hit.clear();
			counter++;
			pos.x += radius*pos.direction.x*1.5f;
			pos.y += radius*pos.direction.y*1.5f;
			interval = Constants.Spells.QUAKEBLADE_TICK_INTERVAL;
			radius += Constants.Spells.QUAKEBLADE_RADIUS_INCREASE;
			if(counter >= 5){
				attack.deleteFromWorld();
			}
		}
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position pos = SoC.game.positionmapper.get(victim);
		Bounds bon = SoC.game.boundsmapper.get(victim);
		hitbox = new Circle(attackpos.x, attackpos.y, radius);
		enemy = new Rectangle(pos.x, pos.y, bon.width, bon.height);
		
		return (!hit.contains(victim) && Intersector.overlaps(hitbox, enemy));
	}

	@Override
	public void handle(Entity attack, Entity victim) {
		hit.add(victim);
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
	public void frame(Entity attack, SpriteBatch batch) {
		Position pos = SoC.game.positionmapper.get(attack);
		renderer.time = Constants.Spells.QUAKEBLADE_TICK_INTERVAL*(counter);
		batch.draw(renderer.frame(0), pos.x-128, pos.y-64);
	}

	@Override
	public void delete() {
		
	}

}
