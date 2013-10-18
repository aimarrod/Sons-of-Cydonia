package com.soc.game.attacks.processors;

import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Position;
import com.soc.game.components.State;

public class HarmfulEnemyProcessor implements AttackProcessor{

	Bag<Entity> hit;
	float timer;
	
	public HarmfulEnemyProcessor(){
		hit = new Bag<Entity>();
		timer = 0.5f;
	}
	
	@Override
	public void process(Entity attack) {
		
		if(SoC.game.statemapper.get(attack).state == State.DYING){
			SoC.game.groupmanager.remove(attack, Constants.Groups.ENEMY_ATTACKS);
		}
		timer -= SoC.game.world.delta;
		if(timer <= 0){
			timer = 0.5f;
			hit.clear();
		}
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position victimpos = SoC.game.positionmapper.get(victim);
		Bounds attackbounds = SoC.game.boundsmapper.get(attack);
		Bounds victimbounds = SoC.game.boundsmapper.get(victim);
		State state = SoC.game.statemapper.get(victim);
		
		return (!hit.contains(victim) && (state.state != State.CHARGING && state.state != State.SPINNING)  && attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y);
	
	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {
		
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
	public void delete() {
		
	}
	
}
