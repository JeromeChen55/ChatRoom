package com.chen.qqserver.service;

import com.chen.qqcommon.Message;
import com.chen.qqcommon.MessageType;
import com.chen.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Chen
 * @version 1.0
 * Monitor port 9999, wait client connection, keep communication
 */
public class QQServer {

    private ServerSocket ss = null;

    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();

    /** init validUsers*/
    static {
        validUsers.put("100", new User("100", "123456"));
        validUsers.put("200", new User("200", "123456"));
        validUsers.put("300", new User("300", "123456"));
        validUsers.put("刘鸿鹏", new User("刘鸿鹏", "123456"));
        validUsers.put("老八", new User("老八", "123456"));

    }

    private boolean checkUser(String userId, String password) {
        User user = validUsers.get(userId);
        //verify the userId
        if(user == null) {
            return false;
        }
        //verify the password
        if(!user.getPassword().equals(password)) {
            return false;
        }
        return true;
    }


    public QQServer() {
        //Use properties to store port
        try {
            System.out.println("服务端在9999监听");
            ss = new ServerSocket(9999);

            while (true) {
                Socket socket = ss.accept();

                ObjectInputStream objectInputStream =
                        new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream =
                        new ObjectOutputStream(socket.getOutputStream());

                User u = (User)objectInputStream.readObject();
                Message message = new Message();

                //    !!! Suppose verify in database !!!
                if(checkUser(u.getUserId(), u.getPassword())) {
                    //pass,reply message to client
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    objectOutputStream.writeObject(message);

                    //start thread, keep communication
                    ServerConnectClientThread serverConnectClientThread
                            = new ServerConnectClientThread(socket, u.getUserId());
                    serverConnectClientThread.start();
                    ManageClientThreads.addClientThread(u.getUserId(), serverConnectClientThread);


                } else {
                    //denied
                    System.out.println("用户id" + u.getUserId() + "pwd=" + u.getPassword() + "验证失败");
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    objectOutputStream.writeObject(message);
                    socket.close();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //after stop monitor, close the server socket
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
