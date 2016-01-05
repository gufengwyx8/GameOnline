package com.game.view.hall;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.game.main.GameClient;
import com.game.model.User;

public class PlayerList extends JPanel {

	private ImageIcon background;
	private JTable playerlist;
	private JScrollPane scrollPane;
	private MyTableModel model;
	private Vector<String> columnNames;

	public PlayerList() {
		SetWindow();
	}

	private void SetWindow() {
		this.setLayout(null);
		this.setPreferredSize(new Dimension(320, 234));

		columnNames = new Vector<String>();
		columnNames.add("头像");
		columnNames.add("昵称");
		columnNames.add("级别");
		columnNames.add("积分");
		columnNames.add("胜率");
		model = new MyTableModel(null, columnNames);
		playerlist = new JTable(model);
		playerlist.setVisible(true);
		scrollPane = new JScrollPane(playerlist);
		playerlist.setFillsViewportHeight(true);
		scrollPane.setBounds(2, 37, 323, 204);
		this.add(scrollPane);

	}

	protected void paintComponent(Graphics g) {
		background = new ImageIcon("picture\\pic\\玩家列表背景.png");
		g.drawImage(background.getImage(), 0, 0, null);
	}

	public void AddPlayer(User player) {
		ImageIcon icon = new ImageIcon(player.getListpath());

		Vector<Object> rowData = new Vector<Object>();
		rowData.add(icon);
		rowData.add(player.getName());
		rowData.add(GameClient.getInstance().getLevelStr(player));
		rowData.add(String.valueOf(player.getScore()));
		rowData.add(String.valueOf(player.getRate() + "%"));
		Vector<Object> cells = new Vector<Object>();
		cells.add(rowData);

		model.addRow(rowData);
	}

	public void DeletePlayer(int num) {
		if (num < model.getRowCount()) {
			model.removeRow(num);
		}
	}

	public void ClearPlayer() {
		for (int i = 0; 0 < model.getRowCount();) {
			model.removeRow(i);
		}
	}

	class MyTableModel extends DefaultTableModel {

		@SuppressWarnings("unchecked")
		public MyTableModel(Vector cells, Vector columnNames) {
			super(cells, columnNames);
		}

		@SuppressWarnings("unchecked")
		public Class getColumnClass(int col) {
			Vector<Object> v = (Vector<Object>) super.dataVector.elementAt(0);
			if (v.elementAt(col) != null) {
				return v.elementAt(col).getClass();
			} else {
				return "".getClass();
			}
		}

		public boolean isCellEditable(int row, int col) {
			return false;
		}
	}
}
