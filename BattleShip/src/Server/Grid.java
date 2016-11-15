package Server;

/**
 * Created by Evan on 11/6/2016.
 */
    public class Grid {

    String[][] board;

    public Grid(int height, int width){

        board = new String[height][width];

    }

    public void createBoard(){

        for (int i = 0; i < board.length;i++){

            for (int j = 0; j < board.length;j++){

                board[i][j] = "|__";

                System.out.print(board[i][j] + "|");
            }

            System.out.println();
        }
    }
}
