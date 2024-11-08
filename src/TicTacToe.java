import java.util.Scanner;

public class TicTacToe
{
    public static boolean done = false;
    final static int ROWS = 3;
    final static int COLS = 3;
    static String board[][];

    public static void main(String[] args)
    {
        //1. Clear the board, move count to 0 and set the player to X (since X always moves first)
        //2. Show the board, get the coordinates for the move which should be 1 – 3 for the row and col
        //3. convert the player move to the array indices (which are 0 – 2) by subtracting 1
        //4. loop (through #2 and #3) until the converted player coordinates are a valid move
        //5. Record the move on the board and increment the move counter
        //6. if appropriate check for a win or a tie (i.e. if it is possible for a win or a tie at this point in the
        //game, check for it.)
        //7. If there is a win or tie announce it and then prompt the players to play again or exit.
        //8. Toggle the player for the next move (i.e. X becomes O, O becomes X)




        Scanner in = new Scanner(System.in);


        String player;
        int moveCount;
        board = new String[ROWS][COLS];

        do {
            moveCount = 0;
            player = "X";
            clearBoard();
            showBoard();
            int row, col;

            do {
                // Get row and column inputs, ensuring they are within valid range
                row = SafeInput.getRangedInt(in, "Player " + player + " Enter your row", 1, 3);
                col = SafeInput.getRangedInt(in, "Player " + player + " Enter your col", 1, 3);

                row--; // Adjust for 0-based index
                col--;
                if(!isValidMove(row, col))
                    showBoard(); // shows board after a non-valid
                if (isValidMove(row, col))
                { // check for valid move
                    recordMove(player, row, col); // if the move is valid record it
                    showBoard(); // shows board after a move was made
                    moveCount++; // update move count after recorded move
                    if(moveCount >= 5)
                    { // check game state once enough moves have been made for someone to win
                        if (isWin(player)) {
                            System.out.println("Player " + player + " wins!");
                            done = SafeInput.getYNConfirm(in,"Are you done playing? ");
                            break;
                        }
                    }
                    if(moveCount > 7) // checks for ties once possible to tie
                    {
                        if (isTie(board))
                        {
                            System.out.println("It's a draw");
                            done = SafeInput.getYNConfirm(in, "Are you done playing? "); // prompt to play another game
                            break;
                        }
                    }

                    if (player.equalsIgnoreCase("X")) { // switch player after seeing if a winning move was made
                        player = "O";
                    } else
                        player = "X";
                }

            } while (!isValidMove(row, col));

        }while(!done);




    }
    private static void clearBoard()
    {
        for(int r = 0; r < ROWS; r++)
        {
            for(int c = 0; c < COLS; c++)
                board[r][c] = " ";
        }

    }
    private static void showBoard()
    {
        {
            for(int r = 0; r < ROWS; r++)
            {
                System.out.print("|");
                for(int c = 0; c < COLS; c++){
                    System.out.print(board[r][c]+ "|");
                }
                System.out.println();
            }

        }
    }
    private static boolean isValidMove(int r, int c)
    {
        boolean retVal = false;

        if(board[r][c].equals(" "))
            retVal = true;

        return retVal;
    }
    private static void recordMove(String player, int r, int c)
    {
        board[r][c] = player;
    }
    private static boolean isWin(String player)
    {
        if(isColWin(player) || isRowWin(player) || isDiagonalWin(player))
        {
            return true;
        }
        return false;
    }
    private static boolean isRowWin(String player)
    {
        for(int row=0; row < ROWS; row++)
        {
            if(board[row][0].equals(player) && board[row][1].equals(player) &&
                    board[row][2].equals(player))
            {
                return true;
            }
        }
        return false; // no row win
    }
    private static boolean isColWin(String player)
    {
        for(int col=0; col < COLS; col++)
        {
            if(board[0][col].equals(player) && board[1][col].equals(player) &&
                    board[2][col].equals(player))
            {
                return true;
            }
        }
        return false; // no cow win
    }
    private static boolean isDiagonalWin(String player)
    {
        if(board[0][0].equals(player) && board[1][1].equals(player) &&
                board[2][2].equals(player))
        {
            return true;
        }
        if(board[0][2].equals(player) && board[1][1].equals(player) &&
                board[2][0].equals(player))
        {
            return true;
        }

        return false; // no row win
    }
    private static boolean vectorHasBoth(String a, String b, String c)  //checks to see if win vectors contain x and o
    {
        boolean hasX = false;
        boolean hasO = false;

        if (a.equals("X") || b.equals("X") || c.equals("X")) {
            hasX = true;
        }
        if (a.equals("O") || b.equals("O") || c.equals("O")) {
            hasO = true;
        }

        // Return true if both X and O are present
        return hasX && hasO;
    }
    private static boolean isTie(String[][] board)  // takes string values from all win vectors and puts them in previous method
    {
        for(int c=0; c < 3; c++)
        {
            if(!vectorHasBoth(board[c][0], board[c][1],board[c][2]))
            {
                return false;
            }
            if(!vectorHasBoth(board[0][c], board[1][c],board[2][c])){
                return false;
            }

        }
        if(!vectorHasBoth(board[0][0],board[1][1],board[2][2]))
        {
            return false;
        }
        if(!vectorHasBoth(board[0][2],board[1][1],board[2][0]))
        {
            return false;
        }
        return true; // if is vectorHasBoth is false that means a player can still win on that win vector so isTie should return false
                     // isTie returns true only if all win vectors go through vectorHasBoth and return false
    }
}
