package com.game.model;

import java.io.Serializable;

import com.game.model.item.Item;

public class Chess implements Serializable {

	public static final int MIME_VALUE = -1;

	private int x, y;
	private int num;
	private boolean flag, open;
	private Item item;

	public Chess() {

	}

	public Chess(int x, int y, int num) {
		this.x = x;
		this.y = y;
		this.num = num;
		this.flag = false;
		this.open = false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isMime() {
		return num == MIME_VALUE;
	}

	public void setMime(boolean isMime) {
		if (isMime) {
			num = MIME_VALUE;
		} else {
			num = 0;
		}
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
}
