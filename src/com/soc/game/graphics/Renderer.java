package com.soc.game.graphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Renderer {

	public int direction = 0;
	public float ox = 0;
	public float oy = 0;
	public float r = 1;
	public float g = 1;
	public float b = 1;
	public float a = 1;
	public float scaleX = 1;
	public float scaleY = 1;
	public float rotation;
	public float time;
	
	public abstract TextureRegion frame(float delta);
}
