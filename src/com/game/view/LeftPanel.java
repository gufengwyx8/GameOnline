package com.game.view;
import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel{
    private MainGameFrame parent;
    private ChatPanel chatpanel;
    private UserPanel userpanel;

    public LeftPanel(MainGameFrame p){
        parent=p;
        SetWindow();
    }

    private void SetWindow(){
        this.setLayout(new BorderLayout());

        userpanel=new UserPanel(parent);
        this.add(userpanel,BorderLayout.CENTER);
        userpanel.setVisible(true);

        chatpanel=new ChatPanel(parent);
        this.add(chatpanel,BorderLayout.SOUTH);
        chatpanel.setVisible(true);
    }

    public UserPanel GetUserPanel(){
        return userpanel;
    }

	public ChatPanel getChatpanel() {
		return chatpanel;
	}

	public UserPanel getUserpanel() {
		return userpanel;
	}
}
