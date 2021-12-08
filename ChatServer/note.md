# 课内要求
1) 用户可以通过客户端连接到服务器端并进行网上聊天。聊天时能够启动多个客户
端。
2) 服务器端启动后，接收客户端发来的用户名和密码验证信息。验证通过则以当前
的聊天客户列表信息进行响应；此后接收客户端发来的聊天信息，转发给客户端指定的
聊天客户（即私聊）或所有其他客户端；在客户端断开连接后提示退出聊天系统的信息。
3) 客户端启动后在 GUI 界面接收用户输入的服务器端信息、账号和密码等验证客户
的身份。验证通过则显示当前系统在线客户列表。客户可以与指定对象进行私聊，也可
以向系统中所有在线客户发送信息。
撰写报告时，要求给出系统结构图；分别给出服务器端和客户端的程序流程图及程
序源码；给出程序的运行测试结果；使用 Wireshark 软件对程序运行过程进行抓包，说
明抓包环境，并对抓包结果进行分析。