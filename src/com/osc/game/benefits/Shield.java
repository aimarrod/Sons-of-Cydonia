package com.osc.game.benefits;

import com.artemis.Entity;
import com.artemis.utils.ImmutableBag;
import com.soc.core.Constants;
import com.soc.core.SoC;
import com.soc.game.components.Bounds;
import com.soc.game.components.Position;
import com.soc.game.graphics.AnimatedRenderer;
import com.soc.game.graphics.DirectionalAnimatedRenderer;
import com.soc.utils.GraphicsLoader;

public class Shield implements Benefit{
		public float timer;
		public DirectionalAnimatedRenderer renderer; 
		
		public Shield(){
			timer=Constants.Buff.SHIELD_CHARGE_TIME;
			renderer=GraphicsLoader.loadShield();
		}
		@Override
		public void process(Entity e) {
			timer -= SoC.game.world.delta;
			if(timer <= 0){
				ImmutableBag<Entity> proj = SoC.game.groupmanager.getEntities(Constants.Groups.DESTROYABLE_PROJECTILES);
				Position pos = SoC.game.positionmapper.get(e);
				Bounds bon = SoC.game.boundsmapper.get(e);
				
				float posx = pos.x;
				float posy = pos.y;
				
				if(pos.direction.x != 0) posx +=  bon.width*(pos.direction.x);
				else posy += bon.height*(pos.direction.y);
				
				for(int i = 0; i < proj.size(); i++){
					Entity ent = proj.get(i);
					
					Position pos2 = SoC.game.positionmapper.get(ent);
					Bounds bon2 = SoC.game.boundsmapper.get(ent);
					
					if(posx < pos2.x + bon2.width && posx + bon.width > pos2.x && posy < pos2.y + bon2.height && posy + bon.height > pos2.y){
						ent.deleteFromWorld();
					}
				}
			}
		}
}
