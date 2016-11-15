package Server;

/**
 * Created by Evan on 11/6/2016.
 */
public class BattleShipDriver {

    public static void main(String args[]){

        int portNum = 9999;
        int height = 10;
        int width = 10;

        String[][] defBoardSize;

        if(args.length == 0){
            UsageMessage();
        }

        if(args.length == 1){
            portNum = Integer.parseInt(args[0]);
            defBoardSize = new String[height][width];
        }

        if(args.length == 2){
            portNum = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[1]);
            width = height;
        }

        Grid grid = new Grid(height,width);
        grid.createBoard();

    }
    private static void UsageMessage(){
        System.out.println("Usage: <port> <boardSize>");
    }
}
