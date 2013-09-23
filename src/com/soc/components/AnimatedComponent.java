package com.soc.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Animation;

public class AnimatedComponent extends Component{

	public Animation[] animations;
	public float ox = 0;
	public float oy = 0;
	public float r = 1;
	public float g = 1;
	public float b = 1;
	public float a = 1;
	public float scaleX = 1;
	public float scaleY = 1;
	public float rotation;
	public float time;
}
