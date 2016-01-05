package com.game.view;
import javax.swing.UIManager;
public class Type {
    public Type(){
        String type = UIManager.getSystemLookAndFeelClassName();
        if (!UIManager.getLookAndFeel().getName().equals(type)) {
            try {
                UIManager.setLookAndFeel(type);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}