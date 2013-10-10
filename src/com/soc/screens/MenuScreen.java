package com.soc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.soc.core.SoC;
import com.soc.utils.GameLoader;

public class MenuScreen extends AbstractScreen implements InputProcessor{
	private Texture background;
	private Image hand;
	private int focusedBotton;
	private Texture handT;
	private TextButton startGameButton;
	private TextButton loadGameButton;
	private TextButton optionsButton;
	private TextButton exitButton;
	private TextButton []buttons;
	private TextButtonStyle normalStyle;
	private TextButtonStyle focusedStyle;
	public MenuScreen(SoC game) {
		super(game);
		background=new Texture(Gdx.files.internal("resources/background.jpg"));
		hand=new Image(new Texture(Gdx.files.internal("resources/hand.png")));
		handT=new Texture(Gdx.files.internal("resources/hand.png"));
		normalStyle=new TextButtonStyle();
		normalStyle.font=getSkin().getFont("gameFont");
		normalStyle.up=getSkin().getDrawable("normal-button");
		normalStyle.down=getSkin().getDrawable("pushed-button");
		focusedStyle=new TextButtonStyle();
		focusedStyle.font=getSkin().getFont("gameFont");
		focusedStyle.up=getSkin().getDrawable("focused-button");
		focusedStyle.down=getSkin().getDrawable("pushed-button");
		startGameButton = new TextButton( "Start game", normalStyle);
		loadGameButton = new TextButton( "Load Game", normalStyle);
		optionsButton = new TextButton( "Options", normalStyle);
		exitButton = new TextButton( "Exit", normalStyle );
		buttons=new TextButton[4];
		buttons[0]=startGameButton;
		buttons[1]=loadGameButton;
		buttons[2]=optionsButton;
		buttons[3]=exitButton;
		focusedBotton=1;
	}
    @Override
    public void show()
    {
        super.show();
        // retrieve the default table actor
        Table table = super.getTable();
        // register the button "start game"
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
            	if (button==0){
                GameLoader.newGame("warrior");
                startGameButton.removeListener(this);
            	}
            }

        } );
        
        startGameButton.addListener(new ClickListener(){
        	public boolean mouseMoved(InputEvent event,
                    float x,
                    float y){
        		if(focusedBotton!=1){
        			buttons[focusedBotton-1].setStyle(normalStyle);
        		}
        		focusedBotton=1;
        		return true;
        		
        	}

        });
        table.add( startGameButton ).size( 300, 60 ).uniform().spaceBottom( 10 );
        table.row();

        // register the button "options"
        loadGameButton.addListener( new InputListener() {
            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
            	if (button==0){
            		
            	}
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
        		if(focusedBotton!=2){
        			buttons[focusedBotton-1].setStyle(normalStyle);
        		}
        		focusedBotton=2;
        		return true;
        		
        	}

        });
        table.add( loadGameButton ).uniform().fill().spaceBottom( 10 );
        table.row();

        // register the button "high scores"
        optionsButton.addListener( new InputListener() {
            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
            	if (button==0){
            		
            	}
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
            {
                System.out.println("touchdown");
                return true;
            }
        } );
        
        optionsButton.addListener(new ClickListener(){
        	public boolean mouseMoved(InputEvent event,
                    float x,
                    float y){
        		if(focusedBotton!=3){
        			buttons[focusedBotton-1].setStyle(normalStyle);
        		}
        		focusedBotton=3;
        		return true;
        		
        	}

        });
        table.add( optionsButton ).uniform().fill().spaceBottom(10);
        table.row();
        // register the button "high scores"
        exitButton.addListener( new InputListener() {
            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
            	if (button==0){
            		
            	}
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
            {
                SoC.game.dispose();
                return true;
            }
        } );
        
        exitButton.addListener(new ClickListener(){
        	public boolean mouseMoved(InputEvent event,
                    float x,
                    float y){
        		if(focusedBotton!=4){
        			buttons[focusedBotton-1].setStyle(normalStyle);
        		}
        		focusedBotton=4;
        		return true;
        		
        	}

        });
        table.add( exitButton ).uniform().fill();
    }
    
	public void render(float delta) {
	       Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
	        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
	        batch.begin();
	        batch.draw(background, 0, 0, Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height);
	        batch.draw(handT, buttons[focusedBotton-1].getX()-20, buttons[focusedBotton-1].getY()+13, 0,0,20,20);
	        buttons[focusedBotton-1].setStyle(focusedStyle);
	        //Update delta and draw the actors inside the stage
	        batch.end();
	        stage.act( delta );
	        stage.draw();
		
	}
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		if(Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)){
			buttons[focusedBotton-1].setStyle(normalStyle);
			if(focusedBotton==1)
				focusedBotton=4;
			else
				focusedBotton--;
			return true;
		}else{
			if(Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)){
				buttons[focusedBotton-1].setStyle(normalStyle);
				if(focusedBotton==4)
					focusedBotton=1;
				else
					focusedBotton++;
				return true;
			}
		}		
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
