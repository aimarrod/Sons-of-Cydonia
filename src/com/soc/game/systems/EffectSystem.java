package com.soc.game.systems;

import java.util.HashMap;
import java.util.Map;

import com.artemis.Aspect;
import com.artemis.ComponentManager;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.audio.Sound;
import com.soc.core.SoC;
import com.soc.game.components.Attack;
import com.soc.game.components.Position;
import com.soc.utils.EffectsPlayer;

public class EffectSystem extends EntityProcessingSystem{

	public Map<Entity, Sound> effects;
	public Map<Entity, Long> ids;
	@Mapper ComponentMapper<Position> pm;
	
	@SuppressWarnings("unchecked")
	public EffectSystem(){
		super(Aspect.getAspectForAll(Attack.class));
		effects = new HashMap<Entity, Sound>();
		ids = new HashMap<Entity, Long>();
	}
	

	@Override
	protected void process(Entity e) {
		if(effects.containsKey(e)){
			if(SoC.game.cameraSystem.isVisible(pm.get(e))){
				Sound s = effects.remove(e);
				if(s != null){
					s.stop(ids.remove(e));
				}
			}
		}
	}

	
	public void addSound(Entity e, String sound){
		if(!effects.containsKey(e)){
			Sound s = EffectsPlayer.get(sound);
			effects.put(e, s);
			long id = s.play();
			s.setLooping(id, true);
			ids.put(e, id);
		}
	}
	
	public void stopSound(Entity e){
		Sound s = effects.remove(e);
		if(s != null){
			s.stop(ids.remove(e));
		}
	}
	
	@Override
	public void removed(Entity e){
		Sound s = effects.remove(e);
		if(s != null){
			s.stop(ids.remove(e));
		}
	}



}
