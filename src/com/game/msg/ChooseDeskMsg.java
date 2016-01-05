package com.game.msg;

import java.io.Serializable;
import java.util.List;

import javax.swing.JOptionPane;

import com.game.main.GameClient;
import com.game.main.Server;
import com.game.model.User;
import com.game.view.MainGameFrame;

public class ChooseDeskMsg extends Msg implements Serializable {

	private int deskId;
	private List<User> userList;
	private boolean allowed = true;

	public ChooseDeskMsg(int roomId) {
		this.deskId = roomId;
	}

	public void process(GameClient client) {
		if (client.getUser().equals(user)) {
			if (client.getMainGameFrame() != null) {
				return;
			}
			client.getUser().setDeskId(user.getDeskId());
			if (allowed) {
				client.setMainGameFrame(new MainGameFrame(client.getUser()));
				System.out.println("进入桌子" + deskId);
			} else {
				JOptionPane.showMessageDialog(null, "桌子满");
				System.out.println("桌子满");
			}
		}
		userList.remove(client.getUser());
		if (allowed) {
			if (client.getMainGameFrame() != null) {
				if (userList.size() > 0) {
					client.getMainGameFrame().GetLeftPanel().GetUserPanel()
							.GetOtherInfoPanel().setPlayer2(userList.get(0));
				}
				if (userList.size() > 1) {
					client.getMainGameFrame().GetLeftPanel().GetUserPanel()
							.GetOtherInfoPanel().setPlayer3(userList.get(1));
				}
				if (userList.size() > 2) {
					client.getMainGameFrame().GetLeftPanel().GetUserPanel()
							.GetOtherInfoPanel().setPlayer4(userList.get(2));
				}
			}
			client.getMainFrame().AddPlayerInDesk(user);
		}
	}

	public void process(Server server) {
		if (deskId == 0) {
			userList = server.getUserListByDesk(user);
		}
		user.setDeskId(deskId);
		userList = server.getUserListByDesk(user);
		if (userList.size() >= Integer.parseInt(server.getProperties().get(
				"MAX_DESK_SIZE"))) {
			allowed = false;
			user.setDeskId(0);
		} else {
			server.getClientUserByUser(user).setDeskId(deskId);
			userList.add(user);
		}
		server.sendMsgByRoom(this);
	}

	public int getDeskId() {
		return deskId;
	}

	public void setDeskId(int roomId) {
		this.deskId = roomId;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public boolean isAllowed() {
		return allowed;
	}

	public void setAllowed(boolean allowed) {
		this.allowed = allowed;
	}

}
