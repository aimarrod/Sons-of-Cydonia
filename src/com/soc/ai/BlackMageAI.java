package com.soc.ai;

import com.artemis.Entity;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.SoC;
import com.soc.game.components.State;

public class BlackMageAI extends AI{

	public boolean init;
	public float timer;
	public float castTimer;
	
	public BlackMageAI(){
		modules = new AIModule[1];
		modules[0] = new BasicPathfinding(0, true, false, true);
		init = true;
	}
	
	@Override
	public void process(Entity e) {
		State state = SoC.game.statemapper.get(e);
		if(state.state == State.DYING || state.state == State.FALLING ) return;
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
					castTimer = 0.5f;
					timer = 1f;
				}
			}
		}
	}

	@Override
	public void death(Entity e) {
		// TODO Auto-generated method stub
		
	}

}
