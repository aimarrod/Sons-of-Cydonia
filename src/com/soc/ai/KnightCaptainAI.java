package com.soc.ai;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.core.Constants.World;
import com.soc.game.components.Position;
import com.soc.game.components.State;

public class KnightCaptainAI extends AI{
	
	public boolean init;
	
	public KnightCaptainAI(){
		modules = new AIModule[2];
		modules[0] = new BasicFollowing(32, false, true, true);
		modules[1] = new SpellAttack(new int[]{Constants.Spells.SLASH, Constants.Spells.WHIRLBLADE, Constants.Spells.QUAKEBLADE}, new float[]{64, 200, 400}, new float[]{0.5f, 2.0f, 3.0f});
		init = false;
	}

	@Override
	public void process(Entity e) {
		State state = SoC.game.statemapper.get(e);
		if(state.state == State.DYING) return;
		
		if(init){	
			processModules(e); 
		} else {
			Position playerPos = SoC.game.positionmapper.get(SoC.game.player);
			if((int)(playerPos.y*World.TILE_FACTOR) > 111){
				EntityFactory.createWall(e, 151, 106, 2).addToWorld();
				EntityFactory.createWall(e, 152, 106, 2).addToWorld();
				EntityFactory.createWall(e, 153, 106, 2).addToWorld();
				EntityFactory.createWall(e, 154, 106, 2).addToWorld();
				EntityFactory.createWall(e, 155, 106, 2).addToWorld();
				EntityFactory.createWall(e, 151, 146, 2).addToWorld();
				EntityFactory.createWall(e, 152, 145, 2).addToWorld();
				EntityFactory.createWall(e, 153, 144, 2).addToWorld();
				EntityFactory.createWall(e, 154, 145, 2).addToWorld();
				EntityFactory.createWall(e, 155, 146, 2).addToWorld();
				
				SoC.game.musicmanager.play(e, "battle-at-the-summit.ogg");

				init = true;
			}
		}
	}

	@Override
	public void death(Entity e) {
		SoC.game.progress.knightCaptainDefeated = true;
	}
}
