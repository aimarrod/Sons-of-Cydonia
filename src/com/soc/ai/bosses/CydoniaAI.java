package com.soc.ai.bosses;

import java.util.Random;
import java.util.Vector;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.soc.ai.AI;
import com.soc.core.Constants;
import com.soc.core.Constants.World;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.Buff;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.states.benefits.Inmune;
import com.soc.game.states.benefits.Teleport;

public class CydoniaAI extends AI{
	
	public int leftmostTile = 27;
	public int leftTile = 35;
	public int rightTile = 65;
	public int rightmostTile = 72;
	public int topTile = 176;
	public int bottomTile = 156;
	public int attackCounter = 0;
	public float timer;
	public float x, y;
	public boolean init, teleported, standard, waving, horizontal;
	public int state;
	
	public static final int STANDARD = 0;
	public static final int CASTING = 1;
	public static final int WAVING = 2;
	
	public Vector2[] windblades;
	
	public CydoniaAI(){
		standard = true;
		init = false;
		teleported = false;
		timer=5f;
		waving=false;
		windblades = new Vector2[]{
				new Vector2(leftTile-7, topTile+12),
				new Vector2(leftTile-7, bottomTile-12),
				new Vector2(rightTile+7, topTile+12),
				new Vector2(rightTile+7, bottomTile-12),
		};
	}




	public void init(Entity e){
		Position pos = SoC.game.positionmapper.get(SoC.game.player);
		if(pos.x * World.TILE_FACTOR > 47 && pos.x * World.TILE_FACTOR < 52 && pos.y *World.TILE_FACTOR > 185){
			init = true;
			Buff.addbuff(SoC.game.player, new Teleport(50*World.TILE_SIZE, 165*World.TILE_SIZE, 0));
		}
	}
	
	public void wave(Entity e, Vector2 direction){
		Position p=SoC.game.positionmapper.get(e);
		Entity fireStone=null;
		int holeWave=0;
		if(direction.x==0 && direction.y==-1){
			holeWave=leftmostTile+AI.rng.nextInt(rightmostTile-leftmostTile)+1;
			for(int i=this.leftmostTile;i<rightmostTile;i=i+2){
				fireStone = EntityFactory.createFireStone(i*Constants.World.TILE_SIZE, topTile*Constants.World.TILE_SIZE, p.z,direction,true);
		    	SoC.game.groupmanager.add(fireStone, Constants.Groups.ENEMY_ATTACKS);
		    	SoC.game.groupmanager.add(fireStone, Constants.Groups.MAP_BOUND);
		    	SoC.game.groupmanager.add(fireStone, Constants.Groups.PROJECTILES);
		    	SoC.game.levelmanager.setLevel(fireStone, Constants.Groups.LEVEL+p.z);
		    	fireStone.addToWorld();
			}
		}else if(direction.x==-1 && direction.y==0){
			for(int i=this.bottomTile;i<(topTile);i=i+2){
				fireStone = EntityFactory.createFireStone(rightmostTile*Constants.World.TILE_SIZE, i*Constants.World.TILE_SIZE, p.z,direction,true);
				SoC.game.groupmanager.add(fireStone, Constants.Groups.ENEMY_ATTACKS);
				SoC.game.groupmanager.add(fireStone, Constants.Groups.MAP_BOUND);
				SoC.game.groupmanager.add(fireStone, Constants.Groups.PROJECTILES);
				SoC.game.levelmanager.setLevel(fireStone, Constants.Groups.LEVEL+p.z);
				fireStone.addToWorld();
			}
		}

	}

	@Override
	public void process(Entity e) {
		if(!init){
			init(e);
			return;
		}
		timer-=SoC.game.world.delta;
		if(state == STANDARD){
			standard(e);
			return;
		}
		if(state == CASTING){
			casting(e);
			return;
		}
		if(state == WAVING){
			if(timer<=0){
				wave(e,new Vector2(0,-1));
				timer=0f;
			}
			return;
		}

	}


	@Override
	public void death(Entity e) {
		// TODO Auto-generated method stub
		
	}
	
	private void sendWindBlades(){
		Stats stats = SoC.game.statsmapper.get(SoC.game.player);

		for (int i = 0; i < windblades.length; i++){
			Vector2 windblade = windblades[i];
			Position playPos = SoC.game.positionmapper.get(SoC.game.player);
			Vector2 direction = new Vector2();
		
			float dstx = playPos.x - windblade.x*World.TILE_SIZE;
			float dsty = playPos.y - windblade.y*World.TILE_SIZE;
		
			if(Math.abs(dstx) > Math.abs(dsty)){
				direction.x = Math.signum(dstx);
				direction.y = Math.signum(dsty)*(Math.abs(dsty)/Math.abs(dstx));
			} else {
				direction.y = Math.signum(dsty);
				direction.x = Math.signum(dstx)*(Math.abs(dstx)/Math.abs(dsty));
			}
		
			Entity blade = EntityFactory.createWindblade(windblade.x*World.TILE_SIZE, windblade.y*World.TILE_SIZE, 0, direction, (int) (stats.maxHealth*0.07f+stats.armor));
			SoC.game.groupmanager.add(blade, Constants.Groups.PROJECTILES);
			SoC.game.groupmanager.add(blade, Constants.Groups.ENEMY_ATTACKS);
			SoC.game.groupmanager.add(blade, Constants.Groups.MAP_BOUND);
			SoC.game.groupmanager.add(blade, Constants.Groups.DESTROYABLE_PROJECTILES);
			SoC.game.levelmanager.setLevel(blade, Constants.Groups.LEVEL+0);
			blade.addToWorld();
		}
	}
	
	
	private void sendPoisonCloud(Entity e) {
		Position pos = SoC.game.positionmapper.get(e);
		Position playPos = SoC.game.positionmapper.get(SoC.game.player);
	
		float dstx = playPos.x - pos.x;
		float dsty = playPos.y - pos.y;
	
		if(Math.abs(dstx) > Math.abs(dsty)){
			pos.direction.x = Math.signum(dstx);
			pos.direction.y = Math.signum(dsty)*(Math.abs(dsty)/Math.abs(dstx));
		} else {
			pos.direction.y = Math.signum(dsty);
			pos.direction.x = Math.signum(dstx)*(Math.abs(dstx)/Math.abs(dsty));
		}
		Entity cloud = EntityFactory.createMovingCloud(pos, SoC.game.boundsmapper.get(e));
		SoC.game.groupmanager.add(cloud, Constants.Groups.PROJECTILES);
		SoC.game.groupmanager.add(cloud, Constants.Groups.ENEMY_ATTACKS);
		SoC.game.groupmanager.add(cloud, Constants.Groups.MAP_BOUND);
		SoC.game.levelmanager.setLevel(cloud, Constants.Groups.LEVEL + pos.z);
		cloud.addToWorld();		
	}
	
	private void callFireRain(boolean horizontal){
		Stats stats = SoC.game.statsmapper.get(SoC.game.player);
		for(int i=this.leftmostTile;i < rightmostTile;i=i+((horizontal)?2:8)){
			for(int j=this.bottomTile;j<(topTile);j=j+((horizontal)?8:2)){
				Entity spawned = EntityFactory.createMeteor(i*World.TILE_SIZE+World.TILE_SIZE*0.5f, j*World.TILE_SIZE+World.TILE_SIZE*0.5f, 0, (int) (stats.maxHealth*0.2f));	
				SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMY_ATTACKS);
				SoC.game.groupmanager.add(spawned, Constants.Groups.MAP_BOUND);
				SoC.game.levelmanager.setLevel(spawned, Constants.Groups.LEVEL +0);
				spawned.addToWorld();				
			}
		}
		horizontal = !horizontal;
	}
	
	
	public void standard(Entity e){
		if(timer <= 0){
			if(!teleported){
				teleported = true;

				Position pos = SoC.game.positionmapper.get(e);

				pos.direction.x = 0;
				pos.direction.y = -1;
			
				x = AI.rng.nextInt(29) + leftTile;
				y = AI.rng.nextInt(21) + bottomTile;
			
				Buff.addbuff(e, new Teleport(x*World.TILE_SIZE, y*World.TILE_SIZE, 0));
			
				timer = Constants.Buff.TELEPORT_CAST_TIME;
				SoC.game.charactermapper.get(e).renderers[State.ATTACK].time=0;
				SoC.game.statemapper.get(e).state = State.ATTACK;
			} else {
				teleported = false;
				if(attackCounter > 1){
					Position pos = SoC.game.positionmapper.get(e);

					pos.direction.x = 0;
					pos.direction.y = -1;
					
					Buff.addbuff(e, new Teleport((leftTile+(rightTile-leftTile)*0.5f)*World.TILE_SIZE, (bottomTile+(topTile-bottomTile)*0.5f)*World.TILE_SIZE, 0));
					SoC.game.buffmapper.get(e).removebuff(Inmune.class, e);
					
					timer = 5f;
					state = CASTING;
					SoC.game.charactermapper.get(e).renderers[State.ATTACK].time=0;
					SoC.game.statemapper.get(e).state = State.ATTACK;
					
					attackCounter = 0;
				} else {
					int attack = AI.rng.nextInt(10);
					if(attack <= 2){
						callFireRain(horizontal);
					} else if(attack <= 7){
						sendWindBlades();
					} else {
						sendPoisonCloud(e);
					}
					attackCounter++;
				}
			}
		}
	}
	
	public void casting(Entity e){
		if(SoC.game.damagemapper.has(e)){
			Stats stats = SoC.game.statsmapper.get(e);
			if((stats.maxHealth/stats.health - (SoC.game.damagemapper.get(e).damage - stats.armor)) <= 0.5){
				state = WAVING;
			} else {
				state = STANDARD;
			}
		} else {
			if(timer <= 0){
				for(int i=this.leftmostTile;i < rightmostTile;i++){
					for(int j=this.bottomTile;j<topTile;j++){
						Entity spawned = EntityFactory.createFlame(i*World.TILE_SIZE+World.TILE_SIZE*0.5f, j*World.TILE_SIZE+World.TILE_SIZE*0.5f, 0, 0);	
						SoC.game.groupmanager.add(spawned, Constants.Groups.ENEMY_ATTACKS);
						SoC.game.groupmanager.add(spawned, Constants.Groups.MAP_BOUND);
						SoC.game.levelmanager.setLevel(spawned, Constants.Groups.LEVEL +0);
						spawned.addToWorld();				
					}
				}
				timer = 20f;
			}
		}
	}
}
