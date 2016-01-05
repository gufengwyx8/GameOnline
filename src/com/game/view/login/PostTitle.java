package com.game.view.login;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class PostTitle extends JPanel {

	private ImageIcon background;
	private JLabel title;
	private GameButton exit;
	private Post parent;

	public PostTitle(Post p) {
		parent = p;
		SetWindow();
	}

	private void SetWindow() {
		this.setPreferredSize(new Dimension(358, 30));
		this.setLayout(null);

		title = new JLabel();
		title.setText("��Ϸע��");
		title.setBounds(35, 8, 200, 16);
		title.setFont(new Font("��������", 0, 14));
		title.setForeground(Color.WHITE);
		this.add(title);

		exit = new GameButton();
		exit.SetReleasePicture("picture\\�˳���ť(�ͷ�).png");
		exit.SetPressPicture("picture\\�˳���ť(����).png");
		exit.SetMovePicture("picture\\�˳���ť(�ƶ�).png");
		exit.setBounds(310, 0, 40, 40);
		this.add(exit);
		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				ExitButtonActionPerformed(evt);
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		background = new ImageIcon("picture\\ע�������.png");
		g.drawImage(background.getImage(), 0, 0, null);
	}

	private void ExitButtonActionPerformed(ActionEvent evt) {
		parent.setVisible(false);
	}
}
