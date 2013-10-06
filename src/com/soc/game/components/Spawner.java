package com.soc.game.components;

import com.artemis.Component;
import com.soc.core.Constants;

public class Spawner extends Component{
	public String type;
	public int max, range;
	public float interval;
	public float time;
	
	public Spawner(String type, int max, int range, float interval){
		this.type = type;
		this.max = max;
		this.interval = interval;
		this.range = range;
		this.time = 0;
	}
}
