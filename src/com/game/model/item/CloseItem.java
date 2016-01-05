package com.game.model.item;

import java.util.List;

import com.game.main.GameClient;
import com.game.model.User;
import com.game.view.GameButton;

public class CloseItem extends Item {

	@Override
	public String getImagePath() {
		return "picture\\·â±Õ.png";
	}

	@Override
	public void process(final User user, final GameClient client) {
		new Thread(new Runnable() {
			public void run() {
				if (!client.getUser().equals(user)
						&& (client.getUser().getTeam() == 2 || client.getUser()
								.getTeam() != user.getTeam())) {
					List<GameButton> btnList = client.getMainGameFrame()
							.getCenterpanel().GetUpPanel().getGamepanel()
							.getBtnList();
					int height = client.getUser().getBoard().getHeight();
					int x = 10, y = 10;
					for (int i = 0; i < 5; i++) {
						for (int j = 0; j < 5; j++) {
							btnList.get((i + x) * height + y + j).setEnabled(
									false);
						}
					}
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					for (int i = 0; i < 5; i++) {
						for (int j = 0; j < 5; j++) {
							btnList.get((i + x) * height + y + j).setEnabled(
									true);
						}
					}
				}
			}
		}).start();
	}

}
