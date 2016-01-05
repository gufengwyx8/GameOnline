package com.game.model.item;

import com.game.main.GameClient;
import com.game.model.Board;
import com.game.model.User;
import com.game.msg.WinMsg;

public class FindMimeItem extends Item {

	@Override
	public String getImagePath() {
		return "picture\\Ö¸Ê¾µÆ.png";
	}

	@Override
	public void process(User user, GameClient client) {
		if (client.getUser().equals(user)) {
			Board b = client.getUser().getBoard();
			for (int i = 0; i < b.getWidth(); i++) {
				for (int j = 0; j < b.getHeight(); j++) {
					if (b.getChess(i, j).isMime() && !b.getChess(i, j).isFlag()) {
						b.getChess(i, j).setFlag(true);
					}
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
		}
	}

}
