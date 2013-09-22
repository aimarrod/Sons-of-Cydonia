package com.soc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.components.AnimatedComponent;

public class AnimationLoader {

	public static void loadHumanoidSpriteSheet(Texture sheet, AnimatedComponent comp, float duration){
		int hframes = sheet.getWidth()/FRAME_SIZE_HORIZONTAL;
		int vframes = sheet.getHeight()/FRAME_SIZE_VERTICAL;
		
		comp.animations = new Animation[vframes];

	   	TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/hframes, sheet.getHeight() / vframes);
	   	for(int i = 0; i < tmp.length; i++){
	   		comp.animations[i] = new Animation(duration/hframes, tmp[i]);
	   	}
	}
	
	
	final static int FRAME_SIZE_VERTICAL = 64;
	final static int FRAME_SIZE_HORIZONTAL = 64;
}
