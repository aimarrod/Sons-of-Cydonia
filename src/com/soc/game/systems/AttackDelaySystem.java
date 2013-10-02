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

public class AttackDelaySystem extends EntityProcessingSystem{

	 @Mapper ComponentMapper<State> sm;
	 @Mapper ComponentMapper<Delay> dm;
	 @Mapper ComponentMapper<Stats> stm;
	 @Mapper ComponentMapper<Position> pm;
	 
     @SuppressWarnings("unchecked")
     public AttackDelaySystem() {
             super(Aspect.getAspectForAll(Delay.class, State.class, Position.class, Stats.class));
     }
     protected void process(Entity e) {
         Delay del = dm.get(e);
         del.delay -= world.delta;
         del.expiration -= world.delta;
         if(del.delay <= 0 && !del.attacked){
        	 SoC.game.spells[del.attack].create(del.group, pm.get(e), stm.get(e));
        	 del.attacked = true;
        	 return;
         }
         if(del.expiration <= 0){
        	 e.removeComponent(del);
        	 e.changedInWorld();
        	 sm.get(e).state = State.IDLE;
         }
     }

}
