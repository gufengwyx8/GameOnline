package com.game.msg;

import com.game.main.GameClient;
import com.game.main.Server;

public class ChatMsg extends Msg {

	private String msg;

	public ChatMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public void process(GameClient client) {
		client.getMainGameFrame().getLeftpanel().getChatpanel().addMsg(
				user.getName() + " ˵: " + msg);
	}

	@Override
	public void process(Server server) {
		
	}

}
