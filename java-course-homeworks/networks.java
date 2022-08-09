// Server.java
package com.company;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

    public void run() {
        try {
            ServerSocket serversocket = new ServerSocket(7777);
            Socket socket = null;
            String object;
            while (serversocket != null) {
                socket = serversocket.accept();
                ObjectInputStream obIn = new ObjectInputStream(socket.getInputStream());
                MyFrame1 frameServer = (MyFrame1) obIn.readObject();
                frameServer.setVisible(true);
                frameServer.setTitle("On Server");
            }

        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

// Client.java
package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.QuadCurve2D;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


class MyFrame1 extends JFrame implements ActionListener {
    public boolean l = false;

    JButton button;
    JPanel pic, btn;
    public MyFrame1() {
        setSize(800, 600);
        setTitle("Window");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        DrawComponent1 dc1 = new DrawComponent1();
        DrawComponent2 dc2 = new DrawComponent2();
        pic = new JPanel(new CardLayout());
        pic.add(dc1);
        pic.add(dc2);
        button = new JButton("Грусть");
        btn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btn.add(button);
        button.addActionListener(this);
        getContentPane().add("Center", pic);
        getContentPane().add("South", btn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(l) {
            ((CardLayout) pic.getLayout()).first(pic);
            l = false;
            button.setText("Грусть");
        } else {
            ((CardLayout) pic.getLayout()).last(pic);
            l = true;
            button.setText("Радость");
        }
    }
}

class DrawComponent1 extends JComponent {
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // окно
        g2.setPaint(new Color(68, 114, 150));
        Rectangle2D sky = new Rectangle2D.Double(70, 50, 600, 400);
        g2.fill(sky);
        g2.draw(sky);
        g2.setPaint(new Color(37, 32, 8));
        Rectangle2D w1 = new Rectangle2D.Double(200, 120, 300, 10);
        g2.fill(w1);
        g2.draw(w1);
        Rectangle2D w2 = new Rectangle2D.Double(200, 330, 300, 10);
        g2.fill(w2);
        g2.draw(w2);
        Rectangle2D w3 = new Rectangle2D.Double(190, 120, 30, 220);
        g2.fill(w3);
        g2.draw(w3);
        Rectangle2D w4 = new Rectangle2D.Double(500, 120, 30, 220);
        g2.fill(w4);
        g2.draw(w4);
        Rectangle2D w5 = new Rectangle2D.Double(350, 55, 10, 70);
        g2.fill(w5);
        g2.draw(w5);
        Rectangle2D w6 = new Rectangle2D.Double(380, 55, 10, 70);
        g2.fill(w6);
        g2.draw(w6);
        g2.setPaint(new Color(255, 203, 0));
        Ellipse2D sun = new Ellipse2D.Double(290, 150, 150, 150);
        g2.fill(sun);
        g2.draw(sun);
        g2.setPaint(new Color(9, 7, 1));
        Ellipse2D eye1 = new Ellipse2D.Double(330, 190, 10, 10);
        g2.fill(eye1);
        g2.draw(eye1);
        Ellipse2D eye2 = new Ellipse2D.Double(390, 190, 10, 10);
        g2.fill(eye2);
        g2.draw(eye2);
        QuadCurve2D smile = new QuadCurve2D.Double();
        smile.setCurve(340,240,360,270,390,240);
        g2.draw(smile);

    }
}
class DrawComponent2 extends JComponent {
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // окно
        g2.setPaint(new Color(68, 114, 150));
        Rectangle2D sky = new Rectangle2D.Double(70, 50, 600, 400);
        g2.fill(sky);
        g2.draw(sky);
        g2.setPaint(new Color(37, 32, 8));
        Rectangle2D w1 = new Rectangle2D.Double(200, 120, 300, 10);
        g2.fill(w1);
        g2.draw(w1);
        Rectangle2D w2 = new Rectangle2D.Double(200, 330, 300, 10);
        g2.fill(w2);
        g2.draw(w2);
        Rectangle2D w3 = new Rectangle2D.Double(190, 120, 30, 220);
        g2.fill(w3);
        g2.draw(w3);
        Rectangle2D w4 = new Rectangle2D.Double(500, 120, 30, 220);
        g2.fill(w4);
        g2.draw(w4);
        Rectangle2D w5 = new Rectangle2D.Double(350, 55, 10, 70);
        g2.fill(w5);
        g2.draw(w5);
        Rectangle2D w6 = new Rectangle2D.Double(380, 55, 10, 70);
        g2.fill(w6);
        g2.draw(w6);
        g2.setPaint(new Color(255, 203, 0));
        Ellipse2D sun = new Ellipse2D.Double(290, 150, 150, 150);
        g2.fill(sun);
        g2.draw(sun);
        g2.setPaint(new Color(9, 7, 1));
        Ellipse2D eye1 = new Ellipse2D.Double(330, 190, 10, 10);
        g2.fill(eye1);
        g2.draw(eye1);
        Ellipse2D eye2 = new Ellipse2D.Double(390, 190, 10, 10);
        g2.fill(eye2);
        g2.draw(eye2);
        QuadCurve2D smile = new QuadCurve2D.Double();
        smile.setCurve(340,240,360,220,390,240);
        g2.draw(smile);
    }
}


public class Client {
    private Socket socket;
    private ObjectOutputStream out;

    public static void main(String[] args) {
        Client client = new Client();
        client.startClient();
    }

    @SuppressWarnings("unchecked")
    public void startClient(){

        try {
            socket = new Socket("localhost", 7777);
            MyFrame1 frame = new MyFrame1();
            frame.setTitle("On Client");
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(frame);

            out.close();
            socket.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }}