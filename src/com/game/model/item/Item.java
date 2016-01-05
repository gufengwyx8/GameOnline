package com.game.model.item;

import java.io.Serializable;
import java.util.Random;

import com.game.main.GameClient;
import com.game.model.User;

public abstract class Item implements Serializable {

	public static final Item[] ITEM_ARR = new Item[] { new GoldOneItem(),
			new GoldTwoItem(), new CloseItem(), new SpeedItem(),
			new BlindItem(), new FindMimeItem(), new StopItem() };

	public abstract String getImagePath();

	public abstract void process(User user, GameClient client);

	public boolean equals(Object o) {
		if (!(o instanceof Item)) {
			return false;
		}
		return getImagePath().equals(((Item) o).getImagePath());
	}

	public static Item[] createItems(int count) {
		Item[] arr = new Item[count];
		Random r = new Random();
		for (int i = 0; i < count; i++) {
			int id = r.nextInt(ITEM_ARR.length);
			arr[i] = ITEM_ARR[id];
		}
		return arr;
	}
}
