package com.chen.qqserver.service;

import com.chen.qqcommon.Message;
import com.chen.qqcommon.MessageType;
import com.chen.qqcommon.User;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.*;
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
    private boolean abandon = true;

    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();

    /** init validUsers*/
    static {
        validUsers.put("cjy", new User("cjy", "aaa"));
        validUsers.put("lhp", new User("lhp", "aaa"));
        validUsers.put("wpf", new User("wpf", "aaa"));
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

                ObjectOutputStream objectOutputStream =
                        new ObjectOutputStream(socket.getOutputStream());

                // catch the unpredictable datagram and throw away
                ObjectInputStream objectInputStream =
                        null;
                try {
                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                } catch (IOException e) {
                    continue;
                } finally {
                    System.out.println("try..");
                }

                if (objectInputStream == null) {
                    continue;
                }

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
