package com.soc.hud;

import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hud extends VoidEntitySystem{

	OrthographicCamera camera;
	SpriteBatch batch;
	Bag<HudElement> elements;
	
	public Hud(OrthographicCamera camera){
		this.camera = camera;
		this.batch = new SpriteBatch();
		this.elements = new Bag<HudElement>();
		elements.add(new StatBar());
	}

	@Override
	protected void begin() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	}
	
	@Override
	protected void processSystem() {
		for(int i = 0; i < elements.size(); i++)
			elements.get(i).render(camera, batch);
	}
	
	@Override
	protected void end() {
		batch.end();
	}
	
}
