package com.soc.utils;

import java.io.IOException;

import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.GameProgress;
import com.soc.core.GameScreen;
import com.soc.core.SoC;
import com.soc.game.components.Player;

public class GameLoader {
	
	public static void initialize(){
		FileHandle file = Gdx.files.external("saves");
		if(!file.exists()){
		    file.mkdirs();
		}
	}
	
	public static FileHandle[] getHandles(){
		FileHandle[] handles = new FileHandle[3]; 
		FileHandle[] list = Gdx.files.external("saves").list(".json");
		for(int i = 0; i < handles.length; i++){
			if(i < list.length){
				handles[i] = list[i];
			} else {
				handles[i] = null;
			}
		}
		return handles;
	}
	
	public static void saveGame(int slotNum) throws IOException{
		
		Json json = new Json();
		FileHandle handle = Gdx.files.external("saves/save_"+slotNum+".json");
		if(handle.exists()){
			handle.file().delete();
		} 
		handle.file().createNewFile();
		
		SavedGame save = new SavedGame();
		save.map = SoC.game.map.name;
		save.position = SoC.game.positionmapper.get(SoC.game.player);
		save.stats = SoC.game.statsmapper.get(SoC.game.player);
		save.player=new SavedPlayer(SoC.game.playermapper.get(SoC.game.player));	
		save.progress=SoC.game.progress;
		handle.writeString(json.toJson(save), false);
	}
	
	public static void newGame(String clazz){
		SoC.game.player = EntityFactory.createCharacter(2300, 600, 0, 0, 0, Constants.Classes.WARRIOR);
		SoC.game.setScreen(new GameScreen());
		SoC.game.progress = new GameProgress();
		MapLoader.loadMap("finalMap.tmx");


		SoC.game.world.getManager(GroupManager.class).add(SoC.game.player, Constants.Groups.PLAYERS);
		SoC.game.world.getManager(LevelManager.class).setLevel(SoC.game.player, Constants.Groups.LEVEL+SoC.game.positionmapper.get(SoC.game.player).z);
		SoC.game.world.getManager(GroupManager.class).add(SoC.game.player, Constants.Groups.CHARACTERS);
		SoC.game.world.getManager(TagManager.class).register(Constants.Tags.PLAYER, SoC.game.player);
		SoC.game.world.addEntity(SoC.game.player);
		
	}
	
	public static void loadGame(FileHandle file){
				
		Json json = new Json();
		System.out.println("FIle: "+file);
		SavedGame save = json.fromJson(SavedGame.class, file);

		
		SoC.game.progress = save.progress;
		if(SoC.game.progress==null){
			SoC.game.progress=new GameProgress();
		}
		SoC.game.player = EntityFactory.loadCharacter(save.position, save.stats, Constants.Characters.WARRIOR, new Player(save.player));
		SoC.game.setScreen(new GameScreen());
		MapLoader.loadMap(save.map);
		SoC.game.world.getManager(GroupManager.class).add(SoC.game.player, Constants.Groups.PLAYERS);
		SoC.game.world.getManager(LevelManager.class).setLevel(SoC.game.player, Constants.Groups.LEVEL+SoC.game.positionmapper.get(SoC.game.player).z);
		SoC.game.world.getManager(GroupManager.class).add(SoC.game.player, Constants.Groups.CHARACTERS);
		SoC.game.world.getManager(TagManager.class).register(Constants.Tags.PLAYER, SoC.game.player);
		SoC.game.world.addEntity(SoC.game.player);
	}

}

