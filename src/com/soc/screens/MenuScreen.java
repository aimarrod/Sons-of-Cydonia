package com.soc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.soc.game.GameSOC;
import com.soc.utils.GameManager;

public class MenuScreen extends AbstractScreen{

	public MenuScreen(GameSOC game) {
		super(game);
	}
    /*@Override
    public void resize(
        int width,
        int height )
    {
        super.resize( width, height );
        final float buttonX = ( width - BUTTON_WIDTH ) / 2;
        float currentY = 280f;
 
        // label "welcome"
        Label welcomeLabel = new Label( "Welcome to Tyrian for Android!", new Skin(Gdx.files.internal("resources/uiskin.json")));
        welcomeLabel.setX( ( width - welcomeLabel.getWidth() ) / 2 );
        welcomeLabel.setY( currentY + 100 );
        stage.addActor( welcomeLabel );
 
        // button "start game"
        TextButton startGameButton = new TextButton( "Start game", new Skin(Gdx.files.internal("resources/uiskin.json")));
        startGameButton.setX(buttonX);
        startGameButton.setY(currentY);
        startGameButton.setWidth( BUTTON_WIDTH);
        startGameButton.setHeight( BUTTON_HEIGHT);
        stage.addActor( startGameButton );
 
        // button "options"
        TextButton optionsButton = new TextButton( "Options",  new Skin(Gdx.files.internal("resources/uiskin.json")) );
        optionsButton.setX(buttonX);
        optionsButton.setY( currentY -= BUTTON_HEIGHT + BUTTON_SPACING );
        optionsButton.setWidth(BUTTON_WIDTH);
        optionsButton.setHeight( BUTTON_HEIGHT);
        stage.addActor( optionsButton );
 
        // button "hall of fame"
        TextButton hallOfFameButton = new TextButton( "Hall of Fame",  new Skin(Gdx.files.internal("resources/uiskin.json")) );
        hallOfFameButton.setX(buttonX); 
        hallOfFameButton.setY( currentY -= BUTTON_HEIGHT + BUTTON_SPACING );
        hallOfFameButton.setWidth( BUTTON_WIDTH);
        hallOfFameButton.setHeight( BUTTON_HEIGHT);
        stage.addActor( hallOfFameButton );
    }*/
    
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
                System.out.println("touchdown");
                return true;
            }
            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {System.out.println("adasdas");
                GameManager.instance.load("initial");
            }

        } );
        table.add( startGameButton ).size( 300, 60 ).uniform().spaceBottom( 10 );
        table.row();

        // register the button "options"
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
        table.add( optionsButton ).uniform().fill().spaceBottom( 10 );
        table.row();

        // register the button "high scores"
        TextButton highScoresButton = new TextButton( "High Scores", getSkin() );
        highScoresButton.addListener( new InputListener() {
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
        table.add( highScoresButton ).uniform().fill();
    }
}
