package com.soc.game.attacks.processors;

import java.util.Random;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.soc.core.SoC;
import com.soc.game.states.alterations.Push;
import com.soc.game.components.Attack;
import com.soc.game.components.Bounds;
import com.soc.game.components.Damage;
import com.soc.game.components.Debuff;
import com.soc.game.components.Position;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.utils.GraphicsLoader;

public class RedPushAttackProcessor implements AttackProcessor{
	public AnimatedRenderer renderer;
	public Entity hit;
	public float deathTimer;
	boolean isOver;
	public RedPushAttackProcessor(){
		renderer=GraphicsLoader.loadRedPush();
		deathTimer=2f;
		isOver=false;
		
	}
	@Override
	public void process(Entity attack) {
		if(isOver){
			deathTimer-=SoC.game.world.delta;
			if(deathTimer<=0)attack.deleteFromWorld();
		}
	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
		if(hit!=null) return false;
		Position attackpos = SoC.game.positionmapper.get(attack);
		Position victimpos = SoC.game.positionmapper.get(victim);
		Bounds attackbounds = SoC.game.boundsmapper.get(attack);
		Bounds victimbounds = SoC.game.boundsmapper.get(victim);
		return (attackpos.x < victimpos.x + victimbounds.width && attackpos.x + attackbounds.width > victimpos.x && attackpos.y < victimpos.y + victimbounds.height && attackpos.y + attackbounds.height > victimpos.y);
	}

	@Override
	public void frame(Entity attack, SpriteBatch sprite) {
		Position pos = SoC.game.positionmapper.get(attack);
		sprite.draw(renderer.frame(SoC.game.world.delta), pos.x+renderer.ox, pos.y+renderer.oy);	
	}

	@Override
	public void handle(Entity attack, Entity victim) {
		isOver=true;
		hit=victim;
		Attack a = SoC.game.attackmapper.get(attack);
		Position pos1 = SoC.game.positionmapper.get(attack);
		Position pos2 = SoC.game.positionmapper.get(victim);
		if(SoC.game.damagemapper.has(victim)){
			SoC.game.damagemapper.get(victim).damage+=a.damage;
		}else{
			victim.addComponent(new Damage(a.damage, false));
			victim.changedInWorld();
		}
		Vector2 [] directions=new Vector2 [2];
		Vector2 pushDirection = new Vector2(1,0);
		Vector2 pushDirection2=new Vector2(0,1);
		directions[0]=pushDirection;
		directions[1]=pushDirection2;
		Random r=new Random();
		Debuff.addDebuff(victim, new Push(directions[r.nextInt(2)], 300, 300));
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

}
