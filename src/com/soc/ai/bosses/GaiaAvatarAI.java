package com.soc.ai.bosses;

import java.util.Random;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.soc.ai.AI;
import com.soc.core.Constants.World;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.attacks.spells.Spell;
import com.soc.game.components.Bounds;
import com.soc.game.components.Buff;
import com.soc.game.components.Damage;
import com.soc.game.components.Delay;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.states.benefits.Inmune;
import com.soc.game.states.benefits.Teleport;

public class GaiaAvatarAI extends AI{

	public float timer, x, y;
	public boolean teleport;
	public Random r;
	
	
	public GaiaAvatarAI(){
		timer = 0;
		r = new Random();
	}
	
	@Override
	public void process(Entity e) {
		
		timer -= SoC.game.world.delta;
		if(timer > 0) return;

				
		State state = SoC.game.statemapper.get(e);
		Stats stats = SoC.game.statsmapper.get(e);
		
		if(teleport){
			teleport = false;
			SoC.game.buffmapper.get(e).removebuff(Inmune.class,e);
			state.state = State.IDLE;
		}

		Damage dm = SoC.game.damagemapper.getSafe(e);
				
		if(dm!=null && stats.health > dm.damage-stats.armor && stats.health > 1){
			x = r.nextInt(78) + 114;
			y = r.nextInt(25) + 14;
			Buff.addbuff(e, new Teleport(x*World.TILE_SIZE, y*World.TILE_SIZE, 0));
			Buff.addbuff(e, new Inmune());
			timer = Constants.Buff.TELEPORT_CAST_TIME;
			SoC.game.charactermapper.get(e).renderers[State.ATTACK].time=0;
			SoC.game.statemapper.get(e).state = State.ATTACK;
			teleport = true;
			e.removeComponent(Delay.class);
			e.changedInWorld();
		}
		
		if(SoC.game.statemapper.get(e).state >= State.BLOCKED) return;

		Position pos = SoC.game.positionmapper.get(e);
		Position playerPos = SoC.game.positionmapper.get(SoC.game.player);
		

		
		int attack = r.nextInt(105);
		if(attack <= 80){
			x = playerPos.x - pos.x;
			y = playerPos.y - pos.y;
			
			if(Math.abs(x) > Math.abs(y)){
				pos.direction.x = Math.signum(x);
				pos.direction.y = Math.signum(y)*(Math.abs(y)/Math.abs(x));
			} else {
				pos.direction.y = Math.signum(y);
				pos.direction.x = Math.signum(x)*(Math.abs(x)/Math.abs(y));
			}
			Spell s = SoC.game.spells[Constants.Spells.WINDBLADE];
			e.addComponent(new Delay(Constants.Groups.ENEMY_ATTACKS, s.cast, s.blocking, Constants.Spells.WINDBLADE));
			SoC.game.statemapper.get(e).state = s.state;
			e.changedInWorld();
		} else if(attack <= 84){
			Spell s = SoC.game.spells[Constants.Spells.TENTACLES];
			e.addComponent(new Delay(Constants.Groups.ENEMY_ATTACKS, s.cast, s.blocking, Constants.Spells.TENTACLES));
			SoC.game.statemapper.get(e).state = s.state;
			e.changedInWorld();
		} else{
			x = r.nextInt(78) + 114;
			y = r.nextInt(25) + 14;
			Buff.addbuff(e, new Teleport(x*World.TILE_SIZE, y*World.TILE_SIZE, 0));
			Buff.addbuff(e, new Inmune());
			timer = Constants.Buff.TELEPORT_CAST_TIME;
			SoC.game.charactermapper.get(e).renderers[State.ATTACK].time=0;
			SoC.game.statemapper.get(e).state = State.ATTACK;
			teleport = true;
		}

		
	}

	@Override
	public void death(Entity e) {
		SoC.game.progress.gaiaAvatarDefeated=true;		
	}

}
