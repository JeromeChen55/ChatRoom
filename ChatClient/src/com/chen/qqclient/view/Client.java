package com.chen.qqclient.view;

import com.chen.qqclient.service.MessageClientServer;
import com.chen.qqclient.service.UserClientService;

import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {

        private final JFrame frame = new JFrame("Client");  // 窗口
        public static JList<String> onlinelist = new JList<>();  // 在线人员
        public static JTextArea textArea = new JTextArea();  //信息框
        public static JTextArea textArea1 = new JTextArea();  // 发送框
        private final JButton buttonSend = new JButton("发送");  // 发送按钮
        private final JButton buttonClose = new JButton("关闭");  // 发送按钮
        private final JButton buttonAllChat = new JButton("切换群聊");  // 群聊按钮
        private final JButton buttonRefresh = new JButton("刷新列表");  // 发送按钮
        public static final JLabel jLabelonlinenum = new JLabel("当前在线人员: 0");  // 在线人员标签
        private final JLabel jLabelInfo = new JLabel();  // 个人信息

        UserClientService userClientService = Login.userClientService;
        MessageClientServer messageClientServer = Login.messageClientServer;
        String UserId="";
        public static String MsgDest = "MessageToAll";

        public Client(String _UserId, String _UserPwd) {
            UserId = _UserId;
            setFrame();
            setList();
            setTextArea();
            setTextArea1();
            setbuttonAllChat();
            setbuttonClose();
            textArea.append("欢迎(用户" + _UserId + ")\n" );
            setbuttonRefresh();
            setSendButton();
            setjLabelonlinenum();
            setjLabelInfo();
            frame.setVisible(true);
        }

    private void setjLabelInfo() {
        jLabelInfo.setText("     " + UserId + "的聊天室 ");
        jLabelInfo.setBounds(500, 5, 180, 20);
        frame.add(jLabelInfo);
    }

    private void setbuttonAllChat() {
        buttonAllChat.setBounds(585, 560, 85, 50);
        buttonAllChat.addActionListener(
                e ->
                {
                   MsgDest = "MessageToAll";
                }
        );
        frame.add(buttonAllChat);
    }

    private void setbuttonClose() {
        buttonClose.setBounds(500, 560, 85, 50);
        buttonClose.addActionListener(
                e ->
                {
                    userClientService.logout();

                    System.exit(0);
                }
        );
        frame.add(buttonClose);
    }

    private void setbuttonRefresh() {
        buttonRefresh.setBounds(585, 610, 85, 50);
        buttonRefresh.addActionListener(
                e ->
                {
                    userClientService.onlineFriendList();
                }
        );
        frame.add(buttonRefresh);

    }

    /**
     * 设置窗口相关属性
     */
    public void setFrame() {
        frame.setLocationRelativeTo(null);  // 设置窗口左上角在启动时处于屏幕中间

        frame.setSize(700, 720);
        frame.setTitle(UserId + "的聊天室");
        frame.setDefaultCloseOperation(frame.DO_NOTHING_ON_CLOSE);  // 退出、最小化、关闭
        frame.setLayout(null);  // 空白布局
        frame.setResizable(false);  // 不可设置窗口大小
       // frame.setUndecorated(true);
    }

    /**
     * 设置list的相关属性
     */
    private void setList() {
        JScrollPane scrollPane = new JScrollPane();  // 带滚动条的画布
        scrollPane.setBounds(500, 55, 180, 490);
        scrollPane.setViewportView(onlinelist);  // 画布中添加list

        /*
        设置监听事件,当鼠标单机莫一行松开后在发送框添加私聊的格式
         */
        onlinelist.addListSelectionListener(e -> {
            // 判断是否单击松开
            if (!onlinelist.getValueIsAdjusting()) {
                MsgDest = onlinelist.getSelectedValue();
            }
        });
        frame.getContentPane().add(scrollPane);
    }

    /**
     * 设置test are相关属性
     */
    private void setTextArea() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 5, 480, 600);
        textArea.setLineWrap(true); // 自动换行
        textArea.setWrapStyleWord(true);  // 断行不断字
        textArea.setEditable(false);  // 不允许直接编辑
        scrollPane.setViewportView(textArea);
        frame.getContentPane().add(scrollPane);
    }

    /**
     * 设置test are1相关属性
     */
    private void setTextArea1() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 610, 480, 50);

        textArea.setLineWrap(true); // 自动换行
        textArea.setWrapStyleWord(true);  // 断行不断字
        scrollPane.setViewportView(textArea1);
        frame.getContentPane().add(scrollPane);
    }

    /**
     * 设置button相关属性
     */
    private void setSendButton() {
        buttonSend.setBounds(500, 610, 85, 50);
        buttonSend.addActionListener(
                e ->
                {
                    sendEvent();
                }
        );
        frame.add(buttonSend);
    }

    /**
     * 设置标签的相关属性
     */
    private void setjLabelonlinenum() {
        jLabelonlinenum.setBounds(500, 30, 180, 20);
        frame.add(jLabelonlinenum);
    }


    private void sendEvent()
    {
        String s = textArea1.getText();
        if(s.isEmpty())
        {
            JOptionPane.showMessageDialog(frame, "不能发送空消息");
        }
        else
        {
            if(MsgDest.equals("MessageToAll"))
            {
                messageClientServer.sendMessageToAll(s, UserId);
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateNowStr = sdf.format(d);
                textArea.append( "\n" + dateNowStr);
                textArea.append("\n" + UserId + "对大家说" + s );
                textArea1.setText("");
            }
            else
            {
                if(UserId.equals(MsgDest))
                {
                    JOptionPane.showMessageDialog(frame, "请不要自言自语");

                } else {
                    messageClientServer.sendMessageToOne(s,UserId,MsgDest);
                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateNowStr = sdf.format(d);
                    textArea.append( "\n" + dateNowStr);
                    textArea.append("\n" + UserId + "对"+ MsgDest +"说"  + s );
                    //清空输入框
                    textArea1.setText("");
                }

            }
        }

    }





}
