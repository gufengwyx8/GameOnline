package com.game.msg;

import com.game.main.GameClient;
import com.game.main.Server;
import com.game.model.Board;
import com.game.model.User;

public class ReadyMsg extends Msg {

	private boolean start = false;
	private boolean restart = false;
	private boolean otherStart = false;

	public void process(GameClient client) {
		if (!client.getUser().equals(user)) {
			for (int i = 2; i < 5; i++) {
				if (user.equals(client.getMainGameFrame().getLeftpanel()
						.getUserpanel().GetOtherInfoPanel().getUser(i))) {
					client.getMainGameFrame().getLeftpanel().getUserpanel()
							.GetOtherInfoPanel().setUser(user, i);
					break;
				}
			}
		}
		if (otherStart && user.equals(client.getUser())) {
			client.getMainGameFrame().getLeftpanel().getChatpanel().addMsg(
					"系统: 其他玩家正在游戏，请等待其他玩家游戏结束");
		}
		if (!start || (restart && !user.equals(client.getUser()))) {
			return;
		}
		int width = Integer.parseInt(client.getProperty("BOARD_WIDTH"));
		int height = Integer.parseInt(client.getProperty("BOARD_HEIGHT"));
		int mimeCount = Integer.parseInt(client.getProperty("MIME_COUNT"));
		client.getUser().setBoard(new Board());
		client.getUser().getBoard().initBoard(width, height, mimeCount);
		if (!restart) {
			client.getUser().setStart(true);
			for (int i = 5; i > 0; i--) {
				client.getMainGameFrame().getLeftpanel().getChatpanel().addMsg(
						"系统: 游戏将在" + i + "秒后开始!!");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			client.getMainGameFrame().getCenterpanel().GetUpPanel().startTime();
		}
		client.getMainGameFrame().SetGameStart(true);
	}

	public void process(Server server) {
		server.getClientUserByUser(user).setReady(user.isReady());
		for (User u : server.getUserListByDesk(user)) {
			if (!restart && u.isStart()) {
				otherStart = true;
				return;
			}
			if (!u.isReady()) {
				return;
			}
		}
		start = user.isReady();
		if (server.getUserListByDesk(user).size() == 0) {
			start = false;
		}
		if (start) {
			for (User u : server.getUserListByDesk(user)) {
				u.setStart(true);
			}
			server.getClientUserByUser(user).setStart(true);
		}
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isRestart() {
		return restart;
	}

	public void setRestart(boolean restart) {
		this.restart = restart;
	}
}
