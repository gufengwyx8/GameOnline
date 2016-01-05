package com.game.view.hall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class HallList extends JPanel {

    private ImageIcon background;
    private JLabel title;
    private ArrayList<Item> itemlist;
    private JPanel listpanel;
    private JScrollPane scrollpanel;
    private int Num;
    private MainFrame parent;

    public HallList(MainFrame p) {
        parent = p;
        SetWindow();
    }

    private void SetWindow() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(267, 643));
        itemlist = new ArrayList<Item>();
        Num = 0;

        title = new JLabel();
        title.setText("欢迎来到 挖金子");
        title.setBounds(15, 15, 100, 14);
        this.add(title);
        this.setBackground(Color.WHITE);

        listpanel = new JPanel();
        listpanel.setBounds(4, 40, 260, 603);
        listpanel.setVisible(true);
        listpanel.setBackground(Color.WHITE);
        listpanel.setLayout(null);

        scrollpanel = new JScrollPane();
        scrollpanel.setBounds(4, 40, 260, 603);
        scrollpanel.getViewport().setView(listpanel);
        this.add(scrollpanel);
    }

    public Item GetItem(int num) {
        if (num < itemlist.size()) {
            return itemlist.get(num);
        }
        return null;
    }

    public int GetNum() {
        return Num;
    }

    public void AddItem(String name) {
        Item item = new Item(itemlist, parent);
        listpanel.add(item);
        item.setVisible(true);
        item.SetListVisitable(false);
        item.SetTitle(name);
        itemlist.add(item);
        item.setBounds(4, (itemlist.size() - 1) * 20, 260, 20);
        item.SetNum(itemlist.size() - 1);
        Num++;
    }

    public void ChangePerSize() {
        this.setPreferredSize(new Dimension(260, 20 * Num));
    }

    public void deleteitem(int num) {
        if (num < itemlist.size()) {
            itemlist.remove(num);
        }
    }

    protected void paintComponent(Graphics g) {
        background = new ImageIcon("picture\\pic\\大厅列表背景.png");
        g.drawImage(background.getImage(), 0, 0, null);
    }

    public class Item extends JPanel {

        private boolean isselect, times;
        private ListButton titlebutton;
        private ArrayList<ListButton> listbutton;
        private int num;
        private ArrayList<Item> parent;
        private ArrayList<HousePanel> houselist;
        private ListButton item;
        private MainFrame root;

        public Item(ArrayList<Item> p, MainFrame r) {
            parent = p;
            root = r;
            this.setLayout(null);
            isselect = true;
            times = false;
            listbutton = new ArrayList<ListButton>();
            houselist = new ArrayList<HousePanel>();

            titlebutton = new ListButton(listbutton);
            titlebutton.SetReleasePicture("picture\\pic\\大厅列表(未选中).png");
            titlebutton.SetPress1Picture("picture\\pic\\大厅列表(按下).png");
            titlebutton.SetPress2Picture("picture\\pic\\大厅列表(移动).png");
            titlebutton.SetMove1Picture("picture\\pic\\大厅列表(移入).png");
            titlebutton.SetMove2Picture("picture\\pic\\大厅列表(移入下拉).png");
            titlebutton.SetRelease2Picture("picture\\pic\\大厅列表(未选中2).png");
            titlebutton.setBounds(0, 0, 260, 20);
            titlebutton.SetText("新手场", 38, 0, 100, 20);
            titlebutton.SetNumber(-1);
            this.add(titlebutton);
            titlebutton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    isselect = true;
                    if (times) {   //收缩
                        for (int i = num + 1; i < parent.size(); i++) {
                            parent.get(i).setBounds(4, i * 20, 260, 20);
                        }
                        SetListVisitable(false);
                        setBounds(4, GetNum() * 20, 260, 20);
                        titlebutton.SetPicture(2);
                    } else {    //展开
                        for (int i = 0; i < num; i++) {    //收缩之前行
                            parent.get(i).Select(false);
                            parent.get(i).SetListVisitable(false);
                            parent.get(i).setBounds(4, i * 20, 260, 20);
                            parent.get(i).SetTimes(false);
                        }

                        titlebutton.SetPicture(1);
                        setBounds(4, GetNum() * 20, 260, Size() * 20);
                        SetListVisitable(true);

                        for (int i = num + 1; i < parent.size(); i++) {   //收缩之后行
                            parent.get(i).Select(false);
                            parent.get(i).SetListVisitable(false);
                            parent.get(i).setBounds(4, (i + Size() - 1) * 20, 260, 20);
                            parent.get(i).SetTimes(false);
                        }
                    }
                    times = !times;
                }
            });

            titlebutton.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent evt) {
                    for (int j = 0; j < parent.size(); j++) {
                        if (j != num) {
                            parent.get(j).GetTitleButton().SetPicture(0);
                            parent.get(j).SetListVisitable(false);
                            parent.get(j).Select(false);
                        }
                    }
                    repaint();
                }
            });
        }

        public void SetTimes(boolean bool) {
            times = bool;
        }

        public void SetNum(int n) {
            num = n;
        }

        public int GetNum() {
            return num;
        }

        public int Size() {
            return 1 + listbutton.size();
        }

        public ListButton GetChild(int num){
            if(num<listbutton.size())
                return listbutton.get(num);
            return null;
        }

        public void AddItem(String name, int x, int y, int weight, int height,int chairnum) {
            item = new ListButton(listbutton);
            item.SetReleasePicture("picture\\pic\\item(未选中).png");
            item.SetPress1Picture("picture\\pic\\item(移动).png");
            item.SetPress2Picture("picture\\pic\\item(移动).png");
            item.SetMove1Picture("picture\\pic\\item(移入).png");
            item.setBounds(0, (listbutton.size() + 1) * 20, 260, 20);
            item.SetText(name, x, y, weight, height);
            listbutton.add(item);
            item.SetNumber(listbutton.size() - 1);
            this.add(item);
            Num++;

            item.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent evt) {
                    titlebutton.SetPicture(4);
                    Select(false);
                }
            });
        }

        public ListButton GetTitleButton() {
            return titlebutton;
        }

        public ListButton GetItem(int i) {
            if (i < listbutton.size()) {
                return listbutton.get(i);
            }
            return null;
        }

        public void RemoveItem(int i) {
            listbutton.remove(i);
        }

        public boolean IsSelect() {
            return isselect;
        }

        public void Select(boolean bool) {
            isselect = bool;
        }

        public void SetListVisitable(boolean bool) {
            if (bool) {
                for (int i = 0; i < listbutton.size(); i++) {
                    this.add(listbutton.get(i));
                }
            } else {
                for (int i = 0; i < listbutton.size(); i++) {
                    this.remove(listbutton.get(i));
                }
            }
            repaint();
        }

        public void SetTitle(String str) {
            titlebutton.SetText(str, 38, 0, 100, 20);
        }

		public ArrayList<HousePanel> getHouselist() {
			return houselist;
		}

		public void setHouselist(ArrayList<HousePanel> houselist) {
			this.houselist = houselist;
		}

		public MainFrame getRoot() {
			return root;
		}

		public void setRoot(MainFrame root) {
			this.root = root;
		}
    }

    public class ListButton extends JButton {

        private int picture;
        private ImageIcon background;
        private String presspath1, presspath2, releasepath, move1path, release2path, move2path;
        private boolean num;
        private JLabel title;
        private int number;
        private ArrayList<ListButton> parent;

        public ListButton(ArrayList<ListButton> p) {
            parent = p;
            picture = 0;
            presspath1 = "";
            releasepath = "";
            num = true;
            number = 0;
            setContentAreaFilled(false);
            this.setLayout(null);

            title = new JLabel();
            title.setText("listbutton");
            title.setBounds(38, 0, 100, 20);
            this.add(title);

            this.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent evt) {
                    MousePressed(evt);
                }

                public void mouseEntered(MouseEvent evt) {
                    MouseEntered(evt);
                }

                public void mouseExited(MouseEvent evt) {
                    MouseExited(evt);
                }
            });
        }

        public void SetNumber(int n) {
            number = n;
        }

        public int GetPicture() {
            return picture;
        }

        public int GetNumber() {
            return number;
        }

        public void SetText(String str, int x, int y, int weight, int height) {
            title.setText(str);
            title.setBounds(x, y, weight, height);
            repaint();
        }

        public void SetPicture(int num) {
            picture = num;
            repaint();
        }

        public void SetPress1Picture(String path) {
            presspath1 = path;
        }

        public void SetPress2Picture(String path) {
            presspath2 = path;
        }

        public void SetReleasePicture(String path) {
            releasepath = path;
        }

        public void SetRelease2Picture(String path) {
            release2path = path;
        }

        public void SetMove1Picture(String path) {
            move1path = path;
        }

        public void SetMove2Picture(String path) {
            move2path = path;
        }

        protected void paintComponent(java.awt.Graphics g) {
            if (picture == 0) {
                background = new ImageIcon(releasepath);
            } else if (picture == 1) {
                background = new ImageIcon(presspath1);
            } else if (picture == 2) {
                background = new ImageIcon(move1path);
            } else if (picture == 3) {
                background = new ImageIcon(presspath2);
            } else if (picture == 4) {
                background = new ImageIcon(release2path);
            } else {
                background = new ImageIcon(move2path);
            }
            g.drawImage(background.getImage(), 0, 0, null);
        }

        private void MousePressed(MouseEvent evt) {
            num = !num;
            if (!evt.isMetaDown()) {
                if (num) {
                    picture = 3;
                } else {
                    picture = 1;
                }
                for (int i = 0; i < parent.size(); i++) {
                    if (i != number) {
                        parent.get(i).SetPicture(0);
                    }
                }
                repaint();
            }
        }

        private void MouseEntered(MouseEvent evt) {
            if (picture == 0) {
                picture = 2;
            } else if (picture == 4) {
                picture = 5;
            }
            repaint();
        }

        private void MouseExited(MouseEvent evt) {
            if (picture == 2) {
                picture = 0;
            } else if (picture == 5) {
                picture = 4;
            }
            repaint();
        }
    }
}
