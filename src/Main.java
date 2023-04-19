import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;
    public static Random rnd;

    public static void battleshipGame() {
        // TODO: Add your code here (and add more methods).
    }

    /**
     * #input: board, row, col
     * #output: void
     * the funtion prints the diff boards */
    public static void print_Board(char[][] board, int row, int col){
        System.out.print("  ");
        for(int p=0; p< col;p++)
            System.out.print(p+" ");
        System.out.println();

        for(int i=0; i < row; i++){
            System.out.print(i+ " ");
            for(int j=0; j < col; j++){
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
    }//end
    public static void main(String[] args) throws IOException {
        /**String path = "C:\\Users\\hilal\\IdeaProjects\\HW0_324075548_\\src\\HW0_input.txt";
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
**/
        char[][] board= new char[][]{{'-', '-', '-','-'}, {'-', 'X', '-','X'}, {'X', '-', '-','-'}};
        print_Board(board,3,4);
    }
}



