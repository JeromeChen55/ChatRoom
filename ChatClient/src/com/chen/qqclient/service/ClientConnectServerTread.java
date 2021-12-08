package com.chen.qqclient.service;

import com.chen.qqcommon.Message;
import com.chen.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

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
                System.out.println("客户端线程，等待从服务器端发送的消息");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //if server did not send the message object, the thread would be blocked
                Message message = (Message) ois.readObject();

                if(message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {
                    //display the online friend information
                    //define the list split by a single space
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("\n当前在线用户列表");
                    for (int i = 0; i < onlineUsers.length; i++) {
                        System.out.println("用户： " + onlineUsers[i]);
                    }
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    // Display the message got
                    System.out.println("\n" + message.getSender() + "对" + message.getGetter() +
                            "说" + message.getContent());
                } else if(message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    System.out.println("\n" + message.getSender() + "对大家说" + message.getContent());
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
