package hu.bme.mit.brszta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shape {

    protected enum Tetrominoe {
        NoShape, TShape, LShape, MirroredLShape, LineShape,
        SquareShape, ZShape, SShape
    }

    private List<Integer> shapeRotationBuffer = new ArrayList<>();
    //Az alakzat koordinátái a mátrixban
    private int[][] coords;
    private int[][] tempCoords;
    //Az alakzat indexe - hogy melyik alakzat van épp játékban
    private int index;
    //Az alakzat neve:
    private String strName;
    //Az alakzat orientációja 1-től 4-ig mennyit forgattuk jobbra
    private int shapeRotaion;

    //A játékban lévő alakzat X és Y koordinátái a pályán
    private int positionX;
    private int positionY;

    public Shape() {
        coords = new int[4][4];
        tempCoords = new int[4][4];
    }

    public void setRandomShape(){
        //Beállít egy random alakzatot
        var r = new Random();
        int x = Math.abs(r.nextInt()) % 7 + 1;
        index = x;
        setShape(x);
    }

    public void setShapeRotationBuffer(int shapeRotationBuffer) {
        this.shapeRotationBuffer.add(shapeRotationBuffer);
    }

    //Az alakzat x kiterjedése a 4x4-es mátrixon belül:
    public int getMaxX(){
        return maxX();
    }
    //Az alakzat y kiterjedése a 4x4-es mátrixon belül:
    public int getMaxY(){
        return maxY();
    }

    public int getNextShapeIndex(){
        //Visszaadja a Shape indexét 1-től 7-ig vannak indexelve
        return index;
    }
    public int getShapePositionX(){
        //Visszaadja a Shape jelenlegi helyzetét a Boardon
        return positionX;
    }

    public int getShapePositionY(){
        //Visszaadja a Shape jelenlegi helyzetét a Boardon
        return positionY;
    }

    public int[][] getShapeCoords(){
        return coords;
    }

    int maxX(){
        int maxX = 0;
        //coords = shape.getShapeCoords();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (coords[i][j]==1 && maxX < j){
                    maxX = j;
                }
            }
        }
        return maxX;
    }
    int maxY(){
        int maxY = 0;
        //coords = shape.getShapeCoords();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (coords[i][j]==1 && maxY < i){
                    maxY = i;
                }
            }
        }
        return maxY;
    }
    int minY(){
        int minY = 4;
        //coords = shape.getShapeCoords();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (coords[i][j]==1 && i < minY){
                    minY = i;
                }
            }
        }
        return minY;
    }
    int minX(){
        int minX = 4;
        //coords = shape.getShapeCoords();
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (coords[i][j]==1 && j < minX){
                    minX = j;
                }
            }
        }
        return minX;
    }

    private void setShapeInTopLeftSide(){
        int maxX = maxX();
        int maxY = maxY();
        int minX = minX();
        int minY = minY();
        int [][] newCoords = new int[4][4];

        for (int i = 0; i < maxY-minY+1; i++){
            for (int j = 0; j < maxX-minX+1; j++){
                newCoords[i][j] = coords[i+minY][j+minX];
                //System.out.println(coords[i][j]);
            }
        }
        coords = newCoords;
        //DEBUG
        /*
        System.out.println("Debug, Shape o, setShapeInTopLeftSide : Set TopLeft:");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(coords[i][j]);
            }
            System.out.println();
        }
         */
        //DEBUG
    }

    public void startRotateRight(){

        rotateRightShape();
    }

    public void saveShapeCoords(){
        tempCoords = coords;
    }

    public void resetShapeCoords(){
        coords = tempCoords;
    }
    private void rotateRightShape() {
        //Elforgatja jobbra a Shape-t
        //Elkéri a jelenlegi Shape koordinátáit
        int[][] curShape = getShapeCoords();
        //Létrehoz egy új Shape matrixot
        int[][] newShape = new int[4][4];
        //Egyel megnöveli a Shape roration intet
        shapeRotaion=+1;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //Beírja az új elforgatott eredményt a változóba
                newShape[i][j] = curShape[4-j-1][i];
            }
        }

        /*
        System.out.println("Debug, Shape o, rotaterightShape fgv, Shape index "+ index);
        System.out.println("Debug, Shape o, rotaterightShape fgv, Shape name "+ Shape.Tetrominoe.values()[index]);
        System.out.println("Debug, Shape o, rotaterightShape fgv, Rotated Shape:");

        //DEBUG
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(newShape[i][j]);
            }
            System.out.println();
        }
         */
        //DEBUG
        //felülírja a Shape mátrixát
        coords = newShape;
        setShapeInTopLeftSide();
    }

    void setShape(int index){
        //index = shape.ordinal();
        strName = Tetrominoe.values()[index].toString();
        final int[][][] shapes = new int[][][]{
                {
                    {0, 0, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}},
                {
                    {1, 0, 0, 0},
                    {1, 1, 0, 0},
                    {1, 0, 0, 0},
                    {0, 0, 0, 0}}, //TShape
            {
                    {1, 0, 0, 0},
                    {1, 0, 0, 0},
                    {1, 1, 0, 0},
                    {0, 0, 0, 0}}, //LShape
            {
                    {1, 1, 0, 0},
                    {1, 0, 0, 0},
                    {1, 0, 0, 0},
                    {0, 0, 0, 0}}, //MirroredLShape
            {
                    {1, 0, 0, 0},
                    {1, 0, 0, 0},
                    {1, 0, 0, 0},
                    {1, 0, 0, 0}}, //LineShape
            {
                    {1, 1, 0, 0},
                    {1, 1, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}}, //SquareShape
            {
                    {1, 1, 0, 0},
                    {0, 1, 1, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}}, //ZShape
            {
                    {0, 1, 1, 0},
                    {1, 1, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}}, //SShape
        };
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                coords[i][j] = shapes[index][i][j];
            }
        }
    }
}
