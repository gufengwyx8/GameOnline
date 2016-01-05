package com.game.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.game.main.GameClient;
import com.game.model.User;
import com.game.msg.ChangeTeamMsg;
import com.game.msg.LeaveDeskMsg;
import com.game.msg.Msg;
import com.game.msg.ReadyMsg;

public class StatePanel extends JPanel {

	public static void main(String[] args) {
		new Type();
		GameClient.getInstance().setUser(new User());
		new MainGameFrame(new User());
	}

	private MainGameFrame parent;
	private ImageIcon background;
	private Dimension dimension;
	private GameButton teama, teamb, teamc, superplayer, ready, exit;
	private JLabel text;
	private Icon picture;

	public StatePanel(MainGameFrame p) {
		parent = p;
		SetWindow();
	}

	private void SetWindow() {
		dimension = new Dimension(570, 45);
		this.setLayout(null);
		this.setPreferredSize(dimension);

		picture = new ImageIcon("picture\\text.png");
		text = new JLabel();
		text.setIcon(picture);
		text.setBounds(20, 13, 62, 21);
		this.add(text);

		teama = new GameButton();
		teama.SetReleasePicture("picture\\TeamButton1(撤销).png");
		teama.SetPressPicture("picture\\TeamButton1(按下).png");
		teama.SetMovePicture("picture\\TeamButton1(移动).png");
		teama.SetOtherPichterPath("picture\\TeamButton1(禁用).png", false);
		teama.setBounds(105, 8, 30, 31);
		this.add(teama);
		teama.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				if (teama.GetPictureNum() != 3) {
					parent.GetPlayerInfo(0).setTeam(0);
					parent.GetLeftPanel().GetUserPanel().GetMyInfoPanel()
							.RePaintTeamPicture();
					teamb.clear();
					teamc.clear();
					ChangeTeamMsg msg = new ChangeTeamMsg();
					msg.setUser(GameClient.getInstance().getUser());
					msg.setTeam(0);
					msg.send();
				}
			}
		});

		teamb = new GameButton();
		teamb.SetReleasePicture("picture\\TeamButton2(撤销).png");
		teamb.SetPressPicture("picture\\TeamButton2(按下).png");
		teamb.SetMovePicture("picture\\TeamButton2(移动).png");
		teamb.SetOtherPichterPath("picture\\TeamButton2(禁用).png", false);
		teamb.setBounds(145, 8, 30, 31);
		this.add(teamb);
		teamb.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				if (teamb.GetPictureNum() != 3) {
					parent.GetPlayerInfo(0).setTeam(1);
					parent.GetLeftPanel().GetUserPanel().GetMyInfoPanel()
							.RePaintTeamPicture();
					teama.clear();
					teamc.clear();
					ChangeTeamMsg msg = new ChangeTeamMsg();
					msg.setUser(GameClient.getInstance().getUser());
					msg.setTeam(1);
					msg.send();
				}
			}
		});

		teamc = new GameButton();
		teamc.SetReleasePicture("picture\\TeamButton3(撤销).png");
		teamc.SetPressPicture("picture\\TeamButton3(按下).png");
		teamc.SetMovePicture("picture\\TeamButton3(移动).png");
		teamc.SetOtherPichterPath("picture\\TeamButton3(禁用).png", false);
		teamc.SetPictureNum(1);
		teamc.setBounds(185, 8, 30, 31);
		this.add(teamc);
		teamc.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				if (teamc.GetPictureNum() != 3) {
					parent.GetPlayerInfo(0).setTeam(2);
					parent.GetLeftPanel().GetUserPanel().GetMyInfoPanel()
							.RePaintTeamPicture();
					teama.clear();
					teamb.clear();
					ChangeTeamMsg msg = new ChangeTeamMsg();
					msg.setUser(GameClient.getInstance().getUser());
					msg.setTeam(2);
					msg.send();
				}
			}
		});

		superplayer = new GameButton();
		superplayer.SetReleasePicture("picture\\superplayer(撤销).png");
		superplayer.SetPressPicture("picture\\superplayer(按下).png");
		superplayer.SetMovePicture("picture\\superplayer(移动).png");
		superplayer.SetReleasedEvent(true);
		superplayer.setBounds(290, 8, 90, 33);
		this.add(superplayer);

		ready = new GameButton();
		ready.SetReleasePicture("picture\\ready(撤销).png");
		ready.SetPressPicture("picture\\ready(按下).png");
		ready.SetMovePicture("picture\\ready(移动).png");
		ready.SetReleasedEvent(true);
		ready.SetOtherPichterPath("picture\\ready(撤销).png", true);
		ready.setBounds(385, 8, 90, 33);
		this.add(ready);
		ready.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				if (parent.isGamestart()
						|| GameClient.getInstance().getUser().isStart()) {
					return;
				}
				GameClient.getInstance().getUser().setReady(
						!GameClient.getInstance().getUser().isReady());
				parent.GetLeftPanel().GetUserPanel().GetMyInfoPanel().SetReady(
						GameClient.getInstance().getUser().isReady());
				if (GameClient.getInstance().getUser().isReady()) {
					if (teama.GetPictureNum() != 1) {
						teama.SetPictureNum(3);
					}
					if (teamb.GetPictureNum() != 1) {
						teamb.SetPictureNum(3);
					}
					if (teamc.GetPictureNum() != 1) {
						teamc.SetPictureNum(3);
					}
				} else {
					reset();
				}
				Msg msg = new ReadyMsg();
				msg.setUser(GameClient.getInstance().getUser());
				msg.send();
				// parent.SetGameStart(true);
			}
		});

		exit = new GameButton();
		exit.SetReleasePicture("picture\\exit(撤销).png");
		exit.SetPressPicture("picture\\exit(按下).png");
		exit.SetMovePicture("picture\\exit(移动).png");
		exit.SetReleasedEvent(true);
		exit.setBounds(460, 8, 90, 33);
		this.add(exit);
		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
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

	public void reset() {
		if (teama.GetPictureNum() != 1) {
			teama.SetPictureNum(0);
			teama.setOtherpicture(false);
			teama.setReleasedevent(false);
		}
		if (teamb.GetPictureNum() != 1) {
			teamb.SetPictureNum(0);
			teamb.setOtherpicture(false);
			teamb.setReleasedevent(false);
		}
		if (teamc.GetPictureNum() != 1) {
			teamc.SetPictureNum(0);
			teamc.setOtherpicture(false);
			teamc.setReleasedevent(false);
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		background = new ImageIcon("picture\\StatePanel背景.jpg");
		g.drawImage(background.getImage(), 0, 0, null);
	}
}
