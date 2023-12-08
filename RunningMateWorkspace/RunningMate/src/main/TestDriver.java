package main;

import boundaries.PartyRegisterFrame;
import controllers.PartyRegisterController;


public class TestDriver {

	public static void main(String[] args) {
		new PartyRegisterFrame(new PartyRegisterController(),"MEM00001");
	}

}
