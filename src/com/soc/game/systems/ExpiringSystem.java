package com.soc.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.soc.core.SoC;
import com.soc.game.components.Enemy;
import com.soc.game.components.Expires;
import com.soc.game.components.Player;

public class ExpiringSystem extends EntityProcessingSystem{
	 @Mapper ComponentMapper<Expires> em;
	 @Mapper ComponentMapper<Player> pm;
	 @Mapper ComponentMapper<Enemy> enm;

	 
     @SuppressWarnings("unchecked")
     public ExpiringSystem() {
             super(Aspect.getAspectForAll(Expires.class));
     }
     protected void process(Entity e) {	 
         Expires exp = em.get(e);
         exp.delay -= world.delta;
         if (exp.delay <= 0) {
            if(pm.has(e)){
            	e.deleteFromWorld();
            	SoC.game.resetWorld();
            	SoC.game.hudSystem.hideCharacterGameMenu();
            	SoC.game.openGameOverScren();
            } else {
            	 if(enm.has(e)) enm.get(e).processor.death(e);
                 e.deleteFromWorld();
             }
         }

     }
}
