package com.soc.game.components;

import com.artemis.Component;
import com.badlogic.gdx.Input;

public class Player extends Component {
	public int move_up;
	public int move_down;
	public int move_left;
	public int move_right;
	public int attack;
	public int [] spellkeys;

	public Player(){
		move_up = Input.Keys.W;
		move_down = Input.Keys.S;
		move_left = Input.Keys.A;
		move_right = Input.Keys.D;
		attack = Input.Keys.SPACE;
		spellkeys = new int[]{Input.Keys.NUM_1, Input.Keys.NUM_2, Input.Keys.NUM_3, Input.Keys.NUM_4};
	}
}
