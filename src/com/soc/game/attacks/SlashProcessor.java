package com.soc.game.attacks;

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
import com.soc.game.components.Position;
import com.soc.game.components.State;

public class SlashProcessor implements AttackProcessor {
	public Bag<Entity> hit;
	public Rectangle hitbox;
	@Mapper
	ComponentMapper<Damage> dm = SoC.game.world.getMapper(Damage.class);
	public SlashProcessor() {
		hit = new Bag<Entity>();
	}

	@Override 
	public void process(Entity attack) {
		attack.deleteFromWorld();
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position victimpos = SoC.game.positionmapper.get(victim);
		Bounds attackbounds = SoC.game.boundsmapper.get(attack);
		Bounds victimbounds = SoC.game.boundsmapper.get(victim);
		State state = SoC.game.statemapper.get(victim);
		
		if(attackbounds.height < 0) return (!hit.contains(victim) && state.state !=State.DYING && attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y + attackbounds.height < victimpos.y + victimbounds.height && attackpos.y > victimpos.y);	
		else if(attackbounds.width < 0) return (!hit.contains(victim) && state.state !=State.DYING && attackpos.x  + attackbounds.width < victimpos.x + victimbounds.width && attackpos.x > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y);
		else return (!hit.contains(victim) && state.state !=State.DYING && attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y);
		
	}

	@Override
	public void handle(Entity attack, Entity victim) {
		Attack a = SoC.game.attackmapper.get(attack);
		hit.add(victim);
		if(SoC.game.damagemapper.has(victim)){
			SoC.game.damagemapper.get(victim).damage+=a.damage;
		}else{
			victim.addComponent(new Damage(2000));
			victim.changedInWorld();
		}
		
	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {
		ShapeRenderer shapes = new ShapeRenderer();
		shapes.setProjectionMatrix(SoC.game.camera.combined);
		shapes.begin(ShapeType.Filled);
		
		Rectangle rect;
		Position attackpos = SoC.game.positionmapper.get(attack);
		Bounds attackbounds = SoC.game.boundsmapper.get(attack);
		rect = new Rectangle(attackpos.x, attackpos.y, attackbounds.width, attackbounds.height);
		shapes.rect(rect.x, rect.y, rect.width, rect.height);
		shapes.end();
	}

}
