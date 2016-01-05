package com.game.msg;

import com.game.main.GameClient;
import com.game.main.Server;
import com.game.model.item.Item;

public class ItemMsg extends Msg {

	private Item item;

	@Override
	public void process(GameClient client) {
		item.process(user, client);
		client.getMainGameFrame().getCenterpanel().GetUpPanel().getToolpanel()
				.removeItem(item);
	}

	@Override
	public void process(Server server) {

	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
