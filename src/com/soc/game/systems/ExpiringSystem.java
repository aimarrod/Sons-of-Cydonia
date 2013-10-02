package com.soc.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.soc.game.components.Expires;
import com.soc.game.components.Player;
import com.soc.game.components.State;
import com.soc.utils.GameManager;

public class ExpiringSystem extends EntityProcessingSystem{
	 @Mapper ComponentMapper<Expires> em;
	 @Mapper ComponentMapper<Player> pm;
	 
     @SuppressWarnings("unchecked")
     public ExpiringSystem() {
             super(Aspect.getAspectForAll(Expires.class));
     }
     protected void process(Entity e) {	 
         Expires exp = em.get(e);
         exp.delay -= world.delta;
         if (exp.delay <= 0) {
            e.deleteFromWorld();
            if(pm.has(e)){
    			GameManager.instance.openGameOverScren();
             }
         }

     }
}
