package com.soc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.soc.game.GameSOC;

public class AbstractScreen implements Screen{
	protected final GameSOC game;
	protected final SpriteBatch batch;
	protected final Stage stage;
	public AbstractScreen(GameSOC game){
		this.game=game;
		this.batch=new SpriteBatch();
		this.stage=new Stage(0,0,true);
	}
	@Override
	public void render(float delta) {
	       Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
	        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
	        //Update delta and draw the actors inside the stage
	        stage.act( delta );
	        stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport( width, height, true );
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
		
	}

}
