package com.soc.game.attacks.processors;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.soc.core.SoC;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Debuff;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.game.states.alterations.Push;
import com.soc.utils.GraphicsLoader;

public class FireStoneProcessor implements AttackProcessor{
	public AnimatedRenderer initial;
	public AnimatedRenderer running;
	public AnimatedRenderer death;
	public Entity hit;
	float timer;
	float deathTimer;
	boolean finalBoss;
	
	public FireStoneProcessor(boolean finalBoss){
		this.hit=null;
		this.initial=GraphicsLoader.loadFireStoneInitial();
		this.running = GraphicsLoader.loadFireStoneRunning();
		this.death=GraphicsLoader.loadFireStoneDeath();
		timer=0;
		deathTimer=0;
		this.finalBoss=finalBoss;
	}
	@Override
	public void process(Entity attack) {
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
if(hit != null) return false;
		
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position victimpos = SoC.game.positionmapper.get(victim);
		Bounds attackbounds = SoC.game.boundsmapper.get(attack);
		Bounds victimbounds = SoC.game.boundsmapper.get(victim);
		
		return (attackpos.z == victimpos.z && attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y);
	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {
		Position pos = SoC.game.positionmapper.get(attack);
		if(hit==null && timer<0.6f){
			sprite.draw(initial.frame(SoC.game.world.delta), pos.x+initial.ox, pos.y+initial.oy);
		}else if(hit ==null ){
			sprite.draw(running.frame(SoC.game.world.delta), pos.x+running.ox, pos.y+running.oy);
		}else if(hit !=null){
			sprite.draw(death.frame(SoC.game.world.delta), pos.x+running.ox, pos.y+running.oy);
		}
	}

	@Override
	public void handle(Entity attack, Entity enemy) {
		hit=enemy;
		Debuff.addDebuff(hit, new Push(SoC.game.positionmapper.get(attack).direction,100,100));
		int damage=0;
		if(finalBoss)
			damage = (int) (SoC.game.statsmapper.get(hit).maxHealth*0.4);
		else
			damage = (int) (SoC.game.statsmapper.get(hit).maxHealth*0.1);
		if(SoC.game.damagemapper.has(hit)){
			SoC.game.damagemapper.get(hit).pureDamage+=damage;
		}else{
			hit.addComponent(new Damage(damage, true));
			hit.changedInWorld();
		}
	}

	@Override
	public void delete() {

		
	}

}
