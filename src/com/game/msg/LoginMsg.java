package com.game.msg;

import com.game.main.GameClient;
import com.game.main.Server;
import com.game.view.hall.MainFrame;

public class LoginMsg extends Msg {

	public LoginMsg() {
		type = Msg.LOGIN_MSG;
	}

	@Override
	public void process(GameClient client) {
		System.out.println("登录成功");
		client.setUser(user);
		System.out.println(user.getUuid());
		client.getLoginFrame().dispose();
		client.setLoginFrame(null);
		client.setMainFrame(new MainFrame(user));
	}

	@Override
	public void process(Server server) {
		System.out.println("用户" + user.getName() + " uuid:" + user.getUuid()
				+ "登录成功");
	}

}
