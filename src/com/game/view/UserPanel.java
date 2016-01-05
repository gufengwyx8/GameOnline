package com.game.view;

import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel {
	private MainGameFrame parent;
	private MyInfoPanel myinfopanel;
	private OtherInfoPanel otherinfopanel;

	public UserPanel(MainGameFrame p) {
		parent = p;
		SetWindow();
	}

	private void SetWindow() {
		this.setLayout(new BorderLayout());
		this.setSize(230, 405);

		myinfopanel = new MyInfoPanel(parent);
		this.add(myinfopanel, BorderLayout.NORTH);
		myinfopanel.setVisible(true);

		otherinfopanel = new OtherInfoPanel(parent);
		this.add(otherinfopanel, BorderLayout.CENTER);
		otherinfopanel.setVisible(true);
	}

	public MyInfoPanel GetMyInfoPanel() {
		return myinfopanel;
	}

	public OtherInfoPanel GetOtherInfoPanel() {
		return otherinfopanel;
	}
}
