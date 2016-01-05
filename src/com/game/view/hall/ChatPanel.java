package com.game.view.hall;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.game.main.GameClient;
import com.game.msg.RoomChatMsg;

public class ChatPanel extends JPanel {

	private ImageIcon background;
	private GameButton enter;
	private JTextField input;
	private JTextArea show;
	private JScrollPane pane;

	public ChatPanel() {
		SetWindow();
	}

	private void SetWindow() {
		this.setLayout(null);
		this.setPreferredSize(new Dimension(323, 380));

		input = new JTextField();
		input.setBounds(10, 356, 250, 20);
		input.setBorder(javax.swing.BorderFactory
				.createLineBorder(new java.awt.Color(255, 255, 255)));
		input.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RoomChatMsg msg = new RoomChatMsg();
				msg.setMsg(input.getText());
				msg.setUser(GameClient.getInstance().getUser());
				msg.send();
				input.setText("");
			}
		});
		this.add(input);

		show = new JTextArea();
		show.setBounds(15, 10, 320, 330);
		show.setEditable(false);
		pane = new JScrollPane(show);
		pane.setBounds(1, 6, 320, 330);
		this.add(pane);

		enter = new GameButton();
		enter.SetReleasePicture("picture\\pic\\输入(默认).png");
		enter.SetPressPicture("picture\\pic\\输入(按下).png");
		enter.SetMovePicture("picture\\pic\\输入(移入).png");
		enter.setBounds(292, 354, 23, 21);
		this.add(enter);
		enter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				RoomChatMsg msg = new RoomChatMsg();
				msg.setMsg(input.getText());
				msg.setUser(GameClient.getInstance().getUser());
				msg.send();
				input.setText("");
			}
		});
	}

	public void ShowChat(String str) {
		show.setText(show.getText() + str + "\n");
		show.selectAll();
	}

	public void ClearChar() {
		show.setText("");
	}

	protected void paintComponent(Graphics g) {
		background = new ImageIcon("picture\\pic\\聊天面板背景.png");
		g.drawImage(background.getImage(), 0, 0, null);
	}
}
