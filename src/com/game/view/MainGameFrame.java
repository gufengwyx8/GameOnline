package com.game.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.game.main.GameClient;
import com.game.model.User;

public class MainGameFrame extends JFrame {

	private GameClient gameClient = GameClient.getInstance();

	private LeftPanel leftpanel;
	private CenterPanel centerpanel;
	private TitlePanel titlepanel;
	private boolean gamestart;
	private List<User> player = new ArrayList<User>();
	private int priposx, priposy;
	private int gold, time;

	public MainGameFrame(User user) {
		super("挖金子");
		setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		addPlayer(user);
		initialization();
		SetWindow();
		this.setVisible(true);
	}

	private void SetWindow() {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen = kit.getScreenSize();

		this.setSize(800, 608);
		this.setLayout(null);
		this.setLocation((screen.width - 825) / 2, (screen.height - 625) / 2);

		titlepanel = new TitlePanel();
		titlepanel.setBounds(0, 0, 800, 34);
		this.add(titlepanel);
		titlepanel.setVisible(true);

		leftpanel = new LeftPanel(this);
		leftpanel.setBounds(0, 34, 230, 575);
		this.add(leftpanel);
		leftpanel.setVisible(true);

		centerpanel = new CenterPanel(this);
		centerpanel.setBounds(230, 34, 570, 575);
		this.add(centerpanel);
		centerpanel.setVisible(true);

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

		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = tk.getImage("picture\\鼠标.png");
		Cursor cu = tk.createCustomCursor(img, new Point(0, 0), "stick");
		this.setCursor(cu);
		this.setResizable(false);
	}

	private void initialization() {
		gamestart = false;
		time = 0;
		gold = 0;
		// PlayerInfoTest();
	}

	public void SetGameStart(boolean bool) {
		gamestart = bool;
		GameClient.getInstance().getUser().setStart(bool);
		centerpanel.GetUpPanel().getGamepanel().setVisible(bool);
		centerpanel.GetUpPanel().setVisible(true);
		centerpanel.GetUpPanel().repaint();
		if (bool) {
			centerpanel.GetUpPanel().getGamepanel().SetPanel();
			centerpanel.GetUpPanel().getGamepanel().updateComponent();
		}else{
			this.centerpanel.GetStatePanel().reset();
		}
		//this.repaint();
		//this.validate();
		// centerpanel.GetStatePanel().SetTeamButtonforbidden();
	}

	public boolean IsGameStart() {
		return gamestart;
	}

	public LeftPanel GetLeftPanel() {
		return leftpanel;
	}

	public int GetTime() {
		return time;
	}

	public void SetTime(int t) {
		time = t;
	}

	public void SetGold(int g) {
		gold = g;
	}

	public int GetGold() {
		return gold;
	}

	public User GetPlayerInfo(int num) {
		if (player.size() <= num) {
			return null;
		}
		return player.get(num);
	}

	public void addPlayer(User user) {
		if (player.size() < Integer.parseInt(gameClient.getProperties().get(
				"MAX_PLAYER"))) {
			player.add(user);
		}
	}

	// private void PlayerInfoTest() { // 测试数据
	// int[][] panel = new int[20][20];
	//
	// User p = new User();
	// p.setExist(true);
	// p.setName("2b2b");
	// p.setScore(12);
	// p.setPicturepath("picture\\MyPicture.png");
	// p.setGame(panel);
	// player.add(p);
	// for (int i = 0; i < 20; i++) {
	// for (int j = 0; j < 20; j++) {
	// Random random = new Random();
	// panel[i][j] = random.nextInt(5) - 1;
	// }
	// }
	//
	// p = new User();
	// p.setExist(true);
	// p.setName("bbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
	// p.setScore(5);
	// p.setPicturepath("picture\\girl.jpg");
	// player.add(p);
	// }

	private void MousePressed(MouseEvent evt) {
		if (evt.getY() >= 0 && evt.getY() <= 34) {
			priposx = evt.getXOnScreen();
			priposy = evt.getYOnScreen();
		}
	}

	private void MouseDragged(MouseEvent evt) {
		if (evt.getY() >= 0 && evt.getY() <= 34) {
			Point curlocation = this.getLocation();
			int posx = priposx - evt.getXOnScreen();
			int posy = priposy - evt.getYOnScreen();

			this.setLocation(curlocation.x - posx, curlocation.y - posy);
			repaint();
			priposx = evt.getXOnScreen();
			priposy = evt.getYOnScreen();
		}
	}

	public LeftPanel getLeftpanel() {
		return leftpanel;
	}

	public CenterPanel getCenterpanel() {
		return centerpanel;
	}

	public boolean isGamestart() {
		return gamestart;
	}

	public void setGamestart(boolean gamestart) {
		this.gamestart = gamestart;
	}
}
