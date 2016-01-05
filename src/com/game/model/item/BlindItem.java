package com.game.model.item;

import javax.swing.JOptionPane;

import com.game.main.GameClient;
import com.game.model.User;

public class BlindItem extends Item {

	@Override
	public String getImagePath() {
		return "picture\\Ê§Ã÷.png";
	}

	@Override
	public void process(final User user, final GameClient client) {
		new Thread(new Runnable() {
			public void run() {
				if (!client.getUser().equals(user)
						&& (client.getUser().getTeam() == 2 || client.getUser()
								.getTeam() != user.getTeam())) {
					JOptionPane.showMessageDialog(null, "½ûÖ¹²Ù×÷10Ãë");
					client.getMainGameFrame().getCenterpanel().GetUpPanel()
							.getGamepanel().setVisible(false);
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					client.getMainGameFrame().getCenterpanel().GetUpPanel()
							.getGamepanel().setVisible(true);
				}
			}
		}).start();
	}
}
