package com.game.msg;

import javax.swing.JOptionPane;

import com.game.main.GameClient;
import com.game.main.Server;

public class RegMsg extends Msg {

	private boolean success;

	@Override
	public void process(GameClient client) {
		if (success) {
			JOptionPane.showMessageDialog(null, "ע��ɹ�");
			client.getLoginFrame().SetPostVisited(false);
		} else {
			JOptionPane.showMessageDialog(null, "ע��ʧ�ܣ��û����ظ�");
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
