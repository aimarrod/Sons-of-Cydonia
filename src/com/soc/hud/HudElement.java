package com.soc.hud;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface HudElement {
	public void render(OrthographicCamera camera, SpriteBatch batch);
}
