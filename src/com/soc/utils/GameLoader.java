package com.soc.utils;

import java.io.File;

import com.artemis.Entity;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
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
		File file = new File("saves/");
		if(!file.exists()){
		    file.mkdirs();
		}
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
	
	public static void loadGame(String file){
		Entity player = SoC.game.world.createEntity();
		
		Stats stats = new Stats();
		Position pos = new Position();
		String clazz = "";
		String map = "";
		
		JsonReader json = new JsonReader();
		JsonValue node = json.parse(Gdx.files.internal(file)).child;
		while(node != null){
			if(node.name.equals(Attributes.MAP)){
				map = node.asString();
			}
			if(node.name.equals(Attributes.POSITION)){
				JsonValue attr = node.child;
				while(attr != null){
					if(attr.name().equals("x")) pos.x = attr.asFloat();
					if(attr.name().equals("y")) pos.y = attr.asFloat();
					if(attr.name().equals("z")) pos.z = attr.asInt();
					attr = attr.next;
				}
			}
			if(node.name.equals(Attributes.STATS)){
				JsonValue stat = node.child;
				while(stat != null){
					if(stat.name().equals("health")) stats.health = stat.asInt();
					if(stat.name().equals("mana")) stats.mana = stat.asInt();
					if(stat.name().equals("experience")) stats.experience = stat.asInt();
					if(stat.name().equals("maxHealth")) stats.maxHealth = stat.asInt();
					if(stat.name().equals("maxMana")) stats.maxMana = stat.asInt();
					if(stat.name().equals("maxExperience")) stats.maxExperience = stat.asInt();
					if(stat.name().equals("strength")) stats.strength = stat.asInt();
					if(stat.name().equals("agility")) stats.agility = stat.asInt();
					if(stat.name().equals("intelligence")) stats.intelligence = stat.asInt();
					if(stat.name().equals("armor")) stats.armor = stat.asInt();
					if(stat.name().equals("level")) stats.level = stat.asInt();
					if(stat.name().equals("stats.attack")) stats.attack = stat.asInt();
					if(stat.name().equals("stats.spells")){
						JsonValue spell = stat.child;
						stats.spells = new int[stat.size];
						int i = 0;
						while(spell != null){
							stats.spells[i] = spell.asInt();
							i++;
							spell=spell.child;
						}
					}
				}
			}
			if(node.name.equals(Attributes.CLASS)) clazz = node.asString();
			node = node.next;
		}
		SoC.game.player = EntityFactory.loadCharacter(pos, stats, clazz);
		MapLoader.loadMap(map);
		SoC.game.world.getManager(GroupManager.class).add(SoC.game.player, Constants.Groups.PLAYERS);
		SoC.game.world.getManager(LevelManager.class).setLevel(SoC.game.player, Constants.Groups.LEVEL+SoC.game.positionmapper.get(SoC.game.player).z);
		SoC.game.world.getManager(GroupManager.class).add(SoC.game.player, Constants.Groups.CHARACTERS);
		SoC.game.world.getManager(TagManager.class).register(Constants.Tags.PLAYER, SoC.game.player);
		SoC.game.world.addEntity(SoC.game.player);
		SoC.game.setScreen(new GameScreen());
	}
}

