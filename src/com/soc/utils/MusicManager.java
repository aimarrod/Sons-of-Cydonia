package com.soc.utils;

import java.util.HashMap;
import java.util.Map;

import com.artemis.Entity;
import com.artemis.Manager;
import com.artemis.utils.Bag;
import com.soc.core.SoC;
import com.soc.game.components.Spawner;

public class MusicManager extends Manager{
	public Map<String, Bag<Entity>> bossesByMusic;
    private Map<Entity, String> musicByBoss;

	@Override
	protected void initialize() {
		bossesByMusic = new HashMap<String, Bag<Entity>>();
		musicByBoss = new HashMap<Entity, String>();
	}
	
    public void play(Entity boss, String music) {
    	Bag<Entity> bosses = bossesByMusic.get(music);
    	if(bosses == null){
    		bosses = new Bag<Entity>();
    		bossesByMusic.put(music, bosses);
    	}
    	bosses.add(boss);
    	musicByBoss.put(boss, music);
    	if(!MusicPlayer.isPlaying(music)){
    		MusicPlayer.play(music);
    	}
    }
    
    @Override
    public void deleted(Entity e) {
    	String music = musicByBoss.get(e);
    	if(music != null){
    		Bag<Entity> bosses = bossesByMusic.get(music);
    		bosses.remove(e);
    		if(bosses.isEmpty()){
    			MusicPlayer.resumePrevious();
    		}
    	}
    }
    
    public void clear(){
    	bossesByMusic.clear();
    	musicByBoss.clear();
    }
}
