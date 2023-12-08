package main;

import boundaries.PartyRegisterFrame;
import boundaries.RecordFrame;
import controllers.PartyRegisterController;
import controllers.RecordingController;


public class TestDriver {

	public static void main(String[] args) {
		new PartyRegisterFrame(new PartyRegisterController(),"MEM00001");
//		new RecordFrame(new RecordingController(), "MEM00004");
	}

}
