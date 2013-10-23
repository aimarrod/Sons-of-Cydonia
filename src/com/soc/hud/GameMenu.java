package com.soc.hud;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.soc.core.SoC;
import com.soc.screens.OptionsScreen;
import com.soc.screens.SaveScreen;

public class GameMenu extends Table implements InputProcessor {
	private Table table;
	private TextButton resumeGameButton;
	private TextButton saveGameButton;
	private TextButton controlsButton;
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
		normalStyle.font=skin.getFont("buttonFont");
		normalStyle.up=skin.getDrawable("normal-button");
		normalStyle.down=skin.getDrawable("pushed-button");
		focusedStyle=new TextButtonStyle();
		focusedStyle.font=skin.getFont("buttonFont");
		focusedStyle.up=skin.getDrawable("focused-button");
		focusedStyle.down=skin.getDrawable("pushed-button");
		resumeGameButton = new TextButton( "Resume Game", focusedStyle);
		saveGameButton = new TextButton( "Save Game", normalStyle);
		optionsButton = new TextButton( "Options", normalStyle);
		exitButton = new TextButton( "Exit", normalStyle );
		controlsButton=new TextButton("Controls",normalStyle);
		buttons=new TextButton[5];
		buttons[0]=resumeGameButton;
		buttons[1]=saveGameButton;
		buttons[2]=controlsButton;
		buttons[3]=optionsButton;
		buttons[4]=exitButton;
		focusedButton=1;
	    table.add( resumeGameButton ).size( 300, 60 ).uniform().spaceBottom( 10 );
	    table.row();
	    table.add( saveGameButton ).uniform().fill().spaceBottom( 10 );
	    table.row();
	    table.add( controlsButton ).uniform().fill().spaceBottom(10);
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
            	SoC.game.hudSystem.hideCharacterGameMenu();
				SoC.game.archiveProcessors();
				SoC.game.setScreen(new SaveScreen(SoC.game));
			}else if(focusedButton==3){
				SoC.game.world.getSystem(HudSystem.class).popInstructions();
			}else if(focusedButton==4){
				SoC.game.screens.push(SoC.game.getScreen());
            	SoC.game.hudSystem.hideCharacterGameMenu();
				SoC.game.archiveProcessors();
				SoC.game.setScreen(new OptionsScreen(SoC.game,false));
			}else if(focusedButton==5){
            	SoC.game.player.deleteFromWorld();
            	SoC.game.resetWorld();
            	SoC.game.hudSystem.hideCharacterGameMenu();
            	SoC.game.clearProcessors();
            	SoC.game.openMenuScreen();
			}
			return true;
		}
		if(keycode==Input.Keys.UP){
			buttons[focusedButton-1].setStyle(normalStyle);
			if(focusedButton==1)
				focusedButton=5;
			else
				focusedButton--;
			buttons[focusedButton-1].setStyle(focusedStyle);
			return true;
		}else{
			if(keycode==Input.Keys.DOWN){
				buttons[focusedButton-1].setStyle(normalStyle);
				if(focusedButton==5)
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
		if(getX()-150<screenX && getX()+150>screenX && (getY()+75)<height-screenY && (getY()+135)>height-screenY){
			SoC.game.world.getSystem(HudSystem.class).toogleGameMenu();
			return true;
		}else if(getX()-150<screenX && getX()+150>screenX && (getY()+5)<height-screenY && (getY()+65)>height-screenY){
			SoC.game.screens.push(SoC.game.getScreen());
        	SoC.game.hudSystem.hideCharacterGameMenu();
			SoC.game.archiveProcessors();
			SoC.game.setScreen(new SaveScreen(SoC.game));
			return true;
		}else if(getX()-150<screenX && getX()+150>screenX && (getY()-5)>height-screenY && (getY()-65)<height-screenY){
			SoC.game.world.getSystem(HudSystem.class).popInstructions();
			return true;
		}else if(getX()-150<screenX && getX()+150>screenX && (getY()-75)>height-screenY && (getY()-135)<height-screenY){
			SoC.game.screens.push(SoC.game.getScreen());
        	SoC.game.hudSystem.hideCharacterGameMenu();
			SoC.game.archiveProcessors();
			SoC.game.setScreen(new OptionsScreen(SoC.game,false));
			return true;
		}else if(getX()-150<screenX && getX()+150>screenX && (getY()-145)>height-screenY && (getY()-200)<height-screenY){
        	SoC.game.player.deleteFromWorld();
        	SoC.game.resetWorld();
        	SoC.game.hudSystem.hideCharacterGameMenu();
        	SoC.game.clearProcessors();
        	SoC.game.openMenuScreen();
			return true;
		}
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
		}else if(getX()-150<screenX && getX()+150>screenX && (getY()-145)>height-screenY && (getY()-200)<height-screenY){
			buttons[focusedButton-1].setStyle(normalStyle);
			focusedButton=5;
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
