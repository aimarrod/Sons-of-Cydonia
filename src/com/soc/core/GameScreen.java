package com.soc.core;

import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.soc.game.systems.AttackRenderSystem;
import com.soc.game.systems.CameraSystem;
import com.soc.game.systems.CharacterRenderSystem;
import com.soc.game.systems.MapCollisionSystem;
import com.soc.game.systems.MapRenderSystem;
import com.soc.hud.HudSystem;
import com.soc.utils.MapLoader;

public class GameScreen implements Screen {

	private OrthographicCamera camera;
	private FPSLogger fps;
	
	public GameScreen(String mapName) {
		camera = SoC.game.camera;
		fps=new FPSLogger();
		
		TiledMap map = MapLoader.loadMap(mapName);
		SoC.game.world.getSystem(MapRenderSystem.class).changeMap(map);
		SoC.game.world.getSystem(MapCollisionSystem.class).loadTiles(map);
				
		camera.setToOrtho(false, 1280, 900);
		
		SoC.game.world.getManager(GroupManager.class).add(SoC.game.player, Constants.Groups.PLAYERS);
		SoC.game.world.getManager(TagManager.class).register(Constants.Tags.PLAYER, SoC.game.player);
		SoC.game.world.addEntity(SoC.game.player);
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		SoC.game.world.setDelta(delta);
	    SoC.game.world.process();
	     
	    SoC.game.cameraSystem.process();
	    SoC.game.mapRenderSystem.process();
	    SoC.game.characterRenderSystem.process();
	    SoC.game.attackRenderSystem.process();
	    SoC.game.hudSystem.process();

	}

	@Override
	public void resize(int width, int height) {
		SoC.game.hudSystem.setViewport(width, height);
		camera.setToOrtho(false, width, height);
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