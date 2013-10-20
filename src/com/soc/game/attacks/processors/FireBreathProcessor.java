package com.soc.game.attacks.processors;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Debuff;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.game.states.alterations.Push;
import com.soc.utils.GraphicsLoader;

public class FireBreathProcessor implements AttackProcessor {
	public AnimatedRenderer initial;
	public AnimatedRenderer mid;
	public AnimatedRenderer end;
	public Entity hit;
	float timer;
	float deathTimer;
	Circle hitbox;
	Rectangle enemy;
	public FireBreathProcessor(){
		initial=GraphicsLoader.loadInitialFireBreath();
		mid=GraphicsLoader.loadMidFireBreath();
		end=GraphicsLoader.loadEndFireBreath();
		timer=0f;
		deathTimer=0;	
		hit=null;
				
	}
	@Override
	public void process(Entity attack) {
		System.out.println(timer);
		timer+=SoC.game.world.delta;
		if(hit!=null){
			deathTimer+=SoC.game.world.delta;
			if(deathTimer>1f){
			attack.deleteFromWorld();
			delete();
			}
		}
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		// TODO Auto-generated method stub
		if(hit != null) return false;
		
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position victimpos = SoC.game.positionmapper.get(victim);
		Bounds victimbounds = SoC.game.boundsmapper.get(victim);
		hitbox = new Circle(attackpos.x, attackpos.y, Constants.Spells.SPIN_RADIUS);
		enemy = new Rectangle(victimpos.x, victimpos.y, victimbounds.width, victimbounds.height);
		return (hit==null && Intersector.overlaps(hitbox, enemy));
	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {
		Position pos = SoC.game.positionmapper.get(attack);
		if(hit==null && timer<0.6f){
			sprite.draw(initial.frame(SoC.game.world.delta), pos.x+initial.ox, pos.y+initial.oy);
		}else if(hit ==null ){
			sprite.draw(mid.frame(SoC.game.world.delta), pos.x+mid.ox, pos.y+mid.oy);
		}else if(hit !=null){
			sprite.draw(end.frame(SoC.game.world.delta), pos.x+end.ox, pos.y+end.oy);
		}
		
	}

	@Override
	public void handle(Entity attack, Entity enemy) {
		hit=enemy;
		Debuff.addDebuff(hit, new Push(SoC.game.positionmapper.get(attack).direction,1000,1000));
		
	}

	@Override
	public void delete() {
		if(hit != null){
			SoC.game.statemapper.get(hit).state = State.IDLE;
			int damage = (int) (SoC.game.statsmapper.get(hit).maxHealth*0.1);
			if(SoC.game.damagemapper.has(hit)){
				SoC.game.damagemapper.get(hit).pureDamage+=damage;
			}else{
				hit.addComponent(new Damage(damage, true));
				hit.changedInWorld();
			}
		}
		
	}

}
