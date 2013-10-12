package com.soc.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.soc.game.components.Buff;
import com.soc.game.components.Debuff;

public class BuffProcessingSystem extends EntityProcessingSystem{
	
	@Mapper ComponentMapper<Buff> bm; 
	@SuppressWarnings("unchecked")
	public BuffProcessingSystem() {
		super(Aspect.getAspectForAll(Buff.class));
	}
	@Override
	protected void process(Entity e) {
		Buff buff = bm.get(e);
		for(int i = 0; i < buff.buffs.size(); i++){
			buff.buffs.get(i).process(e);
		}
		if(buff.buffs.isEmpty()){
			e.removeComponent(buff);
			e.changedInWorld();
		}	
	}
}
