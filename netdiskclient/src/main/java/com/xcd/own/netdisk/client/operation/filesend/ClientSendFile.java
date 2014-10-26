package com.xcd.own.netdisk.client.operation.filesend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 14-10-26.
 */
public class ClientSendFile
{
    public ClientSendFile(int clientPort)
    {
        //ClientSendFile的socket
        ServerSocket s = null;
        try {
            s = new ServerSocket(clientPort);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("The ClientServer is start:"+s);

        try
        {
            for(;;)
            {
                try {
                    //阻塞，直到有客户端连接
                    Socket socket=s.accept();
                    //通过构造函数，启动线程
                    new SendFileThread(socket);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        finally
        {
            try {
                s.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
