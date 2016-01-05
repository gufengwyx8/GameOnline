package com.game.msg;

import com.game.main.GameClient;
import com.game.main.Server;

public class RoomChatMsg extends Msg {

	private String msg;

	@Override
	public void process(GameClient client) {
		client.getMainFrame().SetChatContent(user.getName() + " ˵: " + msg);
	}

	@Override
	public void process(Server server) {
		server.sendMsgByRoom(this);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
