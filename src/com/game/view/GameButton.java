package com.game.view;

import javax.swing.*;
import java.awt.event.*;

public class GameButton extends JButton {

	private int picture;
	private ImageIcon background;
	private String presspath, releasepath, movepath, otherpicturepath,
			rightmousepath, rightmovepath;
	private boolean releasedevent, otherpicture, rightmouse;

	public GameButton() {
		picture = 0;
		presspath = "";
		releasepath = "";
		releasedevent = false;
		otherpicture = false;
		rightmouse = false;
		setContentAreaFilled(false);
		this.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent evt) {
				MousePressed(evt);
			}

			public void mouseReleased(MouseEvent evt) {
				MouseReleased(evt);
			}

			public void mouseEntered(MouseEvent evt) {
				MouseEntered(evt);
			}

			public void mouseExited(MouseEvent evt) {
				MouseExited(evt);
			}
		});
	}

	public void SetReleasedEvent(boolean b) {
		releasedevent = b;
	}

	public void SetOtherPichterPath(String path, boolean bool) {
		otherpicturepath = path;
		otherpicture = bool;
	}

	public void SetRightMovePath(String path) {
		rightmovepath = path;
	}

	public void SetRightMousePath(String path, boolean bool) {
		rightmouse = bool;
		rightmousepath = path;
	}

	public void clear() {
		if (picture != 3) {
			picture = 0;
			repaint();
		}
	}

	public int GetPictureNum() {
		return picture;
	}

	public void SetPictureNum(int num) {
		picture = num;
		if (num == 3) {
			otherpicture = true;
			releasedevent = true;
		}
		repaint();
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
		} else if (picture == 3) {
			background = new ImageIcon(otherpicturepath);
		} else if (picture == 4) {
			background = new ImageIcon(rightmousepath);
		} else {
			background = new ImageIcon(rightmovepath);
		}
		g.drawImage(background.getImage(), 0, 0, null);
	}

	private void MousePressed(MouseEvent evt) {
		if (!evt.isMetaDown()) {
			if (picture != 3 && picture != 1 && picture != 5) {
				picture = 1;
				repaint();
			}
		} else {
			if (picture == 2) {
				rightmouse = true;
				picture = 5;
			} else if (picture == 5) {
				rightmouse = false;
				picture = 2;
			}
			repaint();
		}
	}

	private void MouseEntered(MouseEvent evt) {
		if (picture != 1 && picture != 3 && picture != 4) {
			picture = 2;
			repaint();
		} else if (picture == 4) {
			picture = 5;
			repaint();
		}
	}

	private void MouseReleased(MouseEvent evt) {
		if (!evt.isMetaDown()) {
			if (releasedevent) {
				if (otherpicture) {
					if (picture != 5) {
						picture = 3;
					}
				} else {
					picture = 0;
				}
				repaint();
			}
		}
	}

	private void MouseExited(MouseEvent evt) {
		if (picture != 1 && picture != 3 && picture != 4 && picture != 5) {
			picture = 0;
			repaint();
		} else if (picture == 5) {
			picture = 4;
			repaint();
		}
	}

	public int getPicture() {
		return picture;
	}

	public void setPicture(int picture) {
		this.picture = picture;
	}

	public boolean isOtherpicture() {
		return otherpicture;
	}

	public void setOtherpicture(boolean otherpicture) {
		this.otherpicture = otherpicture;
	}

	public boolean isReleasedevent() {
		return releasedevent;
	}

	public void setReleasedevent(boolean releasedevent) {
		this.releasedevent = releasedevent;
	}

	public String getOtherpicturepath() {
		return otherpicturepath;
	}

	public void setOtherpicturepath(String otherpicturepath) {
		this.otherpicturepath = otherpicturepath;
	}

	public boolean isRightmouse() {
		return rightmouse;
	}

	public void setRightmouse(boolean rightmouse) {
		this.rightmouse = rightmouse;
	}
}
