package com.soc.game.attacks;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.Constants;
import com.soc.core.Constants.World;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Position;
import com.soc.game.components.Push;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.utils.EffectsPlayer;

public class WhirlbladeProcessor implements AttackProcessor {
	public Bag<Entity> hit;
	public float timer;
	public float interval;
	public Position pos;
	public Bounds bon;
	public Circle hitbox;
	private Rectangle enemy;
	
	@Mapper
	ComponentMapper<Damage> dm = SoC.game.world.getMapper(Damage.class);
	public WhirlbladeProcessor(Position pos, Bounds bon) {
		this.hit = new Bag<Entity>();
		this.pos = pos;
		this.bon = bon;
		this.timer = Constants.Spells.SPIN_DURATION;
		this.interval = Constants.Spells.SPIN_DURATION*0.2f;
		this.hitbox = new Circle();
	}

	@Override 
	public void process(Entity attack) {
		timer -= SoC.game.world.delta;
		if(timer <= 0){
			attack.deleteFromWorld();
			delete();
		} else {
			Position attackpos = SoC.game.positionmapper.get(attack);
			attackpos.x = pos.x + bon.width*0.5f;
			attackpos.y = pos.y + bon.height*0.5f;
			attackpos.z = pos.z; 
			
			interval -= SoC.game.world.delta;
			if(interval <= 0){
				hit.clear();
				interval = Constants.Spells.SPIN_DURATION*0.2f;
			}
		}
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position pos = SoC.game.positionmapper.get(victim);
		Bounds bon = SoC.game.boundsmapper.get(victim);
		hitbox = new Circle(attackpos.x, attackpos.y, Constants.Spells.SPIN_RADIUS);
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
			victim.addComponent(new Damage(a.damage));
		}
		Position pos1 = SoC.game.positionmapper.get(attack);
		Position pos2 = SoC.game.positionmapper.get(victim);
		Push p = new Push(new Vector2(), 50, 400);
		p.direction.x = Math.signum(pos2.x - pos1.x);
		p.direction.y = Math.signum(pos2.y - pos1.y);

		victim.addComponent(p);
		victim.changedInWorld();
	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {

	}

	@Override
	public void delete() {
		
	}

}
