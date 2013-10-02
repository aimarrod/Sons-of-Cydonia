package com.soc.game.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DirectionalStaticRenderer extends DirectionalRenderer {
	public TextureRegion[] sprites;
	
	public DirectionalStaticRenderer(){
		sprites = new TextureRegion[4];
	}
	
	@Override
	public TextureRegion frame(float delta) {
		return sprites[direction];
	}
}
