package com.game.view.hall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TitlePanel extends JPanel {

	private ImageIcon background = new ImageIcon("picture\\pic\\标题栏背景.png");
	private StatePanel statepanel;
	private GameButton exit;
	private MainFrame parent;

	public TitlePanel(MainFrame p) {
		parent = p;
		SetWindow();
	}

	private void SetWindow() {
		this.setPreferredSize(new Dimension(1024, 121));
		this.setLayout(null);

		statepanel = new StatePanel(parent);
		statepanel.setBounds(322, 25, 380, 60);
		statepanel.setVisible(true);
		this.add(statepanel);

		exit = new GameButton();
		exit.SetReleasePicture("picture\\pic\\关闭按钮(未选中).png");
		exit.SetPressPicture("picture\\pic\\关闭按钮(按下).png");
		exit.SetMovePicture("picture\\pic\\关闭按钮(选中).png");
		exit.setBounds(977, 0, 47, 20);
		this.add(exit);
		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				System.exit(0);
			}
		});
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, null);
	}

	public StatePanel getStatepanel() {
		return statepanel;
	}

	public void setStatepanel(StatePanel statepanel) {
		this.statepanel = statepanel;
	}
}
