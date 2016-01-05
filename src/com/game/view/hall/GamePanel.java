package com.game.view.hall;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GamePanel extends JPanel {

	private ImageIcon background;
	private ArrayList<Desk> desklist;
	private MainFrame parent;

	public GamePanel(MainFrame p) {
		parent = p;
		SetWindow();
	}

	private void SetWindow() {
		this.setLayout(null);
		this.setPreferredSize(new Dimension(692, 616));
		desklist = new ArrayList<Desk>();
	}

	public void AddDesk() {
		Desk desk = new Desk(parent);
		desk.SetDeskNum(desklist.size() + 1);
		desk.setVisible(true);
		desklist.add(desk);
		int y = (desklist.size() - 1) / 4;
		int x = (desklist.size() - 1) % 4;
		desk.setBounds(x * 170, y * 170, 170, 170);
		desk.CreatChair(desklist.size());
		this.add(desk);
	}

	public Desk GetDesk(int num) {
		if (num >= 0 && num < desklist.size()) {
			return desklist.get(num);
		}
		return null;
	}

	public void ChangeToPerSize() {
		int y = (desklist.size() - 1) / 4;
		if (y * 170 + 160 > this.getHeight()) {
			this.setPreferredSize(new Dimension(692, y * 170 + 170));
		}
	}

	public void DeleteDesk(int num) {
		if (num >= 0 && num < desklist.size()) {
			desklist.remove(num);
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		background = new ImageIcon("picture\\pic\\ÓÎÏ·Ãæ°å±³¾°.png");
		g.drawImage(background.getImage(), 0, 0, null);
	}
}
