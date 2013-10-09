package com.soc.core;

import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.soc.game.graphics.Renderer;
import com.soc.game.map.Map;
import com.soc.game.systems.CameraSystem;
import com.soc.game.systems.RenderSystem;
import com.soc.hud.HudSystem;
import com.soc.utils.LevelManager;
import com.soc.utils.MapLoader;

public class GameScreen implements Screen {

	private FPSLogger fps;
	
	public GameScreen() {
		fps=new FPSLogger();
		Gdx.input.setInputProcessor(SoC.game.inputMultiplexer);
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		SoC.game.world.setDelta(delta);
	    SoC.game.world.process();
	     
	    SoC.game.cameraSystem.process();
	    SoC.game.renderSystem.process();
	    SoC.game.hudSystem.process();
	    fps.log();

	}

	@Override
	public void resize(int width, int height) {
		SoC.game.hudSystem.setViewport(width, height);
		SoC.game.camera.setToOrtho(false, width, height);
	}

	@Override
	public void show() {
		System.out.println("Show");
	}

	@Override
	public void hide() {
		System.out.println("Hide");
	}

	@Override
	public void pause() {
		System.out.println("Pause");
	}

	@Override
	public void resume() {
		System.out.println("Resume");
	}

	@Override
	public void dispose() {
		System.out.println("Dispose");
	}

}
