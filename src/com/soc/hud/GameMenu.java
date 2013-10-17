package com.soc.hud;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.soc.core.SoC;
import com.soc.screens.GameOverScreen;
import com.soc.screens.LoadScreen;
import com.soc.screens.MenuScreen;
import com.soc.screens.SaveScreen;
import com.soc.utils.GameLoader;

public class GameMenu extends Table implements InputProcessor {
	private Table table;
	private TextButton resumeGameButton;
	private TextButton saveGameButton;
	private TextButton optionsButton;
	private TextButton exitButton;
	private TextButton [] buttons;
	private Skin skin;
	private int focusedButton;
	private TextButtonStyle normalStyle;
	private TextButtonStyle focusedStyle;
	private int height;
	
	public GameMenu(HudSystem parent){
		super(parent.skin);
		table=this;
		this.skin = parent.skin;
		this.height=900;
		this.focusedButton=1;
		normalStyle=new TextButtonStyle();
		normalStyle.font=skin.getFont("gameFont");
		normalStyle.up=skin.getDrawable("normal-button");
		normalStyle.down=skin.getDrawable("pushed-button");
		focusedStyle=new TextButtonStyle();
		focusedStyle.font=skin.getFont("gameFont");
		focusedStyle.up=skin.getDrawable("focused-button");
		focusedStyle.down=skin.getDrawable("pushed-button");
		resumeGameButton = new TextButton( "Resume Game", focusedStyle);
		saveGameButton = new TextButton( "Save Game", normalStyle);
		optionsButton = new TextButton( "Options", normalStyle);
		exitButton = new TextButton( "Exit", normalStyle );
		buttons=new TextButton[4];
		buttons[0]=resumeGameButton;
		buttons[1]=saveGameButton;
		buttons[2]=optionsButton;
		buttons[3]=exitButton;
		focusedButton=1;
	    table.add( resumeGameButton ).size( 300, 60 ).uniform().spaceBottom( 10 );
	    table.row();
	    table.add( saveGameButton ).uniform().fill().spaceBottom( 10 );
	    table.row();
	    table.add( optionsButton ).uniform().fill().spaceBottom(10);
	    table.row();
	    table.add( exitButton ).uniform().fill();
	}
	
	public void setViewport(int height){
		this.height=height;
	}
	@Override
	public boolean keyDown(int keycode) {
		if(keycode==SoC.game.playermapper.get(SoC.game.player).gameMenu){
			SoC.game.world.getSystem(HudSystem.class).toogleGameMenu();
			return true;
		}
		if(keycode == Input.Keys.ENTER){
			if(focusedButton==1){
				SoC.game.world.getSystem(HudSystem.class).toogleGameMenu();
			}else if(focusedButton==2){
				SoC.game.screens.push(SoC.game.getScreen());
				SoC.game.archiveProcessors();
				SoC.game.setScreen(new SaveScreen(SoC.game));
			}else if(focusedButton==3){
				//Options
			}else if(focusedButton==4){
            	SoC.game.player.deleteFromWorld();
            	SoC.game.resetWorld();
            	SoC.game.openMenuScreen();
			}	
			return true;
		}
		if(keycode==Input.Keys.UP ||keycode==Input.Keys.W){
			buttons[focusedButton-1].setStyle(normalStyle);
			if(focusedButton==1)
				focusedButton=4;
			else
				focusedButton--;
			buttons[focusedButton-1].setStyle(focusedStyle);
			return true;
		}else{
			if(keycode==Input.Keys.S || keycode==Input.Keys.DOWN){
				buttons[focusedButton-1].setStyle(normalStyle);
				if(focusedButton==4)
					focusedButton=1;
				else
					focusedButton++;
				buttons[focusedButton-1].setStyle(focusedStyle);
				return true;
			}
		}	
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
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
		if(getX()-150<screenX && getX()+150>screenX && (getY()+75)<height-screenY && (getY()+135)>height-screenY){
			buttons[focusedButton-1].setStyle(normalStyle);
			focusedButton=1;
			buttons[focusedButton-1].setStyle(focusedStyle);
			return true;
		}else if(getX()-150<screenX && getX()+150>screenX && (getY()+5)<height-screenY && (getY()+65)>height-screenY){
			buttons[focusedButton-1].setStyle(normalStyle);
			focusedButton=2;
			buttons[focusedButton-1].setStyle(focusedStyle);
			return true;
		}else if(getX()-150<screenX && getX()+150>screenX && (getY()-5)>height-screenY && (getY()-65)<height-screenY){
			buttons[focusedButton-1].setStyle(normalStyle);
			focusedButton=3;
			buttons[focusedButton-1].setStyle(focusedStyle);
			return true;
		}else if(getX()-150<screenX && getX()+150>screenX && (getY()-75)>height-screenY && (getY()-135)<height-screenY){
			buttons[focusedButton-1].setStyle(normalStyle);
			focusedButton=4;
			buttons[focusedButton-1].setStyle(focusedStyle);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
