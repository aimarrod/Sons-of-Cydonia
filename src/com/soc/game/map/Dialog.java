package com.soc.game.map;

public class Dialog {
	public String text;
	public boolean popped;
	
	private Dialog(String text){
		this.text = text;
		popped = false;
	}
	
	static Dialog[] dialogs = new Dialog[]{
		new Dialog("\nCONTROLS 1:\n\n"
				+ "SPACE: attack\n"
				+ "NUM KEYS:  skills\n"
				+ "I Inventory \n\n" 
				+ "INVENTORY\n\n"
				+ "ENTER: use object\n"
				+ "TAB: Switch object\n"
				+ "E: Use object"),
		new Dialog("\nCONTROLS 2:\n\n"
				+ "ESC:  menu\n"
				+ "C:  character stats\n"
				+ "SHIFT LEFT: Shield\n"),
		new Dialog("\nTip:\n\n"
				+ "Double tap a movement key to run"),
		new Dialog("\nTip:\n\n"
				+ "Save your game ofter, you never know what may lie ahead!")
	};
}
