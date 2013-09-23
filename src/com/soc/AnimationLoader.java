package com.soc;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.components.AnimatedComponent;

public class AnimationLoader {

	public static void loadCharacterSpriteSheet(Texture sheet, AnimatedComponent comp, float duration, int hsize, int vsize){
		int hframes = sheet.getWidth()/hsize;
		int vframes = sheet.getHeight()/vsize;
		
		comp.animations = new Animation[vframes];
		comp.ox = -(hsize-Constants.Characters.WIDTH_PIXELS)/2;
		comp.oy = -(vsize-Constants.Characters.HEIGHT_PIXELS)/2;


	   	TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/hframes, sheet.getHeight() / vframes);
	   	for(int i = 0; i < tmp.length; i++){
	   		comp.animations[i] = new Animation(duration/hframes, tmp[i]);
	   	}
	}
	
	public static void loadProjectileSpriteSheet(Texture sheet, AnimatedComponent comp, float duration, int hsize, int vsize){
		int hframes = sheet.getWidth()/hsize;
		int vframes = sheet.getHeight()/vsize;
		
		comp.animations = new Animation[vframes];

	   	TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/hframes, sheet.getHeight() / vframes);
	   	for(int i = 0; i < tmp.length; i++){
	   		comp.animations[i] = new Animation(duration/hframes, tmp[i]);
	   	}
	}
}
