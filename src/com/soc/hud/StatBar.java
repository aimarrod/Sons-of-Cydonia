package com.soc.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StatBar implements HudElement{
	public float posx;
	public float posy;
	public TextureRegion container;
	public TextureRegion healthbar;
	public TextureRegion manabar;
	public TextureRegion expbar;
	
	public StatBar(){
		Texture tex = new Texture(Gdx.files.internal("resources/bars.png"));
		container = new TextureRegion(new Texture(Gdx.files.internal("resources/bar-container.png")));
		TextureRegion[][] tmp = TextureRegion.split(tex, tex.getWidth(), 8);
		healthbar = tmp[0][0];
		manabar = tmp[1][0];
		expbar = tmp[2][0];
	}

	@Override
	public void render(OrthographicCamera camera, SpriteBatch batch) {
		posx = camera.position.x - camera.viewportWidth*0.5f + 10;
		posy = camera.position.y + camera.viewportHeight*0.5f - 10 - container.getRegionHeight();
		batch.draw(container, posx, posy);
		batch.draw(healthbar, posx+73, posy+48);
		batch.draw(manabar, posx+73, posy+28);
		batch.draw(expbar, posx+73, posy+8);
	}
	
	
	
}
