package com.soc.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Polygon;

public class Bounds extends Component{
	public int width, height;
	
	public Bounds(int width, int height){
		this.width = width;
		this.height = height;
	}
}
