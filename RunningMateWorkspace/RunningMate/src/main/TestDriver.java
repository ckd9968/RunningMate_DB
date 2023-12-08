package main;

import boundaries.PartyRegisterFrame;
import boundaries.RecordFrame;
import controllers.RecordingController;
import controllers.partyRegisterController;


public class TestDriver {

	public static void main(String[] args) {
		new PartyRegisterFrame(new partyRegisterController());
	}

}
