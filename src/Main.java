import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;
    public static Random rnd;


    /** check between two number and returns the smaller
     */
    public static int min(int rBoard, int i) {
        if(rBoard > i)
            return i;
        return rBoard;
    }


    /** #input: board, coordinates (x,y), ship length, row, col of board
     * checks if the ship doesn't crash other ship
     * output: T/F
     */
    public static boolean is_ships_crashing(char[][] board,int x, int y, int ori, int ship_len, int rBoard, int cBoard){
        if(ori == 1){
            if(x < 0)
                x+=1;
            for (int row = x; row < min(rBoard,x+ship_len); row++){
                if(board[row][y] != '–')
                    return false;
            }
        }
        else {
            if(y< 0)
                y+=1;
            for(int col=y ; col < min(y+ship_len, cBoard); col++){
                if(board[x][col] != '–')
                    return false;
            }
        }
        return true;
    }

    /** create a starter board
     */
    public static void create_board(char[][] board, int n, int m){
        for (int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                board[i][j]='–';
            }
        }
    }

    /** #input: board, coordinates (x,y) , ship length
     *  check around the ship, if there's any ships there, for horizontal ships
     *  output: T/F
     */
    public static boolean is_ships_crashing_ghost_hor(char[][] board,int x, int y, int ship_len, int row, int col) {
        int ori=0;
        return is_ships_crashing(board, x - 1, y - 1, ori, ship_len + 2, row, col) &&
                is_ships_crashing(board, x + 1, y - 1, ori, ship_len + 2, row, col) &&
                is_ships_crashing(board, x, y - 1, 1, 1, row, col) &&
                is_ships_crashing(board, x, y + ship_len, 1, 1, row, col);
    }

    /** #input: board, coordinates (x,y) , ship length
     *  check around the ship, if there's any ships there, for vertical ships
     *  output: T/F
     */
    public static boolean is_ships_crashing_ghost_ver(char[][] board,int x, int y, int ship_len, int row, int col){
        int ori=1;
        return is_ships_crashing(board, x - 1, y - 1, ori, ship_len + 2, row, col) &&
                is_ships_crashing(board, x - 1, y + 1, ori, ship_len + 2, row, col) &&
                is_ships_crashing(board, x - 1, y, 0, 1, row, col) &&
                is_ships_crashing(board, x + ship_len, y, 0, 1, row, col);
    }

    /**
     * #input: board, num_rows, num_col, coordinates (x,y), oriantation, ship_length
     * checks if the location of the ship is avaliable on the board
     * checks if the coordination isn't out bound the board
     * checks if the ship doesn't crash other ship
     * #output: true if valid location else false
     */
    public static boolean is_valid_board_location(char[][] board, int row, int col, int x, int y, int ori, int ship_len){
        if (!(ori==0||ori==1)){
            System.out.println("Illegal orientation, try again!");
            return false;
        }
        if(x > row || x < 0 || y > col || y<0 ){
            System.out.println("Illegal tile, try again!");
            return false;
        }
        if((ori == 0 && y+ship_len-1 > col) || (ori==1 && x+ship_len-1 > row)){
            System.out.println("Battleship exceeds the boundaries of the board, try again!");
            return false;
        }
        if(!is_ships_crashing(board,x,y,ori, ship_len, row, col)){
            System.out.println("Battleship overlaps another battleship, try again!");
            return false;
        }
        //checks is the ship has any ships near it
        if(ori==0 && !is_ships_crashing_ghost_hor(board,x,y,ship_len,row,col)){
            System.out.println("Adjacent battleship detected, try again!");
            return false;
        }
        //checks is the ship has any ships near it
        if(ori==1 && !is_ships_crashing_ghost_ver(board,x,y,ship_len,row,col)){
            System.out.println("Adjacent battleship detected, try again!");
            return false;
        }
        return true;
    }

    /** #input: board, coordinates (x,y), ship length
     *  locate the battleship on the board
     */
    public static void locate_battleship(char[][] board,int x, int y, int ori, int ship_len){
        if(ori == 0){
            for(int col=y; col < ship_len + y ; col++){
                board[x][col]= '#';

            }
        }
        if(ori == 1){
            for (int row=x; row < ship_len+x; row++){
                board[row][y]='#';
            }
        }
    }

    /**
     *#input: board, ship_array, length of board game
     * the func gets from user the (x,y) coordination and orientation, check if the coordination is valid
     * and if so addes the ship to the board accordenly
     * #output: void
     */
    public static void add_ships_to_board(char[][] boardgame, int[] ship_array, int n, int m){
        create_board(boardgame,n,m);
        for (int i=0 ; i< ship_array.length ; i++){
            if (!(ship_array[i] == 0)){
                for(int j=0; j < ship_array[i]; j++) {//insert the location of the ships in the same sizes
                    System.out.println("Enter location and orientation for battleship of size s");
                    int x= scanner.nextInt();
                    int y= scanner.nextInt();
                    int ori= scanner.nextInt();
                    //check if possible and puts the sips on the board if so
                    if(is_valid_board_location(boardgame, n, m, x, y, ori, i))
                        locate_battleship(boardgame,x,y,ori, i); //put the ships in the right place
                }
            }
        }
    }

    /** #input: board, ship_array, length of board game
     * the func random the (x,y) coordination and orientation, check if the coordination is valid
     * and if so addes the ship to the board accordenly
     * #output: void
     *
     */
    public static void add_ships_to_board_computer(char[][] boardgame, int[] ship_array, int n, int m){
        for (int i=0 ; i< ship_array.length ; i++){
            if (!(ship_array[i] == 0)){
                for(int j=0; j < ship_array[i]; j++) {//insert the location of the ships in the same sizes
                    System.out.println("Enter location and orientation for battleship of size s");
                    int x= rnd.nextInt(n);
                    int y= rnd.nextInt(m);
                    int ori= rnd.nextInt(2);
                    //check if possible and puts the sips on the board if so
                    while(!is_valid_board_location(boardgame,n,m,x,y,ori,i)){
                        x= rnd.nextInt(n);
                        y= rnd.nextInt(m);
                        ori= rnd.nextInt(2);
                    }
                    locate_battleship(boardgame,x,y,ori, i); //put the ships in the right place
                }
            }
        }
    }


    /**
     * #input: board, row, col
     * #output: void
     * the funtion prints the diff boards */
    public static void print_Board(char[][] board, int row, int col){
        System.out.print("  ");
        for(int p=0; p< col;p++)
            System.out.print(p+" ");//print first row of 0 1 2...
        System.out.println();

        for(int i=0; i < row; i++){
            System.out.print(i+ " ");//print first col 0 1 2...
            for(int j=0; j < col; j++){
                System.out.print(board[i][j]+" "); // print internal board -,X...
            }
            System.out.println();
        }
    }//end


    /**
     *#input: string of sizes and amount of ships
     * pulls the amount of ships out of the string and their sizes
     * #output: return an array of ships ordered by sizes.
     * */
    public static void input_ships(char[][] player_board, char [][] computer_board, int n, int m){
        System.out.println("Enter the battleships sizes");
        String ships = scanner.nextLine();
        int len = Character.getNumericValue(ships.charAt(ships.length()-1));
        int[] ship_array= new int[len+1];
        for(int i=0; i<ships.length() ; i++){
            if(ships.charAt(i) ==('X')){
                //histogram of ships ordered by shorter to longer ships
                ship_array[Character.getNumericValue(ships.charAt(i+1))] = Character.getNumericValue(ships.charAt(i-1));
            }
        }//for
        add_ships_to_board(player_board,ship_array,n,m);// player locates his ships
        add_ships_to_board_computer(computer_board,ship_array,n,m);//random location and input to computer boardgame

    }



    /** start of the game
     * enter the board, the MXN of the board, ships etc..
     */
    public static void battleshipGame() {
        System.out.println("Enter the board size");
        String MXN_board= scanner.nextLine();//get number of rows from user
        int n= Integer.getInteger(MXN_board.substring(0,0));
        int m= Integer.getInteger(MXN_board.substring(2,2));
        //declare the board games of both players
        char[][] player_board= new char[n][m];
        char[][] computer_board= new char[n][m];

        input_ships(player_board, computer_board,n,m);//entering ships by player and from there locate on player and computer board
        print_Board(player_board,n,m);//print his board
        print_Board(computer_board,n,m);

        //declare the guessing boards of both players
        char[][] player_guess= new char[n][m];
        char[][] computer_guess= new char[n][m];
        create_board(player_guess,n,m);
        create_board(computer_guess,n,m);


    }
    public static void main(String[] args) throws IOException {
        String path = args[0];
        scanner = new Scanner(new File(path));
        int numberOfGames = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Total of " + numberOfGames + " games.");

        for (int i = 1; i <= numberOfGames; i++) {
            scanner.nextLine();
            int seed = scanner.nextInt();
            rnd = new Random(seed);
            scanner.nextLine();
            System.out.println("Game number " + i + " starts.");
            battleshipGame();
            System.out.println("Game number " + i + " is over.");
            System.out.println("------------------------------------------------------------");
        }
        System.out.println("All games are over.");
    }


}




