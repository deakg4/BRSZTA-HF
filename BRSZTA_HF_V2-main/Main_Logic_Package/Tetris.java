package Main_Logic_Package;

public class Tetris extends Board {
    private static Shape shape = new Shape();
    private static Board board = new Board();
    static int boardwidth = board.getBoardWidth();
    static int boardheight = board.getBoardHeight();
    static int [][]testBoard = new int[board.getBoardWidth()][board.getBoardHeight()];
    //Beállítja a Shape alakzatot
    static int [][]curShape = shape.getShapeCoords();


    public static void initTetris(){


        /*
        curShape = rotateRightShape(curShape);
        curShape = rotateRightShape(curShape);
        curShape = rotateRightShape(curShape);
         */

        /*
        shape.setRandomShape();
        board.addShape(shape, board.getBoardWidth()/2, 0);
         */

        //board.start();


        //DEBUG

        //board.addRandomShape();

        //DEBUG




        //DEBUG
        /*curShape = shape.getShapeCoords();
        System.out.println("Debug, Tetris o, initTetris fgv, Shape rotation: "+shape.getShapeRotaion());
        System.out.println("curShape:");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(curShape[i][j]);
            }
            System.out.println();
        }
         */
        //DEBUG


        //shape.startRotateRight(shape);
        //Hozzáad egy alakzatot a 0, 0 koordinátára
        //board.addShape(shape,0,3);

        //Elforgatja az alakzatot jobbra:
        //board.rotateShape(shape,1);
        //Mozgatja az alakzatot 2, 3 (->,v) vektorral


        //board.subtractShape(shape,0,6);

        //DEBUG
        testBoard = board.getBoard();
        System.out.println("Debug, Tetris o, initTetris fgv, Board kiírás");
        System.out.println(testBoard);
        for (int i = 0; i < boardheight; i++) {
            for (int j = 0; j < boardwidth; j++) {
                System.out.print(testBoard[i][j]);
            }
            System.out.println();
        }

        System.out.println("Tetris o, init Tetris fgv, getNumLinesRemoved test:" + board.getNumLinesRemoved());
        //DEBUG

    }
}