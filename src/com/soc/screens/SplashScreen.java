package com.soc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.game.GameSOC;

public class SplashScreen extends AbstractScreen{
	private Texture splashTexture;
	private TextureRegion splashTextureRegion;
	public SplashScreen(GameSOC game) {
		super(game);
	}
	
	public void show(){
		super.show();
		splashTexture=new Texture("resources/splashscreen.png");
		//linear texture filter to improve the stretching
        splashTexture.setFilter( TextureFilter.Linear, TextureFilter.Linear );
        //The first two values the position, the other two the size of the texture
        splashTextureRegion = new TextureRegion( splashTexture, 0, 0, 300, 225 );
	}
	public void render(float delta){
		super.render(delta);
		batch.begin();
		batch.draw(splashTextureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
	}
	public void dispose(){
		super.dispose();
		splashTexture.dispose();
	}

}
