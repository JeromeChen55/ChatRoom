package com.chen.qqclient.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Welcome extends Frame {

    private final JFrame frame = new JFrame("Access");  // 窗口
    private final JButton button = new JButton("ACCESS");  // 登入按钮

    public Welcome() {
        setFrame();  // 设置窗口函数
        setButton();  // 设置按钮函数
        frame.setVisible(true);  // 设置窗口的可见性为TRUE
    }


    KeyListener key_Listener = new KeyListener(){
        @Override
        public void keyTyped(KeyEvent e) {
        }
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyChar() == KeyEvent.VK_ENTER )
            {
                frame.dispose();//隐藏登录窗口
                new Login();
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {

        }
    };

    private void setFrame() {
        //frame.setLocationRelativeTo(null);  // 设置窗口左上角在启动时处于屏幕中间
        frame.setSize(300, 300);

        frame.setLocationRelativeTo(null);  // 设置窗口左上角在启动时处于屏幕中间

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 退出、最小化、关闭
        frame.setLayout(null);  // 空白布局
        frame.setResizable(false);  // 不可设置窗口大小
        frame.setUndecorated(true);
      //  frame.setBackground(new Color(0,0,0,0));
    }

    private void setButton() {
        button.setBounds(100, 100, 100, 100);

        button.addKeyListener(key_Listener);
        button.addActionListener(
                e -> {
                        frame.dispose();//隐藏登录窗口
                        new Login();
                    }
        );
        frame.add(button);
    }



    public static void main(String[] args) {

        new Welcome();
    }


}
