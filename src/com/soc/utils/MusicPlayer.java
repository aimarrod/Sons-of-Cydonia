package com.soc.utils;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicPlayer {
	private static final String BASE_DIR = "resources/music/";
	
	public static MusicPlayer instance;
	public Stack<Music> music;
	public Music current;
	
	private MusicPlayer(){
		this.music = new Stack<Music>();
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
		instance.current.setVolume(0.5f);
	}
	
	public static void dispose(){
		if(instance.current == null) return;
		instance.current.dispose();
		instance.current = instance.music.pop();
		if(instance.current != null){
			instance.current.setLooping(true);
			instance.current.play();
			instance.current.setVolume(0.5f);
		}
	}
	
	public static void play(String file){
		Music m = Gdx.audio.newMusic(Gdx.files.internal(BASE_DIR + file));
		if(instance.current != null) instance.music.push(instance.current);
		instance.current = m;
		instance.current.setLooping(true);
		instance.current.play();
		instance.current.setVolume(0.5f);
	}
	
	public static void reset(){
		if(instance.current != null)
			instance.current.dispose();
		while(!instance.music.isEmpty()){
			instance.music.pop().dispose();
		}
	}
}
