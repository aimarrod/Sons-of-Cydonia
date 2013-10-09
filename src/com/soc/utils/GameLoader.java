package com.soc.utils;

import java.io.File;
import java.io.IOException;

import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.soc.core.Constants;
import com.soc.core.EntityFactory;
import com.soc.core.GameScreen;
import com.soc.core.Constants.Attributes;
import com.soc.core.SoC;
import com.soc.game.components.Position;
import com.soc.game.components.Stats;

public class GameLoader {
	
	public static void initialize(){
		FileHandle file = Gdx.files.external("saves");
		if(!file.exists()){
		    file.mkdirs();
		}
	}
	
	public static FileHandle[] getHandles(){
		return Gdx.files.external("saves").list();
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
		
		handle.writeString(json.toJson(save), false);
	}
	
	public static void newGame(String clazz){
		SoC.game.player = EntityFactory.createCharacter(2300, 650, 0, 0, 0, Constants.Classes.WARRIOR);
		MapLoader.loadMap("starting.tmx");
		SoC.game.world.getManager(GroupManager.class).add(SoC.game.player, Constants.Groups.PLAYERS);
		SoC.game.world.getManager(LevelManager.class).setLevel(SoC.game.player, Constants.Groups.LEVEL+SoC.game.positionmapper.get(SoC.game.player).z);
		SoC.game.world.getManager(GroupManager.class).add(SoC.game.player, Constants.Groups.CHARACTERS);
		SoC.game.world.getManager(TagManager.class).register(Constants.Tags.PLAYER, SoC.game.player);
		SoC.game.world.addEntity(SoC.game.player);
		SoC.game.setScreen(new GameScreen());
		
	}
	
	public static void loadGame(FileHandle file){
				
		Json json = new Json();
		SavedGame save = json.fromJson(SavedGame.class, file);

		
		
		SoC.game.player = EntityFactory.loadCharacter(save.position, save.stats, Constants.Characters.WARRIOR);
		MapLoader.loadMap(save.map);
		SoC.game.world.getManager(GroupManager.class).add(SoC.game.player, Constants.Groups.PLAYERS);
		SoC.game.world.getManager(LevelManager.class).setLevel(SoC.game.player, Constants.Groups.LEVEL+SoC.game.positionmapper.get(SoC.game.player).z);
		SoC.game.world.getManager(GroupManager.class).add(SoC.game.player, Constants.Groups.CHARACTERS);
		SoC.game.world.getManager(TagManager.class).register(Constants.Tags.PLAYER, SoC.game.player);
		SoC.game.world.addEntity(SoC.game.player);
		SoC.game.setScreen(new GameScreen());
	}
}

