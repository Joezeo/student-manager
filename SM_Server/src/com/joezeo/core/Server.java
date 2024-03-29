package com.joezeo.core;

import com.joezeo.utils.ServerCloseUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 学生管理系统服务器端
 */
public class Server {
    /**
     * 服务器套接字socket对象 使用单例模式
     */
    private static ServerSocket server;

    // 私有化构造器
    private Server() {
    }

    /**
     * 用于控制服务器端关闭的boolean值
     */
    private static boolean isRunning = false;

    /**
     * 静态初始化块：初始化服务器socket
     */
    static {
        try {
            server = new ServerSocket(8888);
            isRunning = true;
            System.out.println("MESSAGE：初始化服务器成功");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR：初始化服务器失败");
        }
    }

    /**
     * 服务器main函数入口
     *
     * @param args 参数列表
     */
    public static void main(String[] args) {
        while (isRunning) {
            try {
                //获取客户端套接字
                Socket client = server.accept();

                //根据客户端套接字创建线程
                new Thread(new Channel(client)).start();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("ERROR：服务器通信异常");
                isRunning = false;
                ServerCloseUtils.close(server);
            }
        }
    }
}
