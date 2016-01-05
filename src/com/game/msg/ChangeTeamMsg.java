package com.game.msg;

import com.game.main.GameClient;
import com.game.main.Server;
import com.game.model.User;

public class ChangeTeamMsg extends Msg {

	private int team;

	@Override
	public void process(GameClient client) {
		if (client.getUser().equals(user)) {
			client.getUser().setTeam(team);
		} else {
			for (int i = 2; i < 5; i++) {
				if (client.getMainGameFrame().getLeftpanel().getUserpanel()
						.GetOtherInfoPanel().getUser(i).equals(user)) {
					client.getMainGameFrame().getLeftpanel().getUserpanel()
							.GetOtherInfoPanel().setUser(user, i);
					break;
				}
			}
		}
	}

	@Override
	public void process(Server server) {
		User u = server.getClientUserByUser(user);
		u.setTeam(team);
		user.setTeam(team);
		System.out.println(u.getName() + " " + team);
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

}
