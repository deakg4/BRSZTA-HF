package GUI_Package;

import java.io.Serializable;

/*
    This class is used for collecting all the data (Game board state, point, username etc.)
    and then send it over the socket all-in-one.
 */

public class SendReceiveObject implements Serializable {
    public int[][] mx;
    public int pnt;
    public String usrNm;

    public SendReceiveObject() {
        mx = new int[20][10];
        pnt = 0;
        usrNm = "";
    }
}
