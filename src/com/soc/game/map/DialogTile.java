package com.soc.game.map;

import com.soc.core.Constants;

public class DialogTile extends Tile{
	public Dialog dialog;

	public DialogTile(int id) {
		super(Constants.World.TILE_DIALOG);
		this.dialog = Dialog.dialogs[id];
	}
	
}
