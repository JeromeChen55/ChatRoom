package com.chen.qqclient.view;


import javax.swing.*;
import com.chen.qqclient.service.MessageClientServer;
import com.chen.qqclient.service.UserClientService;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Login extends JFrame {
   private static String UserName = "";
    private static String password = "";
    /**Control display the menu*/
    private boolean loop = true;
    /**Receive user keyborad input*/
    private String key = "";
    /**Login server and sign in*/
    public static UserClientService userClientService = new UserClientService();
    /**Chat service between clients*/
    public static MessageClientServer messageClientServer = new MessageClientServer();

    private final JFrame frame = new JFrame("Login");  // 窗口
    private final JLabel label1 = new JLabel();  // 用户名
    private final JLabel label2 = new JLabel();  // 密码
    private final JTextField textField1 = new JTextField();  //用户名
    private final JPasswordField textField2 = new JPasswordField();  // 密码
    private final JButton buttonLogin = new JButton("登入");  // 登入按钮
    private final JButton buttonClose = new JButton("关闭");  // 关闭按钮
    /**
     * 构造LoginUI类,通过函数设置窗口、标签等信息
     */


    public Login() {
        setFrame();  // 设置窗口函数
        setLabel1();  // 设置标签1函数
        setTextField1();  // 设置文本1函数
        setLabel2();  // 设置标签2函数
        setTextField2();  // 设置文本2函数
        setbuttonLogin();  // 设置按钮函数
        setbuttonClose();  // 设置按钮函数
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
                LoginEvent();
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {

        }
    };

    private  void LoginEvent() {
        UserName = textField1.getText();
        password = textField2.getText();
        if (UserName.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(frame, "服务器IP和昵称不能为空");  // 弹出信息框
        }
        else if(!userClientService.checkUser(UserName, password) )
        {//账号名或密码错误
            JOptionPane.showMessageDialog(frame, "账号名或密码错误");
        }
        else {//登录成功
            frame.dispose();//隐藏登录窗口
            new Client(textField1.getText(), textField2.getText());//显示新窗口
        }
    };
    /**
     * 设置button相关属性
     */
    private void setbuttonLogin() {
        buttonLogin.setBounds(50, 150, 100, 40);
        /*
        lambda表达式 设置监听事件 实质是实现内部类ActionListener
        重写其中的actionPerformed方法 e（ActionEvent）
         */

        buttonLogin.addActionListener(
                e -> {
                    LoginEvent();
                }
        );
        frame.add(buttonLogin);
    }

    private void setbuttonClose() {
        buttonClose.setBounds(250, 150, 100, 40);
        /*
        lambda表达式 设置监听事件 实质是实现内部类ActionListener
        重写其中的actionPerformed方法 e（ActionEvent）
         */
        buttonClose.addActionListener(
                e -> {
                    System.exit(0);
                }
        );
        frame.add(buttonClose);
    }


    /**
     * 设置窗口相关属性
     */
    private void setFrame() {

        frame.setLocationRelativeTo(null);  // 设置窗口左上角在启动时处于屏幕中间

        frame.setSize(450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 退出、最小化、关闭
        frame.setLayout(null);  // 空白布局
        frame.setResizable(false);  // 不可设置窗口大小
     //   frame.setUndecorated(true);
     //   frame.setBackground(new Color(0,0,0,0));
    }

    /**
     * 设置label1相关属性
     */
    private void setLabel1() {
        label1.setText("用户名：");
        label1.setBounds(50, 75, 100, 20);  // 设置位置和大小
        label1.setOpaque(true);
        frame.add(label1);
    }

    /**
     * 设置text1相关属性
     */
    private void setTextField1() {
        textField1.setBounds(140, 75, 200, 25);
        frame.add(textField1);
    }

    /**
     * 设置label2相关属性
     */
    private void setLabel2() {
        label2.setText("密码：");
        label2.setBounds(50, 110, 100, 20);
        frame.add(label2);
    }

    /**
     * 设置text2相关属性
     */
    private void setTextField2() {
        textField2.setBounds(140, 110, 200, 25);
        textField2.addKeyListener(key_Listener);
        frame.add(textField2);
    }

}
