package GUI_Package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Extend_Test implements ActionListener{

private JButton Button_MoveDown;


public Extend_Test(JButton button_movedown){

Button_MoveDown = button_movedown;

}

@Override
public void actionPerformed(ActionEvent event) {
    if(event.getSource() == Button_MoveDown){
        System.out.println("Masik osztaly Action Listener");
    }

}



}
