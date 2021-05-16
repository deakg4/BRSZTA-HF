package GUI_Package;

import NetworkPackage.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableCellRenderer;

public class Settings_Form {

    private JPanel Panel_Background;
    private JTabbedPane Tab_Main;
    private JPanel Tab_Scoreboard;
    private JPanel Tab_Network;
    private JTable Table_Score;
    private JScrollPane ScrollPane_Table;
    private JLabel Label_IP_Address;
    private JFormattedTextField TextField_IP_Address_1;
    private JLabel Label_Port;
    private JButton Button_Connect_Server;
    private JButton Button_OK;
    private JButton Button_Disconnect_Server;
    private JFormattedTextField TextField_Port;
    private JFormattedTextField TextField_Conn_Status;
    private JButton Button_OK_2;
    private JFormattedTextField TextField_IP_Address_2;
    private JFormattedTextField TextField_IP_Address_3;
    private JFormattedTextField TextField_IP_Address_4;
    private JButton Button_Connect_Client;
    private JButton Button_Disconnect_Client;
    private JLabel Label_Server;
    private JLabel Label_Client;

    private static JFrame settingsForm = new JFrame("Settings_Form");
    private static Main_Form mainForm = null;
    private static DefaultTableModel scoreTable;

    public static String IP_part_1;
    public static String IP_part_2;
    public static String IP_part_3;
    public static String IP_part_4;
    public static String Port;
    public static String IP;

    private static boolean NetworkConnected = false;

    private static String type;

    private static Boolean FirstScan = false;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Constructor and Action Listeners                                            *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public Settings_Form() {

        settingsForm.setTitle("Settings");

        settingsForm.setResizable(false);

        InitNetworkConnection();

        InitScoreTable();

        setConnectionTypeButtons();

        //Connect Button Action Listener
        Button_Connect_Server.addActionListener(new TetrisServer.ServerConnectListener(Button_Connect_Server));

        Button_Connect_Server.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NetworkConnect();
            }
        });

        //Disconnect Button Action Listener
        Button_Disconnect_Server.addActionListener(new TetrisServer.ServerDisconnectListener(Button_Disconnect_Server));

        Button_Disconnect_Server.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NetworkDisconnect();
            }
        });

        Button_Connect_Client.addActionListener(new TetrisClient.ClientConnectListener(Button_Connect_Client));

        Button_Connect_Client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NetworkConnect();
            }
        });

        Button_Disconnect_Client.addActionListener(new TetrisClient.ClientDisconnectListener(Button_Disconnect_Client));

        Button_Disconnect_Client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NetworkDisconnect();
            }
        });

        //OK Button
        Button_OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CloseForm();
            }
        });

        Button_OK_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CloseForm();
            }
        });
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Set the Main Form Object Pointer                                            *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public void SetMainForm(Main_Form mainForm) {
        this.mainForm = mainForm;
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Open Settings Form                                                          *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public void OpenForm() {
        settingsForm.setContentPane(new Settings_Form().Panel_Background);
        settingsForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsForm.pack();
        settingsForm.setVisible(true);

        Image myIcon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Gergo\\IdeaProjects\\BRSZTA_HF\\src\\GUI_Package\\Wrench_Logo.png");
        settingsForm.setIconImage(myIcon);

        InitNetworkConnection();
        setConnectionTypeButtons();
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Close Settings Form                                                         *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public void CloseForm() {
        settingsForm.dispose();
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Set Server / Client Type                                                    *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public void setConnectionType(String Type)
    {
        type = Type;

        setConnectionTypeButtons();
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Set Server / Client Type Buttons                                            *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    private void setConnectionTypeButtons()
    {
        if(type == "Server")
        {
            Button_Connect_Server.setVisible(true);
            Button_Disconnect_Server.setVisible(true);

            Button_Connect_Client.setVisible(false);
            Button_Disconnect_Client.setVisible(false);

            Label_Server.setVisible(true);
            Label_Client.setVisible(false);

        }
        else if (type == "Client")
        {
            Button_Connect_Server.setVisible(false);
            Button_Disconnect_Server.setVisible(false);

            Button_Connect_Client.setVisible(true);
            Button_Disconnect_Client.setVisible(true);

            Label_Server.setVisible(false);
            Label_Client.setVisible(true);

        }
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Init Score Table                                                            *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    private void InitScoreTable()
    {
        if(FirstScan == false) {
            scoreTable = (DefaultTableModel) Table_Score.getModel();
            FirstScan = true;

            scoreTable.addColumn("Helyezés");
            scoreTable.addColumn("Felhasználónév");
            scoreTable.addColumn("Pont");

            scoreTable.addRow(new Object[]{"1.", "", ""});
            scoreTable.addRow(new Object[]{"2.", "", ""});
            scoreTable.addRow(new Object[]{"3.", "", ""});
            scoreTable.addRow(new Object[]{"4.", "", ""});
            scoreTable.addRow(new Object[]{"5.", "", ""});
            scoreTable.addRow(new Object[]{"6.", "", ""});
            scoreTable.addRow(new Object[]{"7.", "", ""});
            scoreTable.addRow(new Object[]{"8.", "", ""});
            scoreTable.addRow(new Object[]{"9.", "", ""});
            scoreTable.addRow(new Object[]{"10.", "", ""});

        }
        else
        {
            Table_Score.setModel(scoreTable);
        }

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        Table_Score.setEnabled(false);
        Table_Score.getColumnModel().getColumn(0).setPreferredWidth(20);
        Table_Score.getColumnModel().getColumn(1).setPreferredWidth(200);
        Table_Score.getColumnModel().getColumn(2).setPreferredWidth(150);
        Table_Score.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        Table_Score.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        Table_Score.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        Table_Score.setRowHeight(30);



    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Add new value to Score Table                                                *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public void addValueToScoreTable(int row, String username, int point)
    {
        scoreTable.setValueAt(username, row-1, 1);
        scoreTable.setValueAt(point, row-1, 2);

        //scoreTable.fireTableCellUpdated(row-1, 1);
        //scoreTable.fireTableCellUpdated(row-1, 2);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *      Init Network Connection Settings                                             *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    private void InitNetworkConnection()
    {

        if(NetworkConnected == true)
        {
            TextField_IP_Address_1.setText(IP_part_1);
            TextField_IP_Address_2.setText(IP_part_2);
            TextField_IP_Address_3.setText(IP_part_3);
            TextField_IP_Address_4.setText(IP_part_4);

            TextField_Port.setText(Port);

            TextField_Conn_Status.setText("Connected");
            TextField_Conn_Status.setBackground(Color.green);

            if(type == "Server") {
                Button_Connect_Server.setEnabled(false);
                Button_Disconnect_Server.setEnabled(true);
            }
            else if(type =="Client")
            {
                Button_Connect_Client.setEnabled(false);
                Button_Disconnect_Client.setEnabled(true);
            }

        }
        else {
            if(type == "Server") {
                Button_Connect_Server.setEnabled(true);
                Button_Disconnect_Server.setEnabled(false);
                TextField_Conn_Status.setText("Not Connected");
            }
            else if(type =="Client")
            {
                Button_Connect_Client.setEnabled(true);
                Button_Disconnect_Client.setEnabled(false);
                TextField_Conn_Status.setText("Not Connected");
            }
        }
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *      Network Connect                                                              *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    private void NetworkConnect()
    {
        IP_part_1 = TextField_IP_Address_1.getText();         //Get IP Address_1 from TextField
        IP_part_2 = TextField_IP_Address_2.getText();         //Get IP Address_2 from TextField
        IP_part_3 = TextField_IP_Address_3.getText();         //Get IP Address_3 from TextField
        IP_part_4 = TextField_IP_Address_4.getText();         //Get IP Address_4 from TextField

        Port = TextField_Port.getText();               //Get Port from TextField

        if( IP_part_1.isEmpty() || IP_part_2.isEmpty() || IP_part_3.isEmpty() || IP_part_4.isEmpty() || Port.isEmpty() )
        {
            TextField_Conn_Status.setText("IP Address and Port Number required!");
            TextField_Conn_Status.setBackground(Color.red);
        }
        else if (    Integer.parseInt(IP_part_1) < 0 ||  Integer.parseInt(IP_part_1) > 255 ||
                     Integer.parseInt(IP_part_2) < 0 ||  Integer.parseInt(IP_part_2) > 255 ||
                     Integer.parseInt(IP_part_3) < 0 ||  Integer.parseInt(IP_part_3) > 255 ||
                     Integer.parseInt(IP_part_4) < 0 ||  Integer.parseInt(IP_part_4) > 255  )
        {
            TextField_Conn_Status.setText("Wrong IP Address!");
            TextField_Conn_Status.setBackground(Color.red);
        }
        else if( Integer.parseInt(Port) < 0 || Integer.parseInt(Port) > 9999)
        {
            TextField_Conn_Status.setText("Wrong Port Number!");
            TextField_Conn_Status.setBackground(Color.red);
        }
        else {

            IP_part_1 = FormatIP(IP_part_1);
            IP_part_2 = FormatIP(IP_part_2);
            IP_part_3 = FormatIP(IP_part_3);
            IP_part_4 = FormatIP(IP_part_4);

            IP=IP_part_1 + "." + IP_part_2 + "." + IP_part_3 + "." + IP_part_4;

            SetConnectionStatus();
        }
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *      Set Connection Status                                                        *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public void SetConnectionStatus(){

        mainForm.SetNetworkStatus(true);
        mainForm.ConnectionStatus = true;
        NetworkConnected = true;

        TextField_Conn_Status.setText("Connected");
        TextField_Conn_Status.setBackground(Color.green);

        if(type == "Server") {
            Button_Disconnect_Server.setEnabled(true);
            Button_Connect_Server.setEnabled(false);
        }
        else if(type == "Client")
        {
            Button_Disconnect_Client.setEnabled(true);
            Button_Connect_Client.setEnabled(false);
        }
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *     Get IP                                                                        *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public String getIP()
    {
        return IP;
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *     Get Port                                                                        *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public String getPort()
    {
        return Port;
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *      Network Disconnect                                                           *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    private void NetworkDisconnect()
    {
        //TODO DISCONNECT IN NETWORK MANAGEMENT CLASS

        TextField_Conn_Status.setText("Not Connected");
        TextField_Conn_Status.setBackground(Color.white);

        if(type == "Server") {
            Button_Connect_Server.setEnabled(true);
            Button_Disconnect_Server.setEnabled(false);
        }
        else if(type == "Client")
        {
            Button_Connect_Client.setEnabled(true);
            Button_Disconnect_Client.setEnabled(false);
        }

        mainForm.SetNetworkStatus(false);
        mainForm.ConnectionStatus = true;
        NetworkConnected = false;
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *      Formatting the IP address to send                                            *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    private String FormatIP(String IP_Part)
    {
        if(Integer.parseInt(IP_Part) < 10)
        {
            return ("00" + IP_Part);
        }
        else if(Integer.parseInt(IP_Part) < 100)
        {
            return ("0" + IP_Part);
        }
        else
        {
            return IP_Part;
        }
    }
}
