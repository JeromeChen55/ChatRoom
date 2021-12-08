package com.chen.qqserver.service;

import com.chen.qqcommon.Message;
import com.chen.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Chen
 * @version 1.0
 * the instance of this class keep communication with a client
 */
public class ServerConnectClientThread extends Thread {

    private Socket socket;
    private String userId;

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {

        while (true) {
            try {
                System.out.println("服务端和客户端" + userId + "保持通信中，读取数据");
                ObjectInputStream objectInputStream
                        = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputStream.readObject();
                // action depends message type
                if (message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)) {
                    System.out.println(message.getSender() + "索取在线用户列表");
                    String onlineUser = ManageClientThreads.getOnlineUser();
                    //package in message
                    Message message2 = new Message();
                    message2.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    message2.setContent(onlineUser);
                    message2.setGetter(message.getSender());
                    //return message to client
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(message2);


                } else if (message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    System.out.println(message.getSender() + "退出");
                    ManageClientThreads.removeServerConnectClientThread(message.getSender());
                    socket.close();
                    break;
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    // get the message sender
                    ServerConnectClientThread serverConnectClientThread
                            = ManageClientThreads.getServerConnectClientThread(message.getGetter());
                    // forward the message
                    ObjectOutputStream objectOutputStream
                            = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                    objectOutputStream.writeObject(message);
                    // if client is not online, the message can store in database

                }  else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();
                    Iterator<String> iterator = hm.keySet().iterator();
                    while (iterator.hasNext()) {
                        // Get online user id
                        String onlineUserId = iterator.next().toString();

                        if(!onlineUserId.equals(message.getSender())) {

                            ObjectOutputStream objectOutputStream
                                    = new ObjectOutputStream(hm.get(onlineUserId).getSocket().getOutputStream());
                            objectOutputStream.writeObject(message);
                        }
                    }
                } else {
                    System.out.println("其他类型message暂时不处理");
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


        }

    }
}
