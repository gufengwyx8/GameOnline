package com.game.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.game.main.GameClient;
import com.game.model.item.Item;
import com.game.msg.ItemMsg;

public class ToolPanel extends JPanel {

	public static final int GAME_ITEM_COUNT = 5;

	private MainGameFrame parent;
	private ImageIcon background;
	private Item[] gameItems = new Item[GAME_ITEM_COUNT];
	private JLabel[] gameItemLabels = new JLabel[GAME_ITEM_COUNT];

	public ToolPanel(MainGameFrame p) {
		parent = p;
		SetWindow();
	}
	
	private void SetWindow() {
		this.setPreferredSize(new Dimension(564, 42));
		this.setLayout(null);
		for (int i = 0; i < GAME_ITEM_COUNT; i++) {
			gameItemLabels[i] = new JLabel();
			gameItemLabels[i].setBounds(i * 40 + 57, 5, 28, 31);
			gameItemLabels[i].addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					for (int i = 0; i < GAME_ITEM_COUNT; i++) {
						if (gameItemLabels[i] == e.getSource()) {
							if (gameItems[i] != null) {
								ItemMsg msg = new ItemMsg();
								msg.setItem(gameItems[i]);
								msg.setUser(GameClient.getInstance().getUser());
								msg.send();
							}
						}
					}
				}
			});
			this.add(gameItemLabels[i]);
		}
	}

	public void paintComponent(Graphics g) {
		background = new ImageIcon("picture\\ToolPanel±³¾°.png");
		g.drawImage(background.getImage(), 0, 0, null);
	}

	public Item[] getGameItems() {
		return gameItems;
	}

	public void addItem(Item item) {
		for (int i = 0; i < GAME_ITEM_COUNT; i++) {
			if (gameItems[i] == null) {
				setGameItems(item, i);
				break;
			}
		}
	}

	public void setGameItems(Item gameItem, int index) {
		this.gameItems[index] = gameItem;
		if (gameItem != null) {
			gameItemLabels[index]
					.setIcon(new ImageIcon(gameItem.getImagePath()));
			gameItemLabels[index].setVisible(true);
		} else {
			gameItemLabels[index].setVisible(false);
		}
		parent.validate();
		parent.repaint();
	}

	public void removeItem(Item item) {
		for (int i = 0; i < GAME_ITEM_COUNT; i++) {
			if (item.equals(gameItems[i])) {
				setGameItems(null, i);
				break;
			}
		}
	}
}
