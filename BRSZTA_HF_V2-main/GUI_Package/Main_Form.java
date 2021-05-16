package GUI_Package;

import Main_Logic_Package.Board;
import Main_Logic_Package.Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;


public class Main_Form extends Tetris {

    private JPanel Panel_Background;
    private JButton Button_Settings;
    private JFormattedTextField ActualScore;
    private JFormattedTextField TextField_Username;
    private JSeparator Separator;
    private JButton Button_Exit;
    private JButton Button_Rotate;
    private JButton Button_MoveDown;
    private JPanel GameField;
    private JPanel GameField_Multi_Left;
    private JPanel GameField_Single;
    private JPanel GameField_Multi_Right;
    private JSeparator GameField_Multi_Separator;
    private JButton Button_Start;
    private JButton Button_MoveRight;
    private JButton Button_MoveLeft;
    private JLabel Label_Game_Mode;
    private JPanel Panel_NextTetromino;
    private JButton Button_Stop;
    private JButton Button_NewGame;

    private static JFrame mainForm = new JFrame("Main_Form");
    protected static Settings_Form SettingsForm = new Settings_Form();

    private Draw Panel_Game_Single = new Draw();
    private Draw Panel_Game_Multi_Right = new Draw();
    private Draw Panel_Game_Multi_Left = new Draw();

    private Draw_Small PanelNextTetromino = new Draw_Small();

    private static String type;

    private static javax.swing.Timer Tick_Timer;
    private static int TimerInterval = 1000;
    protected static Boolean GameStarted = false;
    protected static boolean ConnectionStatus = false;

    protected Board board = new Board();

    public static String Username = "";
    public static String PartnerUsername = "";

    public static int Point = 0;
    public static int PartnerPoint = 0;

    private static Boolean GameStopped = false;

    protected static int[][] Matrix1 = new int[20][10];

    protected static int[][] Matrix2 = new int[20][10];

    public static SendReceiveObject sendReceive ;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Constructor and Action Listeners                                            *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public Main_Form(String Type) {
        sendReceive = new SendReceiveObject();

        if(Type == "Server" || Type == "Client")
        {
            type = Type;

        }
        else
        {
            System.out.println("Wrong Server / Client Type!");
        }

        mainForm.setResizable(false);

        InitSettingsForm();

        InitMainForm();

        Tick_Timer = new javax.swing.Timer(TimerInterval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimerCalling();
            }
        });

        mainForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                Tick_Timer.stop();
            }
        });

        Button_Settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsForm.OpenForm();
            }
        });

        Button_Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Tick_Timer.stop();

                SettingsForm.CloseForm();
                System.exit(0);
            }
        });

        TextField_Username.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        TextField_Username.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                TextField_Username.setEditable(false);
                Button_Start.setEnabled(true);
                Button_Stop.setEnabled(true);

                Button_MoveDown.setEnabled(true);
                Button_MoveLeft.setEnabled(true);
                Button_MoveRight.setEnabled(true);
                Button_Rotate.setEnabled(true);

                Button_NewGame.setEnabled(true);

                Username = TextField_Username.getText();
            }
        });

        Button_Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GameStopped = false;

                Button_Start.setEnabled(true);
                Button_Stop.setEnabled(true);

                Button_MoveDown.setEnabled(true);
                Button_MoveLeft.setEnabled(true);
                Button_MoveRight.setEnabled(true);
                Button_Rotate.setEnabled(true);

                if(Button_Start.getText() == "Start")
                {
                    //TODO CALL MAIN LOGIC START GAME
                    if(GameStarted)
                    {
                        board.pause();
                        //Tick_Timer.stop();

                        Button_Rotate.setEnabled(true);
                        Button_MoveRight.setEnabled(true);
                        Button_MoveLeft.setEnabled(true);
                        Button_MoveDown.setEnabled(true);

                    }
                    else {

                        Tick_Timer.start();
                        shapeBuffer = board.generateRandomShapes(200);
                        board.start();

                        GameStarted = true;
                    }
                    Button_Start.setText("Pause");

                }
                else if(Button_Start.getText() == "Pause") {
                    //TODO CALL MAIN LOGIC PAUSE GAME
                    board.pause();

                    Button_Start.setText("Start");

                    Button_Rotate.setEnabled(false);
                    Button_MoveRight.setEnabled(false);
                    Button_MoveLeft.setEnabled(false);
                    Button_MoveDown.setEnabled(false);

                }
            }
        });

        Button_Stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GameStopped = true;

                Button_Start.setText("Start");
                Button_Start.setEnabled(false);
                Button_Stop.setEnabled(false);

                Button_MoveDown.setEnabled(false);
                Button_MoveLeft.setEnabled(false);
                Button_MoveRight.setEnabled(false);
                Button_Rotate.setEnabled(false);

                StopGame();

                board.stop();

                GameStarted = false;

                JOptionPane.showMessageDialog(new JFrame(), "Game Over!", "Game Over!", JOptionPane.ERROR_MESSAGE);

            }
        });

        Button_NewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // TODO CALL MAIN LOGIC NEW GAME

                TextField_Username.setText("");
                TextField_Username.setEditable(true);

                Button_NewGame.setEnabled(false);

                Button_Start.setText("Start");
                Button_Start.setEnabled(false);
                Button_Stop.setEnabled(false);

                Button_MoveDown.setEnabled(false);
                Button_MoveLeft.setEnabled(false);
                Button_MoveRight.setEnabled(false);
                Button_Rotate.setEnabled(false);

                StopGame();

                board.stop();

                if(!ConnectionStatus) {
                    board.update();
                    Matrix1 = board.getBoard();
                    Panel_Game_Single.SetMatrix(Matrix1);
                    GameField_Single.repaint();
                    SetActualScore(board.getNumLinesRemoved());
                    Point = board.getNumLinesRemoved();

                }else{
                    board.update();
                    Matrix1 = board.getBoard();
                    Panel_Game_Multi_Right.SetMatrix(Matrix1);
                    GameField_Multi_Right.repaint();
                    Panel_Game_Multi_Left.SetMatrix(Matrix2);
                    GameField_Multi_Left.repaint();
                    SetActualScore(board.getNumLinesRemoved());
                    Point = board.getNumLinesRemoved();
                    board.setServer(type);
                }

                GameStarted = false;


            }
        });

        Button_MoveDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                board.update();

                repaintAll();

            }
        });

        Button_MoveRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.moveShape(1,0);

                repaintAll();

            }
        });

        Button_MoveLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.moveShape(-1,0);

                repaintAll();

            }
        });

        Button_Rotate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.rotateShape(1);

                repaintAll();

            }
        });


    }



    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Open Main Form                                                              *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public void OpenForm()
    {
        mainForm.setContentPane(new Main_Form(type).Panel_Background);
        mainForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainForm.pack();
        mainForm.setVisible(true);

        Image myIcon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Gergo\\IdeaProjects\\BRSZTA_HF\\src\\GUI_Package\\Tetris_Icon.png");
        mainForm.setIconImage(myIcon);

    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Init Settings Form                                                          *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    private void InitSettingsForm()
    {
        SettingsForm.SetMainForm(this);
        SettingsForm.setConnectionType(type);
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Init Main Form                                                              *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    private void InitMainForm()
    {
        mainForm.setTitle("Tetris");

        Label_Game_Mode.setText("Single Player");

        GameField_Single.setEnabled(true);
        GameField_Single.setVisible(true);

        GameField_Multi_Left.setEnabled(false);
        GameField_Multi_Right.setEnabled(false);
        GameField_Multi_Left.setVisible(false);
        GameField_Multi_Right.setVisible(false);
        GameField_Multi_Separator.setVisible(false);

        GameField_Single.add(Panel_Game_Single);

        PanelNextTetromino.SetTetrominoType(5);
        Panel_NextTetromino.add(PanelNextTetromino);

        Button_NewGame.setEnabled(false);

        Button_Start.setText("Start");
        Button_Start.setEnabled(false);
        Button_Stop.setEnabled(false);

        Button_MoveDown.setEnabled(false);
        Button_MoveLeft.setEnabled(false);
        Button_MoveRight.setEnabled(false);
        Button_Rotate.setEnabled(false);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Timer calling function                                                      *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


    private void setShapesOnBoard(){
        board.importShapes(shapeBuffer);
    }

    private void repaintAll(){
        Matrix1 = board.getBoard();
        if(!ConnectionStatus) {

            Panel_Game_Single.SetMatrix(Matrix1);
            GameField_Single.repaint();
            SetActualScore(board.getNumLinesRemoved());
            Point = board.getNumLinesRemoved();
            int nextTetromino = board.getNextShape();
            PanelNextTetromino.SetTetrominoType(nextTetromino);
            PanelNextTetromino.repaint();
        }else{
            Panel_Game_Multi_Right.SetMatrix(Matrix1);
            GameField_Multi_Right.repaint();
            Panel_Game_Multi_Left.SetMatrix(Matrix2);
            GameField_Multi_Left.repaint();
            SetActualScore(board.getNumLinesRemoved());
            Point = board.getNumLinesRemoved();
        }
    }

    private void TimerCalling()
    {
        if(GameStarted) {
            board.update();
            repaintAll();
        }

        if(board.GetGameFieldFull())
        {
            GameStopped = true;

            Button_Start.setText("Start");
            Button_Start.setEnabled(false);
            Button_Stop.setEnabled(false);

            Button_MoveDown.setEnabled(false);
            Button_MoveLeft.setEnabled(false);
            Button_MoveRight.setEnabled(false);
            Button_Rotate.setEnabled(false);

            StopGame();

            board.stop();

            GameStarted = false;

            JOptionPane.showMessageDialog(new JFrame(), "Game Over!", "Game Over!", JOptionPane.ERROR_MESSAGE);

        }

    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Switch between Single / Multiplayer Mode                                    *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public void SetNetworkStatus(boolean Status)
    {
        if(Status == true)
        {
            ConnectionStatus = true;
            Label_Game_Mode.setText("Multiplayer");

            GameField_Single.setEnabled(false);
            GameField_Single.setVisible(false);

            GameField_Multi_Left.setEnabled(true);
            GameField_Multi_Right.setEnabled(true);
            GameField_Multi_Left.setVisible(true);
            GameField_Multi_Right.setVisible(true);
            GameField_Multi_Separator.setVisible(true);

            GameField_Single.remove(Panel_Game_Single);

            GameField_Multi_Right.add(Panel_Game_Multi_Right);
            GameField_Multi_Left.add(Panel_Game_Multi_Left);

        }
        else
        {
            ConnectionStatus = false;
            Label_Game_Mode.setText("Single Player");

            GameField_Single.setEnabled(true);
            GameField_Single.setVisible(true);

            GameField_Multi_Left.setEnabled(false);
            GameField_Multi_Right.setEnabled(false);
            GameField_Multi_Left.setVisible(false);
            GameField_Multi_Right.setVisible(false);
            GameField_Multi_Separator.setVisible(false);

            GameField_Single.add(Panel_Game_Single);

            GameField_Multi_Right.remove(Panel_Game_Multi_Right);
            GameField_Multi_Left.remove(Panel_Game_Multi_Left);

        }
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Get TextField_Username                                                      *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public String GetUsername()
    {
        return (TextField_Username.getText());
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Set Actual Score                                                            *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public void SetActualScore(int actualScore)
    {
        ActualScore.setText(Integer.toString(actualScore));
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Set Next Tetromino in Small panel (0-7)                                     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public void SetNextTetromino(int tetrominoType)
    {
        PanelNextTetromino.SetTetrominoType(tetrominoType);
        PanelNextTetromino.repaint();
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Set SinglePlayer Matrix for Panel display                                   *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public void SetSinglePlayerMatrix(int Matrix[][])
    {
        if(ConnectionStatus == false)
        {
            for (int row = 0; row < 20; row++) {
                for (int column = 0; column < 10; column++)
                {
                    Matrix1[row][column] = Matrix[row][column];
                }
            }

            Panel_Game_Single.SetMatrix(Matrix1);
            GameField_Single.repaint();
        }
        else
        {
            System.out.println("FAULT in SinglePlayer mode -> Panel matrix display!");
        }
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Set MultiPlayer Matrix for Panel display                                    *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public void SetMultiPlayerMatrix(int MatrixLeft[][], int MatrixRight[][])
    {
        if(ConnectionStatus == true)
        {
            for (int row = 0; row < 20; row++) {
                for (int column = 0; column < 10; column++)
                {
                    Matrix1[row][column] = MatrixLeft[row][column];
                }
            }
            for (int row = 0; row < 20; row++) {
                for (int column = 0; column < 10; column++)
                {
                    Matrix2[row][column] = MatrixRight[row][column];
                }
            }

            Panel_Game_Multi_Left.SetMatrix(Matrix1);
            Panel_Game_Multi_Right.SetMatrix(Matrix2);

            GameField_Multi_Left.repaint();
            GameField_Multi_Right.repaint();

        }
        else
        {
            System.out.println("FAULT in MultiPlayer mode -> Panel matrix display!");
        }
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Add New Score to Score table                                                *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public void AddNewScore(int row, String username, int point)
    {
        if(row <= 10)
        {
            SettingsForm.addValueToScoreTable(row, username, point);
        }
        else
        {
            System.out.println("FAULT in Score Table Row number!");
        }
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Set Partner's UserName                                                      *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public static void SetPartnerUsername(String PUsername)
    {
        PartnerUsername = PUsername;
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Set Partner's Point                                                         *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    public static void SetPartnerPoint(int point)
    {
        PartnerPoint = point;
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *       Stop Game                                                                   *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    private void StopGame()
    {
        if (ConnectionStatus)
        {
            if (Point > PartnerPoint) {
                AddNewScore(1, Username, Point);
                AddNewScore(2, PartnerUsername, PartnerPoint);
            } else {
                AddNewScore(1, PartnerUsername, PartnerPoint);
                AddNewScore(2, Username, Point);
            }
        }
        else
        {
            AddNewScore(1, Username, Point);
        }
    }
}


