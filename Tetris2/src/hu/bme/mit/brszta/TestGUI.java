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
    private JButton restartButton;

    private Board board = new Board();



    public TestGUI(String title){
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();



        board.start();

        board.setTestGUI(this);

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.restart();
                board.printDebugger();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.pause();
                board.printDebugger();
            }
        });

        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.moveShape(1,0);
                board.printDebugger();
            }
        });

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.moveShape(-1,0);
                board.printDebugger();
            }
        });

        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.rotateShape(1);
                board.printDebugger();
            }
        });

        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.update();
                //Ez egy olyan getter ami átveszi a boardot.
                int [][] BOARDMATRIX = board.getBoard();
                System.out.println("Action Listener down Button:");
                board.printDebugger();
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
    void setTfNextShape(int index){
        tfNextShape.setText(String.valueOf(index));
    }
    void setTfNextShapeRotation(int rotationIndex){
        tfNextShapeRotation.setText(String.valueOf(rotationIndex));
    }
}
