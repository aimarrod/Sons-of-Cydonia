package com.soc.game.components;

import com.artemis.Component;

public class Delay extends Component{
	public Delay(String group, float delay, float expiration, int attack) {
		this.delay = delay;
		this.attack = attack;
		this.expiration = expiration;
		this.attacked = false;
		this.group = group;
	}
	public float delay;
	public float expiration;
	public boolean attacked;
	public int attack;
	public String group;
	
}
