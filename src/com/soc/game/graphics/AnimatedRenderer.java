package com.soc.game.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedRenderer extends Renderer{

	public Animation animation;
	public boolean loops;
	
	public AnimatedRenderer(boolean loops){	
		time = 0;
		this.loops = loops;
	}

	@Override
	public TextureRegion frame(float delta) {
		time += delta;
		return animation.getKeyFrame(time, loops);
	}
	
	

}
