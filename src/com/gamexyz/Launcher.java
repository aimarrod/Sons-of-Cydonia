package com.gamexyz;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Launcher extends Game {
	public static final int FRAME_WIDTH = 1440;
	public static final int FRAME_HEIGHT = 900;

	@Override
	public void create() {
		setScreen(new GameXYZ(this));

	}

	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = FRAME_WIDTH;
		cfg.height = FRAME_HEIGHT;
		cfg.useGL20 = true;
		cfg.title = "GameXYZ";
		new LwjglApplication(new Launcher(), cfg);
	}

}
