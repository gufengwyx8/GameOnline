package com.game.view.login;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;

public class LoginFrame extends JFrame {

	private TitlePanel titlepanel;
	private CenterPanel centerpanel;
	private StatePanel statepanel;
	private Post post;
	private int priposx, priposy;

	public LoginFrame() {
		setUndecorated(true);
		SetWindow();
		this.setSize(358, 248);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void SetWindow() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen = kit.getScreenSize();

		this.setPreferredSize(new Dimension(358, 248));
		this.setLayout(null);
		this.setLocation((screen.width - 358) / 2, (screen.height - 248) / 2);

		titlepanel = new TitlePanel();
		titlepanel.setBounds(0, 0, 358, 30);
		this.add(titlepanel);
		titlepanel.setVisible(true);

		centerpanel = new CenterPanel(this);
		centerpanel.setBounds(0, 30, 358, 176);
		this.add(centerpanel);
		centerpanel.setVisible(true);

		statepanel = new StatePanel(this);
		statepanel.setBounds(0, 206, 358, 42);
		this.add(statepanel);
		statepanel.setVisible(true);

		this.setResizable(false);

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

	public void SetPostVisited(boolean bool) {
		if (post != null) {
			post.dispose();
		}
		post = new Post(this);
		post.setSize(350, 355);
		post.setVisible(bool);
		repaint();
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

	public CenterPanel getCenterpanel() {
		return centerpanel;
	}

	public void setCenterpanel(CenterPanel centerpanel) {
		this.centerpanel = centerpanel;
	}

}