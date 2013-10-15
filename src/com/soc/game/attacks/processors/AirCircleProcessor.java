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
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Debuff;
import com.soc.game.components.Position;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.game.graphics.Renderer;
import com.soc.game.states.alterations.Poison;
import com.soc.utils.GraphicsLoader;

public class AirCircleProcessor implements AttackProcessor {
	public float timer, distance;
	private Renderer renderer;

	@Mapper
	ComponentMapper<Damage> dm = SoC.game.world.getMapper(Damage.class);

	public AirCircleProcessor() {
		this.timer = Constants.Spells.AIR_CIRCLE_TIME;
		this.renderer = GraphicsLoader.loadAirCircle();
	}

	@Override
	public void process(Entity attack) {
		timer -= SoC.game.world.delta;
		if(timer <= 0){
			Entity cloud = EntityFactory.createPoisonCloud(SoC.game.positionmapper.get(attack), SoC.game.boundsmapper.get(attack));
			SoC.game.groupmanager.add(cloud, Constants.Groups.ENEMY_ATTACKS);
			SoC.game.groupmanager.add(cloud, Constants.Groups.MAP_BOUND);
			SoC.game.levelmanager.setLevel(cloud, Constants.Groups.LEVEL +(SoC.game.positionmapper.get(attack).z));
			cloud.addToWorld();
			attack.deleteFromWorld();
		}
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		return false;
	}

	@Override
	public void handle(Entity attack, Entity victim) {
	}

	@Override
	public void frame(Entity attack, SpriteBatch batch) {
		Position pos = SoC.game.positionmapper.get(attack);
		if(distance <= 0) batch.draw(renderer.frame(SoC.game.world.delta), pos.x+renderer.ox, pos.y+renderer.oy);
	}

	@Override
	public void delete() {

	}

}
