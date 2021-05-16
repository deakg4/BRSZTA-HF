package GUI_Package;
import java.awt.*;
import javax.swing.*;
import java.awt.Graphics;

public class Draw extends JPanel{

private static int rectangleSize= 30;
private static int borderWidth = 2;


private int[][] Matrix = new int[20][10];




public void SetMatrix(int Matrix[][])
{
    for(int row = 0; row < 20; row++)
    {
        for(int column = 0; column < 10; column++)
        {
            this.Matrix[row][column] = Matrix[row][column];
        }
    }
}



@Override
public void paintComponent(Graphics g){

    super.paintComponent(g);
    this.setBackground(Color.white);


    for (int row = 0; row < 20; row++) {
        for (int column = 0; column < 10; column++) {
            if (Matrix[row][column] == 1)
            {
                    g.setColor(Color.black);
                    g.fillRect((column * rectangleSize), (row * rectangleSize), rectangleSize-borderWidth, rectangleSize-borderWidth);
            }
        }
    }



}

@Override
public Dimension getPreferredSize()
{
    return new Dimension(300, 600);
}


}
