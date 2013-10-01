package com.soc.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.soc.game.components.State;

public class AttackDelaySystem extends EntityProcessingSystem{

	 @Mapper ComponentMapper<State> es;
     
     @SuppressWarnings("unchecked")
     public AttackDelaySystem() {
             super(Aspect.getAspectForAll(State.class));
     }
     protected void process(Entity e) {
         State state = es.get(e);
         state.statetime -= world.getDelta();
         if (state.statetime <=0) {
           state.state=State.IDLE;
           //Aunque deberia ser el delay del ataque no?
           state.statetime=1000;
           //EntityFactory.createAttack(....);
         }
     }

}
