package com.game.model.item;

import com.game.main.GameClient;
import com.game.model.Board;
import com.game.model.Chess;
import com.game.model.User;
import com.game.msg.WinMsg;

public class GoldTwoItem extends Item {

	@Override
	public String getImagePath() {
		return "picture\\Н№зг+2.png";
	}

	@Override
	public void process(User user, GameClient client) {
		int count = 0;
		if (client.getUser().equals(user)) {
			Board b = client.getUser().getBoard();
			for (int i = 0; i < b.getWidth(); i++) {
				for (int j = 0; j < b.getHeight(); j++) {
					if (b.getChess(i, j).isMime() && !b.getChess(i, j).isFlag()) {
						b.getChess(i, j).setMime(false);
						for (Chess c : b.getAroundChess(i, j)) {
							if (c.getNum() > 0) {
								c.setNum(c.getNum() - 1);
							}
							if (!c.isMime() && c.getNum() == 0) {
								b.open(c.getX(), c.getY(), 0);
							}
						}
						if (b.isWin()) {
							b.openAllChess();
						}
						client.getMainGameFrame().getCenterpanel().GetUpPanel()
								.getGamepanel().updateComponent();
						if (b.isWin()) {
							WinMsg msg = new WinMsg();
							msg.setScore(b.getGoldCount());
							msg.setUser(GameClient.getInstance().getUser());
							msg.send();
						}
						if (++count >= 2) {
							return;
						}
					}
				}
			}

		}
	}

}
