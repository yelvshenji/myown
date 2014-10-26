package com.xcd.own.netdisk.client.operation;

import com.xcd.own.netdisk.common.attribute.Datapackage;
import com.xcd.own.netdisk.common.attribute.FileInfo;
import com.xcd.own.netdisk.common.attribute.UserInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Administrator on 14-10-26.
 */
public class SendBean extends Thread {
    private Socket socket;  //客户端的socket
    private ObjectInputStream in;   //定义IO对象
    private ObjectOutputStream out; //定义IO对象
    private Datapackage dp; //客户端数据包
    private String requestType; //保存构造函数传入的请求类型
    private String para[];  //保存构造函数传入的变长参数列表
    private static String serverIP = "127.0.0.1";   //定义服务器端的IP地址和端口号
    private static int serverPort = 3333;   //定义服务器端的端口号

    public SendBean(String peerIP, int peerPort, String requestType, String... para) {
        try {
            //构建与服务端的连接
            if (peerIP == null && peerPort == 0) {
                socket = new Socket(serverIP, serverPort);
            }
            //构建与客户端的点对点连接
            else {
                socket = new Socket(peerIP, peerPort);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            this.requestType = requestType;
            this.para = para;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            start();
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e2) {
                e.printStackTrace();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public Datapackage getDp() {
        return dp;
    }

    public void setDp(Datapackage dp) {
        this.dp = dp;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String[] getPara() {
        return para;
    }

    public void setPara(String[] para) {
        this.para = para;
    }

    public void run() {
        //注册
        if (requestType.equals("registerRequest")) {
            this.SubmitRegisterInfo(para[0], para[1], para[2], Integer.parseInt(para[3])
                    , Integer.parseInt(para[4]));
        }
        //登录
        else if (requestType.equals("loginRequest")) {
            this.SubmitNamePw(para[0], para[1]);
        }
        //上传
        else if (requestType.equals("uploadFile")) {
            this.SubmitFileInfo(para[0], para[1], para[2], para[3]);
        }
        //查询
        else if (requestType.equals("queryFile")) {
            this.QueryFileInfo(para[0], para[1], Integer.parseInt(para[2]));
        }
        //删除
        else if (requestType.equals("deleteFile")) {
            this.DeleteFileInfo(para[0]);
        }
        //更新
        else if (requestType.equals("updateUserInfo")) {
            this.UpdateUserInfo(para[0], para[1], para[2]);
        }
        //下载
        else if (requestType.equals("downloadFile")) {
            this.DownloadFile(para[0], para[1], para[2]);
        }
    }

    //向服务器提交登录信息:用户名和密码
    public void SubmitNamePw(String username, String password) {
        try {
            UserInfo ui = new UserInfo();
            ui.setUname(username);
            ui.setUpw(password);

            dp = new Datapackage();
            dp.setDataHeader("loginRequest");
            dp.setUid(ui);

            out.writeObject(dp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //向服务器提交注册信息:用户名、密码和IP地址等
    public void SubmitRegisterInfo(String username, String password, String ip, int port, int online) {
        try {
            UserInfo ui = new UserInfo();
            ui.setUname(username);
            ui.setUpw(password);
            ui.setUip(ip);
            ui.setUport(port);
            ui.setUonline(online);

            dp = new Datapackage();
            dp.setDataHeader("registerRequest");
            dp.setUid(ui);

            out.writeObject(dp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //向服务器提交上传元数据信息
    public void SubmitFileInfo(String filename, String filepath, String filesize, String fileowner) {
        try {
            FileInfo fid = new FileInfo();
            fid.setFname(filename);
            fid.setFpath(filepath);
            fid.setFsize(filesize);
            fid.setFowner(fileowner);

            dp = new Datapackage();
            dp.setDataHeader("uploadFile");
            dp.setFid(fid);

            out.writeObject(dp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //向服务器提交查询元数据信息请求
    public void QueryFileInfo(String fname, String fowner, int type) {
        try {
            FileInfo fid = new FileInfo();
            fid.setFowner(fowner);

            dp = new Datapackage();
            dp.setDataHeader("queryFile");
            dp.setFid(fid);
            dp.setSearchType(type);
            dp.setSeachKeyword(fname);

            out.writeObject(dp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //向服务器提交上传删除元数据信息请求
    public void DeleteFileInfo(String fname) {
        try {
            FileInfo fid = new FileInfo();
            fid.setFname(fname);

            dp = new Datapackage();
            dp.setDataHeader("deleteFile");
            dp.setFid(fid);

            out.writeObject(dp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //向服务器提交更新用户信息(IP，在线状态等)请求
    public void UpdateUserInfo(String fname, String ip, String online) {
        try {
            UserInfo ui = new UserInfo();
            ui.setUname(fname);
            ui.setUip(ip);
            ui.setUonline(Integer.parseInt(online));

            dp = new Datapackage();
            dp.setDataHeader("updateUserInfo");
            dp.setUid(ui);

            out.writeObject(dp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //向服务器下载文件信息(路径，文件名，大小)请求
    public void DownloadFile(String filename, String filepath, String filesize) {
        try {
            FileInfo fid = new FileInfo();
            fid.setFname(filename);
            fid.setFpath(filepath);
            fid.setFsize(filesize);

            dp = new Datapackage();
            dp.setDataHeader("downloadFile");
            dp.setFid(fid);

            out.writeObject(dp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
