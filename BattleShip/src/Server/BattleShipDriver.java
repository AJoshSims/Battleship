package Server;

/**
 * @author Evan Arroyo
 * @author Joshua Sims
 */
public class BattleShipDriver {

    public static void main(String args[]){

        int portNum = 9999;

        int height = 10;
        int width = 10;

        int row = 0;
        int col = 0;

        char[][] defBoardSize;

        if(args.length == 0){
            UsageMessage();
        }

        if(args.length == 1){
            portNum = Integer.parseInt(args[0]);
            defBoardSize = new char[height][width];
        }

        if(args.length == 2){
            portNum = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[1]);
            width = height;
        }

        if(args.length == 4){
            portNum = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[1]);
            width = height;
            row = Integer.parseInt(args[2]);
            col = Integer.parseInt(args[3]);

        }


        Game g = new Game(height, width);

        //PLAYER 1 Random Board
        g.createBoard();
        g.addShips();
        g.attack(row, col);
        g.print();

        //PLAYER 2 Random Board
        g.createBoard();
        g.addShips();
        g.attack(row, col);
        g.print();

        //PLAYER 3 Random Board
        g.createBoard();
        g.addShips();
        g.attack(row, col);
        g.print();

        //PLAYER 4 Random Board
        g.createBoard();
        g.addShips();
        g.attack(row, col);
        g.print();

    }
    private static void UsageMessage(){
        System.out.println("Usage: <port> <boardSize>");
    }
}
