package com.game.view.hall;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.game.main.GameClient;
import com.game.model.User;

public class StatePanel extends JPanel {

	private ImageIcon background = new ImageIcon("picture\\pic\\状态栏背景.png"),
			lostpic, winpic, tiepic;
	private JLabel scoretext, name, win, lost, ratetext, tie;
	private MainFrame parent;

	public StatePanel(MainFrame p) {
		parent = p;
		SetWindow();
	}

	private void SetWindow() {
		this.setPreferredSize(new Dimension(380, 60));
		this.setLayout(null);

		scoretext = new JLabel();
		scoretext
				.setText("积分：" + String.valueOf(parent.GetPlayer().getScore()));
		scoretext.setBounds(10, 30, 150, 20);
		this.add(scoretext);

		name = new JLabel();
		name.setText(parent.GetPlayer().getName());
		name.setBounds(10, 10, 200, 20);
		this.add(name);

		ratetext = new JLabel();
		ratetext.setText("胜利率：" + String.valueOf(parent.GetPlayer().getRate())
				+ "%");
		ratetext.setBounds(100, 30, 80, 20);
		this.add(ratetext);

		win = new JLabel();
		winpic = new ImageIcon("picture\\pic\\winpic.png");
		win.setIcon(winpic);
		win.setText(String.valueOf(parent.GetPlayer().getWin()));
		win.setBounds(200, 30, 50, 20);
		this.add(win);

		lost = new JLabel();
		lostpic = new ImageIcon("picture\\pic\\lostpic.png");
		lost.setIcon(lostpic);
		lost.setText(String.valueOf(parent.GetPlayer().getLost()));
		lost.setBounds(250, 30, 50, 20);
		this.add(lost);

		tie = new JLabel();
		tiepic = new ImageIcon("picture\\pic\\teipic.png");
		tie.setIcon(tiepic);
		tie.setText(String.valueOf(parent.GetPlayer().getTie()));
		tie.setBounds(300, 30, 50, 20);
		this.add(tie);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		User u = GameClient.getInstance().getUser();
		scoretext.setText(u.getScore() + "");
		ratetext.setText(u.getRate() + "%");
		win.setText(u.getWin() + "");
		lost.setText(u.getLost() + "");
		tie.setText(u.getTie() + "");
		g.drawImage(background.getImage(), 0, 0, null);

	}
}
