package com.soc.utils;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class EffectsPlayer {

	public HashMap<String, Sound> sounds;
	public static EffectsPlayer instance;
	public float volume;
	
	private EffectsPlayer(){
		sounds = new HashMap<String, Sound>();
		volume=0.5f;
	}
	
	public static void initialize(){
		instance = new EffectsPlayer();
	}
	
	public static void load(String name){
		instance.sounds.put(name, Gdx.audio.newSound(Gdx.files.internal("resources/effects/"+name)));
	}
	
	public static void play(String name){
		if(name == null || name == "") return;
		Sound sound = instance.sounds.get(name);
		if(sound == null){
			sound = Gdx.audio.newSound(Gdx.files.internal("resources/effects/"+name));
			instance.sounds.put(name, sound);
		}
		System.out.println(instance.volume);
		sound.play(instance.volume);
	}
	
	public static Sound get(String name){
		Sound sound = instance.sounds.get(name);
		if(sound == null){
			sound = Gdx.audio.newSound(Gdx.files.internal("resources/effects/"+name));
			instance.sounds.put(name, sound);
		}
		return sound;
	}
	
	public static void stop(String name){
		if(name == null || name == "") return;
		Sound sound = instance.sounds.get(name);
		if(sound != null){
			sound.stop();
		}
	}
	
	public static void clear(){
		Iterator<Sound> sounds = instance.sounds.values().iterator();
		while(sounds.hasNext()){
			sounds.next().dispose();
		}
		instance.sounds.clear();
	}
}
