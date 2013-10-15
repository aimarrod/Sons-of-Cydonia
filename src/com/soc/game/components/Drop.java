package com.soc.game.components;

import com.artemis.Component;
import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soc.core.SoC;

public class Drop extends Component{
	public int item;
	public TextureRegion tex;
	
	public Drop(int item){
		this.item = item;
		tex = SoC.game.items[item].icon;
	}
	
	public void draw(Entity e, SpriteBatch batch){
		Position pos = SoC.game.positionmapper.get(e);
		Bounds bon = SoC.game.boundsmapper.get(e);

		batch.draw(tex, pos.x-bon.width*0.5f, pos.y-bon.height*0.5f, bon.width, bon.height);
	}
}
