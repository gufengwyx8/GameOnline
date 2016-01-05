package com.game.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.game.main.GameClient;
import com.game.model.Board;
import com.game.model.Chess;
import com.game.model.item.Item;
import com.game.msg.ReadyMsg;
import com.game.msg.WinMsg;

public class GamePanel extends JPanel {

	private MainGameFrame parent;
	private Board board;
	private boolean leftFlag = false, rightFlag = false;
	private List<GameButton> btnList = new ArrayList<GameButton>();

	public GamePanel(MainGameFrame p) {
		parent = p;
		SetWindow();
		SetPanel();
	}

	public synchronized void repaint() {
		super.repaint();
	}

	private void SetWindow() {
		this.setPreferredSize(new Dimension(460, 460));
		this.setLayout(new GridLayout(20, 20));
	}

	public synchronized void SetPanel() {
		this.removeAll();
		btnList.clear();
		board = parent.GetPlayerInfo(0).getBoard();
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				GameButton btn = CreatGoldButton();
				btnList.add(btn);
				this.add(btn);
			}
		}
		this.repaint();
		this.validate();
	}

	private GameButton CreatGoldButton() {
		GameButton button = new GameButton();
		button.SetReleasePicture("picture\\金矿(撤销).png");
		button.SetPressPicture("picture\\金矿(按下).png");
		button.SetMovePicture("picture\\金矿(移动).png");
		button.SetRightMousePath("picture\\金矿(稻草人).png", true);
		button.SetRightMovePath("picture\\金矿(稻草人移动).png");
		button.SetReleasedEvent(true);
		button.setSize(23, 23);
		button.addMouseListener(new Monitor());
		return button;
	}

	public void setButtonBackground(GameButton button, String path) {
		button.SetReleasePicture(path);
		button.SetMovePicture(path);
		button.SetPressPicture(path);
		button.SetRightMovePath(path);
		button.SetRightMousePath(path, true);
	}

	public synchronized void updateComponent() {
		board = GameClient.getInstance().getUser().getBoard();
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				GameButton button = btnList.get(i * board.getHeight() + j);
				Chess c = board.getChess(i, j);
				button.setPicture(0);
				if (c.isOpen()) {
					switch (c.getNum()) {
					case -1:
						setButtonBackground(button, "picture\\金矿(陷阱).png");
						break;
					case 0:
						setButtonBackground(button, "picture\\金矿(0).png");
						break;
					case 1:
						setButtonBackground(button, "picture\\金矿(1).png");
						break;
					case 2:
						setButtonBackground(button, "picture\\金矿(2).png");
						break;
					case 3:
						setButtonBackground(button, "picture\\金矿(3).png");
						break;
					default:
						break;
					}
				} else if (!c.isFlag()) {
					button.SetReleasePicture("picture\\金矿(撤销).png");
					button.SetPressPicture("picture\\金矿(按下).png");
					button.SetMovePicture("picture\\金矿(移动).png");
					button.SetRightMousePath("picture\\金矿(稻草人).png", true);
					button.SetRightMovePath("picture\\金矿(稻草人移动).png");
				} else if (c.isFlag()) {
					button.SetReleasePicture("picture\\金矿(撤销).png");
					button.SetPressPicture("picture\\金矿(按下).png");
					button.SetMovePicture("picture\\金矿(移动).png");
					button.SetRightMousePath("picture\\金矿(稻草人).png", true);
					button.SetRightMovePath("picture\\金矿(稻草人移动).png");
					button.setPicture(4);
				}
			}
		}
		this.repaint();
		this.validate();
	}

	class Monitor extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
				leftFlag = true;
			} else if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
				rightFlag = true;
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (board.isWin()) {
				return;
			}
			int x = btnList.indexOf(e.getSource()) / board.getHeight();
			int y = btnList.indexOf(e.getSource()) % board.getHeight();
			boolean isLive = true;
			if (leftFlag && rightFlag) {
				isLive = board.openWithDetector(x, y);
			} else if (leftFlag) {
				isLive = board.open(x, y, 0);
			} else if (rightFlag) {
				board.setFlag(x, y);
			}
			leftFlag = false;
			rightFlag = false;
			if (!isLive) {
				board.showAllMime();
			} else if (board.isWin()) {
				board.openAllChess();
			}
			updateComponent();
			if (!isLive) {
				JOptionPane.showMessageDialog(null, "遇到陷阱，重新开始");
				ReadyMsg msg = new ReadyMsg();
				msg.setRestart(true);
				msg.setUser(GameClient.getInstance().getUser());
				msg.send();
			} else if (board.isWin()) {
				WinMsg msg = new WinMsg();
				msg.setScore(12);
				msg.setUser(GameClient.getInstance().getUser());
				msg.send();
			}
			parent.getCenterpanel().GetUpPanel().setGold(board.getGoldCount());
			List<Item> items = board.getItems();
			for (Item i : items) {
				parent.getCenterpanel().GetUpPanel().getToolpanel().addItem(i);
			}
		}
	}

	public List<GameButton> getBtnList() {
		return btnList;
	}

	public void setBtnList(List<GameButton> btnList) {
		this.btnList = btnList;
	}
}
