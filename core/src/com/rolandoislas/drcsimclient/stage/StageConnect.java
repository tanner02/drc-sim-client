package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rolandoislas.drcsimclient.Client;

/**
 * Created by Rolando on 12/29/2016.
 */
public class StageConnect extends Stage {
	private final Preferences lastHostPreferences;
	private final TextField textfield;

	public StageConnect(String message) {
		// Textfield
		Skin textFieldSkin = new Skin();
		textFieldSkin.add("cursor", new Texture("image/textfield-cursor.png"));
		textFieldSkin.add("selection", new Texture("image/textfield-selection.png"));
		TextField.TextFieldStyle textfieldStyle = new TextField.TextFieldStyle();
		textfieldStyle.font = new BitmapFont(Gdx.files.internal("font/collvetica.fnt"));
		textfieldStyle.font.getData().setScale(2);
		textfieldStyle.fontColor = new Color(1, 1, 1, 1);
		textfieldStyle.cursor = textFieldSkin.getDrawable("cursor");
		textfieldStyle.selection = textFieldSkin.getDrawable("selection");
		textfield = new TextField("", textfieldStyle);
		textfield.setBounds(Gdx.graphics.getWidth() * .15f, Gdx.graphics.getHeight() * .6f,
				Gdx.graphics.getWidth() * .7f, Gdx.graphics.getHeight() * .1f);
		textfield.setMessageText("hostname or ip");
		lastHostPreferences = Gdx.app.getPreferences("com.rolandoislas.drcsimclient.lasthost");
		textfield.setText(lastHostPreferences.getString("lastHost"));
		addActor(textfield);
		// Connect Button
		TextButton.TextButtonStyle connectButtonStyle = new TextButton.TextButtonStyle();
		connectButtonStyle.font = textfieldStyle.font;
		TextButton connectButton = new TextButton("Connect", connectButtonStyle);
		connectButton.setBounds(textfield.getX(), textfield.getY() - textfield.getHeight(),
				connectButton.getWidth(), connectButton.getHeight());
		connectButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				connect();
			}
		});
		addActor(connectButton);
		// Error Message
		Label.LabelStyle errorLabelStyle = new Label.LabelStyle();
		errorLabelStyle.font = textfieldStyle.font;
		errorLabelStyle.fontColor = textfieldStyle.fontColor;
		Label labelError = new Label(message, errorLabelStyle);
		float iconSize = Gdx.graphics.getWidth() * .1f;
		labelError.setBounds(iconSize, 10, Gdx.graphics.getWidth() - iconSize * 2,
				Gdx.graphics.getHeight() * .1f);
		labelError.setAlignment(Align.center);
		addActor(labelError);
		// Settings Button
		Skin settingsButtonSkin = new Skin();
		settingsButtonSkin.add("image", new Texture("image/settings-icon.png"));
		Button.ButtonStyle settingsButtonStyle = new Button.ButtonStyle();
		settingsButtonStyle.up = settingsButtonSkin.getDrawable("image");
		settingsButtonStyle.down = connectButtonStyle.up;
		settingsButtonStyle.checked = connectButtonStyle.up;
		Button settingsButton = new Button(settingsButtonStyle);
		settingsButton.setSize(iconSize, iconSize);
		settingsButton.setPosition(Gdx.graphics.getWidth() - settingsButton.getWidth() - 10, 10);
		settingsButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Client.setStage(new StageSettings());
			}
		});
		addActor(settingsButton);
		// Title
		Label title = new Label("DRC Sim", errorLabelStyle);
		title.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * .1f);
		title.setPosition(0, Gdx.graphics.getHeight() - title.getHeight());
		title.setAlignment(Align.center);
		addActor(title);
		// Info Button
		Skin infoButtonSkin = new Skin();
		infoButtonSkin.add("image", new Texture("image/icon-512.png"));
		Button.ButtonStyle infoButtonStyle = new Button.ButtonStyle();
		infoButtonStyle.up = infoButtonSkin.getDrawable("image");
		infoButtonStyle.down = infoButtonStyle.up;
		infoButtonStyle.checked = infoButtonStyle.up;
		Button infoButton = new Button(infoButtonStyle);
		infoButton.setBounds(10, 10, iconSize, iconSize);
		infoButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.net.openURI("https://github.com/rolandoislas/drc-sim");
			}
		});
		addActor(infoButton);
	}

	private void connect() {
		lastHostPreferences.putString("lastHost", textfield.getText());
		lastHostPreferences.flush();
		if (Client.connect(textfield.getText()))
			Client.setStage(new StageControl());
	}

	public StageConnect() {
		this("");
	}

	@Override
	public void onBackButtonPressed() {
		Gdx.app.exit();
	}

	@Override
	public boolean keyDown(int keyCode) {
		if (keyCode == Input.Keys.ENTER) {
			Gdx.input.setOnscreenKeyboardVisible(false);
			connect();
		}
		return super.keyDown(keyCode);
	}
}
