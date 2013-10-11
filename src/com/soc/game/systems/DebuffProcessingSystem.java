package com.soc.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.soc.core.SoC;
import com.soc.game.components.Debuff;
import com.soc.game.components.Position;


public class DebuffProcessingSystem extends EntityProcessingSystem{

	@Mapper ComponentMapper<Debuff> dm; 
	
	public DebuffProcessingSystem() {
		super(Aspect.getAspectForAll(Debuff.class));
	}

	@Override
	protected void process(Entity e) {
		Debuff debuff = dm.get(e);
		for(int i = 0; i < debuff.debuffs.size(); i++){
			debuff.debuffs.get(i).process(e);
		}
		if(debuff.debuffs.isEmpty()){
			e.removeComponent(debuff);
			e.changedInWorld();
		}
	}

	
	
}
