package com.game.view.hall;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.game.main.GameClient;
import com.game.model.User;
import com.game.msg.ChooseRoomMsg;
import com.game.msg.Msg;

public class MainFrame extends JFrame {

	private TitlePanel titlepanel;
	private CenterPanel centerpanel;
	private HousePanel housepanel;
	private ArrayList<HousePanel> houselist;
	private int priposx, priposy;
	private User player;
	private HousePanel curhousepanel;
	public boolean lock;
	private List<User> roomList = new ArrayList<User>();

	public MainFrame(User player) {
		setUndecorated(true);
		this.player = player;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		houselist = new ArrayList<HousePanel>();
		SetWindow();
		this.setSize(1024, 768);
		this.setVisible(true);
	}

	private void SetWindow() {
		lock = false;
		Toolkit kit = Toolkit.getDefaultToolkit();
		this.setPreferredSize(new Dimension(1024, 768));
		this.setLayout(null);
		this.setResizable(false);
		this.setLocation((kit.getScreenSize().width - 1024) / 2, (kit
				.getScreenSize().height - 768) / 2);

		titlepanel = new TitlePanel(this);
		titlepanel.setBounds(0, 0, 1024, 121);
		titlepanel.setVisible(true);
		this.add(titlepanel);

		centerpanel = new CenterPanel(this);
		centerpanel.setBounds(0, 121, 1024, 647);
		centerpanel.setVisible(true);
		this.add(centerpanel);
		Test();

		this.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent evt) {
				MousePressed(evt);
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {

			public void mouseDragged(MouseEvent evt) {
				MouseDragged(evt);
			}
		});
	}

	public ArrayList<HousePanel> GetHousePanelList() {
		return houselist;
	}

	public void SetCurHousePanel(HousePanel h) {
		curhousepanel = h;
	}

	public HousePanel GetCurHousePanel() {
		return curhousepanel;
	}

	public HousePanel GetHousePanel() {
		return housepanel;
	}

	public CenterPanel GetCenterPanel() {
		return centerpanel;
	}

	public void ClearFrame() {
		centerpanel.setVisible(false);
	}

	public void AddHallList() {
		centerpanel.AddHallParentItem("新手房");
		centerpanel.AddHallChildItem("新手房", "房间1", 0, 19);
		centerpanel.GetHallList().GetItem(0).GetChild(0).addActionListener(
				new ActionListener() {

					public void actionPerformed(ActionEvent evt) {
						Msg msg = new ChooseRoomMsg(1);
						msg.setUser(GameClient.getInstance().getUser());
						msg.send();
						// frame.AddPlayerInDesk(0, 1, client.getUser());
					}
				});
		centerpanel.AddHallChildItem("新手房", "房间2", 0, 10);
		centerpanel.GetHallList().GetItem(0).GetChild(1).addActionListener(
				new ActionListener() {

					public void actionPerformed(ActionEvent evt) {
						Msg msg = new ChooseRoomMsg(2);
						msg.setUser(GameClient.getInstance().getUser());
						msg.send();
					}
				});

		centerpanel.AddHallParentItem("普通房");
		centerpanel.AddHallChildItem("普通房", "房间1", 1, 6);
		centerpanel.GetHallList().GetItem(1).GetChild(0).addActionListener(
				new ActionListener() {

					public void actionPerformed(ActionEvent evt) {
						Msg msg = new ChooseRoomMsg(3);
						msg.setUser(GameClient.getInstance().getUser());
						msg.send();
					}
				});
		centerpanel.AddHallChildItem("普通房", "房间2", 1, 14);
		centerpanel.GetHallList().GetItem(1).GetChild(1).addActionListener(
				new ActionListener() {

					public void actionPerformed(ActionEvent evt) {
						Msg msg = new ChooseRoomMsg(4);
						msg.setUser(GameClient.getInstance().getUser());
						msg.send();
					}
				});
		centerpanel.AddHallChildItem("普通房", "房间3", 1, 12);
		centerpanel.GetHallList().GetItem(1).GetChild(2).addActionListener(
				new ActionListener() {

					public void actionPerformed(ActionEvent evt) {
						Msg msg = new ChooseRoomMsg(5);
						msg.setUser(GameClient.getInstance().getUser());
						msg.send();
					}
				});
		centerpanel.ListChangeSize();
	}

	public User GetPlayer() {
		return player;
	}

	private void MousePressed(MouseEvent evt) {
		if (evt.getY() >= 0 && evt.getY() <= 121) {
			priposx = evt.getXOnScreen();
			priposy = evt.getYOnScreen();
		}
	}

	private void MouseDragged(MouseEvent evt) {
		if (evt.getY() >= 0 && evt.getY() <= 121) {
			Point curlocation = this.getLocation();
			int posx = priposx - evt.getXOnScreen();
			int posy = priposy - evt.getYOnScreen();

			this.setLocation(curlocation.x - posx, curlocation.y - posy);
			repaint();
			priposx = evt.getXOnScreen();
			priposy = evt.getYOnScreen();
		}
	}

	private void Test() {
		AddHallList();
	}

	public void SetChatContent(String str) {
		if (curhousepanel != null) {
			curhousepanel.SetChatContent(str);
		}
	}

	public void AddPlayerInList(User player) {
		if (curhousepanel != null) {
			roomList.add(player);
			curhousepanel.AddPlayerInList(player);
		}
	}

	public void ClearPlayerInList() {
		if (curhousepanel != null) {
			roomList.clear();
			curhousepanel.ClearPlayerInList();
		}
	}

	public void AddPlayerInDesk(User player) {
		if (curhousepanel != null) {
			curhousepanel.SetPlayerInDesk(player.getDeskId() - 1, player);
		}
	}

	public void DeletePlayerInDesk(User player) {
		if (curhousepanel != null) {
			curhousepanel.DeletePlayerInDesk(player);
		}
	}

	public void DeletePlayerInList(int num) {
		if (curhousepanel != null) {
			roomList.remove(num);
			curhousepanel.DeletePlayerInList(num);
		}
	}

	public List<User> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<User> roomList) {
		this.roomList = roomList;
	}

	public TitlePanel getTitlepanel() {
		return titlepanel;
	}

	public void setTitlepanel(TitlePanel titlepanel) {
		this.titlepanel = titlepanel;
	}
}
