package com.soc.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DirectionalStaticRenderer extends DirectionalRenderer {
	public Sprite[] sprite;
	
	@Override
	public TextureRegion frame(float delta) {
		return sprite[direction];
	}
}
