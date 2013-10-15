package com.soc.game.attacks.processors;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Debuff;
import com.soc.game.components.Position;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.game.states.alterations.Poison;
import com.soc.utils.GraphicsLoader;

public class PoisonCloudProcessor implements AttackProcessor {
	public float timer;
	public Circle hitbox;
	private Rectangle enemy;
	private AnimatedRenderer renderer;

	@Mapper
	ComponentMapper<Damage> dm = SoC.game.world.getMapper(Damage.class);

	public PoisonCloudProcessor() {
		this.timer = Constants.Spells.POISON_CLOUD_DURATION;
		this.hitbox = new Circle();
		this.renderer = GraphicsLoader.loadCloud();
	}

	@Override
	public void process(Entity attack) {
		timer -= SoC.game.world.delta;
		if(timer <= 0){
			attack.deleteFromWorld();
		}
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position pos = SoC.game.positionmapper.get(victim);
		Bounds bon = SoC.game.boundsmapper.get(victim);
		hitbox = new Circle(attackpos.x, attackpos.y,
				Constants.Spells.POISON_CLOUD_RADIUS);
		enemy = new Rectangle(pos.x, pos.y, bon.width, bon.height);

		return Intersector.overlaps(hitbox, enemy);
	}

	@Override
	public void handle(Entity attack, Entity victim) {
		Debuff.addDebuff(victim, new Poison());
	}

	@Override
	public void frame(Entity attack, SpriteBatch batch) {
		Position pos = SoC.game.positionmapper.get(attack);
		batch.draw(renderer.frame(SoC.game.world.delta), pos.x+renderer.ox, pos.y+renderer.oy);
	}

	@Override
	public void delete() {

	}

}
