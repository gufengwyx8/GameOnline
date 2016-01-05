package com.game.main;

import java.util.Map;

import com.game.model.User;
import com.game.service.UserManager;
import com.game.util.PropertiesUtil;
import com.game.view.MainGameFrame;
import com.game.view.Type;
import com.game.view.hall.MainFrame;
import com.game.view.login.LoginFrame;

public class GameClient {

	public static final String PROPERTIES_PATH = "config.properties";

	private Map<String, String> properties = PropertiesUtil
			.getPropertiesMap(PROPERTIES_PATH);

	private UserManager userManager = new UserManager();
	private MainGameFrame mainGameFrame;
	private LoginFrame loginFrame;
	private MainFrame mainFrame;
	private NetClient netClient = new NetClient(this, properties
			.get("SERVER_IP"), Integer.parseInt(properties
			.get("SERVER_TCP_PORT")), Integer.parseInt(properties
			.get("CLIENT_UDP_PORT")));
	private User user;

	private static GameClient instance;

	public static GameClient getInstance() {
		if (instance == null) {
			instance = new GameClient();
		}
		return instance;
	}

	private GameClient() {

	}

	public static void main(String[] args) {
		new Type();
		GameClient gc = GameClient.getInstance();
		gc.loginFrame = new LoginFrame();
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public MainGameFrame getMainGameFrame() {
		return mainGameFrame;
	}

	public void setMainGameFrame(MainGameFrame mainGameFrame) {
		this.mainGameFrame = mainGameFrame;
	}

	public NetClient getNetClient() {
		return netClient;
	}

	public void setNetClient(NetClient netClient) {
		this.netClient = netClient;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public String getProperty(String key) {
		return properties.get(key);
	}

	public LoginFrame getLoginFrame() {
		return loginFrame;
	}

	public void setLoginFrame(LoginFrame loginFrame) {
		this.loginFrame = loginFrame;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public String getLevelStr(User user) {
		int score = user.getScore();
		String[] str = properties.get("SCORE_MAP").split(";");
		if (score < 0) {
			return str[0].split(" ")[1];
		}
		for (int i = 0; i < str.length - 1; i++) {
			if (score <= Integer.parseInt(str[i].split(" ")[0])
					&& score < Integer.parseInt(str[i + 1].split(" ")[0])) {
				return str[i].split(" ")[1];
			}
		}
		return str[str.length - 1].split(" ")[1];
	}
}
