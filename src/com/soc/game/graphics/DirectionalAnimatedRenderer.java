package com.soc.game.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DirectionalAnimatedRenderer extends DirectionalRenderer{
	public Animation[] animations;
	public boolean loops;
	
	public DirectionalAnimatedRenderer(boolean loops){
		this.loops = loops;
	}
	
	@Override
	public TextureRegion frame(float delta) {
		time += delta;
		return animations[direction].getKeyFrame(time, loops);
	}
	
	public void reset(){
		time = 0;
	}
	
	public boolean isFinished(){
		return animations[direction].isAnimationFinished(time);
	}
}
