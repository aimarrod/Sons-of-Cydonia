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
	
	public void pause(){
		current.pause();
	}
	
	public void resume(){
		current.setLooping(true);
		current.play();
	}
	
	public void dispose(){
		current.dispose();
		current = music.pop();
		if(current != null){
			current.setLooping(true);
			current.play();
		}
	}
	
	public void play(String file){
		Music m = Gdx.audio.newMusic(Gdx.files.internal(BASE_DIR + file));
		music.push(current);
		current = m;
		current.setLooping(true);
		current.play();
	}
	
	public void reset(){
		if(current != null)
			current.dispose();
		while(!music.isEmpty()){
			music.pop().dispose();
		}
	}
}
