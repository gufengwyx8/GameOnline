package com.game.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.game.main.GameClient;
import com.game.msg.ChatMsg;
import com.game.msg.Msg;

public class ChatPanel extends JPanel {

	private MainGameFrame parent;
	private ImageIcon background;
	private WritePanel writepanel;
	private JTextArea show;
	private JScrollPane pane;
	private JTextField textfield;

	public ChatPanel(MainGameFrame p) {
		parent = p;
		SetWindow();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		background = new ImageIcon("picture\\ChatPanel背景.jpg");
		g.drawImage(background.getImage(), 0, 0, null);
	}

	private void SetWindow() {
		this.setPreferredSize(new Dimension(230, 170));
		this.setLayout(null);

		show = new JTextArea(5, 5);
		show.setWrapStyleWord(true);
		show.setBackground(new Color(64, 96, 144));
		show.setEditable(false);
		pane = new JScrollPane(show, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setPreferredSize(new Dimension(210, 116));
		pane.setBorder(null);
		pane.setBounds(10, 13, 210, 116);
		this.add(pane);

		writepanel = new WritePanel(parent);
		writepanel.setBounds(0, 135, 230, 35);
		this.add(writepanel);
		writepanel.setVisible(true);
	}

	public void addMsg(String msg) {
		show.setText(show.getText() + msg + "\n");
		show.selectAll();
	}

	public void sendMsg() {
		Msg msg = new ChatMsg(textfield.getText());
		msg.setUser(parent.GetPlayerInfo(0));
		GameClient.getInstance().getNetClient().send(msg);
		textfield.setText("");
	}

	private class WritePanel extends JPanel {

		private MainGameFrame parent;
		private ImageIcon background;
		private SendButton OK;

		public WritePanel(MainGameFrame p) {
			parent = p;
			SetWindow();
		}

		private void SetWindow() {
			this.setPreferredSize(new Dimension(230, 35));
			this.setLayout(null);

			OK = new SendButton();
			OK.setBounds(190, 0, 35, 30);
			this.add(OK);

			textfield = new JTextField();
			textfield.setBounds(10, 6, 175, 20);
			this.add(textfield);
			textfield.addActionListener(new Monitor());
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			background = new ImageIcon("picture\\WritePanel背景.png");
			g.drawImage(background.getImage(), 0, 0, null);
		}

		class Monitor implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				sendMsg();
			}
		}

		public MainGameFrame getParent() {
			return parent;
		}
	}

	private class SendButton extends JButton {

		private int picture;
		private ImageIcon background;

		public SendButton() {
			picture = 0;
			this.setSize(35, 30);
			setContentAreaFilled(false);
			this.addMouseListener(new MouseAdapter() {

				public void mousePressed(MouseEvent evt) {
					MousePressed(evt);
				}

				public void mouseReleased(MouseEvent evt) {
					MouseReleased(evt);
				}
			});
		}

		protected void paintComponent(java.awt.Graphics g) {
			if (picture == 0) {
				background = new ImageIcon("picture\\发送Button(按下).png");
			} else {
				background = new ImageIcon("picture\\发送Button(释放).png");
			}
			g.drawImage(background.getImage(), 0, 0, null);
		}

		private void MousePressed(MouseEvent evt) {
			picture = 1;
			repaint();
		}

		private void MouseReleased(MouseEvent evt) {
			picture = 0;
			repaint();

			sendMsg();
		}
	}
}
