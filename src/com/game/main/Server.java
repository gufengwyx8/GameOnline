package com.game.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.game.model.User;
import com.game.msg.LeaveDeskMsg;
import com.game.msg.LeaveRoomMsg;
import com.game.msg.LoginErrorMsg;
import com.game.msg.LoginMsg;
import com.game.msg.Msg;
import com.game.service.UserManager;
import com.game.util.PropertiesUtil;

public class Server {

	public static final String PROPERTIES_PATH = "config.properties";

	private List<Client> clientList = new ArrayList<Client>();
	private UserManager userManager = new UserManager();
	private DatagramSocket ds;

	private Map<String, String> properties = PropertiesUtil
			.getPropertiesMap(PROPERTIES_PATH);

	private UDPThread udpThread = new UDPThread();

	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		ServerSocket ss = null;
		new Thread(udpThread).start();
		try {
			ss = new ServerSocket(Integer.parseInt(properties
					.get("SERVER_TCP_PORT")));
			while (ss != null) {
				Socket s = ss.accept();
				String ip = s.getInetAddress().getHostAddress();
				DataInputStream dis = new DataInputStream(s.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(s
						.getOutputStream());
				int port = dis.readInt();
				String name = dis.readUTF();
				String password = dis.readUTF();
				User user = userManager.login(name, password);
				if (user != null) {
					LoginMsg msg = new LoginMsg();
					user.setUuid(UUID.randomUUID().toString());
					msg.setUser(user);
					msg.process(this);
					oos.writeObject(msg);
					oos.flush();
					Client c = new Client(ip, port, user);
					clientList.add(c);
					new Thread(new TCPThread(user, s)).start();
				} else {
					LoginErrorMsg msg = new LoginErrorMsg();
					oos.writeObject(msg);
					oos.flush();
					s.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				ss = null;
			}
		}
	}

	class TCPThread implements Runnable {

		private User user;
		private Socket s;

		public TCPThread(User user, Socket s) {
			this.user = user;
			this.s = s;
		}

		public void run() {
			try {
				while (true) {
					int i = (int) (Math.random() * 100);
					DataInputStream dis = new DataInputStream(s
							.getInputStream());
					DataOutputStream dos = new DataOutputStream(s
							.getOutputStream());
					dos.writeInt(i);
					if (i != dis.readInt()) {
						throw new IOException();
					}
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				// e.printStackTrace();
				System.out.println("ÓÃ»§" + user.getName() + " uuid:"
						+ user.getUuid() + "ÍË³ö");
				Msg msg = new LeaveDeskMsg();
				msg.setUser(user);
				Msg msg2 = new LeaveRoomMsg();
				msg2.setUser(user);
				for (Client c : clientList) {
					if (c.getUser().getRoomId() == msg.getUser().getRoomId()) {
						send(ds, c, msg);
						send(ds, c, msg2);
					}
				}
			}
			for (int i = 0; i < clientList.size(); i++) {
				if (clientList.get(i).getUser().equals(user)) {
					clientList.remove(i);
					break;
				}
			}
		}
	}

	class UDPThread implements Runnable {
		private byte[] buf = new byte[40960];

		public void run() {
			try {
				ds = new DatagramSocket(Integer.parseInt(properties
						.get("SERVER_UDP_PORT")));
			} catch (SocketException e) {
				e.printStackTrace();
			}
			while (ds != null) {
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				try {
					ds.receive(dp);
					ByteArrayInputStream bais = new ByteArrayInputStream(buf,
							0, dp.getLength());
					ObjectInputStream ois = new ObjectInputStream(bais);
					Msg msg = (Msg) ois.readObject();
					msg.process(Server.this);
					for (Client c : clientList) {
						if ((c.getUser().getRoomId() == msg.getUser()
								.getRoomId()
								&& c.getUser().getDeskId() == msg.getUser()
										.getDeskId() && msg.getUser()
								.getDeskId() != 0)
								|| c.getUser().equals(msg.getUser())) {
							send(ds, c, msg);
						}
					}
					if (getClientUserByUser(msg.getUser()) == null) {
						Client c = new Client(msg.getIp(), msg.getPort(), null);
						send(ds, c, msg);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void sendMsgByRoom(Msg msg) {
		for (Client c : clientList) {
			if (c.getUser().getRoomId() == msg.getUser().getRoomId()
					&& !c.getUser().equals(msg.getUser())) {
				send(ds, c, msg);
			}
		}
	}

	public void send(DatagramSocket ds, Client c, Object o) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(40960);
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(o);
			oos.flush();
			byte[] buf2 = baos.toByteArray();
			DatagramPacket dp = new DatagramPacket(buf2, buf2.length);
			dp.setSocketAddress(new InetSocketAddress(c.getIp(), c.getPort()));
			ds.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class Client {
		private User user;
		private int port;
		private String ip;

		public Client(String ip, int port, User user) {
			this.user = user;
			this.port = port;
			this.ip = ip;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
	}

	public List<Client> getClientList() {
		return clientList;
	}

	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public User getClientUserByUser(User user) {
		for (Client c : clientList) {
			if (c.getUser().equals(user)) {
				return c.getUser();
			}
		}
		return null;
	}

	public List<User> getUserListByDesk(User user) {
		List<User> userList = new ArrayList<User>();
		for (Client c : clientList) {
			if ((c.getUser().getRoomId() == user.getRoomId() && c.getUser()
					.getDeskId() == user.getDeskId())
					&& !c.getUser().equals(user)) {
				userList.add(c.getUser());
			}
		}
		return userList;
	}

	public List<User> getUserListByRoom(User user) {
		List<User> userList = new ArrayList<User>();
		for (Client c : clientList) {
			if ((c.getUser().getRoomId() == user.getRoomId())
					&& !c.getUser().equals(user)) {
				userList.add(c.getUser());
			}
		}
		return userList;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}
