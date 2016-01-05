package com.game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.game.model.item.Item;

public class Board implements Serializable {
	private static final Random r = new Random();

	private Chess[][] arr;
	private int width, height;
	private List<Item> items = new ArrayList<Item>();

	public Chess[][] initBoard(int width, int height, int mimeCount) {
		this.width = width;
		this.height = height;
		arr = new Chess[width][height];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				arr[i][j] = new Chess();
			}
		}

		putMime(mimeCount);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (!arr[i][j].isMime()) {
					arr[i][j].setNum(countMime(i, j));
				}
			}
		}

		Item[] items = Item.createItems(5);
		for (Item i : items) {
			int x = -1, y = -1;
			while (getChess(x, y) == null || getChess(x, y).isMime()
					|| getChess(x, y).getItem() != null) {
				x = r.nextInt(width);
				y = r.nextInt(height);
			}
			getChess(x, y).setItem(i);
		}

		return arr;
	}

	public void putMime(int mimeCount) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < mimeCount; i++) {
			int x = r.nextInt(arr.length);
			int y = r.nextInt(arr[x].length);
			if (!arr[x][y].isMime()) {
				arr[x][y].setMime(true);
			} else {
				i--;
			}
		}
	}

	public int countMime(int x, int y) {
		int num = 0;
		for (Chess c : getAroundChess(x, y)) {
			if (c.isMime()) {
				num++;
			}
		}
		return num;
	}

	public boolean isValid(int x, int y) {
		if (x < 0 || x >= arr.length || y < 0 || y >= arr[x].length) {
			return false;
		}
		return true;
	}

	public List<Chess> getAroundChess(int x, int y) {
		List<Chess> aroundChess = new ArrayList<Chess>();
		if (isValid(x - 1, y - 1)) {
			aroundChess.add(arr[x - 1][y - 1]);
		}
		if (isValid(x - 1, y)) {
			aroundChess.add(arr[x - 1][y]);
		}
		if (isValid(x - 1, y + 1)) {
			aroundChess.add(arr[x - 1][y + 1]);
		}
		if (isValid(x, y - 1)) {
			aroundChess.add(arr[x][y - 1]);
		}
		if (isValid(x, y + 1)) {
			aroundChess.add(arr[x][y + 1]);
		}
		if (isValid(x + 1, y - 1)) {
			aroundChess.add(arr[x + 1][y - 1]);
		}
		if (isValid(x + 1, y)) {
			aroundChess.add(arr[x + 1][y]);
		}
		if (isValid(x + 1, y + 1)) {
			aroundChess.add(arr[x + 1][y + 1]);
		}
		return aroundChess;
	}

	public boolean open(int x, int y, int depth) {
		if (depth <= 0) {
			items.clear();
		}
		if (!isValid(x, y)) {
			return true;
		}
		if (arr[x][y].isOpen()) {
			return true;
		}
		if (arr[x][y].isFlag()) {
			return true;
		}
		arr[x][y].setOpen(true);
		if (arr[x][y].getItem() != null) {
			items.add(arr[x][y].getItem());
		}
		if (depth == 0 && arr[x][y].isMime()) {
			return false;
		}
		if (arr[x][y].getNum() == 0) {
			open(x - 1, y - 1, depth + 1);
			open(x - 1, y, depth + 1);
			open(x - 1, y + 1, depth + 1);
			open(x, y - 1, depth + 1);
			open(x, y + 1, depth + 1);
			open(x + 1, y - 1, depth + 1);
			open(x + 1, y, depth + 1);
			open(x + 1, y + 1, depth + 1);
		}
		return true;
	}

	public boolean openWithDetector(int x, int y) {
		if (!isValid(x, y)) {
			return true;
		}
		if (arr[x][y].isFlag()) {
			return true;
		}
		if (!arr[x][y].isOpen()) {
			return true;
		}
		items.clear();
		List<Chess> aroundChess = this.getAroundChess(x, y);
		boolean noFlag = false;
		for (Chess c : aroundChess) {
			if (!c.isFlag() && c.isMime()) {
				noFlag = true;
			} else if (c.isFlag() && !c.isMime()) {
				return false;
			}
		}
		if (!noFlag) {
			for (Chess c : aroundChess) {
				if (!c.isMime()) {
					c.setOpen(true);
					if (c.getItem() != null) {
						items.add(c.getItem());
					}
				}
			}
		}

		return true;
	}

	public int getGoldCount() {
		int num = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (arr[i][j].isOpen() && !arr[i][j].isMime()) {
					num++;
				}
			}
		}
		return num;
	}

	public boolean isWin() {
		boolean flag = true;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (arr[i][j].isMime() && !arr[i][j].isFlag()) {
					flag = false;
					break;
				}
			}
			if (!flag) {
				break;
			}
		}
		if (flag) {
			return true;
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (!arr[i][j].isMime() && !arr[i][j].isOpen()) {
					return false;
				}
			}
		}
		return true;
	}

	public void showAllMime() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				arr[i][j].setOpen(true);
			}
		}
	}

	public void openAllChess() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (arr[i][j].isMime()) {
					arr[i][j].setFlag(true);
				} else if (!arr[i][j].isMime()) {
					arr[i][j].setOpen(true);
				}
			}
		}
	}

	public void setFlag(int x, int y) {
		if (!arr[x][y].isOpen()) {
			arr[x][y].setFlag(!arr[x][y].isFlag());
		}
	}

	public Chess getChess(int x, int y) {
		if (!isValid(x, y)) {
			return null;
		}
		return arr[x][y];
	}

	public Chess[][] getArr() {
		return arr;
	}

	public void setArr(Chess[][] arr) {
		this.arr = arr;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
