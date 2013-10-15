package com.soc.ai;

import com.artemis.Entity;
import com.osc.game.states.benefits.ShieldBuff;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.attacks.spells.Spell;
import com.soc.game.components.Buff;
import com.soc.game.components.Delay;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Velocity;

public class SatanAI implements AI{
	float shieldCD;
	float lastTimeShield;
	float timer;
	float limitXLeft;
	float limitXRight;
	float limitYBottom;
	float limitYUp;
	float timerPushAttack;
	boolean casting;

	public SatanAI(){
		shieldCD=15f;
		lastTimeShield=0f;
		timer=0;
		limitXLeft=1*Constants.World.TILE_SIZE;
		limitXRight=33*Constants.World.TILE_SIZE;
		limitYBottom=54*Constants.World.TILE_SIZE;
		limitYUp=83*Constants.World.TILE_SIZE;
		timerPushAttack=7f;
		casting=false;
	}
	@Override
	public void process(Entity e) {

		Position pos = SoC.game.positionmapper.get(e);
		Velocity vel = SoC.game.velocitymapper.get(e);
		Stats stats=SoC.game.statsmapper.get(e);
		State state = SoC.game.statemapper.get(e);
		Entity player = SoC.game.player;
		Position playerPos = SoC.game.positionmapper.get(player);
		if((playerPos.x<limitXLeft || playerPos.x>limitXRight) || (playerPos.y<limitYBottom || playerPos.y>limitYUp)){
			state.state=State.IDLE;
			vel.vx=0;
			vel.vy=0;
			return;
		}
		if(state.state == State.DYING) return;
		
		timer-=SoC.game.world.delta;
		if(stats.health<(stats.maxHealth-stats.maxHealth/4)&& (Math.abs(timer)-Math.abs(lastTimeShield))>=shieldCD){
			Buff.addbuff(e, new ShieldBuff());
			lastTimeShield=timer;
		}

		
		float dsty = playerPos.y - pos.y;
		float dstx = playerPos.x - pos.x;
		timerPushAttack-=SoC.game.world.delta;
		if(timerPushAttack<=3 && dstx<15*Constants.World.TILE_SIZE && dsty<15*Constants.World.TILE_SIZE){
			Entity spawned=null;
			
			if(timerPushAttack>0 && !casting){
				System.out.println(timerPushAttack);
				spawned=EntityFactory.createRedCast(pos.x-(Constants.Characters.WIDTH/2), pos.y+Constants.Characters.HEIGHT, playerPos.z,e);
				SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMY_ATTACKS);
				SoC.game.groupmanager.add(spawned, Constants.Groups.MAP_BOUND);
				SoC.game.levelmanager.setLevel(spawned, Constants.Groups.LEVEL +pos.z);
				spawned.addToWorld();
				casting=true;
			}else if(timerPushAttack<=0 && casting){
				spawned=EntityFactory.createRedPush(playerPos.x-(Constants.Characters.WIDTH/2), playerPos.y, playerPos.z,SoC.game.statsmapper.get(player).intelligence);
				SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMY_ATTACKS);
				SoC.game.groupmanager.add(spawned, Constants.Groups.MAP_BOUND);
				SoC.game.levelmanager.setLevel(spawned, Constants.Groups.LEVEL +pos.z);
				spawned.addToWorld();
				casting=false;
				timerPushAttack=7f;
			}
		}
		pos.direction.x = Math.signum(dstx);
		pos.direction.y = Math.signum(dsty); 
		
		vel.vx = vel.speed * pos.direction.x;
		vel.vy = vel.speed * pos.direction.y;
		
		if(state.state != State.ATTACK){	
			if(Math.abs(dstx) < 40 && Math.abs(dsty) < 20 ){
				Spell spell = SoC.game.spells[Constants.Spells.VENOMSWORD];
				state.state = spell.state;
				e.addComponent(new Delay(Constants.Groups.ENEMY_ATTACKS,spell.cast, spell.blocking, Constants.Spells.VENOMSWORD));
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
			vel.vx *= 0.0;
			vel.vx *= 0.0;
		}
		
	}

	@Override
	public void death(Entity e) {
		// TODO Auto-generated method stub
		
	}

}