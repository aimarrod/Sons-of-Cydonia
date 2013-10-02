package com.soc.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;

import com.soc.core.SoC;

public class SplashScreen extends AbstractScreen{
	private Texture splashTexture;
	public SplashScreen(SoC game) {
		super(game);
	}
	
	public void show(){
		super.show();
		splashTexture=new Texture("resources/splashscreen.png");
		//linear texture filter to improve the stretching
        splashTexture.setFilter( TextureFilter.Linear, TextureFilter.Linear );
	}
	public void  resize(int width, int height){
		super.resize(width, height);
		stage.clear();
		TextureRegion splashRegion = new TextureRegion( splashTexture, 0, 0, 300, 225 );
	     // here we create the splash image actor and set its size
        Image splashImage = new Image( splashRegion);
        splashImage.setWidth(width); 
        splashImage.setHeight(height);
        splashImage.setScaling(Scaling.stretch);
        //To fade, completely transparent
        splashImage.getColor().a = 0f;
        splashImage.addAction( sequence( fadeIn( 0.75f ), delay( 1.75f ), fadeOut( 0.75f ),
                new Action() {
                    @Override
                    public boolean act(float delta )
                    {
                        game.setScreen( new MenuScreen( game ) );
                        return true;
                    }
                } ) );
            // add the actor to the stage
            stage.addActor( splashImage );
	}
	public void dispose(){
		super.dispose();
		splashTexture.dispose();
	}

}
