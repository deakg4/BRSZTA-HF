package GUI_Package;

import java.util.Timer;
import java.util.TimerTask;

public class Main_Class {

    public static void main(String[] args) {


        Main_Form mainForm = new Main_Form("Server");
        mainForm.OpenForm();

        mainForm.initTetris();

    }


}
