package com.soc.ai.bosses;

import com.artemis.Entity;
import com.soc.ai.AI;
import com.soc.ai.modules.AIModule;
import com.soc.ai.modules.BasicFollowing;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.core.Constants.World;
import com.soc.game.components.Buff;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.states.benefits.Casting;
import com.soc.utils.MusicPlayer;

public class BlackMageAI extends AI{

	public boolean init;
	public float timer;
	public float castTimer;
	
	public BlackMageAI(){
		modules = new AIModule[1];
		modules[0] = new BasicFollowing(0, true, false, true);
		init = false;
	}
	
	@Override
	public void process(Entity e) {
		State state = SoC.game.statemapper.get(e);
		if(state.state == State.DYING || state.state == State.FALLING ){
			if(SoC.game.buffmapper.has(e)){
				SoC.game.buffmapper.get(e).removebuff(Casting.class, e);;
			}
			return;
		}
		if(init){
			if(state.state == State.SPINNING){
				processModules(e);
			} else if( state.state == State.ATTACK){
				castTimer -= SoC.game.world.delta;
				if(castTimer <= 0){
					Entity spell = EntityFactory.createParadigmShift(e);
					SoC.game.groupmanager.add(spell, Constants.Groups.ENEMY_ATTACKS);
					spell.addToWorld();
					state.state = State.SPINNING;
				}
			} else {
				timer -= SoC.game.world.delta;
				if(timer <= 0){
					state.state = State.ATTACK;
					Buff.addbuff(e, new Casting(2f, Constants.BuffColors.DARK));
					castTimer = 2f;
					timer = 0.5f;
				}
			}
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
		SoC.game.progress.blackMageDefeated = true;
		
		Position pos = SoC.game.positionmapper.get(e);
		EntityFactory.createItem(Constants.Items.MIX_ULTRAPOTION, pos.x, pos.y, pos.z).addToWorld();
		EntityFactory.createItem(Constants.Items.ANTIBURN, pos.x, pos.y, pos.z).addToWorld();
		EntityFactory.createItem(Constants.Items.GOLD_HELM, pos.x, pos.y, pos.z).addToWorld();		
	}

}
