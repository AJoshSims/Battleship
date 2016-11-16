package Server;

/**
 * @author Evan Arroyo
 * @author Joshua Sims
 */

public class Game {

    private static int[] shipLengths = {2, 4, 5, 5, 6};

    private static char[][] board;

    public Grid g;

    public Game(int height, int width){

        board = new char[height][width];

    }

    public void attack(int row, int col){

        if(board[row][col] == '|' || board[row][col] == '-'){
            board[row][col] = '@';
            System.out.println("HIT");
        }else{
            board[row][col] = 'X';
            System.out.println("MISS");
        }

    }
/**
    public void createBoard(){

        g.createBoard();

    }
**/


    public void createBoard(){

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {

                board[i][j] = 'O';

            }

        }

    }



    public void addShips() {


        for (int ship : shipLengths) {

            boolean added = false;

            while (!added) {

                int x = (int) (board.length * Math.random());

                int y = (int) (board[0].length * Math.random());

                boolean side = ((int) (10 * Math.random())) % 2 == 0;

                if (side) {

                    boolean hasSpace = true;

                    for (int i = 0; i < ship; i++) {

                        if (y + i >= board[0].length) {

                            hasSpace = false;
                            break;
                        }

                        if (board[x][y + i] != 'O') {
                            hasSpace = false;
                            break;
                        }
                    }

                    if (!hasSpace) {
                        continue;
                    }

                    for (int i = 0; i < ship; i++) {
                        board[x][y + i] = '-';
                    }

                    added = true;

                } else {

                    boolean hasSpace = true;

                    for (int i = 0; i < ship; i++) {

                        if (x + i >= board.length) {

                            hasSpace = false;
                            break;
                        }

                        if (board[x + i][y] != 'O') {

                            hasSpace = false;
                            break;
                        }
                    }
                    if (!hasSpace) {

                        continue;

                    }
                    for (int i = 0; i < ship; i++) {

                        board[x + i][y] = '|';

                    }
                    added = true;
                }
            }
        }
    }

    public void print() {
        System.out.println(" " + "0 1 2 3 4 5 6 7 8 9 ");
        System.out.println(" " + "--------------------");

        for (int i = 0; i < board.length; i++) {

            for (int j = 0; j < board[0].length; j++) {

                System.out.print(" " + board[i][j]);

            }

            System.out.println("");
        }
    }
}


