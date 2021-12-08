package com.chen.qqclient.service;

import java.util.HashMap;

/**
 * @author Chen
 * @version 1.0
 * Manage threads of client connected to server
 */
public class ManageClientConnectServerThread {

    /**
     * @description: A thread manager
     * @key: userId
     * @value: thread
     */
    private static HashMap<String, ClientConnectServerTread> hm =
            new HashMap<>();

    public static void addClientConnectServerThread(
            String userId,
            ClientConnectServerTread clientConnectServerTread
    ) {
        hm.put(userId, clientConnectServerTread);

    }

    public static ClientConnectServerTread getClientConnectServerThread(String userId) {
        return hm.get(userId);
    }


}
