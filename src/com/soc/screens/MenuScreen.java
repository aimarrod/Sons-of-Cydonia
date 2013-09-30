package com.soc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.soc.game.GameSOC;

public class MenuScreen extends AbstractScreen{

	public MenuScreen(GameSOC game) {
		super(game);
	}
	// setup the dimensions of the menu buttons
    private static final float BUTTON_WIDTH = 300f;
    private static final float BUTTON_HEIGHT = 60f;
    private static final float BUTTON_SPACING = 10f;
    @Override
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
    }
}
