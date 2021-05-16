package GUI_Package;

import javax.swing.*;
import java.awt.*;

public class Draw_Small extends JPanel{

private static int[][] Tetromino_0={{0,0,0,0,0,0},
                                    {0,0,0,0,0,0},
                                    {0,0,0,0,0,0},
                                    {0,0,0,0,0,0},};

private static int[][] Tetromino_4={{0,0,0,0,0,0},
                                    {0,0,0,0,0,0},
                                    {0,1,1,1,1,0},
                                    {0,0,0,0,0,0},};

private static int[][] Tetromino_3={{0,0,0,0,0,0},
                                    {0,1,0,0,0,0},
                                    {0,1,1,1,0,0},
                                    {0,0,0,0,0,0}};

private static int[][] Tetromino_2={{0,0,0,0,0,0},
                                    {0,0,0,1,0,0},
                                    {0,1,1,1,0,0},
                                    {0,0,0,0,0,0}};

private static int[][] Tetromino_5={{0,0,0,0,0,0},
                                    {0,0,1,1,0,0},
                                    {0,0,1,1,0,0},
                                    {0,0,0,0,0,0}};

private static int[][] Tetromino_7={{0,0,0,0,0,0},
                                    {0,0,1,1,0,0},
                                    {0,1,1,0,0,0},
                                    {0,0,0,0,0,0}};

private static int[][] Tetromino_1={{0,0,0,0,0,0},
                                    {0,0,1,0,0,0},
                                    {0,1,1,1,0,0},
                                    {0,0,0,0,0,0}};

private static int[][] Tetromino_6={{0,0,0,0,0,0},
                                    {0,1,1,0,0,0},
                                    {0,0,1,1,0,0},
                                    {0,0,0,0,0,0}};




private static int rectangleSize = 30;
private static int X_Offset = 20;
private static int Y_Offset = -15;
private static int border_Width = 2;

private int tetrominoType = 0;


public void SetTetrominoType(int type)
{
    this.tetrominoType = type;

}


@Override
public void paintComponent(Graphics g){

    super.paintComponent(g);
    this.setBackground(Color.white);


    for (int row = 0; row < 4; row++) {
        for (int column = 0; column < 6; column++) {
            switch (tetrominoType)
            {
                case 0: break;
                case 1:
                    if (Tetromino_1[row][column] == 1)
                    {
                        g.setColor(Color.black);
                        g.fillRect((column * rectangleSize), (row * rectangleSize)+Y_Offset, rectangleSize-border_Width, rectangleSize-border_Width);
                    }break;

                case 2:
                    if (Tetromino_2[row][column] == 1)
                    {
                        g.setColor(Color.black);
                        g.fillRect((column * rectangleSize)+X_Offset, (row * rectangleSize), rectangleSize-border_Width, rectangleSize-border_Width);
                    }break;

                case 3:
                    if (Tetromino_3[row][column] == 1)
                    {
                        g.setColor(Color.black);
                        g.fillRect((column * rectangleSize)+X_Offset, (row * rectangleSize), rectangleSize-border_Width, rectangleSize-border_Width);
                    }break;

                case 4:
                    if (Tetromino_4[row][column] == 1)
                    {
                        g.setColor(Color.black);
                        g.fillRect((column * rectangleSize), (row * rectangleSize), rectangleSize-border_Width, rectangleSize-border_Width);
                    }break;

                case 5:
                    if (Tetromino_5[row][column] == 1)
                    {
                        g.setColor(Color.black);
                        g.fillRect((column * rectangleSize)+X_Offset, (row * rectangleSize), rectangleSize-border_Width, rectangleSize-border_Width);
                    }break;

                case 6:
                    if (Tetromino_6[row][column] == 1)
                    {
                        g.setColor(Color.black);
                        g.fillRect((column * rectangleSize)+X_Offset, (row * rectangleSize), rectangleSize-border_Width, rectangleSize-border_Width);
                    }break;

                case 7:
                    if (Tetromino_7[row][column] == 1)
                    {
                        g.setColor(Color.black);
                        g.fillRect((column * rectangleSize)+X_Offset, (row * rectangleSize), rectangleSize-border_Width, rectangleSize-border_Width);
                    }break;

                default:
                    System.out.println("FAULT: Not a Valid Tetromino Type!");


            }
        }
    }



}

@Override
public Dimension getPreferredSize()
{
    return new Dimension(180, 120);
}


}
