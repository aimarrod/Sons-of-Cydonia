package com.soc.game.attacks.processors;

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
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Debuff;
import com.soc.game.components.Position;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.game.graphics.Renderer;
import com.soc.game.states.alterations.Poison;
import com.soc.utils.EffectsPlayer;
import com.soc.utils.GraphicsLoader;

public class MeteorProcessor implements AttackProcessor {
	public float timer, distance;
	public Bag<Entity> hit;
	public int counter;
	public float radius;
	public Circle hitbox;
	private Rectangle enemy;
	private Renderer fall, blast, shadow;
	private boolean sounded;

	@Mapper
	ComponentMapper<Damage> dm = SoC.game.world.getMapper(Damage.class);

	public MeteorProcessor() {
		this.timer = Constants.Spells.METEOR_BLAST_TICK_INTERVAL;
		this.counter = Constants.Spells.METEOR_BLAST_TICK_NUMBER;
		this.distance = Constants.Spells.METEOR_FALL_DISTANCE;
		this.radius = Constants.Spells.METEOR_RADIUS;
		this.hitbox = new Circle();
		this.enemy = new Rectangle();
		this.blast = GraphicsLoader.loadMeteorBlast();
		this.fall = GraphicsLoader.loadMeteorFall();
		this.shadow = GraphicsLoader.loadMeteorShadow();
		this.hit = new Bag<Entity>();
	}

	@Override
	public void process(Entity attack) {
		if(counter <= 0){
			if(((AnimatedRenderer)blast).animation.isAnimationFinished(blast.time)){
				attack.deleteFromWorld();
			}
			return;
		}
		if(distance <= 0){
			if(!sounded){
				EffectsPlayer.play("spell-explosion.ogg");
				sounded=true;
			}
			timer-= SoC.game.world.delta;
			if(timer <= 0){
				radius += Constants.Spells.METEOR_RADIUS_INCREASE;
				timer = Constants.Spells.METEOR_BLAST_TICK_INTERVAL;
				counter--;
			}
		} else{
			distance -= SoC.game.world.delta*Constants.Spells.METEOR_FALL_SPEED;
			if(distance <= 0){
				SoC.game.levelmanager.setLevel(attack, Constants.Groups.LEVEL+SoC.game.positionmapper.get(attack).z);
			}
		}
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position pos = SoC.game.positionmapper.get(victim);
		Bounds bon = SoC.game.boundsmapper.get(victim);
		
		enemy.set(pos.x, pos.y, bon.width, bon.height);
		hitbox.set(attackpos.x, attackpos.y, radius);

		return (distance<=0 && counter > 0 && !hit.contains(victim) && Intersector.overlaps(hitbox, enemy));
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
	}

	@Override
	public void frame(Entity attack, SpriteBatch batch) {
		Position pos = SoC.game.positionmapper.get(attack);
		if(distance <= 0) batch.draw(blast.frame(SoC.game.world.delta), pos.x+blast.ox, pos.y+blast.oy);
		else{
			batch.draw(fall.frame(SoC.game.world.delta), pos.x+fall.ox, pos.y+distance+fall.oy);
			batch.draw(shadow.frame(SoC.game.world.delta), pos.x+shadow.ox, pos.y+shadow.oy);
		}
	}

	@Override
	public void delete() {

	}

}
