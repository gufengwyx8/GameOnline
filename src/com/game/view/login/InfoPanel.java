package com.game.view.login;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.game.main.GameClient;
import com.game.model.User;
import com.game.msg.Msg;
import com.game.msg.RegMsg;

public class InfoPanel extends JPanel {

	private ImageIcon background;
	private LoginFrame parent;
	private JLabel name, keyword, sex;
	private JTextField Name, Keyword;
	private JRadioButton boybutton, girlbutton;
	private GameButton button;

	public InfoPanel(LoginFrame p) {
		parent = p;
		SetWindow();
	}

	private void SetWindow() {
		this.setLayout(null);
		this.setPreferredSize(new Dimension(350, 325));

		name = new JLabel();
		name.setText("用户姓名：");
		name.setBounds(10, 10, 120, 30);
		name.setFont(new Font("宋体", 1, 18));
		this.add(name);

		Name = new JTextField();
		Name.setBounds(105, 12, 230, 30);
		this.add(Name);

		keyword = new JLabel();
		keyword.setText("用户密码：");
		keyword.setBounds(10, 90, 100, 30);
		keyword.setFont(new Font("宋体", 1, 18));
		this.add(keyword);

		Keyword = new JTextField();
		Keyword.setBounds(105, 92, 230, 30);
		this.add(Keyword);

		sex = new JLabel();
		sex.setText("用户性别：");
		sex.setBounds(10, 170, 100, 30);
		sex.setFont(new Font("宋体", 1, 18));
		this.add(sex);

		boybutton = new JRadioButton();
		boybutton.setText("男");
		boybutton.setBounds(120, 170, 50, 30);
		boybutton.setBackground(Color.white);
		boybutton.setSelected(true);
		this.add(boybutton);
		boybutton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				girlbutton.setSelected(false);
			}
		});

		girlbutton = new JRadioButton();
		girlbutton.setText("女");
		girlbutton.setBounds(170, 170, 50, 30);
		girlbutton.setBackground(Color.white);
		this.add(girlbutton);
		girlbutton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				boybutton.setSelected(false);
			}
		});

		button = new GameButton();
		button.SetReleasePicture("picture\\注册按钮.png");
		button.SetPressPicture("picture\\注册按钮.png");
		button.SetMovePicture("picture\\注册按钮.png");
		button.SetRect(true);
		button.setBounds(95, 230, 174, 48);
		this.add(button);
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				User user = new User();
				user.setName(Name.getText());
				user.setPassword(Keyword.getText());
				if (boybutton.isSelected()) {
					user.setSex(1);
				} else if (girlbutton.isSelected()) {
					user.setSex(2);
				}
				Msg msg = new RegMsg();
				msg.setUser(user);
				msg.setIp(GameClient.getInstance().getNetClient().getIp());
				msg.setPort(GameClient.getInstance().getNetClient()
						.getUdpPort());
				msg.send();
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		background = new ImageIcon("picture\\注册背景.png");
		g.drawImage(background.getImage(), 0, 0, null);
	}

	public void setParent(LoginFrame parent) {
		this.parent = parent;
	}
	
	public LoginFrame d(){
		return parent;
	}
}
