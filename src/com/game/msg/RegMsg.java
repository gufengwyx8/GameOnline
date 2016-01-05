package com.game.msg;

import javax.swing.JOptionPane;

import com.game.main.GameClient;
import com.game.main.Server;

public class RegMsg extends Msg {

	private boolean success;

	@Override
	public void process(GameClient client) {
		if (success) {
			JOptionPane.showMessageDialog(null, "注册成功");
			client.getLoginFrame().SetPostVisited(false);
		} else {
			JOptionPane.showMessageDialog(null, "注册失败，用户名重复");
		}
	}

	@Override
	public void process(Server server) {
		success = server.getUserManager().reg(user);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
