package com.soc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.soc.core.SoC;

public class AbstractScreen implements Screen{
	protected final SoC game;
	protected final Stage stage;
	protected SpriteBatch batch;
	protected Skin skin;
	protected Table table;
	public AbstractScreen(SoC game){
		this.game=game;
		this.batch=new SpriteBatch();
		this.stage=new Stage(0,0,true);
        Gdx.input.setInputProcessor(stage);
	}
	protected Skin getSkin()
	{
	        if( skin == null ) {
	            skin = new Skin(  Gdx.files.internal( "resources/uiskin.json" ) );
	        }
	        return skin;
	    }
    protected Table getTable()
    {
        if( table == null ) {
            table = new Table( getSkin() );
            table.setFillParent( true );
            stage.addActor( table );
        }
        return table;
    }
    public SpriteBatch getBatch()
    {
        if( batch == null ) {
            batch = new SpriteBatch();
        }
        return batch;
    }
	@Override
	public void render(float delta) {
	       Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
	        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
	        batch.begin();
	        batch.end();
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
		stage.dispose();
        if( batch != null ) batch.dispose();
        if( skin != null ) skin.dispose();
	}

}
