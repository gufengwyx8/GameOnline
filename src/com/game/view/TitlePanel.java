package com.game.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.game.main.GameClient;
import com.game.msg.LeaveDeskMsg;
import com.game.msg.Msg;

public class TitlePanel extends JPanel {

	private ImageIcon background;
	private JLabel title;
	private GameButton exit;

	public TitlePanel() {
		SetWindow();
	}

	private void SetWindow() {
		this.setPreferredSize(new Dimension(800, 34));
		this.setLayout(null);

		title = new JLabel();
		title.setText("网络游戏课程设计—挖金子 房间"
				+ GameClient.getInstance().getUser().getRoomId() + " 桌子"
				+ GameClient.getInstance().getUser().getDeskId());
		title.setBounds(35, 8, 400, 16);
		title.setFont(new Font("华文琥珀", 0, 14));
		title.setForeground(Color.WHITE);
		this.add(title);

		exit = new GameButton();
		exit.SetReleasePicture("picture\\ExitButton(撤销).png");
		exit.SetPressPicture("picture\\ExitButton(按下).png");
		exit.SetMovePicture("picture\\ExitButton(移动).png");
		exit.SetReleasedEvent(true);
		exit.setBounds(770, 5, 22, 22);
		this.add(exit);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (GameClient.getInstance().getUser().isStart()) {
					if (JOptionPane.showConfirmDialog(null,
							"游戏正在进行，强行退出会扣分，确定要退出吗?", "提示",
							JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
						return;
					}
				}
				Msg msg = new LeaveDeskMsg();
				msg.setUser(GameClient.getInstance().getUser());
				msg.send();
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		background = new ImageIcon("picture\\titlepicture.jpg");
		g.drawImage(background.getImage(), 0, 0, null);
	}
}
