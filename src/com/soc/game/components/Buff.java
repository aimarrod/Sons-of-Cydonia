package com.soc.game.components;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.osc.game.benefits.Benefit;
import com.soc.core.SoC;
import com.soc.game.alterations.Alteration;

public class Buff extends Component{
	public Bag<Benefit> buffs;
	public Bag<Class <? extends Benefit>> buffClasses;
	
	public Buff(){
		buffs = new Bag<Benefit>();
		buffClasses = new Bag<Class<? extends Benefit>>();
	}
	public <T> T getBuff(Class<T> clazz){
		for(int i = 0; i < buffs.size(); i++){
			if(buffs.get(i).getClass().equals(clazz)){
				return (T) buffs.get(i);
			}
		}
		return null;
	}
	public void removebuff(Entity e,Benefit ben){
		ben.delete(e);
		buffs.remove(ben);
		buffClasses.remove(ben.getClass());
	}
	
	public static void addbuff(Entity e, Benefit benefit){
		Buff b;
		if(SoC.game.buffmapper.has(e)){
			b = SoC.game.buffmapper.get(e);
			if(b.buffClasses.contains(benefit.getClass())){
				return;
			}
		} else{
			b = new Buff();
			e.addComponent(b);
			e.changedInWorld();
		}
		b.buffs.add(benefit);
		b.buffClasses.add(benefit.getClass());
	}
	
	public void removebuff(Class<? extends Benefit> clazz,Entity e){
		for(int i = 0; i < buffs.size(); i++){
			if(buffs.get(i).getClass().equals(clazz)){
				buffs.get(i).delete(e);
				buffs.remove(i);
				buffClasses.remove(clazz);
				return;
			}
		}
	}
}
