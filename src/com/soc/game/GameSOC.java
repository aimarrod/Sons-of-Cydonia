package com.soc.game;

import java.util.Stack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.soc.utils.GameManager;
import com.soc.utils.MusicPlayer;

public class GameSOC extends Game {
	public static final int FRAME_WIDTH = 1440;
	public static final int FRAME_HEIGHT = 900;
	
	public Stack<Screen> screenStack;
	
	@Override
	public void create() {
		screenStack=new Stack<Screen>();
		MusicPlayer.initialize();
		GameManager.initialize(this);
		GameManager.instance.load("initial");
		//GameManager.instance.openSplashScreen();
		//GameManager.instance.closeSplashScreen();
	}

	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = FRAME_WIDTH;
		cfg.height = FRAME_HEIGHT;
		cfg.useGL20 = true;
		cfg.title = "GameXYZ";
		new LwjglApplication(new GameSOC(), cfg);
	}

}
