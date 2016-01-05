package com.game.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import com.game.msg.Msg;

public class NetClient {

	private int tcpPort;
	private String ip;
	private int udpPort;
	private DatagramSocket ds;
	private GameClient client;
	private Socket s;

	public NetClient(GameClient client, String ip, int tcpPort, int udpPort) {
		this.client = client;
		this.ip = ip;
		this.tcpPort = tcpPort;
		this.udpPort = udpPort;
		while (ds == null) {
			try {
				ds = new DatagramSocket(this.udpPort);
			} catch (SocketException e) {
				System.out.println("UDP端口冲突，端口加1");
				ds = null;
				this.udpPort++;
			}
		}
		new Thread(new UDPThread()).start();
	}

	public boolean connect(String name, String password) {
		boolean success = false;
		try {
			s = new Socket(ip, tcpPort);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			dos.writeInt(udpPort);
			dos.writeUTF(name);
			dos.writeUTF(password);
			dos.flush();
			Msg msg = (Msg) ois.readObject();
			if (msg.getType() == Msg.LOGIN_MSG) {
				success = true;
			}
			msg.process(client);
			if (success) {
				new Thread(new TCPThread()).start();
			}
		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(null, "未连接到服务器，请检查网络");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// } finally {
			// if (s != null) {
			// try {
			// s.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// s = null;
			// }
		}
		return success;
	}

	public void send(Msg msg) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(40960);
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(msg);
			byte[] buf = baos.toByteArray();
			DatagramPacket dp = new DatagramPacket(buf, buf.length,
					new InetSocketAddress(ip, Integer.parseInt(GameClient
							.getInstance().getProperty("SERVER_UDP_PORT"))));
			ds.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.flush();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			oos = null;
			baos = null;
		}
	}

	class TCPThread implements Runnable {

		public void run() {
			try {
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				while (true) {
					int i = dis.readInt();
					dos.writeInt(i);
					dos.flush();
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "已断开与服务器的连接");
				System.exit(0);
			}
		}
	}

	class UDPThread implements Runnable {
		private byte[] buf = new byte[40960];

		public void run() {
			while (ds != null) {
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				ObjectInputStream ois = null;
				try {
					ds.receive(dp);
					ByteArrayInputStream bais = new ByteArrayInputStream(buf,
							0, dp.getLength());
					ois = new ObjectInputStream(bais);
					Msg msg = (Msg) ois.readObject();
					msg.process(client);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						ois.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getUdpPort() {
		return udpPort;
	}

	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
	}
}
