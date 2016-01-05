package com.game.service;

import com.game.dao.UserDao;
import com.game.model.User;

public class UserManager {
	private UserDao userDao = new UserDao();

	public User login(String name, String password) {
		return userDao.login(name, password);
	}

	public boolean reg(User user) {
		return userDao.reg(user);
	}

	public boolean update(User user) {
		return userDao.update(user);
	}
}
