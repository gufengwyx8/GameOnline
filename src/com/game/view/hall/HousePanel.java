package com.game.view.hall;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.game.main.GameClient;
import com.game.model.User;
import com.game.msg.LeaveRoomMsg;
import com.game.msg.Msg;

public class HousePanel extends JPanel {

	private ImageIcon background;
	private ImageIcon pic;
	private JLabel path;
	private PlayerList playerlist;
	private ChatPanel chatpanel;
	private JScrollPane scrollpanel;
	private GamePanel gamepanel;
	private MainFrame parent;
	private GameButton exit;

	public HousePanel(MainFrame p) {
		parent = p;
		this.setLayout(null);
		this.setPreferredSize(new Dimension(1024, 647));
		SetWindow();
	}

	private void SetWindow() {
		path = new JLabel();
		pic = new ImageIcon("picture\\pic\\路径图标.png");
		path.setIcon(pic);
		path.setBounds(10, 5, 200, 20);
		this.add(path);

		playerlist = new PlayerList();
		playerlist.setBounds(695, 27, 323, 234);
		playerlist.setVisible(true);
		this.add(playerlist);

		chatpanel = new ChatPanel();
		chatpanel.setBounds(696, 261, 323, 380);
		chatpanel.setVisible(true);
		this.add(chatpanel);

		gamepanel = new GamePanel(parent);
		gamepanel.setBounds(0, 0, 692, 616);
		gamepanel.setBackground(new Color(81, 113, 158));
		gamepanel.setVisible(true);

		scrollpanel = new JScrollPane(gamepanel);
		scrollpanel.setBounds(4, 26, 692, 616);
		scrollpanel
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpanel.setBackground(new Color(81, 113, 158));
		this.add(scrollpanel);

		exit = new GameButton();
		exit.setBounds(993, 1, 25, 25);
		exit.SetReleasePicture("picture\\pic\\游戏面板关闭(未选中).png");
		exit.SetPressPicture("picture\\pic\\游戏面板关闭(选中).png");
		exit.SetMovePicture("picture\\pic\\游戏面板关闭(移入).png");
		this.add(exit);
		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				Msg msg = new LeaveRoomMsg();
				msg.setUser(GameClient.getInstance().getUser());
				msg.send();
			}
		});
	}

	public void SetChatContent(String str) {
		chatpanel.ShowChat(str);
	}

	public void AddPlayerInList(User player) {
		playerlist.AddPlayer(player);
	}

	public void ClearPlayerInList() {
		playerlist.ClearPlayer();
	}

	public void DeletePlayerInList(int num) {
		playerlist.DeletePlayer(num);
	}

	public void AddDesk() {
		gamepanel.AddDesk();
		gamepanel.ChangeToPerSize();
	}

	public void AddDesk(int num) {
		for (int i = 0; i < num; i++) {
			gamepanel.AddDesk();
		}
		gamepanel.ChangeToPerSize();
	}

	public void SetPlayerInDesk(int desknum, User player) {
		for (int i = 0; i < 4; i++) {
			if (player.equals(gamepanel.GetDesk(player.getDeskId() - 1)
					.getPlayers().get(i))) {
				return;
			}
			if (gamepanel.GetDesk(desknum) != null
					&& gamepanel.GetDesk(desknum).getPlayers().get(i) == null) {
				gamepanel.GetDesk(desknum).AddPlayer(player, i);
				break;
			}
		}
	}

	public void DeletePlayerInDesk(User player) {
		if (gamepanel.GetDesk(player.getDeskId() - 1) != null) {
			for (int i = 0; i < 4; i++) {
				if (player.equals(gamepanel.GetDesk(player.getDeskId() - 1)
						.getPlayers().get(i))) {
					gamepanel.GetDesk(player.getDeskId() - 1).DeletePlayer(i);
					break;
				}
			}
		}
	}

	public void SetPath(String str) {
		path.setText(str);
	}

	protected void paintComponent(Graphics g) {
		background = new ImageIcon("picture\\pic\\房间背景.png");
		g.drawImage(background.getImage(), 0, 0, null);
	}
}
