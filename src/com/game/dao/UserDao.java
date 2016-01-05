package com.game.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.game.model.User;

public class UserDao extends BaseDao {
	public User login(String name, String password) {
		Connection conn = getConnection();
		String sql = "select * from user where name = ? and password = ?";
		PreparedStatement ps = prepare(conn, sql);
		ResultSet rs = null;
		User user = null;
		try {
			ps.setString(1, name);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setScore(rs.getInt("score"));
				user.setSex(rs.getInt("sex"));
				user.setWin(rs.getInt("win"));
				user.setLost(rs.getInt("lost"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(ps);
			close(conn);
		}
		return user;
	}

	public boolean reg(User user) {
		Connection conn = getConnection();
		String sql = "select * from user where name = ?";
		PreparedStatement ps = prepare(conn, sql);
		ResultSet rs = null;
		try {
			ps.setString(1, user.getName());
			rs = ps.executeQuery();
			if (rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			close(rs);
			close(ps);
			close(conn);
		}

		conn = getConnection();
		sql = "insert into user values (null,?,?,0,?,0,0)";
		ps = prepare(conn, sql);
		try {
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getSex());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			close(ps);
			close(conn);
		}
	}

	public boolean update(User user) {
		Connection conn = getConnection();
		String sql = "update user set score = ? , win = ? , lost = ? where id = ?";
		PreparedStatement ps = prepare(conn, sql);
		try {
			ps.setInt(1, user.getScore());
			ps.setInt(2, user.getWin());
			ps.setInt(3, user.getLost());
			ps.setInt(4, user.getId());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			close(ps);
			close(conn);
		}
	}
}
