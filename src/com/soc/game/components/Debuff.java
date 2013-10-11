package com.soc.game.components;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.soc.core.SoC;
import com.soc.game.alterations.Alteration;

public class Debuff extends Component{
	public Bag<Alteration> debuffs;
	public Bag<Class> debuffClasses;
	
	public Debuff(){
		debuffs = new Bag<Alteration>();
		debuffClasses = new Bag<Class>();
	}
	
	public void removeDebuff(Alteration alt){
		debuffs.remove(alt);
	}
	
	public static void addDebuff(Entity e, Alteration alteration){
		Debuff d;
		if(SoC.game.debuffmapper.has(e)){
			d = SoC.game.debuffmapper.get(e);
			if(d.debuffClasses.contains(alteration.getClass())){
				return;
			}
		}
		else{
			d = new Debuff();
			e.addComponent(d);
			e.changedInWorld();
		}
		d.debuffs.add(alteration);
		d.debuffClasses.add(alteration.getClass());
	}
}
