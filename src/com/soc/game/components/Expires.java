package com.soc.game.components;

import com.artemis.Component;

public class Expires extends Component{
	public float delay;
	public boolean isExpiring;
	public Expires(float delay,boolean isExp){
		this.delay=delay;
		this.isExpiring=isExp;
	}
}
