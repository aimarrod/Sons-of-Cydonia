package com.soc.screens;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.soc.core.SoC;
import com.soc.utils.GameLoader;

public class SavesScreen extends AbstractScreen{

	FileHandle[] files;
	
	public SavesScreen(SoC game) {
		super(game);
		files = GameLoader.getHandles();
	}
	@Override
	public void show()
	{
	    super.show();
	    Table table = super.getTable();
	    table.row();
	    
	    
	    for(int i = 0; i < files.length; i++){
	    	final FileHandle handle = files[i];
	    	TextButton startGameButton = new TextButton( handle.name(), getSkin() );
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
		            GameLoader.loadGame(handle);
		        }
		
		    } );
		    table.add(startGameButton);
		    table.row();
	    }
	}
	
}
