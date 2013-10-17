package com.soc.game.map;

public class Dialog {
	public String text;
	public boolean popped;
	
	private Dialog(String text){
		this.text = text;
		popped = false;
	}
	
	static Dialog[] dialogs = new Dialog[]{
		new Dialog("CONTROLS 1:\n\nSPACE:  normal attack\nNUMBER KEYS:  skills\nB: Shield"),
		new Dialog("CONTROLS 2:\n\n"
				+ "ESC:  menu\n"
				+ "C:  character stats\n"
				+ "I:  inventory\n\n"
				+ "INVENTORY\n\n"
				+ "ENTER: use object\n"
				+ "TAB: Switch object\n"
				+ "Or use the mouse"),
		new Dialog("Tip:\n\n Double tap a movement key to run"),
		new Dialog("Tip:\n\n Save your game ofter, you never know what may lie ahead!")
	};
}
