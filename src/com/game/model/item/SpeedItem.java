package com.game.model.item;

import com.game.main.GameClient;
import com.game.model.User;

public class SpeedItem extends Item {

	@Override
	public String getImagePath() {
		return "picture\\º”ÀŸ.png";
	}

	@Override
	public void process(User user, GameClient client) {
		if (!client.getUser().equals(user)
				&& (client.getUser().getTeam() == 2 || client.getUser()
						.getTeam() != user.getTeam())) {
			client.getMainGameFrame().getCenterpanel().GetUpPanel().startTime();
		}
	}

}
