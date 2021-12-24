package com.chen.qqclient.service;

import com.chen.qqclient.view.Client;
import com.chen.qqcommon.Message;
import com.chen.qqcommon.MessageType;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import static com.chen.qqclient.view.Client.textArea;

//import static com.chen.qqclient.view.Client.*;

/**
 * @author Chen
 * @version 1.0
 * A thread holds a socket
 */
public class ClientConnectServerTread extends Thread{

    private Socket socket;

    public ClientConnectServerTread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        //This thread keep communication to server in backstage
        while(true) {

            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //if server did not send the message object, the thread would be blocked
                Message message = (Message) ois.readObject();

                if(message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {
                    //display the online friend information
                    //define the list split by a single space
                    String[] onlineUsers = message.getContent().split(" ");

                    // jList添加数据的模式
                    DefaultListModel<String> defaultListModel = new DefaultListModel<>();
                    JList<String> onlinelist = Client.onlinelist;
                    for (int i = 0; i < onlineUsers.length; i++) {
                        defaultListModel.addElement(onlineUsers[i]);
                    }
                    JLabel jLabelonlinenum = Client.jLabelonlinenum;
                    jLabelonlinenum.setText("当前在线人员: " + onlineUsers.length );
                    onlinelist.setModel(defaultListModel);

                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    // Display the message got
                    textArea.append("\n" + message.getSender() + "对" + message.getGetter() +
                            "说" + message.getContent());
                } else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    textArea.append("\n" + message.getSender() + "对大家" + message.getContent());
                } else {
                    System.out.println("其他类型暂时不处理");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }

    }

    public Socket getSocket() {
        return socket;
    }

}
