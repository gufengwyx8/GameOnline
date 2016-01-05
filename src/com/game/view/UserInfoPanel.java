package com.game.view;

import javax.swing.*;

import com.game.main.GameClient;
import com.game.model.User;

import java.awt.*;

public class UserInfoPanel extends JPanel {

	private MainGameFrame parent;
	private User user;
	private ImageIcon background;
	private String backgroundpath;
	private JLabel name, level, playerpicture, team, ready;
	private Icon picture;

	public UserInfoPanel(MainGameFrame p, User player) {
		parent = p;
		user = player;
	}

	private void SetWindow() {
		this.setLayout(null);
		this.removeAll();

		playerpicture = new JLabel();
		playerpicture.setBounds(1, 1, 65, 84);
		this.add(playerpicture);

		picture = new ImageIcon(GetTeamPath());
		team = new JLabel();
		team.setIcon(picture);
		team.setBounds(67, 9, 30, 30);
		this.add(team);

		name = new JLabel();
		name.setBounds(68, 48, 100, 14);
		name.setFont(new Font("宋体", 0, 12));
		name.setForeground(Color.WHITE);
		this.add(name);

		level = new JLabel();
		level.setBounds(68, 62, 100, 14);
		level.setFont(new Font("宋体", 0, 12));
		level.setForeground(Color.WHITE);
		this.add(level);

		picture = new ImageIcon("picture\\ready.png");
		ready = new JLabel();
		ready.setIcon(picture);
		ready.setBounds(150, 30, 45, 25);
		ready.setVisible(user.isReady());
		this.add(ready);
	}

	public void RePaint() {
		picture = new ImageIcon(user.getPicturepath());
		playerpicture.setIcon(picture);
		name.setText(user.getName());
		level.setText(GameClient.getInstance().getLevelStr(user));
		picture = new ImageIcon(GetTeamPath());
		team.setIcon(picture);
		repaint();
		this.validate();
	}

	public void SetReady(boolean bool) {
		if (ready == null) {
			return;
		}
		ready.setVisible(bool);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		background = new ImageIcon(backgroundpath);
		g.drawImage(background.getImage(), 0, 0, null);
	}

	public void SetBackground(String path) {
		backgroundpath = path;
	}

	private String GetTeamPath() {
		switch (user.getTeam()) {
		case 0:
			return "picture\\TeamButton1(撤销).png";
		case 1:
			return "picture\\TeamButton2(撤销).png";
		case 2:
			return "picture\\TeamButton3(撤销).png";
		default:
			return null;
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		if (user != null) {
			SetWindow();
		}
		RePaint();
	}

	public void removeUser() {
		this.user = null;
		this.removeAll();
		this.repaint();
		this.validate();
	}

	public MainGameFrame getParent() {
		return parent;
	}

	public void setParent(MainGameFrame parent) {
		this.parent = parent;
	}
}
