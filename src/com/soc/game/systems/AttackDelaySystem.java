package com.soc.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.soc.core.SoC;
import com.soc.game.components.Delay;
import com.soc.game.components.Position;
import com.soc.game.components.State;
import com.soc.game.components.Stats;
import com.soc.game.components.Character;
import com.soc.utils.EffectsPlayer;

public class AttackDelaySystem extends EntityProcessingSystem{

	 @Mapper ComponentMapper<State> sm;
	 @Mapper ComponentMapper<Delay> dm;
	 @Mapper ComponentMapper<Stats> stm;
	 @Mapper ComponentMapper<Position> pm;
	 @Mapper ComponentMapper<Character> cm;

	 
     @SuppressWarnings("unchecked")
     public AttackDelaySystem() {
             super(Aspect.getAspectForAll(Delay.class, State.class, Position.class, Stats.class));
     }
     protected void process(Entity e) {
         Delay del = dm.get(e);
         del.delay -= world.delta;
         del.expiration -= world.delta;
         if(del.delay <= 0 && !del.attacked){
        	 SoC.game.spells[del.attack].create(e ,del.group, pm.get(e), stm.get(e));
        	 del.attacked = true;
        	 return;
         }
         if(del.expiration <= 0){
        	 e.removeComponent(del);
        	 e.changedInWorld();
        	 State s = sm.get(e);
        	 cm.get(e).renderers[s.state].time = 0;
        	 s.state = State.IDLE;
         }
     }

}
