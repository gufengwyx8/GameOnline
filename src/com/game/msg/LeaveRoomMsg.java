package com.game.msg;

import com.game.main.GameClient;
import com.game.main.Server;
import com.game.view.hall.MainFrame;

public class LeaveRoomMsg extends Msg {

	@Override
	public void process(GameClient client) {
		MainFrame frame = client.getMainFrame();
		if (user.equals(client.getUser())) {
			frame.GetCurHousePanel().setVisible(false);
			frame.GetCenterPanel().setVisible(true);
		} else {
			frame.DeletePlayerInList(frame.getRoomList().indexOf(user));
		}
	}

	@Override
	public void process(Server server) {
		server.getClientUserByUser(user).setRoomId(0);
		server.sendMsgByRoom(this);
	}

}
