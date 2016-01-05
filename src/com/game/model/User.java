package com.game.model;

import java.io.Serializable;

public class User implements Serializable {
	private int id;
	private String name;
	private String password;
	private int score;
	private int team = 2;
	private boolean ready = false;
	private boolean exist = false;
	private int roomId;
	private int deskId;
	private transient Board board = new Board();
	private String uuid;
	private boolean start = false;
	private int sex = -1;
	private String presspath, releasepath, movepath, listpath;
	private int win, lost, tie;

	public User() {
		presspath = "picture\\pic\\测试头像1(按下).png";
		releasepath = "picture\\pic\\测试头像1(未选中).png";
		movepath = "picture\\pic\\测试头像1(选中).png";
		listpath = "picture\\pic\\测试头像2.png";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicturepath() {
		if (sex == 2) {
			return "picture\\girl.jpg";
		}
		return "picture\\boy.jpg";
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getDeskId() {
		return deskId;
	}

	public void setDeskId(int deskId) {
		this.deskId = deskId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean equals(Object o) {
		if (!(o instanceof User)) {
			return false;
		}
		User u = (User) o;
		return id == u.id && uuid.equals(u.uuid);
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPresspath() {
		return presspath;
	}

	public void setPresspath(String presspath) {
		this.presspath = presspath;
	}

	public String getReleasepath() {
		return releasepath;
	}

	public void setReleasepath(String releasepath) {
		this.releasepath = releasepath;
	}

	public String getMovepath() {
		return movepath;
	}

	public void setMovepath(String movepath) {
		this.movepath = movepath;
	}

	public String getListpath() {
		return listpath;
	}

	public void setListpath(String listpath) {
		this.listpath = listpath;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLost() {
		return lost;
	}

	public void setLost(int lost) {
		this.lost = lost;
	}

	public int getTie() {
		return tie;
	}

	public void setTie(int tie) {
		this.tie = tie;
	}

	public int getRate() {
		if (win + lost == 0) {
			return 0;
		}
		return (int) (win * 100.0 / (win + lost));
	}
}
