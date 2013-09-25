package com.soc.utils;

import java.util.Stack;
import java.util.jar.Attributes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.soc.game.GameSOC;
import com.soc.game.GameScreen;

public class GameManager {
	
	public GameSOC game;
	public static GameManager instance;
	
	private GameManager(GameSOC game){
		this.game = game;
	}
	
	public static void initialize(GameSOC game){
		instance = new GameManager(game);
	}

	public void load(String level){
		String map = "";
		String music = "";
		JsonReader json = new JsonReader();
		JsonValue node = json.parse(Gdx.files.internal(Constants.Configuration.LEVEL_DIR + level + ".json")).child;
		while(node != null){
			System.out.println(node.name);
			if(node.name.equals(Attributes.MAP)){
				map = node.asString();
				System.out.println(map);
			}
			if(node.name.equals(Attributes.MUSIC)){
				music = node.asString();
			}
			node = node.next;
		}
		MusicPlayer.instance.reset();
		MusicPlayer.instance.play(music);
		game.setScreen(new GameScreen(game, map));
	}
	
	public void openMenu(){
		game.screenStack.add(game.getScreen());
		//TODO: Open Menu
	}
	
	public void closeMenu(){
		Screen menu = game.getScreen();
		game.setScreen(game.screenStack.pop());
		menu.dispose();
	}
	
	
	public class Attributes{
		final static String MAP = "map"; 
		final static String MUSIC = "music";
	}
}
