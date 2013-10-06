package com.soc.hud;

import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.Bag;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.soc.core.SoC;

public class HudSystem extends VoidEntitySystem{

	OrthographicCamera camera;
	Stage stage;
	StatusBar statusBar;
	ActionBar actionBar;
	Inventory inventory;
	
	public HudSystem(OrthographicCamera camera){
		this.camera = camera;
		this.stage = new Stage();
		this.statusBar = new StatusBar();
		this.actionBar = new ActionBar();
		this.inventory=new Inventory();
		stage.addActor(statusBar);
		stage.addActor(actionBar);
		//stage.addActor(inventory);
	}

	
	@Override
	protected void processSystem() {
		stage.act(world.delta);
		stage.draw();
	}
	
	public void setViewport(int width, int height){
		stage.setViewport(width, height, true);
		statusBar.setPosition(10, height - 65);
		inventory.updateRes(width, height);
	}
	public void toggleInventory(){
		boolean isHidden=inventory.hasParent();
		if(!isHidden){
			stage.addActor(inventory);
			SoC.game.inputMultiplexer.addProcessor(inventory);
		}else{
			inventory.remove();
			SoC.game.inputMultiplexer.removeProcessor(inventory);
		}
	}
	

	
}
