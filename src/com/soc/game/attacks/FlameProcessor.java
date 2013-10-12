package com.soc.game.attacks;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Delay;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Velocity;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.utils.GraphicsLoader;

public class FlameProcessor implements AttackProcessor{
	public AnimatedRenderer renderer;
	public Entity hit;
	public Circle hitbox;
	public float radius;
	private Rectangle enemy;
	
	public FlameProcessor() {
		this.hit = null;
		this.hitbox = new Circle();
		this.renderer = GraphicsLoader.loadFlame();
	}

	@Override 
	public void process(Entity attack) {

	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		if(hit != null) return false;
		
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position victimpos = SoC.game.positionmapper.get(victim);
		Bounds attackbounds = SoC.game.boundsmapper.get(attack);
		Bounds victimbounds = SoC.game.boundsmapper.get(victim);
		
		return (attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y);
	

	}

	@Override
	public void handle(Entity attack, Entity victim) {
	}

	@Override
	public void frame(Entity attack, SpriteBatch batch) {
		Position pos = SoC.game.positionmapper.get(attack);
		batch.draw(renderer.frame(SoC.game.world.delta), pos.x, pos.y);
	}

	@Override
	public void delete() {

	}

}
