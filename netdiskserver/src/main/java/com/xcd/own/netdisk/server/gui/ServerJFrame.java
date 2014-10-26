package com.xcd.own.netdisk.server.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import com.xcd.own.netdisk.server.reaction.StartThread;

/**
 * Created by Administrator on 14-10-26.
 */
public class ServerJFrame extends JFrame implements ActionListener
{
    private JButton start,stop;

    private Vector socketVector;

    private StartThread st;

    public ServerJFrame()
    {
        socketVector = new Vector();

        this.setLayout(new BorderLayout());

        start = new JButton("Start server");
        stop = new JButton("Stop server");
        stop.setEnabled(false);
        start.addActionListener(this);
        stop.addActionListener(this);

        this.add(start,BorderLayout.WEST);
        this.add(stop,BorderLayout.CENTER);

        this.setTitle("P2P Server");
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setLocation(width / 2 - 110, height / 2 - 55);
        this.setSize(220,110);
        this.setVisible( true );
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void actionPerformed( ActionEvent actionEvent)
    {
        if(actionEvent.getSource() == start)
        {
            start.setEnabled(false);
            stop.setEnabled(true);
            st = new StartThread();
        }
        else
        {
            start.setEnabled(true);
            stop.setEnabled(false);
            st.stop();
            st.StopServer();
        }
    }
}
