package com.game.view;

import javax.swing.*;

import com.game.main.GameClient;
import com.game.model.User;

import java.awt.*;

public class MyInfoPanel extends JPanel {

	private MainGameFrame parent;
	private User myinfo;
	private ImageIcon background;
	private JLabel mypicture;
	private Icon picture;
	private ShowInfo showinfo;
	private JLabel teampicture;
	private JLabel ready;

	public MyInfoPanel(MainGameFrame p) {
		parent = p;
		SetWindow();
	}

	private void SetWindow() {
		this.setLayout(null);
		this.setPreferredSize(new Dimension(230, 130));
		myinfo = parent.GetPlayerInfo(0);

		picture = new ImageIcon("picture\\MyPicture.png");
		mypicture = new JLabel();
		mypicture.setIcon(picture);
		mypicture.setBounds(8, 5, 100, 120);
		this.add(mypicture);

		picture = new ImageIcon(GetTeamPicturePath());
		teampicture = new JLabel();
		teampicture.setIcon(picture);
		teampicture.setBounds(123, 17, 30, 30);
		this.add(teampicture);

		picture = new ImageIcon("picture\\ready.png");
		ready = new JLabel();
		ready.setIcon(picture);
		ready.setBounds(120, 93, 45, 25);
		ready.setVisible(false);
		this.add(ready);

		showinfo = new ShowInfo(myinfo);
		showinfo.setBounds(113, 12, 111, 106);
		showinfo.setVisible(true);
		this.add(showinfo);
	}

	public void RePaintTeamPicture() {
		picture = new ImageIcon(GetTeamPicturePath());
		teampicture.setIcon(picture);
		repaint();
	}

	public void SetReady(boolean bReady) {
		ready.setVisible(bReady);
		this.repaint();
		this.validate();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		background = new ImageIcon("picture\\MyInfoPanelbackground.jpg");
		g.drawImage(background.getImage(), 0, 0, null);
	}

	public void updateUser(User user) {
		myinfo = user;
		showinfo.score.setText(user.getScore() + "");
		SetReady(user.isReady());
	}

	private String GetTeamPicturePath() {
		switch (parent.GetPlayerInfo(0).getTeam()) {
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

	private class ShowInfo extends JPanel {

		private ImageIcon background;
		private User player;
		private JLabel name, score, level;

		public ShowInfo(User p) {
			player = p;
			this.setLayout(null);
			name = new JLabel();
			name.setText("昵称：" + player.getName());
			name.setBounds(10, 40, 100, 14);
			name.setFont(new Font("宋体", 0, 12));
			name.setForeground(Color.WHITE);
			this.add(name);

			score = new JLabel();
			score.setText("积分：" + String.valueOf(player.getScore()));
			score.setBounds(10, 54, 100, 14);
			score.setFont(new Font("宋体", 0, 12));
			score.setForeground(Color.WHITE);
			this.add(score);

			level = new JLabel();
			level.setText("等级：" + GameClient.getInstance().getLevelStr(player));
			level.setBounds(10, 68, 100, 14);
			level.setFont(new Font("宋体", 0, 12));
			level.setForeground(Color.WHITE);
			this.add(level);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			background = new ImageIcon("picture\\MyInfoBackground.png");
			g.drawImage(background.getImage(), 0, 0, null);
		}
	}
}
