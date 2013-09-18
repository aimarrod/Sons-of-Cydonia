package com.soc;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.soc.components.Player;
import com.soc.components.Position;
import com.soc.components.Sprite;
import com.soc.components.Velocity;
import com.soc.systems.Map;
import com.soc.systems.MovementSystem;
import com.soc.systems.PlayerInputSystem;
import com.soc.systems.SpriteRenderSystem;

public class GameSOC implements Screen {

	private OrthographicCamera camera;
	private Game game;
	private World world;
	private SpriteRenderSystem spriteRenderSystem;
	private Map map;
	
	public GameSOC(Game game) {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 900);
		this.game=game;
		world=new World();
		//True-->To call the process when we want and no when the world wants
		spriteRenderSystem=world.setSystem(new SpriteRenderSystem(camera),true);
	    world.setSystem(new PlayerInputSystem(camera));
	    world.setSystem(new MovementSystem());
		map = new Map("initial", camera);
		
		world.initialize();
		
	    Entity e = world.createEntity();
	    e.addComponent(new Position(150,150));
	    e.addComponent(new Sprite());
	    e.addComponent(new Player());
	    e.addComponent(new Velocity(0,0));
	    e.addToWorld();
		
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();
		 world.setDelta(delta);
	     world.process();
	     map.render();
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
