package com.soc.game.attacks.processors;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Debuff;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.game.states.alterations.Push;
import com.soc.utils.EffectsPlayer;
import com.soc.utils.GraphicsLoader;

public class FirelionProcessor implements AttackProcessor{
	
	public Bag<Entity> hit;
	public float timer, duration;
	public AnimatedRenderer renderer;
	
	public FirelionProcessor(Position source) {
		this.hit = new Bag<Entity>();
		this.renderer = GraphicsLoader.loadFirelion(source.direction);
		this.timer = Constants.Spells.FIRELION_DELAY;
		this.duration = this.timer*2;
	}

	@Override 
	public void process(Entity attack) {
		timer -= SoC.game.world.delta;
		duration -= SoC.game.world.delta;
		if(duration <= 0){
			attack.deleteFromWorld();
		}
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position victimpos = SoC.game.positionmapper.get(victim);
		Bounds attackbounds = SoC.game.boundsmapper.get(attack);
		Bounds victimbounds = SoC.game.boundsmapper.get(victim);
		
		return (timer <= 0 && !hit.contains(victim) && attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y);
	}

	@Override
	public void handle(Entity attack, Entity victim) {
		
		Attack a = SoC.game.attackmapper.get(attack);
		hit.add(victim);
		if(SoC.game.damagemapper.has(victim)){
			SoC.game.damagemapper.get(victim).damage+=a.damage;
		}else{
			victim.addComponent(new Damage(a.damage, false));
			victim.changedInWorld();
		}
		Debuff.addDebuff(victim, new Push(SoC.game.positionmapper.get(attack).direction.cpy(), 150, 1000));
	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {
		Position pos = SoC.game.positionmapper.get(attack);
		sprite.draw(renderer.frame(SoC.game.world.delta),pos.x+renderer.ox,pos.y+renderer.oy);
	}

	@Override
	public void delete() {
	}
}
