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
	
	public <T> T getDebuff(Class<T> clazz){
		for(int i = 0; i < debuffs.size(); i++){
			if(debuffs.get(i).getClass().equals(clazz)){
				return (T) debuffs.get(i);
			}
		}
		return null;
	}
	
	public void removeDebuff(Alteration alt, Entity e){
		alt.delete(e);
		debuffs.remove(alt);
		debuffClasses.remove(alt.getClass());
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
	
	public void removeDebuff(Class clazz, Entity e){
		for(int i = 0; i < debuffs.size(); i++){
			if(debuffs.get(i).getClass().equals(clazz)){
				debuffs.get(i).delete(e);
				debuffs.remove(i);
				debuffClasses.remove(clazz);
				return;
			}
		}
	}
}
