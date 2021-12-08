package com.chen.qqclient.service;

import com.chen.qqcommon.Message;
import com.chen.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.function.BooleanSupplier;

/**
 * @author Chen
 * @version 1.0
 * support method about message
 */
public class MessageClientServer {

    public void sendMessageToAll (String content, String senderId) {

        Message message = new Message();
        message.setSender(senderId);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
        System.out.println(senderId + "对大家说" + content);

        try {
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream
                            (ManageClientConnectServerThread.getClientConnectServerThread(senderId)
                                    .getSocket().getOutputStream());
            objectOutputStream.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMessageToOne (String content, String senderId, String getterId) {

        Message message = new Message();
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        message.setMesType(MessageType.MESSAGE_COMM_MES);
        System.out.println(senderId + "对" + getterId + "说"  + content);

        try {
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream
                            (ManageClientConnectServerThread.getClientConnectServerThread(senderId)
                                    .getSocket().getOutputStream());
            objectOutputStream.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
