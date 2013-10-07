package com.soc.game.graphics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StaticRenderer extends Renderer{

		public TextureRegion sprite;
		
		public StaticRenderer(){	
		}

		@Override
		public TextureRegion frame(float delta) {
			return sprite;
		}
}
