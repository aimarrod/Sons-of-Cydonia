package com.soc.utils;

import java.util.HashMap;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicPlayer {
	private static final String BASE_DIR = "resources/music/";
	
	public static MusicPlayer instance;
	public float volume;
	public HashMap<String, Music> loaded;
	public Stack<Music> music;
	public Music current;
	
	private MusicPlayer(){
		this.music = new Stack<Music>();
		this.loaded = new HashMap<String, Music>();
		this.volume = 0.5f;
	}
	
	public static void initialize(){
		instance = new MusicPlayer();
	}
	
	public static void pause(){
		instance.current.pause();
	}
	
	public static void resume(){
		instance.current.setLooping(true);
		instance.current.play();
		instance.current.setVolume(instance.volume);
	}
	
	public static void resumePrevious(){
		instance.current.stop();
		instance.current = instance.music.pop();
		instance.current.play();
	}
	
	public static boolean isPlaying(String name){
		Music m = instance.loaded.get(name);
		return m==instance.current;
	}
	
	public static void dispose(){
		if(instance.current == null) return;
		instance.current.dispose();
		instance.current = instance.music.pop();
		if(instance.current != null){
			instance.current.setLooping(true);
			instance.current.play();
			instance.current.setVolume(instance.volume);
		}
	}
	
	public static void load(String name){
		instance.loaded.put(name, Gdx.audio.newMusic(Gdx.files.internal(BASE_DIR + name)));
	}
	
	public static void play(String name){
		Music m = instance.loaded.get(name);
		if(m == null){
			m = Gdx.audio.newMusic(Gdx.files.internal(BASE_DIR + name));
			instance.loaded.put(name, m);
		}
		if(instance.current != null){
			instance.music.push(instance.current);
			instance.current.stop();
		}
		instance.current = m;
		instance.current.setLooping(true);
		instance.current.play();
		instance.current.setVolume(instance.volume);
	}
	
	public static void reset(){
		if(instance.current != null)
			instance.current.dispose();
		while(!instance.music.isEmpty()){
			instance.music.pop().dispose();
		}
		instance.loaded.clear();
	}
	
    public static void setVolume(float volume){
    	instance.volume = volume;
    	instance.current.setVolume(volume);
    }
}
