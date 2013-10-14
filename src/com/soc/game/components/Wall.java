package com.soc.game.components;

import com.artemis.Component;
import com.artemis.Entity;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.soc.core.SoC;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.utils.GraphicsLoader;

public class Wall extends Component{
	public AnimatedRenderer animation;
	
	public Wall(){
		animation = GraphicsLoader.loadWall();
	}
	
	public void draw(Entity e, SpriteBatch batch){
		Position pos = SoC.game.positionmapper.get(e);
		batch.draw(animation.frame(SoC.game.world.delta), pos.x+animation.ox, pos.y+animation.oy);
	}
}
