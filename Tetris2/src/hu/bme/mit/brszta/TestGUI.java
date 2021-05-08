package hu.bme.mit.brszta;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestGUI extends JFrame {
    private JButton leftButton;
    private JButton rightButton;
    private JButton downButton;
    private JButton rotateButton;
    private JPanel mainPanel;
    private JTextField tfScore;
    private JLabel lblScore;
    private JButton pauseButton;
    private JTextField tfNextShape;
    private JTextField tfNextShapeRotation;

    private Board board = new Board();

    public TestGUI(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();


        board.start();

        board.setTestGUI(this);

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.pause();
            }
        });

        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.moveShape(1,0);
            }
        });

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.moveShape(-1,0);
            }
        });

        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.rotateShape(1);
            }
        });

        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.update();
                //Ez egy olyan getter ami átveszi a boardot.
                int [][] BOARDMATRIX = board.getBoard();
                System.out.println("Action Listener down Button:");
                for(int i = 0; i< 20;i++) {
                    for (int j = 0; j < 10; j++) {
                        System.out.print(BOARDMATRIX[i][j]);
                    }
                    System.out.println();
                }
            }
        });

    }
    void setPauseButton(String btnTitle){
        pauseButton.setText(btnTitle);
    }
    void setTfScore(int score){
        //Van rá egy getter is ha így jobb
        //score =  board.getNumLinesRemoved();
        tfScore.setText(String.valueOf(score));
    }
    void setTfNextShape(){

    }
}
