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
            if(y < 0 || y >= cBoard)
                return true;
            if(x < 0)
                x+=1;
            for (int row = x; row < min(rBoard,x+ship_len); row++){
                if(board[row][y] != '–')
                    return false;
            }
        }
        else {
            if(x < 0 || x >= rBoard)
                return true;
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
    public static boolean is_valid_board_location(char[][] board, int row, int col, int x, int y, int ori, int ship_len
                ,boolean is_com){
        if (!(ori==0||ori==1)){
            if(!is_com)
                System.out.println("Illegal orientation, try again!");
            return false;
        }
        if(x >= row || x < 0 || y >= col || y < 0 ){
            if(!is_com)
                System.out.println("Illegal tile, try again!");
            return false;
        }
        if((ori == 0 && y+ship_len-1 >= col) || (ori==1 && x+ship_len-1 >= row)){
            if(!is_com)
                System.out.println("Battleship exceeds the boundaries of the board, try again!");
            return false;
        }
        if(!is_ships_crashing(board,x,y,ori, ship_len, row, col)){
            if(!is_com)
                System.out.println("Battleship overlaps another battleship, try again!");
            return false;
        }
        if(ori==0 && !is_ships_crashing_ghost_hor(board,x,y,ship_len,row,col)){//checks is the ship has any ships near
            if(!is_com)
                System.out.println("Adjacent battleship detected, try again!");
            return false;
        }
        if(ori==1 && !is_ships_crashing_ghost_ver(board,x,y,ship_len,row,col)){//checks is the ship has any ships near
            if(!is_com)
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
        for (int i=1 ; i< ship_array.length ; i++){//i == length of the ship
            //System.out.println("^"+ship_array[i]+"\n");
            if (!(ship_array[i] == 0)){
                for(int j=0; j < ship_array[i]; j++) {//insert the location of the ships in the same sizes
                    System.out.println("Enter location and orientation for battleship of size " + i);
                    String s= scanner.nextLine();
                    s = s.replaceAll(",", ""); // remove parentheses and comma
                    String[] parts = s.split("\\s+"); // split into three parts
                    int x= Integer.parseInt(parts[0]);
                    int y= Integer.parseInt(parts[1]);
                    int ori= Integer.parseInt(parts[2]);
                    //check if possible and puts the sips on the board if so
                    //System.out.println("\n^^^\n");
                    while(!is_valid_board_location(boardgame, n, m, x, y, ori, i, false)){
                        s= scanner.nextLine();
                        s = s.replaceAll(",", ""); // remove parentheses and comma
                        parts = s.split("\\s+"); // split into three parts
                        x= Integer.parseInt(parts[0]);
                        y= Integer.parseInt(parts[1]);
                        ori= Integer.parseInt(parts[2]);
                    }
                    locate_battleship(boardgame,x,y,ori, i); //put the ships in the right place
                    System.out.println("Your current game board:");
                    print_Board(boardgame,n,m);
                }//if of ships in the same sizes
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
        create_board(boardgame,n,m);
        for (int i=1 ; i< ship_array.length ; i++){
            if (!(ship_array[i] == 0)){
                for(int j=0; j < ship_array[i]; j++) {//insert the location of the ships in the same sizes
                    int x= rnd.nextInt(n);
                    int y= rnd.nextInt(m);
                    int ori= rnd.nextInt(2);
                    //check if possible and puts the sips on the board if so
                    while(!is_valid_board_location(boardgame,n,m,x,y,ori,i,true) ){
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
        for(int p=0; p< col;p++) {
            if (row >= 10 && p==0)
                System.out.print(" ");
            System.out.print(p + " ");//print first row of 0 1 2...
        }
        System.out.println();

        for(int i=0; i < row; i++){
            if(row >=10 && i<10){
                System.out.print(" ");
            }
            System.out.print(i+ " ");//print first col 0 1 2...
            for(int j=0; j < col; j++){
                System.out.print(board[i][j]+" "); // print internal board -,X...
            }
            System.out.println();
        }
        System.out.println();
    }//end

    /**
     *#input: string of sizes and amount of ships
     * pulls the amount of ships out of the string and their sizes
     * #output: return an array of ships ordered by sizes.
     * */
    public static int input_ships(char[][] player_board, char [][] computer_board, int n, int m){
        System.out.println("Enter the battleships sizes");
        String ships = scanner.nextLine();
        System.out.println("Your current game board:");
        print_Board(player_board,n,m);
        String[] ship_pairs = ships.split(" "); //split the string to pairs
        int maxIndex = Integer.parseInt(ship_pairs[ship_pairs.length - 1].split("X")[1]);
        int number_of_ships=0;
        int[] ship_array= new int[maxIndex + 1];//the ship array
        for(int i=0; i < ship_pairs.length; i++){
            String[] values = ship_pairs[i].split("X");
            int num_of_ship = Integer.parseInt(values[0]);
            int size_ship = Integer.parseInt(values[1]);
            ship_array[size_ship] = num_of_ship;
            number_of_ships+=num_of_ship;
        }
        add_ships_to_board(player_board,ship_array,n,m);// player locates his ships
        add_ships_to_board_computer(computer_board,ship_array,n,m);//random location and input to computer boardgame
        return number_of_ships;
    }

    /**#input: the current playing board and the guessing board, the current tile to attack
     * checks if the tile out bound the board, check if the tile already attacked
     * check if invalid input
     */
    public static boolean check_tile_to_attack(char[][] player_board, char[][] guessing_board, int x,
                                               int y, int n, int m){
        if(x < 0 || x >= n || y < 0 || y >= m) {
            System.out.println("Illegal tile, try again!");
            return false;
        }
        if(!(guessing_board[x][y] == '–')){
            System.out.println("Tile already attacked, try again!");
            return false;
        }
        return true;
    }

    public static boolean valid_com_guess(char[][] player_board, char[][] guessing_board, int x,
                                          int y, int n, int m){
        if(x < 0 || x > n || y < 0 || y > m) {
            return false;
        }
        if(!(guessing_board[x][y] == '–')){
            return false;
        }
        return true;
    }
    /**#check if the ship drown
     * #input: player board, (x,y)
     * return if the whole ship is XXX true ele false
     */
    public static boolean is_ship_drown(char[][] player_board, int x, int y, int n, int m){
        int i=0, j=0;
        while(i+x < n && player_board[x+i][y] != '–'){ //right
            if(player_board[x+i][y] == '#'){
                return false;
            }
            i++;
        }
        i=0;
        while(x-i >= 0 && player_board[x-i][y] != '–'){//left
            if(player_board[x-i][y] == '#'){
                return false;
            }
            i++;
        }
        //up and down
        while (y-j >=0 && player_board[x][y-j]!='–'){
            if(player_board[x][y-j] =='#')
                return false;
            j++;
        }
        j=0;
        while (y+j < m && player_board[x][y+j]!='–'){
            if(player_board[x][y+j]=='#')
                return false;
            j++;
        }
        return true;
    }

    public static int attack_ship_by_computer(char[][] com_guessing_board, char [][] player_board, int n,
                                          int m, int r) {
        int x= rnd.nextInt(n);
        int y= rnd.nextInt(m);;
        while(!valid_com_guess(player_board, com_guessing_board,x,y,n,m)) {
            x= rnd.nextInt(n);
            y= rnd.nextInt(m);;
        }
        System.out.println("The computer attacked ("+x+", "+y +")");
        if(player_board[x][y] =='#'){
            com_guessing_board[x][y]='V';
            player_board[x][y]='X';
            System.out.println("That is a hit!");
            if(is_ship_drown(player_board,x,y,n,m)){
                r-=1;
                System.out.println("Your battleship has been drowned, you have left "+r+" more battleships!");
            }
        }
        else if(player_board[x][y]=='–'){
            com_guessing_board[x][y]='X';
            System.out.println("That is a miss!");
        }

        return r;
    }

    /** #input: the current gues, board to attck and the guessing board
     *  do the attack and update boards
     */
    public static int attack_f_ship(char[][] player_board,char[][] guessing, int x, int y, int n, int m, int r){
        if(player_board[x][y]=='#'){
            System.out.println("That is a hit!");
            guessing[x][y]='V';
            player_board[x][y]='X';
            if(is_ship_drown(player_board,x,y,n,m)){
                r-=1;
                System.out.println("The computer's battleship has been drowned, "+ r + " more battleships to go!");
                System.out.println("For making sure, computer game board");
                print_Board(player_board,n,m);
            }
            else {
                System.out.println("For making sure, computer game board");
                print_Board(player_board, n, m);
            }
        }//big if
        if(player_board[x][y]=='–'){
            System.out.println("That is a miss!");
            guessing[x][y]='X';
        }
        return r;

    }
    /**#input: guessing board, the computer board, size of the board, r is number of ships of the user
     * the func asks the user for guesses and checks the validation of the guess
     * if so then does the attack on the computer ship
     * update the boards as well */
    public static int attack_ship_by_user(char[][] player_guessing_board, char [][] computer_board, int n,
                                           int m, int r){
        System.out.println("Your current guessing board:");
        print_Board(player_guessing_board, n,m);//print the guess board
        System.out.println("Enter a tile to attack");
        String guess= scanner.nextLine();
        String[] pair= guess.split(", ");
        int x= Integer.parseInt(pair[0]);
        int y= Integer.parseInt(pair[1]);
        while(!check_tile_to_attack(computer_board, player_guessing_board,x,y,n,m)) {
            guess= scanner.nextLine();
            pair= guess.split(", ");
            x= Integer.parseInt(pair[0]);
            y= Integer.parseInt(pair[1]);
        }
        return attack_f_ship(computer_board,player_guessing_board,x,y,n,m,r);
    }

    /** start of the game
     * enter the board, the MXN of the board, ships etc..
     */
    public static void battleshipGame() {
        System.out.println("Enter the board size");
        String MXN_board= scanner.nextLine();//get number of rows from user
        String[] pair= MXN_board.split("X");
        int n= Integer.parseInt(pair[0]);
        int m= Integer.parseInt(pair[1]);
        char[][] player_board= new char[n][m]; //declare the board game of player
        char[][] computer_board= new char[n][m]; //declare the board games of com
        create_board(player_board,n,m);
        int num_ships = input_ships(player_board, computer_board,n,m); //enter ships and locate on both boards
        char[][] player_guess= new char[n][m]; //declare the guessing boards of both players
        char[][] computer_guess= new char[n][m]; //declare the guessing boards of both players
        create_board(player_guess,n,m);//create the guessing board
        create_board(computer_guess,n,m); //create the guessing board
        int num_player=num_ships;//how much left for player
        int num_cop= num_ships;//how much left for computer

        while(!(num_cop == 0) && !(num_player ==0)) {
            num_cop = attack_ship_by_user(player_guess, computer_board, n, m, num_cop);
            if(num_cop == 0){
                System.out.println("You won the game!");
                return;
            }
            num_player = attack_ship_by_computer(computer_guess, player_board, n, m, num_player);
            if(num_player==0){
                System.out.println("Your current game board:");
                print_Board(player_board, n, m);
                System.out.println("You lost ):");
                return;
            }
            System.out.println("Your current game board:");
            print_Board(player_board, n, m);
        }
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




