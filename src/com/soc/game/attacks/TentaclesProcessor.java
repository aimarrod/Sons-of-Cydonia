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
import com.soc.core.SoC;
import com.soc.game.alterations.Poison;
import com.soc.game.alterations.Push;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Debuff;
import com.soc.game.components.Position;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.game.graphics.Renderer;
import com.soc.utils.GraphicsLoader;

public class TentaclesProcessor implements AttackProcessor {
	public float timer, distance;
	public Bag<Entity> hit;
	public boolean hitting;
	public float radius;
	public Circle hitbox;
	private Rectangle enemy;
	private Renderer renderer;

	@Mapper
	ComponentMapper<Damage> dm = SoC.game.world.getMapper(Damage.class);

	public TentaclesProcessor() {
		this.timer = Constants.Spells.TENTACLES_PREPARE;
		this.radius = Constants.Spells.TENTACLES_RADIUS;
		this.hitting = false;
		this.hitbox = new Circle();
		this.renderer = GraphicsLoader.loadTentacles();
		this.hit = new Bag<Entity>();
	}

	@Override
	public void process(Entity attack) {
		timer -= SoC.game.world.delta;
		if(timer <= 0){
			if(!hitting){
				hitting = true;
				timer = Constants.Spells.TENTACLES_DURATION - Constants.Spells.TENTACLES_PREPARE;
			} else {
				attack.deleteFromWorld();
			}
		}
		
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position pos = SoC.game.positionmapper.get(victim);
		Bounds bon = SoC.game.boundsmapper.get(victim);
		hitbox = new Circle(attackpos.x, attackpos.y+110,
				radius);
		enemy = new Rectangle(pos.x, pos.y, bon.width, bon.height);

		return (hitting && !hit.contains(victim) && Intersector.overlaps(hitbox, enemy));
	}

	@Override
	public void handle(Entity attack, Entity victim) {
		Attack a = SoC.game.attackmapper.get(attack);
		hit.add(victim);
		if(SoC.game.damagemapper.has(victim)){
			SoC.game.damagemapper.get(victim).pureDamage+=a.damage;
		}else{
			victim.addComponent(new Damage(a.damage, true));
			victim.changedInWorld();
		}	
		Position pos1 = SoC.game.positionmapper.get(attack);
		Position pos2 = SoC.game.positionmapper.get(victim);
		Push p = new Push(new Vector2(), 400, 600);
		p.direction.x = Math.signum(pos2.x - pos1.x);
		p.direction.y = Math.signum(pos2.y - pos1.y);
		if(p.direction.x == 0 && p.direction.y == 0){
			p.direction.x = (float) Math.random();
			p.direction.y = (float) Math.random();
		}

		Debuff.addDebuff(victim, p);
	}

	@Override
	public void frame(Entity attack, SpriteBatch batch) {
		Position pos = SoC.game.positionmapper.get(attack);
		batch.setColor(1f,1f,1f,0.5f);
		batch.draw(renderer.frame(SoC.game.world.delta), pos.x+renderer.ox, pos.y+renderer.oy);
		batch.setColor(1f,1f,1f,1f);

	}

	@Override
	public void delete() {

	}

}
