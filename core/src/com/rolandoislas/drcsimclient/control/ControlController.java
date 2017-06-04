package com.rolandoislas.drcsimclient.control;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.rolandoislas.drcsimclient.config.ConfigController;
import com.rolandoislas.drcsimclient.data.Constants;
import com.rolandoislas.drcsimclient.stage.StageControl;

import static com.rolandoislas.drcsimclient.Client.sockets;

/**
 * Created by Rolando on 2/6/2017.
 */
public class ControlController implements Control {

	@Override
	public void init(StageControl stage) {

	}

	@Override
	public void update() {
		short buttonBits = 0;
		float[] axes = {0, 0, 0, 0};
		for (Controller controller : Controllers.getControllers()) {
			ConfigController config = new ConfigController(controller.getName());
			config.load();
			// Check buttons
			if (controller.getButton(config.buttonA))
				buttonBits |= Constants.BUTTON_A;
			if (controller.getButton(config.buttonB))
				buttonBits |= Constants.BUTTON_B;
			if (controller.getButton(config.buttonX))
				buttonBits |= Constants.BUTTON_X;
			if (controller.getButton(config.buttonY))
				buttonBits |= Constants.BUTTON_Y;
			if (controller.getPov(config.buttonUp).equals(PovDirection.north))
				buttonBits |= Constants.BUTTON_UP;
			if (controller.getPov(config.buttonDown).equals(PovDirection.south))
				buttonBits |= Constants.BUTTON_DOWN;
			if (controller.getPov(config.buttonLeft).equals(PovDirection.west))
				buttonBits |= Constants.BUTTON_LEFT;
			if (controller.getPov(config.buttonRight).equals(PovDirection.east))
				buttonBits |= Constants.BUTTON_RIGHT;
			if (controller.getButton(config.buttonL))
				buttonBits |= Constants.BUTTON_L;
			if (controller.getButton(config.buttonR))
				buttonBits |= Constants.BUTTON_R;
			if (controller.getButton(config.buttonZL))
				buttonBits |= Constants.BUTTON_ZL;
			if (controller.getButton(config.buttonZR))
				buttonBits |= Constants.BUTTON_ZR;
			if (controller.getButton(config.buttonL3))
				buttonBits |= Constants.BUTTON_L3;
			if (controller.getButton(config.buttonR3))
				buttonBits |= Constants.BUTTON_R3;
			if (controller.getButton(config.buttonMinus))
				buttonBits |= Constants.BUTTON_MINUS;
			if (controller.getButton(config.buttonPlus))
				buttonBits |= Constants.BUTTON_PLUS;
			if (controller.getButton(config.buttonHome))
				buttonBits |= Constants.BUTTON_HOME;
			// Microphone
			if (controller.getButton(config.micBlow))
				sockets.sendMicBlow();
			// Check joystick
			axes[0] = (short) controller.getAxis(config.joystickLeftX);
			axes[1] = (short) controller.getAxis(config.joystickLeftY) * -1;
			axes[2] = (short) controller.getAxis(config.joystickRightX);
			axes[3] = (short) controller.getAxis(config.joystickRightY) * -1;
		}
		sockets.sendButtonInput(buttonBits);
		sockets.sendJoystickInput(axes);
	}

	@Override
	public void vibrate(int milliseconds) {

	}
}
