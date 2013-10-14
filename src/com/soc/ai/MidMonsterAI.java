package com.soc.ai;

import java.util.Random;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Bounds;
import com.soc.game.components.Delay;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Velocity;
import com.soc.game.spells.Spell;

public class MidMonsterAI implements AI{


	public float timer;
	public Random r;
	float limitXLeft;
	float limitXRight;
	float limitYBottom;
	float limitYUp;
	
	
	public MidMonsterAI(){
		timer = 0;
		r = new Random();
		limitXLeft=37*Constants.World.TILE_SIZE;
		limitXRight=61*Constants.World.TILE_SIZE;
		limitYBottom=54*Constants.World.TILE_SIZE;
		limitYUp=83*Constants.World.TILE_SIZE;
	}
	
	@Override
	public void process(Entity e) {
		Position pos = SoC.game.positionmapper.get(e);
		Velocity vel = SoC.game.velocitymapper.get(e);
		State state = SoC.game.statemapper.get(e);
		Entity player = SoC.game.player;
		Position playerPos = SoC.game.positionmapper.get(player);
		if((playerPos.x<limitXLeft || playerPos.x>limitXRight) || (playerPos.y<limitYBottom || playerPos.y>limitYUp)){
			System.out.println("Paso por aqui");
			state.state=State.IDLE;
			vel.vx=0;
			vel.vy=0;
			return;
		}
		
		if(state.state == State.DYING) return;
		
		float dsty = playerPos.y - pos.y;
		float dstx = playerPos.x - pos.x;
		
		pos.direction.x = Math.signum(dstx);
		pos.direction.y = Math.signum(dsty); 
		
		vel.vx = vel.speed * pos.direction.x;
		vel.vy = vel.speed * pos.direction.y;
		
		if(state.state != State.ATTACK){	
			if(Math.abs(dstx) < 40 && Math.abs(dsty) < 20 ){
				Spell spell = SoC.game.spells[Constants.Spells.BITE];
				state.state = spell.state;
				e.addComponent(new Delay(Constants.Groups.ENEMY_ATTACKS,spell.cast, spell.blocking, Constants.Spells.BITE));
				vel.vx = 0;
				vel.vy = 0;
				if(Math.abs(dstx) < Constants.Characters.WIDTH) pos.direction.x = 0;
				e.changedInWorld();
			} else if(vel.vx != 0 && vel.vy != 0){
				state.state = State.WALK;
				if(Math.abs(dstx) < 32) pos.direction.x = 0;
				else if(Math.abs(dsty) < 10) pos.direction.y = 0;
			}
		} else {
			vel.vx *= 0.5;
			vel.vx *= 0.5;
		}
	}

	@Override
	public void death(Entity e) {
		// TODO Auto-generated method stub
		
	}

}
