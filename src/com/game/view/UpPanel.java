package com.game.view;

import javax.swing.*;

import com.game.main.GameClient;

import java.awt.*;

public class UpPanel extends JPanel {

	private MainGameFrame parent;
	private GamePanel gamepanel;
	private ToolPanel toolpanel;
	private ImageIcon background;
	private Dimension dimension;
	private JLabel time, gold;

	public UpPanel(MainGameFrame p) {
		parent = p;
		SetWindow();
	}

	private void SetWindow() {
		dimension = new Dimension(570, 530);
		this.setLayout(null);
		this.setPreferredSize(dimension);

		gamepanel = new GamePanel(parent);
		gamepanel.setBounds(12, 9, 460, 460);
		this.add(gamepanel);
		gamepanel.setVisible(false);

		toolpanel = new ToolPanel(parent);
		toolpanel.setBounds(4, 478, 564, 42);
		toolpanel.setVisible(true);
		this.add(toolpanel);

		time = new JLabel();
		time.setText("0");
		time.setBounds(505, 71, 100, 20);
		time.setFont(new Font("»ªÎÄçúçê", 0, 20));
		time.setForeground(new Color(145, 224, 241));
		this.add(time);

		gold = new JLabel();
		gold.setText(String.valueOf(parent.GetTime()));
		gold.setBounds(497, 23, 100, 24);
		gold.setFont(new Font("»ªÎÄçúçê", 0, 24));
		gold.setForeground(Color.WHITE);
		this.add(gold);
	}

	public void setGold(int num) {
		gold.setText(num + "");
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!parent.IsGameStart()) {
			background = new ImageIcon("picture\\UpPanel±³¾°1.jpg");
		} else {
			background = new ImageIcon("picture\\UpPanel±³¾°2.jpg");
		}
		g.drawImage(background.getImage(), 0, 0, null);
	}

	public void startTime() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (GameClient.getInstance().getUser().isStart()) {
					time.setText(Integer.parseInt(time.getText()) + 1 + "");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				time.setText("0");
			}
		}).start();
	}

	public void updateComponent() {

	}

	public GamePanel getGamepanel() {
		return gamepanel;
	}

	public void setGamepanel(GamePanel gamepanel) {
		this.gamepanel = gamepanel;
	}

	public ToolPanel getToolpanel() {
		return toolpanel;
	}

	public void setToolpanel(ToolPanel toolpanel) {
		this.toolpanel = toolpanel;
	}
}
