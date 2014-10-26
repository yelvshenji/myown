package com.xcd.own.netdisk.server.reaction;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by Administrator on 14-10-26.
 */
public class StartThread extends Thread
{
    //端口号
    static final int portNo=3333;

    //服务器端的socket
    ServerSocket s = null;

    Vector socketVector;

    public StartThread()
    {
        socketVector = new Vector();
        start();
    }

    public void run()
    {
        this.StartServer();
    }

    public void StartServer()
    {

        try {
            s = new ServerSocket(portNo);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("The Server is start:"+s);

        try
        {
            for(;;)
            {
                try {
                    //阻塞，直到有客户端连接
                    Socket socket=s.accept();
                    socketVector.add(socket);
                    //通过构造函数，启动线程
                    new ThreadServer(socket);
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

    public void StopServer()
    {
        for(Iterator iter = socketVector.iterator();iter.hasNext();)
        {
            Socket socket = (Socket)iter.next();
            try {
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            s.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("The Server is stopped!");
    }
}