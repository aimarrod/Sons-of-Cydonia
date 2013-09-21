package com.soc.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Polygon;

public class Bounds extends Component{
	public Polygon bounds;
	
	public Bounds(float[] vertex){
		bounds = new Polygon(vertex);
	}
}
