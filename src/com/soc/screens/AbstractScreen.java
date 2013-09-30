package com.soc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.soc.game.GameSOC;

public class AbstractScreen implements Screen{
	protected final GameSOC game;
	protected final SpriteBatch batch;
	public AbstractScreen(GameSOC game){
		this.game=game;
		this.batch=new SpriteBatch();
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor( 5f, 100f, 0f, 1f );
	    Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
	}

}
