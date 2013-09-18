package com.gamexyz;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.gamexyz.components.Position;
import com.gamexyz.components.Sprite;
import com.gamexyz.systems.SpriteRenderSystem;

public class GameXYZ implements Screen {

	private OrthographicCamera camera;
	private Game game;
	private World world;
	private SpriteRenderSystem spriteRenderSystem;
	public GameXYZ(Game game) {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 900);
		this.game=game;
		world=new World();
		spriteRenderSystem=world.setSystem(new SpriteRenderSystem(camera),true);
		
		world.initialize();
		
	     Entity e = world.createEntity();
	     e.addComponent(new Position(150,150));
	     e.addComponent(new Sprite());
	     e.addToWorld();
		
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();
		 world.setDelta(delta);
	     world.process();
	     spriteRenderSystem.process();
	}

	@Override
	public void resize(int width, int height) {
		System.out.println("Resize");

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
