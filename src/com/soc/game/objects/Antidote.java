package com.soc.game.objects;

import com.artemis.utils.Bag;
import com.soc.core.SoC;
import com.soc.game.components.Debuff;
import com.soc.game.states.alterations.Alteration;

public class Antidote extends Item {
	public Bag<Class<? extends Alteration>> debuffs;
	
	public Antidote(int num,String name, String iconPath, String tooltip, Class<? extends Alteration>... debuffs) {
		super(name, iconPath, tooltip,num);
		this.debuffs = new Bag<Class<? extends Alteration>>();
		for(Class<? extends Alteration> clazz: debuffs){
			this.debuffs.add(clazz);
		}

	}

	@Override
	public void use() {
		if(SoC.game.debuffmapper.has(SoC.game.player)){
			Debuff d = SoC.game.debuffmapper.get(SoC.game.player);
			for(int i = 0; i < debuffs.size(); i++){
				d.removeDebuff(debuffs.get(i), SoC.game.player);
			}
		}
	}
}
