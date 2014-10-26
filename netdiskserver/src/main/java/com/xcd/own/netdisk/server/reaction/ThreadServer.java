package com.xcd.own.netdisk.server.reaction;

import com.xcd.own.netdisk.common.attribute.Datapackage;
import com.xcd.own.netdisk.common.attribute.UserInfo;
import com.xcd.own.netdisk.server.dao.FileTable;
import com.xcd.own.netdisk.server.dao.UserTable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by Administrator on 14-10-26.
 */
public class ThreadServer extends Thread
{
    //客户端的socket
    private Socket clientSocket;

    //定义IO句柄
    private ObjectInputStream sin;
    private ObjectOutputStream sout;

    //保存客户端数据包
    private Datapackage dp;


    //默认的构造函数
    public ThreadServer()
    {

    }

    //带参构造函数
    public ThreadServer(Socket s) throws IOException
    {
        clientSocket=s;

        //初始化sin和sout的句柄
        sin=new ObjectInputStream(clientSocket.getInputStream());
        sout=new ObjectOutputStream(clientSocket.getOutputStream());

        //开启线程
        start();
    }

    //关闭服务
    public void ThreadServerStop()
    {
        try {
            clientSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //处理用户的登录请求
    public void ProcessLogin()
    {
        System.out.println("In Server received the info:"+dp.getDataHeader()+" "+dp.getUid().getUname()+" "+dp.getUid().getUpw());

        UserTable uia= new UserTable();
        UserInfo uid= (UserInfo)uia.getUser(dp.getUid().getUname(), dp.getUid().getUpw());
        if(uid != null)
        {
            dp.setResult("success");
            dp.setUid(uid);
        }
        else
        {
            dp.setResult("failed");
            dp.setUid(null);
        }
        try {
            sout.writeObject(dp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //处理用户的注册请求
    public void ProcessRegister()
    {
        System.out.println("In Server received the info:"+dp.getDataHeader()+" "+dp.getUid().getUname()+" "+dp.getUid().getUpw()+" "+dp.getUid().getUip()+" "+dp.getUid().getUport()+" "+dp.getUid().getUonline());


        UserTable uia=new UserTable();

        if(uia.newUser(dp.getUid()))
        {
            dp.setResult("success");
        }
        else
        {
            dp.setResult("failed");
        }

        try {
            sout.writeObject(dp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //处理用户的上传文件请求
    public void ProcessUploadFile()
    {
        System.out.println("In Server received the info:"+dp.getDataHeader()+" "+dp.getFid().getFpath()+" "+dp.getFid().getFname()+" "+dp.getFid().getFsize()+" "+dp.getFid().getFowner());

        FileTable fia=new FileTable();
        if(fia.newFile(dp.getFid()))
        {
            dp.setResult("success");
        }
        else
        {
            dp.setResult("failed");
        }

        try {
            sout.writeObject(dp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //处理用户的查询文件请求
    public void ProcessQueryFile()
    {
        System.out.println("In Server received the info:"+dp.getDataHeader()+" "+dp.getSeachKeyword()+" "+dp.getFid().getFowner());

        FileTable fia=new FileTable();
        Vector resultSet = fia.getFileInfo(dp.getSeachKeyword(), dp.getFid().getFowner(),dp.getSearchType());
        if(resultSet != null)
        {
            dp.setResult("success");
            dp.setTableData(resultSet);
        }
        else
        {
            dp.setResult("failed");
            dp.setTableData(null);
        }

        try {
            sout.writeObject(dp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //处理用户的删除文件请求
    public void ProcessDeleteFile()
    {
        System.out.println("In Server received the info:"+dp.getDataHeader()+" "+dp.getFid().getFname());

        FileTable fia=new FileTable();
        if(fia.deleteFileInfo(dp.getFid().getFname()))
        {
            dp.setResult("success");
        }
        else
        {
            dp.setResult("failed");
        }
        try {
            sout.writeObject(dp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //处理用户的更新用户信息请求
    public void ProcessUpdateUserInfo()
    {
        System.out.println("In Server received the info:"+dp.getDataHeader()+" "+dp.getUid().getUname()+" "+dp.getUid().getUip()+" "+dp.getUid().getUonline());

        UserTable uia=new UserTable();
        if(uia.updataUserInfo(dp.getUid()))
        {
            dp.setResult("success");
        }
        else
        {
            dp.setResult("failed");
        }
        try {
            sout.writeObject(dp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //线程执行的主体函数
    public void run()
    {
        try {
            //用循环来监听通讯内容
            //for(;;)
            //{
            dp = new Datapackage();

            try {
                dp =(Datapackage)sin.readObject();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //注册请求
            if(dp.getDataHeader().equals("registerRequest"))
            {
                ProcessRegister();
            }
            //登录请求
            else if(dp.getDataHeader().equals("loginRequest"))
            {
                ProcessLogin();
            }
            //上传文件元数据请求
            else if(dp.getDataHeader().equals("uploadFile"))
            {
                ProcessUploadFile();
            }
            //查询文件请求
            else if(dp.getDataHeader().equals("queryFile"))
            {
                ProcessQueryFile();
            }
            //删除文件请求
            else if(dp.getDataHeader().equals("deleteFile"))
            {
                ProcessDeleteFile();
            }
            //更新用户信息请求
            else if(dp.getDataHeader().equals("updateUserInfo"))
            {
                ProcessUpdateUserInfo();
            }
            //}
        } catch (IOException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            System.out.println("close the Server socket and the io");
            try
            {
                clientSocket.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

}








