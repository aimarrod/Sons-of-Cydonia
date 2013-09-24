package com.soc.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AttackRenderer implements Renderer{

	public Animation animation;
	public float time;
	public int damage;
	
	public AttackRenderer(){	
		time = 0;
	}

	@Override
	public TextureRegion frame(float delta) {
		time += delta;
		return animation.getKeyFrame(time, true);
	}
	
	

}
