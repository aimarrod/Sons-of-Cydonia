package com.soc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.soc.core.SoC;
import com.soc.utils.GameLoader;
import com.soc.utils.MusicManager;
import com.soc.utils.MusicPlayer;


public class OptionsScreen extends AbstractScreen implements InputProcessor{
	private Texture background;
	private	Slider slider;
	private Label labelMusic;
	private Label labelResolution;
	private int width=1440;
	private int height=900;
	private TextButton returnButton;
	private TextButtonStyle normalStyle;
	private TextButtonStyle focusedStyle;
	public OptionsScreen(SoC game) {
		super(game);
		this.background=new Texture(Gdx.files.internal("resources/background.jpg"));
		this.slider=new Slider(0,10,1,false,getSkin());
		this.slider.setX(200);
		this.slider.setY(height-150);
		this.slider.setWidth(390);
		this.slider.setValue(MusicPlayer.instance.volume*10);
		this.labelMusic=new Label("music configuration",skin);
		this.labelMusic.setX(200);
		this.labelMusic.setY(height-100);
		this.labelResolution=new Label("resolution",skin);
		this.labelResolution.setX(200);
		this.labelResolution.setY(height-300);
		normalStyle=new TextButtonStyle();
		normalStyle.font=getSkin().getFont("buttonFont");
		normalStyle.up=getSkin().getDrawable("normal-button");
		normalStyle.down=getSkin().getDrawable("pushed-button");
		focusedStyle=new TextButtonStyle();
		focusedStyle.font=getSkin().getFont("buttonFont");
		focusedStyle.up=getSkin().getDrawable("focused-button");
		focusedStyle.down=getSkin().getDrawable("pushed-button");
		returnButton = new TextButton( "Return", focusedStyle);
		returnButton.setX(width-300);
		returnButton.setY(0);
		returnButton.setWidth(200);
		stage.addActor(slider);
		stage.addActor(labelMusic);
		stage.addActor(labelResolution);
		stage.addActor(returnButton);
		SoC.game.inputMultiplexer.addProcessor(this);
	}
	
	public void show(){
		 super.show();
		 slider.addListener( new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				MusicPlayer.instance.volume=slider.getValue()/10;
			}
	        } );
		 
	        returnButton.addListener( new InputListener() {
	            @Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
	            {
	                return true;
	            }
	            @Override
	            public void touchUp(
	                InputEvent event,
	                float x,
	                float y,
	                int pointer,
	                int button )
	            {
	            	if(button==0){
						SoC.game.clearProcessors();
						SoC.game.setScreen(new MenuScreen(game));
	            	}

	            }

	        } );
	}
	
	public void resize(int width, int height) {
		super.resize(width, height);
		this.width=width;
		this.height=height;
	}
	@Override
	public boolean keyDown(int keycode) {
		if(keycode==Keys.ENTER){
			SoC.game.clearProcessors();
			SoC.game.setScreen(new MenuScreen(game));
		}
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void render(float delta) {
	       Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
	        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
	        batch.begin();
	        batch.draw(background, 0, 0, Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height);
	        //Update delta and draw the actors inside the stage
	        batch.end();
	        stage.act( delta );
	        stage.draw();
		
	}

}
