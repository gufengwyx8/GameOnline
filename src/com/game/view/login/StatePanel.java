package com.game.view.login;

import javax.swing.*;

import com.game.main.GameClient;

import java.awt.*;
import java.awt.event.*;

public class StatePanel extends JPanel {

	private ImageIcon background;
	private GameButton login;
	private LoginFrame parent;

	public StatePanel(LoginFrame p) {
		parent = p;
		SetWindow();
	}

	private void SetWindow() {
		this.setLayout(null);
		this.setPreferredSize(new Dimension(358, 42));

		login = new GameButton();
		login.SetReleasePicture("picture\\µÇÂ½°´Å¥(ÊÍ·Å).png");
		login.SetPressPicture("picture\\µÇÂ½°´Å¥(°´ÏÂ).png");
		login.SetMovePicture("picture\\µÇÂ½°´Å¥(ÒÆ¶¯).png");
		login.setBounds(260, 7, 79, 27);
		this.add(login);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String name=parent.getCenterpanel().getUserName();
				String password=parent.getCenterpanel().getKeyword();
				GameClient.getInstance().getNetClient().connect(name, password);
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		background = new ImageIcon("picture\\×´Ì¬À¸±³¾°.png");
		g.drawImage(background.getImage(), 0, 0, null);
	}
}
