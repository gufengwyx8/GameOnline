package com.game.msg;

import com.game.main.GameClient;
import com.game.main.Server;
import com.game.model.User;

public class LeaveDeskMsg extends Msg {

	public void process(GameClient client) {
		client.getMainFrame().DeletePlayerInDesk(user);
		if (client.getUser().equals(user)) {
			client.getMainGameFrame().dispose();
			client.setMainGameFrame(null);
			client.getUser().setDeskId(0);
			client.getUser().setReady(false);
			client.getUser().setStart(false);
			client.getUser().setScore(user.getScore());
			client.getMainFrame().validate();
		} else {
			if (client.getMainGameFrame() != null) {
				for (int i = 2; i < 5; i++) {
					if (user.equals(client.getMainGameFrame().getLeftpanel()
							.getUserpanel().GetOtherInfoPanel().getUser(i))) {
						client.getMainGameFrame().getLeftpanel().getUserpanel()
								.GetOtherInfoPanel().setUser(null, i);
						break;
					}
				}
			}
		}
	}

	public void process(Server server) {
		User u = server.getClientUserByUser(user);
		if (u.isStart()) {
			u.setScore(u.getScore() - 100);
			server.getUserManager().update(u);
			user.setScore(u.getScore());
		}
		u.setReady(false);
		u.setStart(false);
		u.setDeskId(0);
		u.setTeam(2);
		server.sendMsgByRoom(this);
	}

}
