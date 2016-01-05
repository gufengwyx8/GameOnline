package com.game.view.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Post extends JFrame {

	private PostTitle posttitle;
	private InfoPanel infopanel;
	private LoginFrame parent;
	private int priposx, priposy;

	public Post(LoginFrame p) {
		parent = p;
		setUndecorated(true);
		SetWindow();
	}

	private void SetWindow() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen = kit.getScreenSize();
		this.setPreferredSize(new Dimension(350, 355));
		this.setLayout(null);
		this.setResizable(false);
		this.setLocation((screen.width - 350) / 2, (screen.height - 355) / 2);

		posttitle = new PostTitle(this);
		posttitle.setBounds(0, 0, 350, 30);
		posttitle.setVisible(true);
		this.add(posttitle);

		infopanel = new InfoPanel(parent);
		infopanel.setBounds(0, 30, 350, 325);
		infopanel.setVisible(true);
		this.add(infopanel);

		this.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent evt) {
				MousePressed(evt);
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {

			public void mouseDragged(MouseEvent evt) {
				MouseDragged(evt);
			}
		});
	}

	private void MousePressed(MouseEvent evt) {
		if (evt.getY() >= 0 && evt.getY() <= 30) {
			priposx = evt.getXOnScreen();
			priposy = evt.getYOnScreen();
		}
	}

	private void MouseDragged(MouseEvent evt) {
		if (evt.getY() >= 0 && evt.getY() <= 30) {
			Point curlocation = this.getLocation();
			int posx = priposx - evt.getXOnScreen();
			int posy = priposy - evt.getYOnScreen();

			this.setLocation(curlocation.x - posx, curlocation.y - posy);
			repaint();
			priposx = evt.getXOnScreen();
			priposy = evt.getYOnScreen();
		}
	}
}
