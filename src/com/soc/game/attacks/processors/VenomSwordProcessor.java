package com.soc.game.attacks.processors;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.osc.game.states.benefits.Rage;
import com.soc.core.SoC;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Buff;
import com.soc.game.components.Damage;
import com.soc.game.components.Debuff;
import com.soc.game.components.Position;
import com.soc.game.states.alterations.Venom;

public class VenomSwordProcessor implements AttackProcessor{	
	public Entity hit;
	public float range;
	@Mapper
	ComponentMapper<Damage> dm = SoC.game.world.getMapper(Damage.class);
	public VenomSwordProcessor(Vector2 dir, float range) {
		hit = null;
		this.range = range;
	}
	@Override
	public void process(Entity attack) {
		attack.deleteFromWorld();
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		if(hit != null) return false;
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position victimpos = SoC.game.positionmapper.get(victim);
		Bounds attackbounds = SoC.game.boundsmapper.get(attack);
		Bounds victimbounds = SoC.game.boundsmapper.get(victim);
		System.out.println("AttackPosX: "+attackpos.x);
		System.out.println("VictimPosX: "+victimpos.x);
		System.out.println("AttackPosY: "+attackpos.y);
		System.out.println("VictimPosYS: "+victimpos.y);
		System.out.println( ( attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y));
		return ( attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y);
	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {
		
	}

	@Override
	public void handle(Entity attack, Entity victim) {
		hit=victim;
		Attack a = SoC.game.attackmapper.get(attack);
		if(SoC.game.damagemapper.has(victim)){
			SoC.game.damagemapper.get(victim).damage+=a.damage;
		}else{
			victim.addComponent(new Damage(a.damage, false));
			victim.changedInWorld();
		}
		Debuff.addDebuff(victim, new Venom());
		Buff.addbuff(victim, new Rage());
		
	}

	@Override
	public void delete() {
		
	}


}
