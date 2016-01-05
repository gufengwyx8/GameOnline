package com.game.msg;

import javax.swing.JOptionPane;

import com.game.main.GameClient;
import com.game.main.Server;
import com.game.model.User;

public class WinMsg extends Msg {

	private int score;

	@Override
	public void process(GameClient client) {
		if (!client.getUser().equals(user)) {
			if (client.getUser().isStart()) {
				if (client.getUser().getTeam() == user.getTeam()
						&& user.getTeam() != 2) {
					JOptionPane.showMessageDialog(null, "你队友" + user.getName()
							+ "赢了，你获得" + score + "积分", "胜利",
							JOptionPane.INFORMATION_MESSAGE);
					client.getUser().setScore(
							client.getUser().getScore() + score);
					client.getMainGameFrame().getLeftpanel().getUserpanel()
							.GetMyInfoPanel().updateUser(client.getUser());
					client.getUser().setWin(client.getUser().getWin() + 1);
				} else {
					JOptionPane.showMessageDialog(null, "你输了，扣掉" + score
							+ "的积分", "失败", JOptionPane.INFORMATION_MESSAGE);
					client.getUser().setScore(
							client.getUser().getScore() - score);
					client.getUser().setLost(client.getUser().getLost() + 1);
				}
			} else {
				JOptionPane.showMessageDialog(null, "游戏结束，请点准备", "提示",
						JOptionPane.INFORMATION_MESSAGE);
			}
			for (int i = 2; i < 5; i++) {
				if (user.equals(client.getMainGameFrame().getLeftpanel()
						.getUserpanel().GetOtherInfoPanel().getUser(i))) {
					client.getMainGameFrame().getLeftpanel().getUserpanel()
							.GetOtherInfoPanel().setUser(user, i);
					break;
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "你赢了，获得" + score + "积分", "胜利",
					JOptionPane.INFORMATION_MESSAGE);
			client.setUser(user);
			client.getMainGameFrame().getLeftpanel().getUserpanel()
					.GetMyInfoPanel().updateUser(user);
		}
		client.getMainGameFrame().getCenterpanel().GetUpPanel().setGold(0);
		client.getMainFrame().getTitlepanel().getStatepanel().paintComponents(
				client.getMainFrame().getTitlepanel().getStatepanel()
						.getGraphics());
		client.getMainFrame().validate();
		client.getMainFrame().repaint();
		client.getUser().setReady(false);
		client.getUser().setStart(false);
		client.getMainGameFrame().getLeftpanel().getUserpanel()
				.GetMyInfoPanel().SetReady(false);
		for (int i = 2; i < 5; i++) {
			if (client.getMainGameFrame().getLeftpanel().getUserpanel()
					.GetOtherInfoPanel().getUser(i) != null) {
				client.getMainGameFrame().getLeftpanel().getUserpanel()
						.GetOtherInfoPanel().GetUserInfoPanel(i)
						.SetReady(false);
			}
		}
		for (int i = 0; i < 5; i++) {
			client.getMainGameFrame().getCenterpanel().GetUpPanel()
					.getToolpanel().setGameItems(null, i);
		}
		client.getMainGameFrame().SetGameStart(false);
	}

	@Override
	public void process(Server server) {
		User u = server.getClientUserByUser(user);
		u.setScore(u.getScore() + score);
		u.setWin(u.getWin() + 1);
		user.setWin(u.getWin());
		server.getUserManager().update(u);
		user.setScore(u.getScore());
		for (User tmp : server.getUserListByDesk(user)) {
			if (tmp.isStart() && tmp.getTeam() == user.getTeam()
					&& user.getTeam() != 2) {
				tmp.setScore(tmp.getScore() + score);
				tmp.setWin(tmp.getWin() + 1);
			} else {
				tmp.setScore(tmp.getScore() - score);
				tmp.setLost(tmp.getLost() + 1);
			}
			server.getUserManager().update(tmp);
			tmp.setStart(false);
			tmp.setReady(false);
		}
		u.setStart(false);
		u.setReady(false);
		user.setStart(false);
		user.setReady(false);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
