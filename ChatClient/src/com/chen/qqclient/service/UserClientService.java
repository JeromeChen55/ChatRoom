package com.chen.qqclient.service;

import com.chen.qqcommon.Message;
import com.chen.qqcommon.MessageType;
import com.chen.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author Chen
 * @version 1.0
 * Implement user Login and sign in function
 */
public class UserClientService {

    private User u = new User();
    private Socket socket;

    public boolean checkUser(String userId, String pwd) {

        boolean b = false;

        u.setUserId(userId);
        u.setPassword(pwd);

        try {
            //Local ip
            Socket socket = new Socket(InetAddress.getByName("103.46.128.53"), 17907);
            //test
            //Socket socket = new Socket("192.168.199.155", 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            //send user object
            oos.writeObject(u);

            //Read message replied from server
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();

            if(ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
                //Login successful
                //create a thread to keeping communication to server
                ClientConnectServerTread clientConnectServerTread
                        = new ClientConnectServerTread(socket);
                clientConnectServerTread.start();

                //Use a set to manage the threads
                ManageClientConnectServerThread.addClientConnectServerThread(
                        userId,
                        clientConnectServerTread
                );

                b =true;
            } else {
                //Login denied
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return b;
    }

    public void onlineFriendList() {

        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(u.getUserId());

        try {
            //map->thread->socket->stream
            ClientConnectServerTread clientConnectServerTread =
                    ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId());
            Socket socket = clientConnectServerTread.getSocket();
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void logout() {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserId());

        try {
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(
                            ManageClientConnectServerThread.getClientConnectServerThread(u.getUserId())
                                    .getSocket().getOutputStream());

            objectOutputStream.writeObject(message);

            //System.out.println(u.getUserId() + "退出系统");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
