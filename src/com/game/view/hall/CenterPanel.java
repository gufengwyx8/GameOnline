package com.game.view.hall;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CenterPanel extends JPanel {

	private ImageIcon background;
	private HallList halllist;
	private JScrollPane jscrollpane;
	private ExplainPanel explainpanel;
	private MainFrame parent;

	public CenterPanel(MainFrame p) {
		parent = p;
		SetWindow();
	}

	public void SetWindow() {
		this.setLayout(null);
		this.setPreferredSize(new Dimension(1024, 647));

		halllist = new HallList(parent);
		halllist.setBounds(4, 0, 267, 643);
		halllist.setVisible(true);
		this.add(halllist);

		explainpanel = new ExplainPanel();
		explainpanel.setVisible(true);
		jscrollpane = new JScrollPane(explainpanel);
		jscrollpane.setBounds(270, 0, 750, 642);
		jscrollpane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(jscrollpane);
	}

	public void AddHallParentItem(String name) {
		halllist.AddItem(name);
	}

	public void ListChangeSize() {
		halllist.ChangePerSize();
	}

	public void AddHallChildItem(String parentname, String name, int num,
			int chairnum) {
		if (halllist.GetItem(num) != null) {
			halllist.GetItem(num).AddItem(name, 57, 0, 100, 20, chairnum);
			HousePanel housepanel = new HousePanel(parent);
			housepanel.setBounds(0, 121, 1024, 647);
			housepanel.setVisible(true);
			housepanel.SetPath(parentname + ">" + name);
			housepanel.AddDesk(chairnum);
			housepanel.setVisible(false);
			parent.add(housepanel);
			parent.GetHousePanelList().add(housepanel);
		}
	}

	public HallList GetHallList() {
		return halllist;
	}

	protected void paintComponent(Graphics g) {
		background = new ImageIcon("picture\\pic\\主要面板.png");
		g.drawImage(background.getImage(), 0, 0, null);
	}

	private class ExplainPanel extends JPanel {

		public ExplainPanel() {
			this.setLayout(null);
			this.setPreferredSize(new Dimension(750, 2745));
		}

		protected void paintComponent(Graphics g) {
			background = new ImageIcon("picture\\pic\\说明.png");
			g.drawImage(background.getImage(), 0, 0, null);
		}
	}
}
