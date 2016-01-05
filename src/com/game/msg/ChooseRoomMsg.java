package com.game.msg;

import java.util.ArrayList;
import java.util.List;

import com.game.main.GameClient;
import com.game.main.Server;
import com.game.model.User;
import com.game.view.hall.MainFrame;

public class ChooseRoomMsg extends Msg {

	private int roomId;
	private List<User> userList = new ArrayList<User>();

	public ChooseRoomMsg(int roomId) {
		this.roomId = roomId;
	}

	public void process(GameClient client) {
		MainFrame frame = client.getMainFrame();
		if (client.getUser().equals(user)) {
			client.getUser().setRoomId(roomId);
			System.out.println("进入大厅" + roomId);
			frame.ClearFrame();
			frame.SetCurHousePanel(frame.GetHousePanelList().get(roomId - 1));
			frame.SetChatContent("欢迎进入：房间" + roomId);
			frame.ClearPlayerInList();
			frame.AddPlayerInList(client.getUser());
			frame.GetHousePanelList().get(roomId - 1).setVisible(true);
			for (User u : userList) {
				frame.AddPlayerInList(u);
				if (u.getDeskId() > 0) {
					frame.AddPlayerInDesk(u);
				}
			}
		} else {
			frame.AddPlayerInList(user);
		}
	}

	public void process(Server server) {
		server.getClientUserByUser(user).setRoomId(roomId);
		user.setRoomId(roomId);
		userList = server.getUserListByRoom(user);
		server.sendMsgByRoom(this);
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

}
