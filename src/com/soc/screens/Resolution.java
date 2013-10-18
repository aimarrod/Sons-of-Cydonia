package com.soc.screens;

public class Resolution implements Comparable{
	int width,height;
	
	public Resolution(int width, int height){
		this.width=width;
		this.height=height;
	}
	@Override
	public int compareTo(Object o) {
		Resolution res=(Resolution)o; 
		if(this.height>res.height)
			return 1;
		else
			if(this.height<res.height)
				return -1;
			else
				if(this.width>this.width)
					return 1;
				else
					if(this.width<this.width)
						return -1;
					else
						return 0;
	}
	public String toString(){
		return this.width+"x"+this.height;
	}

}
