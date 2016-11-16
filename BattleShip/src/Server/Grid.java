package Server;

/**
 * @author Evan Arroyo
 * @author Joshua Sims
 */

    public class Grid {

    private char[][] board;



    public Grid(int height, int width){

        board = new char[height][width];

    }

    public void createBoard(){

        for(int i = 0; i < board.length; i++) {

            for(int j = 0; j < board[0].length; j++) {

                board[i][j] = 'O';

            }

        }

    }
}
