package com.soc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.soc.core.SoC;
import com.soc.utils.GameLoader;

public class MenuScreen extends AbstractScreen{
	private Texture background;
	private Image hand;
	private int focusedBotton;
	private Texture handT;
	TextButton startGameButton;
	public MenuScreen(SoC game) {
		super(game);
		background=new Texture(Gdx.files.internal("resources/background.jpg"));
		hand=new Image(new Texture(Gdx.files.internal("resources/hand.png")));
		handT=new Texture(Gdx.files.internal("resources/hand.png"));
		focusedBotton=1;
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
        startGameButton = new TextButton( "Start game", getSkin() );
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
        
        startGameButton.addListener(new ClickListener(){
        	public boolean mouseMoved(InputEvent event,
                    float x,
                    float y){
//        		System.out.println("X: "+x);
//        		System.out.println("Y:" +y);
//        		System.out.println("Boton x:"+startGameButton.getX());
//        		System.out.println("Boton y:"+startGameButton.getY());
//        		System.out.println("Height: "+startGameButton.getHeight());
//        		System.out.println("Width: "+startGameButton.getWidth());
//        		if(x>startGameButton.getX() && x<startGameButton.getX()+startGameButton.getWidth() && y<startGameButton.getY()+startGameButton.getHeight() &&y>startGameButton.getX())
//					System.out.println("paso");	
//        			return true;
        		focusedBotton=1;
        		return true;
        		
        	}

        });
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
        
        loadGameButton.addListener(new ClickListener(){
        	public boolean mouseMoved(InputEvent event,
                    float x,
                    float y){
//        		System.out.println("X: "+x);
//        		System.out.println("Y:" +y);
//        		System.out.println("Boton x:"+startGameButton.getX());
//        		System.out.println("Boton y:"+startGameButton.getY());
//        		System.out.println("Height: "+startGameButton.getHeight());
//        		System.out.println("Width: "+startGameButton.getWidth());
//        		if(x>startGameButton.getX() && x<startGameButton.getX()+startGameButton.getWidth() && y<startGameButton.getY()+startGameButton.getHeight() &&y>startGameButton.getX())
//					System.out.println("paso");	
//        			return true;
        		focusedBotton=2;
        		return true;
        		
        	}

        });
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
    
	public void render(float delta) {
	       Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
	        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
	        batch.begin();
	        batch.draw(background, 0, 0, Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height);
	        if(focusedBotton==1)
	        	batch.draw(handT, startGameButton.getX()-20, startGameButton.getY(), 0,0,20,20);
	        //Update delta and draw the actors inside the stage
	        batch.end();
	        stage.act( delta );
	        stage.draw();
		
	}
}
