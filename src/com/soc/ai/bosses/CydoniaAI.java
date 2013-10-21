package com.soc.ai.bosses;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;
import com.soc.ai.AI;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.Constants.World;
import com.soc.core.SoC;
import com.soc.game.components.Buff;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.states.benefits.Teleport;

public class CydoniaAI extends AI{
	
	public int leftmostTile = 27;
	public int leftTile = 35;
	public int rightTile = 65;
	public int rightmostTile = 72;
	public int topTile = 177;
	public int bottomTile = 156;
	public float timer;
	public float x, y;
	public boolean init, teleported, standard;
	
	public CydoniaAI(){
		standard = true;
		init = false;
		teleported = false;
	}
	
	
	public void standard(Entity e){
		timer -= SoC.game.world.delta;
		if(!teleported){
			x = AI.rng.nextInt(29) + leftTile;
			y = AI.rng.nextInt(21) + bottomTile;
			Buff.addbuff(e, new Teleport(x*World.TILE_SIZE, y*World.TILE_SIZE, 0));
			timer = Constants.Buff.TELEPORT_CAST_TIME;
			SoC.game.charactermapper.get(e).renderers[State.ATTACK].time=0;
			SoC.game.statemapper.get(e).state = State.ATTACK;
			teleported = true;
		} else {
			if(timer <= 0){
				teleported = false;
			}
		}
	}
	
	public void init(Entity e){
		Position pos = SoC.game.positionmapper.get(SoC.game.player);
		if(pos.x * World.TILE_FACTOR > 47 && pos.x * World.TILE_FACTOR < 52 && pos.y *World.TILE_FACTOR > 185){
			init = true;
			Buff.addbuff(SoC.game.player, new Teleport(50*World.TILE_SIZE, 165*World.TILE_SIZE, 0));
		}
	}
	
	public void wave(Vector2 direction){
		Entity fireStone=null;
		if(direction.x==0 && direction.y==-1){
			fireStone = EntityFactory.createFireStone(1, 1, 1, new Vector2(Math.signum(1),0),false);
		    SoC.game.groupmanager.add(fireStone, Constants.Groups.ENEMY_ATTACKS);
		    SoC.game.groupmanager.add(fireStone, Constants.Groups.MAP_BOUND);
		    SoC.game.groupmanager.add(fireStone, Constants.Groups.PROJECTILES);
		    SoC.game.levelmanager.setLevel(fireStone, Constants.Groups.LEVEL+1);
		    fireStone.addToWorld();
		}else if(direction.x==-1 && direction.y==0){
			
		}

	}

	@Override
	public void process(Entity e) {
		if(!init){
			init(e);
			return;
		}
		if(standard){
			standard(e);
		}
	}


	@Override
	public void death(Entity e) {
		// TODO Auto-generated method stub
		
	}
}
