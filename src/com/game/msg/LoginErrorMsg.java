package com.game.msg;

import javax.swing.JOptionPane;

import com.game.main.GameClient;
import com.game.main.Server;

public class LoginErrorMsg extends Msg {

	public LoginErrorMsg() {
		type = Msg.LOGIN_ERROR_MSG;
	}

	@Override
	public void process(GameClient client) {
		System.out.println("��¼ʧ��");
		JOptionPane.showMessageDialog(null, "�û������������", "����",
				JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void process(Server server) {

	}

}
