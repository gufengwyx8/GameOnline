package com.game.view.hall;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.game.main.GameClient;
import com.game.model.User;
import com.game.msg.ChooseDeskMsg;
import com.game.msg.Msg;

public class Desk extends JPanel {

	private boolean isplaying;
	private ImageIcon background;
	private ArrayList<User> players;
	private JLabel desknum;
	private ArrayList<GameButton> chairlist;
	private MainFrame parent;

	public Desk(MainFrame p) {
		parent = p;
		SetWindow();
	}

	private void SetWindow() {
		isplaying = false;
		players = new ArrayList<User>();
		for (int i = 0; i < 4; i++) {
			players.add(null);
		}
		this.setPreferredSize(new Dimension(160, 160));
		this.setLayout(null);
		chairlist = new ArrayList<GameButton>();

		desknum = new JLabel();
		desknum.setBounds(55, 147, 50, 14);
		desknum.setText("<->");
		desknum.setHorizontalAlignment(SwingConstants.CENTER);
		desknum.setFont(new Font("宋体", 0, 14));
		desknum.setForeground(Color.WHITE);
		this.add(desknum);

	}

	public void CreatChair(int num) {
		CreatChair(62, 6, num);
		CreatChair(62, 114, num);
		CreatChair(6, 62, num);
		CreatChair(114, 62, num);
		repaint();
	}

	private void CreatChair(int x, int y, final int num) {

		GameButton c = new GameButton();
		c.setBounds(x, y, 36, 36);
		c.SetReleasePicture("picture\\pic\\椅子(未选中).png");
		c.SetPressPicture("picture\\pic\\椅子(按下).png");
		c.SetMovePicture("picture\\pic\\椅子(选中).png");
		chairlist.add(c);
		c.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				Msg msg = new ChooseDeskMsg(num);
				msg.setUser(GameClient.getInstance().getUser());
				msg.send();
			}
		});
		this.add(c);
	}

	public void SetDeskNum(int num) {
		desknum.setText("<" + String.valueOf(num) + ">");
		desknum.setBounds(55, 147, 50, 14);
	}

	public void AddPlayer(User player, int chairnum) {
		if (!isplaying && chairnum >= 0 && chairnum < 4) {
			if (!player.equals(players.get(chairnum))) {
				chairlist.get(chairnum).SetReleasePicture(
						player.getReleasepath());
				chairlist.get(chairnum).SetPressPicture(player.getPresspath());
				chairlist.get(chairnum).SetMovePicture(player.getMovepath());
				chairlist.get(chairnum).repaint();
				players.set(chairnum, player);
			}
		}
	}

	public void DeletePlayer(int chairnum) {
		if (chairnum >= 0 && chairnum < 4 && players.get(chairnum) != null) {
			chairlist.get(chairnum).SetReleasePicture(
					"picture\\pic\\椅子(未选中).png");
			chairlist.get(chairnum).SetPressPicture("picture\\pic\\椅子(按下).png");
			chairlist.get(chairnum).SetMovePicture("picture\\pic\\椅子(选中).png");
			chairlist.get(chairnum).repaint();
			players.set(chairnum, null);
			// players.remove(chairnum);
		}
	}

	protected void paintComponent(Graphics g) {
		background = new ImageIcon("picture\\pic\\桌子背景.png");
		g.drawImage(background.getImage(), 0, 0, null);
	}

	public ArrayList<User> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<User> players) {
		this.players = players;
	}

	public MainFrame getParent() {
		return parent;
	}

	public void setParent(MainFrame parent) {
		this.parent = parent;
	}
}
