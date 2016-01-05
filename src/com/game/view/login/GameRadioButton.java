package com.game.view.login;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GameRadioButton extends JButton {

	private int picture;
	private ImageIcon background;
	private String presspath, releasepath, movepath;
	private boolean isexit, isselect;

	public GameRadioButton() {
		picture = 0;
		presspath = "";
		releasepath = "";
		isexit = true;
		isselect = false;
		setContentAreaFilled(false);
		this.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent evt) {
				MousePressed(evt);
			}

			public void mouseEntered(MouseEvent evt) {
				MouseEntered(evt);
			}

			public void mouseExited(MouseEvent evt) {
				MouseExited(evt);
			}
		});
	}

	public void SetPressPicture(String path) {
		presspath = path;
	}

	public void SetReleasePicture(String path) {
		releasepath = path;
	}

	public void SetMovePicture(String path) {
		movepath = path;
	}

	protected void paintComponent(java.awt.Graphics g) {
		if (picture == 0) {
			background = new ImageIcon(releasepath);
		} else if (picture == 1) {
			background = new ImageIcon(presspath);
		} else if (picture == 2) {
			background = new ImageIcon(movepath);
		}
		g.drawImage(background.getImage(), 0, 0, null);
	}

	private void MousePressed(MouseEvent evt) {

		if (!isexit) {
			isselect = !isselect;
			if (isselect) {
				picture = 2;
			} else {
				picture = 0;
			}
			repaint();
		}
	}

	private void MouseEntered(MouseEvent evt) {
		isexit = false;
		if (isselect) {
			picture = 2;
			repaint();
		}
	}

	private void MouseExited(MouseEvent evt) {
		isexit = true;
		if (isselect) {
			picture = 1;
		} else {
			picture = 0;
		}
		repaint();
	}

	public boolean isIsselect() {
		return isselect;
	}

	public void setIsselect(boolean isselect) {
		this.isselect = isselect;
	}
}
