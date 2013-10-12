package com.soc.game.map;

public class Dialog {
	public String text;
	public boolean popped;
	
	private Dialog(String text){
		this.text = text;
		popped = false;
	}
	
	static Dialog[] dialogs = new Dialog[]{
		new Dialog("CONTROLS 1:\n\nSPACE:  normal attack\nNUMBER KEYS:  skills"),
		new Dialog("CONTROLS 2:\n\nESC:  menu\nC:  character stats\nI:  inventory")
	};
}
