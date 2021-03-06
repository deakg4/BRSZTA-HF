package Main_Logic_Package;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class Board {

    private static int curX = 0;
    private static int curY = 0;

    private int numLinesRemoved;
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 20;
    private final int PERIOD_INTERVAL = 300;

    public boolean isFallingFinished;
    public boolean isPaused = false;
    public static boolean flagSendObject = false;
    private int[][] position;

    private Shape curPiece;
    private Timer timer;
    private static int[][] BOARDMATRIX = new int[BOARD_HEIGHT][BOARD_WIDTH];

    public static List<Integer> shapeBuffer = new ArrayList<>();
    private List<Integer> shapeRotationBuffer = new ArrayList<>();

    private int index;
    private int rotationIndex;
    private int nextIndex;
    private int nextRotationIndex;

    private Boolean GameStopped = false;
    private Boolean isServer = true;
    private Boolean GameFieldFull = false;

    public Board() {
        isServer = true;
        curPiece = new Shape();
        position = new int[curX][curY];
        for(int i = 0; i < BOARD_HEIGHT; i++){
            for(int j = 0; j < BOARD_WIDTH; j++){
                BOARDMATRIX[i][j] = 0;
            }
        }
    }

    public void setServer(String serverType){
        if (serverType == "Server") {
            isServer = true;
        }else if(serverType == "Client"){
            isServer= false;
        }
    }

    public int getBoardWidth(){
        //System.out.println("Debug, Board o, getBoardWidth fgv:");
        return BOARD_WIDTH;
    }
    public int getBoardHeight(){
        //System.out.println("Debug, Board o, getBoardHeight fgv:");
        return BOARD_HEIGHT;
    }

    public void pause(){
        //átállítja az isPaused értékét
        //System.out.println("Debug, Board o, pause fgv:");
        isPaused = !isPaused;
    }

    public int [][] getBoard(){
        int [][] arrBoard = new int[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                arrBoard[i][j] = getBoardValue(i,j);
                //System.out.print(getBoardValue(i,j));
            }
            //System.out.println();
        }
        return arrBoard;
        //return BOARDMATRIX;
    }

    public void start(){
        //System.out.println("Debug, Board o, start fgv:");



        addRandomShape();

        System.out.println("index: " + index);

        GameStopped = false;

    }

    public int getNumLinesRemoved(){
        return numLinesRemoved;
    }

    public List<Integer> generateRandomShapes(int numOfGenerates) {
        for (int i = 1; i <= numOfGenerates; i++) {
            var r = new Random();
            int x = Math.abs(r.nextInt()) % 4;
            int y = Math.abs(r.nextInt()) % 7 + 1;
            shapeBuffer.add(y);
            shapeRotationBuffer.add(x);
        }
        List<Integer> tempShapeBuffer = shapeBuffer;
        index = shapeBuffer.get(0);
        rotationIndex = shapeRotationBuffer.get(0);
        nextIndex = shapeBuffer.get(1);
        nextRotationIndex = shapeRotationBuffer.get(1);
        //Beállítja az új beírando shapet a 0 indexen lévő számra
        //A setRandomShapet kicserélem egy setShape fgv-re mert itt a boardban generálom le a shape számát
        curPiece.setShape(index);
        //Ezzel a remove-val kitörlöm a 0 indexen lévő számot.
        shapeBuffer.remove(0);
        shapeRotationBuffer.remove(0);
        return tempShapeBuffer;
    }


    public void importShapes(List<Integer> shapes){
         shapeBuffer = shapes;
    }
    public List<Integer> exportShapes(){
        return shapeBuffer;
    }

    private void addRandomShape(){
        //Hozzáad egy random alakztatot a board tetejéhez és elforgatja véletlenszerűen
        //DEBUG
        System.out.println("Debug, Board o, addRandomShape fgv");
        //DEBUG
        curX = BOARD_WIDTH/2;
        curY = 0;
        //Hozzáad egy új random shapet
        generateRandomShapes(1);

        if (tryAddShape(curX,curY)){
            addShape(curX, curY);
            rotateShape(rotationIndex);
        }else {

            GameFieldFull = true;

            System.out.println("Játék vége nem lehetett elhelyezni az alakzatot");
        }
    }

    public void stop(){
        GameFieldFull = false;
        GameStopped = true;
        BOARDMATRIX = new int[BOARD_HEIGHT][BOARD_WIDTH];
        numLinesRemoved = 0;
        shapeBuffer.removeAll(shapeBuffer);
        shapeRotationBuffer.removeAll(shapeRotationBuffer);
    }

    public Boolean GetGameFieldFull ()
    {
        return GameFieldFull;
    }

    private boolean tryAddShape(int x, int y){
        //System.out.println("Debug, Board o, tryAddShape fgv");
        int maxX = curPiece.getMaxX();
        int maxY = curPiece.getMaxY();
        int overlaps = 0;
        int [][] tempMatrix = new int[4][4];
        int [][] coords = curPiece.getShapeCoords();
        for (int i = 0; i <= maxY; i++){
            for (int j = 0; j <= maxX; j++){
                tempMatrix[i][j] = BOARDMATRIX[y + i][x + j] + coords[i][j];
                if (tempMatrix[i][j]>1){
                    overlaps +=1;
                }
            }
        }
        if (overlaps > 0){
            System.out.println("Debug, Board o, tryOverlap fgv, : A két alakzat átfedésbe került");
            return false;
        } else {
            return true;
        }
    }

    private void doGameCycle(){
        //System.out.println("Debug, Board o, doGameCycle fgv");
        update();
        //a megrajzoláshoz majd a közös munkánál jutunk el
        //repaint();
    }

    public void update(){
        //System.out.println("Debug, Board o, update fgv:");
        if (isPaused || GameStopped){
            return;
        }
        if (isFallingFinished){
            isFallingFinished = false;
            removeFullLines();
            addRandomShape();
        }else {
            oneLineDown();
        }
    }

    public void addShape(int x, int y) {
        //System.out.println("Debug, Board o, addShape fgv:");
        //int[][] board = new Board().BOARDMATRIX;
        int[][] curShape = curPiece.getShapeCoords();
        //int index = shape.getShapeIndex();
        int maxX = curPiece.getMaxX();
        int maxY = curPiece.getMaxY();
        for (int i = 0; i <= maxY; i++) {
            for (int j = 0; j <= maxX; j++) {
                BOARDMATRIX[y + i][x + j] += curShape[i][j];
            }
        }
        curX = x;
        curY = y;
        /*//DEBUG
        for(int i = 0; i< 20;i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(BOARDMATRIX[i][j]);
            }
            System.out.println();
        }
        //DEBUG-END*/
    }

    public void subtractShape(int x, int y){
        //kivonja az alakztatot a megadott pozícióból
        //System.out.println("Debug, Board o, subtractShape fgv:");
        int[][] curShape = curPiece.getShapeCoords();
        int maxX = curPiece.getMaxX();
        int maxY = curPiece.getMaxY();
        for (int i = 0; i <= maxY; i++) {
            for (int j = 0; j <= maxX; j++) {
                BOARDMATRIX[y + i][x + j] -= curShape[i][j];
            }
        }
    }

    public void moveShape(int moveX, int moveY){
        //System.out.println("Debug, Board o, moveShape fgv:");
        subtractShape(curX, curY);
        if (tryMove(moveX, moveY)) {
            addShape(curX+moveX, curY+moveY);
            //curX = curX + moveX;
            //curY = curY + moveY;
        }else {
            System.out.println("nem lehet a cél mezőre: "+moveX+", "+moveY+" mozgatni az alakzatot");
            addShape(curX, curY);
            //Az alakzat ütközött vagy a pálya végére ért, ilyenkor ellenőrizni kell hogy vannak e teli sorok.
        }
    }

    private void pieceDropped(){

        isFallingFinished = true;
        flagSendObject = true;
    }

    private void oneLineDown(){
        moveShape(0,1);
    }

    private boolean tryRotateShape(int rotateNum){
        //System.out.println("Debug, Board o, tryRotateShape fgv:");
        //megpróbálom elforgatni az alakzatot

        for (int i = 0; i< rotateNum; i++) {
            curPiece.startRotateRight();
        }
        //ellenőrzöm az új helyen az alakzat felülír e egy másik alakzatot
        if (tryOverlap(0, 0)){
            return true;
        }else{
            curPiece.resetShapeCoords();
            System.out.println("Nem lehet elforgatni az alakzatot");
            return false;
            //ha nem sikerül beírni az elforgatott alakzatot a helyére akkor visszaállítja az eredeti állásába.
        }
    }

    public void rotateShape(int rotateNum){
        //System.out.println("Debug, Board o, rotateShape fgv:");
        curPiece.saveShapeCoords();
        subtractShape(curX, curY);
        if (tryRotateShape(rotateNum)) {
            /*for (int i = 0; i < rotateNum; i++) {
                curPiece.startRotateRight();
            }*/
            addShape(curX, curY);
        }else {
            addShape(curX,curY);
            System.out.println("Nem lehet elforgatni az alakzatot");
        }
    }

    private boolean tryMove(int newX, int newY){
        //System.out.println("Debug, Board o, tryMoveShape fgv:");
        //Ellenőrzi, hogy az adott alakzatot lehetséges e a célmezőre írni.
        int x = curX + newX;
        int y = curY + newY;
        int maxX = curPiece.getMaxX();
        int maxY = curPiece.getMaxY();
        //Ellenőrzi, hogy a játéktéren belül vagyunk e
        if(x < 0 || y < 0 || x + maxX >= BOARD_WIDTH || y + maxY >= BOARD_HEIGHT){
            if (y + maxY >= BOARD_HEIGHT){
                //Az alakzat elérte a pálya alját új alakzatot kell letenni.
                pieceDropped();
            }
            //A játéktéren kívülre esik az alakzatunk egy része ezert HAMIS
            return false;
        }else {
            //Játéktéren belül vagyunk
            if (tryOverlap(newX, newY)) {
                //Nincs átfedés az új pozícióban.
                return true;
            }else {
                //Átfedés van az új pozícióban.
                if (newY > 0) pieceDropped();
                return false;
            }
        }
    }

    private boolean tryOverlap(int newX, int newY){
        //Ellenőrizni kell, hogy az újonnan beírt alakzatom nem került e átfedésbe egy már létező alakzattal
        //Az átfedés azt jelenti, hogy a cella érték nagyobb lesz mint 1
        //System.out.println("Debug, Board o, tryOverlap fgv");
        int x = curX + newX;
        int y = curY + newY;
        int maxX = curPiece.getMaxX();
        int maxY = curPiece.getMaxY();
        int overlaps = 0;
        int [][] tempMatrix = new int[4][4];
        int [][] coords = curPiece.getShapeCoords();
        if(x < 0 || y < 0 || x + maxX >= BOARD_WIDTH || y + maxY >= BOARD_HEIGHT) {
            return false;
        }else{
            for (int i = 0; i <= maxY; i++) {
                for (int j = 0; j <= maxX; j++) {
                    tempMatrix[i][j] = BOARDMATRIX[y + i][x + j] + coords[i][j];
                    if (tempMatrix[i][j] > 1) {
                        overlaps += 1;
                    }
                }
            }
            if (overlaps > 0) {
                System.out.println("A két alakzat átfedésbe került");
                return false;
            } else {
                return true;
            }
        }
    }

    public int getBoardValue(int x, int y){
        return BOARDMATRIX[x][y];
    }

    private void removeFullLines(){
        //System.out.println("Debug, Board o, removeFullLines fgv");
        int sumValueLine = 0;
        boolean lineIsFull = false;
        int numFullLine = 0;
        //Végig ellenőrzi, hogy van e teli sor a játékmezőn
        for (int i = BOARD_HEIGHT - 1; i >=0; i--){
            sumValueLine = 0;
            for (int j = BOARD_WIDTH - 1; j >= 0; j--) {
                sumValueLine += BOARDMATRIX[i][j];
            }
            //Ha egy sor elemeinek összege 10 akkor a sor tele van
            if (sumValueLine == BOARD_WIDTH){
                lineIsFull = true;
                //elmentem egy változóba hanyas sorok voltak tele
                numFullLine = i;
            }
        }
        if (lineIsFull){
            //ide kell írni egy fgv-t ami kitörli a teli sort és egy sorral lejjebb másol minden elemet ami a táblán van.
            deleteLines(numFullLine);
            numLinesRemoved++;
            System.out.println("Törölt sorok: "+ numLinesRemoved);
        }
    }

    private void deleteLines(int numFullLine){
        //System.out.println("Debug, Board o, deleteLines fgv");
        for (int j = 0; j <= BOARD_WIDTH - 1; j++) {
            //kinullázza az egész sort/sorokat fentről lefelé haladva
            BOARDMATRIX[numFullLine][j] = 0;
        }
        //azok a sorok amik az eltávolított sor felett vannak egyel lejebb kerülnek
        copyLinesBelow(numFullLine);
    }

    private void copyLinesBelow(int numFullLine){
        //System.out.println("Debug, Board o, deleteLines fgv");
        //Lejjebb kell másolni a sorokat amik az átadott int felett találhatóak.
        int [][] tempBoard = new int[numFullLine][BOARD_WIDTH];
        for (int i = 0; i < numFullLine; i++){
            for (int j = 0; j < BOARD_WIDTH; j++){
                tempBoard[i][j] = BOARDMATRIX[i][j];
                BOARDMATRIX[i][j] = 0;
            }
        }

        for (int i = 0; i < numFullLine; i++){
            for (int j = 0; j < BOARD_WIDTH; j++){
                BOARDMATRIX[i+1][j] = tempBoard[i][j];
            }
        }
        //újra meghívja a removeFullLines fgv-t hogy a többi sort is törölhesse ha több sort kellene egyszerre törölni
        removeFullLines();
    }

    private class GameCycle implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            doGameCycle();
        }
    }

    public int getNextShape(){
        return nextIndex;
    }

    public int getNextRotationIndex(){
        return nextRotationIndex;
    }
}