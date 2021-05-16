package NetworkPackage;

import GUI_Package.*;
import Main_Logic_Package.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TetrisServer extends Main_Form {

    private static Socket clientSocket = null;
    private static ServerSocket serverSocket = null;
    private static ObjectInputStream objectInputStreamS = null;
    private static ObjectOutputStream objectOutputStreamS = null;
    private static boolean isClientConnected = false;


    public TetrisServer(){
        super("Server");
    }

    public static void openSocket() throws Exception{
        serverSocket = new ServerSocket(Integer.parseInt(Settings_Form.Port));
        System.out.println("Waiting for connection...");
        clientSocket = serverSocket.accept();
        System.out.println("Client connected!");
        isClientConnected = true;
        ConnectionStatus = true;
    }

    public static void initObjectSending() {
        try {
            objectOutputStreamS = new ObjectOutputStream(clientSocket.getOutputStream());
            objectInputStreamS = new ObjectInputStream(clientSocket.getInputStream());
            objectOutputStreamS.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void receiveObject() throws Exception {
        try{
            sendReceive = (SendReceiveObject) objectInputStreamS.readObject();
            Matrix2 = sendReceive.mx;
            PartnerPoint = sendReceive.pnt;
            PartnerUsername = sendReceive.usrNm;

            System.out.println("Opponents name: " + PartnerUsername);
            System.out.println("Opponents point: " + PartnerPoint);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendObject() {
        try{
            sendReceive.pnt = Point;
            sendReceive.usrNm = Username;
            sendReceive.mx = Matrix1;
            objectOutputStreamS.writeObject(sendReceive);
            objectOutputStreamS.flush();
            Board.flagSendObject = false;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void closeSocket() throws Exception {
        System.out.println("Close sockets");
        isClientConnected = false;
        ConnectionStatus = false;
        serverSocket.close();
        clientSocket.close();
    }

    public static class ServerConnectListener implements ActionListener {

        JButton btnConnect;

        public ServerConnectListener(JButton button){
            btnConnect = button;
        }

        @Override
        public void actionPerformed(ActionEvent event){
            if(event.getSource() == btnConnect){
                try {
                    openSocket();
                    initObjectSending();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class ServerDisconnectListener implements ActionListener {

        JButton btnDisconnect;

        public ServerDisconnectListener(JButton button){
            btnDisconnect = button;
        }

        @Override
        public void actionPerformed(ActionEvent event){
            if(event.getSource() == btnDisconnect){
                try {
                    System.out.println("Server closing connection");
                    isClientConnected = false;
                    closeSocket();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ObjStreamReaderS implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isClientConnected) {
                    if(objectInputStreamS != null) {
                        try {
                            Thread.sleep(500);
                            receiveObject();
                            System.out.println("Received");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    static class ObjStreamWriterS implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isClientConnected) {
                    try {
                        if (Board.flagSendObject) {
                            sendObject();
                            Thread.sleep(1000);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main (String [] args) throws Exception {
        TetrisServer server = new TetrisServer();
        server.OpenForm();
        initTetris();
        new Thread(new ObjStreamWriterS()).start();
        new Thread(new ObjStreamReaderS()).start();
    }
}

/*new Thread() {

            @Override
            public void run(){
                while(true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isClientConnected) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (server.board.flagSendObject) {
                            try {
                                server.sendObject();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }.start();*/