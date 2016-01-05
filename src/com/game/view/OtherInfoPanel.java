package com.game.view;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.game.model.User;

public class OtherInfoPanel extends JPanel {

	private MainGameFrame parent;
	private ImageIcon background;
	private Dimension dimension;
	private UserInfoPanel player2, player3, player4;

	public OtherInfoPanel(MainGameFrame p) {
		parent = p;
		SetWindow();
	}

	private void SetWindow() {
		this.setLayout(null);
		dimension = new Dimension(230, 275);
		this.setPreferredSize(dimension);

		player2 = new UserInfoPanel(parent, parent.GetPlayerInfo(1));
		player2.SetBackground("picture\\otherbackground1.jpg");
		player2.setBounds(8, 8, 214, 86);
		player2.setVisible(true);
		this.add(player2);

		player3 = new UserInfoPanel(parent, parent.GetPlayerInfo(2));
		player3.SetBackground("picture\\otherbackground2.jpg");
		player3.setBounds(8, 95, 214, 86);
		player3.setVisible(true);
		this.add(player3);

		player4 = new UserInfoPanel(parent, parent.GetPlayerInfo(3));
		player4.SetBackground("picture\\otherbackground3.jpg");
		player4.setBounds(8, 182, 214, 86);
		player4.setVisible(true);
		this.add(player4);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		background = new ImageIcon("picture\\OtherInfoPanel±³¾°.jpg");
		g.drawImage(background.getImage(), 0, 0, null);
	}

	public UserInfoPanel GetUserInfoPanel(int num) {
		switch (num) {
		case 2:
			if (player2 != null) {
				return player2;
			} else {
				return null;
			}
		case 3:
			if (player3 != null) {
				return player3;
			} else {
				return null;
			}
		case 4:
			if (player4 != null) {
				return player4;
			} else {
				return null;
			}
		default:
			return null;
		}
	}

	public void setPlayer2(User player2) {
		this.player2.setUser(player2);
	}

	public void setPlayer3(User player3) {
		this.player3.setUser(player3);
	}

	public void setPlayer4(User player4) {
		this.player4.setUser(player4);
	}

	public void setUser(User user, int num) {
		if (num == 2) {
			if (user == null) {
				player2.removeUser();
			} else {
				player2.setUser(user);
			}
		} else if (num == 3) {
			if (user == null) {
				player3.removeUser();
			} else {
				player3.setUser(user);
			}
		} else if (num == 4) {
			if (user == null) {
				player4.removeUser();
			} else {
				player4.setUser(user);
			}
		}
	}

	public User getUser(int num) {
		if (num == 2) {
			return player2.getUser();
		} else if (num == 3) {
			return player3.getUser();
		} else if (num == 4) {
			return player4.getUser();
		}
		return null;
	}
}
