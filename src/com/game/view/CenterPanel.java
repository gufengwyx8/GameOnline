package com.game.view;

import javax.swing.*;
import java.awt.*;

public class CenterPanel extends JPanel{
    private MainGameFrame parent;
    private UpPanel uppanel;
    private StatePanel statepanel;

    public CenterPanel(MainGameFrame p){
        parent=p;
        SetWindow();
    }

    private void SetWindow(){
        this.setLayout(new BorderLayout());
        this.setSize(570, 575);

        uppanel=new UpPanel(parent);
        this.add(uppanel,BorderLayout.CENTER);
        uppanel.setVisible(true);

        statepanel=new StatePanel(parent);
        this.add(statepanel,BorderLayout.SOUTH);
        statepanel.setVisible(true);
    }

    public StatePanel GetStatePanel(){
        return statepanel;
    }

    public UpPanel GetUpPanel(){
        return uppanel;
    }
}
