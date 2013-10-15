package com.soc.game.attacks;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.utils.GraphicsLoader;

public class RedCastingProcessor implements AttackProcessor{
	public AnimatedRenderer renderer;
	public float timer;
	public Entity owner;
	public RedCastingProcessor(Entity owner){
		this.timer=3f;
		renderer=GraphicsLoader.loadRedCast();
		this.owner=owner;
	}
	@Override
	public void process(Entity attack) {
		timer-=SoC.game.world.delta;
		if(timer<=0)
			attack.deleteFromWorld();
		
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {
		Position pos=SoC.game.positionmapper.get(owner);
		sprite.draw(renderer.frame(SoC.game.world.delta), pos.x+renderer.ox,pos.y+renderer.oy+Constants.Characters.HEIGHT);
		
	}

	@Override
	public void handle(Entity attack, Entity enemy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

}
