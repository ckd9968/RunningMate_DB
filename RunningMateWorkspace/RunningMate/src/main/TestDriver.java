package main;

import boundaries.LoginFrame;
import boundaries.PartyRegisterFrame;
import boundaries.RecordFrame;
import controllers.LoginController;
import controllers.PartyRegisterController;
import controllers.RecordingController;


public class TestDriver {

	public static void main(String[] args) {
		new LoginFrame(new LoginController());
	}

}
