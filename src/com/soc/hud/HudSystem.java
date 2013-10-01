package com.soc.hud;

import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class HudSystem extends VoidEntitySystem{

	OrthographicCamera camera;
	Stage stage;
	StatusBar statusBar;
	ActionBar actionBar;
	
	public HudSystem(OrthographicCamera camera){
		this.camera = camera;
		this.stage = new Stage();
		this.statusBar = new StatusBar();
		this.actionBar = new ActionBar();
		stage.addActor(statusBar);
		stage.addActor(actionBar);
	}

	
	@Override
	protected void processSystem() {
		stage.act(world.delta);
		stage.draw();
	}
	
	public void setViewport(int width, int height){
		stage.setViewport(width, height, true);
		statusBar.setPosition(10, height - 65);
	}

	
}
