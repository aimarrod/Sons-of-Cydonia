package com.soc.game.attacks.processors;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.Constants;
import com.soc.core.Constants.World;
import com.soc.core.SoC;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Debuff;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.states.alterations.Push;
import com.soc.utils.EffectsPlayer;

public class SlashProcessor implements AttackProcessor {
	public Bag<Entity> hit;
	public Rectangle hitbox;
	public float timer;
	@Mapper
	ComponentMapper<Damage> dm = SoC.game.world.getMapper(Damage.class);
	public SlashProcessor() {
		hit = new Bag<Entity>();
		timer = 0.15f;
	}

	@Override 
	public void process(Entity attack) {
		timer -= SoC.game.world.delta;
		if(timer <= 0) attack.deleteFromWorld();
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position victimpos = SoC.game.positionmapper.get(victim);
		Bounds attackbounds = SoC.game.boundsmapper.get(attack);
		Bounds victimbounds = SoC.game.boundsmapper.get(victim);
		
		if(attackbounds.height < 0) return (!hit.contains(victim) && attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y + attackbounds.height < victimpos.y + victimbounds.height && attackpos.y > victimpos.y);	
		else if(attackbounds.width < 0) return (!hit.contains(victim) && attackpos.x  + attackbounds.width < victimpos.x + victimbounds.width && attackpos.x > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y);
		else return (!hit.contains(victim) && attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y);
		
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
	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {
		
	}

	@Override
	public void delete() {
		
	}

}
