package com.chen.qqclient.view;

import com.chen.qqclient.service.MessageClientServer;
import com.chen.qqclient.service.UserClientService;
import com.chen.qqclient.utils.Utility;
import com.chen.qqcommon.User;

import java.io.IOException;

/**
 * @author: Chen
 * @version: 1.0
 * @description: Menu interface
 */
public class QQView {

    /**Control display the menu*/
    private boolean loop = true;
    /**Receive user keyborad input*/
    private String key = "";
    /**Login server and sign in*/
    private UserClientService userClientService = new UserClientService();
    /**Chat service between clients*/
    private MessageClientServer messageClientServer = new MessageClientServer();

    public static void main(String[] args){
        new QQView().mainMenu();
        System.out.println("客户端退出系统");
    }


     /**
     * @description Display the menu
     */
    private void mainMenu(){

        while(loop) {
            System.out.println("欢迎登录QQ");
            System.out.println("1 登录系统");
            System.out.println("9 exit");
            System.out.println("输入你的选择");

            key = Utility.readString(1);

            switch (key) {
                case "1":
                    System.out.println("请输入用户号");
                    String userId = Utility.readString(50);
                    System.out.println("请输入密码");
                    String pwd = Utility.readString(50);
                    //test
                    if (userClientService.checkUser(userId, pwd)) {
                        System.out.println("欢迎(用户" + userId + ")");
                        //Secondary menu
                        while (loop) {
                            System.out.println("\n二级菜单欢迎(用户" + userId + "登录成功)");
                            System.out.println("1 显示在线用户列表");
                            System.out.println("2 群发消息");
                            System.out.println("3 私聊消息");
                            System.out.println("4 发送文件");
                            System.out.println("9 退出系统");
                            System.out.println("请输入你的选择：");

                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.println("请输入想对大家说的话");
                                    String s = Utility.readString(1000);
                                    messageClientServer.sendMessageToAll(s, userId);
                                    break;
                                case "3":
                                    System.out.println("输入想聊天的用户id（需要是在线的）：");
                                    String getterId = Utility.readString(50);
                                    System.out.println("输入想说的话");
                                    String content = Utility.readString(1000);

                                    messageClientServer.sendMessageToOne(content, userId, getterId);

                                    System.out.println("私聊消息");
                                    break;
                                case "4":
                                    System.out.println("发送文件");
                                    break;
                                case "9":
                                    userClientService.logout();
                                    loop = false;
                                    break;
                                default:
                                    break;
                            }
                        }
                    } else {
                        System.out.println("登陆失败");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
                default:
                    System.out.println("Error");
                    break;
            }




        }

    }

}
