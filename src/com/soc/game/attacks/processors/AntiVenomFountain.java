package com.soc.game.attacks.processors;

import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.soc.core.SoC;
import com.soc.game.components.Bounds;
import com.soc.game.components.Buff;
import com.soc.game.components.Debuff;
import com.soc.game.components.Position;
import com.soc.game.components.Stats;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.game.states.alterations.Venom;
import com.soc.game.states.benefits.Rage;
import com.soc.utils.GraphicsLoader;

public class AntiVenomFountain implements AttackProcessor {
	public AnimatedRenderer renderer;
	
	public AntiVenomFountain(){
		this.renderer = GraphicsLoader.loadAntiVenomFountain();
	}
	@Override
	public void process(Entity attack) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean collision(Entity attack, Entity victim) {
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
	public void handle(Entity attack, Entity enemy) {
		Stats s=SoC.game.statsmapper.get(enemy);
		int halfHealth=s.maxHealth/2;
		if(SoC.game.debuffmapper.has(enemy)){
			Debuff debuff=SoC.game.debuffmapper.get(enemy);
			debuff.removeDebuff(Venom.class,enemy);
			if(s.health<=halfHealth){
				s.health=halfHealth;
			}
		}
		
		if(SoC.game.buffmapper.has(enemy)){
			Buff buff=SoC.game.buffmapper.get(enemy);
			buff.removebuff(Rage.class,enemy);
		}
			
		

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

}
