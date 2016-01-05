package com.game.view.login;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CenterPanel extends JPanel {

	private JLabel picturebox, Name, Keyword, Rem, Auto;
	private Icon picture;
	private JTextField name;
	private JPasswordField keyword;
	private ImageIcon background;
	private GameButton post;
	private GameRadioButton remember, auto;
	private LoginFrame parent;

	public CenterPanel(LoginFrame p) {
		parent = p;
		SetWindow();
	}

	private void SetWindow() {
		this.setPreferredSize(new Dimension(358, 176));
		this.setBackground(Color.WHITE);
		this.setLayout(null);

		picture = new ImageIcon("picture\\图片.png");
		picturebox = new JLabel();
		picturebox.setIcon(picture);
		picturebox.setBounds(0, 0, 358, 63);
		this.add(picturebox);

		Name = new JLabel("账号:");
		Name.setBounds(15, 69, 30, 30);
		this.add(Name);

		Keyword = new JLabel("密码:");
		Keyword.setBounds(15, 104, 30, 30);
		this.add(Keyword);

		Rem = new JLabel("记住账号");
		Rem.setBounds(102, 137, 100, 30);
		this.add(Rem);

		Auto = new JLabel("自动登录");
		Auto.setBounds(202, 137, 100, 30);
		this.add(Auto);

		name = new JTextField();
		name.setBounds(50, 73, 230, 25);
		this.add(name);

		keyword = new JPasswordField();
		keyword.setBounds(50, 109, 230, 25);
		keyword.setEchoChar('*');
		this.add(keyword);

		post = new GameButton();
		post.SetReleasePicture("picture\\注册(释放).png");
		post.SetPressPicture("picture\\注册(移动).png");
		post.SetMovePicture("picture\\注册(移动).png");
		post.setBounds(290, 72, 52, 26);
		this.add(post);
		post.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				parent.SetPostVisited(true);
			}
		});

		remember = new GameRadioButton();
		remember.SetReleasePicture("picture\\单选按钮(未选).png");
		remember.SetPressPicture("picture\\单选按钮(选中).png");
		remember.SetMovePicture("picture\\单选按钮(移动).png");
		remember.setBounds(80, 145, 17, 17);
		this.add(remember);
		remember.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
			}
		});

		auto = new GameRadioButton();
		auto.SetReleasePicture("picture\\单选按钮(未选).png");
		auto.SetPressPicture("picture\\单选按钮(选中).png");
		auto.SetMovePicture("picture\\单选按钮(移动).png");
		auto.setBounds(180, 145, 17, 17);
		this.add(auto);
		auto.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		background = new ImageIcon("picture\\背景.png");
		g.drawImage(background.getImage(), 0, 0, null);
	}

	public String getUserName() {
		return name.getText();
	}

	public String getKeyword() {
		return new String(keyword.getPassword());
	}

	public GameRadioButton getRemember() {
		return remember;
	}

	public void setRemember(GameRadioButton remember) {
		this.remember = remember;
	}

	public GameRadioButton getAuto() {
		return auto;
	}

	public void setAuto(GameRadioButton auto) {
		this.auto = auto;
	}
	
}