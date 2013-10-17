package com.soc.game.attacks.processors;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.SoC;
import com.soc.game.components.Bounds;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Velocity;

public class ChargeBossProcessor implements AttackProcessor{
	public Entity hit;
	public float duration;
	public Entity source;
	public Vector2 direction;
	
	public ChargeBossProcessor(Entity source, float duration, Vector2 direction) {
		this.source = source;
		hit = null;
		this.duration = duration;
		this.direction=direction;
	}
	@Override
	public void process(Entity attack) {
		if(SoC.game.statemapper.get(source).state==State.FALLING){
			attack.deleteFromWorld();
			return;
		}
		if(SoC.game.statemapper.get(source).state == State.CHARGING){
			Velocity vAttack = SoC.game.velocitymapper.get(attack);
			Velocity vSource = SoC.game.velocitymapper.get(source);
			duration-= SoC.game.world.delta;
			if(duration>0){
				vAttack.vx=vAttack.speed*direction.x;
				vAttack.vy=vAttack.speed*direction.y;
				vSource.vx=vAttack.speed*direction.x;
				vSource.vy=vAttack.speed*direction.y;
			}else{
				attack.deleteFromWorld();
				delete();
			}
		} else {
			attack.deleteFromWorld();
			delete();
		}
		
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position victimpos = SoC.game.positionmapper.get(victim);
		Bounds attackbounds = SoC.game.boundsmapper.get(attack);
		Bounds victimbounds = SoC.game.boundsmapper.get(victim);
		
		return (hit==null && attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y);
	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handle(Entity attack, Entity enemy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		SoC.game.statemapper.get(source).state=State.IDLE;
		
	}

}
