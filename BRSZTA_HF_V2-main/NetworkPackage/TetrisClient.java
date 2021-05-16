package NetworkPackage;

import GUI_Package.*;
import Main_Logic_Package.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class TetrisClient extends Main_Form{

    protected static Socket clientSocket = null;
    protected static ObjectInputStream objectInputStreamC = null;
    protected static ObjectOutputStream objectOutputStreamC = null;
    private static boolean isClientConnected = false;

    public TetrisClient(){
        super("Client");
    }

    public static void clientConnect() throws Exception{
        clientSocket = new Socket(Settings_Form.IP, Integer.parseInt(Settings_Form.Port));
        System.out.println("I'm connected!");
        isClientConnected = true;
        ConnectionStatus = true;
    }

    public static void initObjectSending() {
        try {
            objectOutputStreamC = new ObjectOutputStream(clientSocket.getOutputStream());
            objectInputStreamC = new ObjectInputStream(clientSocket.getInputStream());
            objectOutputStreamC.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void closeSocket() throws Exception {
        System.out.println("Close socket");
        ConnectionStatus = false;
        isClientConnected = false;
        clientSocket.close();
    }

    public static void receiveObject() throws Exception {
        try{
            sendReceive = (SendReceiveObject) objectInputStreamC.readObject();
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
            objectOutputStreamC.writeObject(sendReceive);
            objectOutputStreamC.flush();
            Board.flagSendObject = false;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static class ClientConnectListener implements ActionListener{
        JButton btnConnect;

        public ClientConnectListener(JButton btnC){
            this.btnConnect = btnC;
        }
        @Override
        public void actionPerformed(ActionEvent event){
            if(event.getSource() == btnConnect){
                try {
                    clientConnect();
                    initObjectSending();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class ClientDisconnectListener implements ActionListener {

        JButton btnDisconnect;

        public ClientDisconnectListener(JButton btnDc){
            btnDisconnect = btnDc;
        }

        @Override
        public void actionPerformed(ActionEvent event){
            if(event.getSource() == btnDisconnect){
                try {
                    System.out.println("Client closing connection");
                    isClientConnected = false;
                    closeSocket();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ObjStreamReaderC implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isClientConnected) {
                    if(objectInputStreamC != null) {
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

    static class ObjStreamWriterC implements Runnable {

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
        TetrisClient client = new TetrisClient();
        client.OpenForm();
        client.initTetris();
        new Thread(new ObjStreamWriterC()).start();
        new Thread(new ObjStreamReaderC()).start();
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
                        if (client.board.flagSendObject) {
                            try {
                                client.sendObject();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            Thread.sleep(1000);
                            client.receiveObject();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();*/


