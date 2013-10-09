package com.soc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.soc.core.SoC;
import com.soc.utils.GameLoader;

public class MenuScreen extends AbstractScreen{

	public MenuScreen(SoC game) {
		super(game);
	}
    @Override
    public void show()
    {
        super.show();
        // retrieve the default table actor
        Table table = super.getTable();
        table.add( "Welcome to Tyrian for Android!" ).spaceBottom( 50 );
        table.row();

        // register the button "start game"
        TextButton startGameButton = new TextButton( "Start game", getSkin() );
        startGameButton.addListener( new InputListener() {
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
                GameLoader.newGame("warrior");
            }

        } );
        table.add( startGameButton ).size( 300, 60 ).uniform().spaceBottom( 10 );
        table.row();

        // register the button "options"
        TextButton loadGameButton = new TextButton( "Load Game", getSkin() );
        loadGameButton.addListener( new InputListener() {
            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                super.touchUp( event, x, y, pointer, button );
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
            {
                System.out.println("touchdown");
                return true;
            }
        } );
        table.add( loadGameButton ).uniform().fill().spaceBottom( 10 );
        table.row();

        // register the button "high scores"
        TextButton optionsButton = new TextButton( "Options", getSkin() );
        optionsButton.addListener( new InputListener() {
            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                super.touchUp( event, x, y, pointer, button );
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
            {
                System.out.println("touchdown");
                return true;
            }
        } );
        table.add( optionsButton ).uniform().fill().spaceBottom(10);
        table.row();
        // register the button "high scores"
        TextButton exitButton = new TextButton( "Options", getSkin() );
        exitButton.addListener( new InputListener() {
            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                super.touchUp( event, x, y, pointer, button );
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
            {
                SoC.game.dispose();
                return true;
            }
        } );
        table.add( exitButton ).uniform().fill();
    }
}
