import java.util.Scanner;

public class ConnectFour 
{
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m"; 

    private final String[][] board;
    private int turn;
    private final String red = ANSI_RED + "O" + ANSI_RESET;
    private final String yellow = ANSI_YELLOW + "O" + ANSI_RESET;
    private final String redWin = ANSI_RED + "The Soviets won yet again!" + ANSI_RESET;
    private final String yellowWin = ANSI_YELLOW + "Oh Jimothy Crickety, the farmers won!" + ANSI_RESET;

    public ConnectFour() {
        board = new String[6][7];
        for (String[] board1 : board) 
        {
            for (int col = 0; col < board[0].length; col++) 
            {
                board1[col] = "-";
            }
        }
    }

    public void clearBoard()
    {
        for(String[] board1 : board)
        {
            for(int col = 0; col < board[0].length; col++)
            {
                board1[col] = "-";
            }
        }
    }

    public void clearTurn()
    {
        turn *= 0;
    }

    public int getTurn()
    {
        return turn;
    }

    public String getRedWin()
    {
        return redWin;
    }

    public String getYellowWin()
    {
        return yellowWin;
    }

    public void printBoard() {
        System.out.print(ANSI_WHITE + "  ");
        for (int col = 0; col < board[0].length; col++) 
        {
            System.out.print(col + " ");
        }
        System.out.println();
    
        for(int row = 0; row < board.length; row++) 
        {
            System.out.print(ANSI_WHITE + row + " ");
            for(String slot : board[row]) 
            {
                switch (slot) {
                    case red -> System.out.print(red + " ");
                    case yellow -> System.out.print(yellow + " ");
                    default -> System.out.print(ANSI_WHITE + slot + " ");
                }
            }
            System.out.println();
        }
        System.out.print(ANSI_RESET);
    }

    public boolean pickLocation(int col)
    {
      return board[0][col].equals("-");
    }

    public void animateDrop(int col, String piece) 
    {
        for (int row = 0; row < board.length; row++) 
        {
            // Find the lowest empty row in the column
            if (row == board.length - 1 || !board[row + 1][col].equals("-")) 
            {
                // Animation: drop piece from top to this row
                for (int animRow = 0; animRow <= row; animRow++) 
                {
                    board[animRow][col] = piece;
                    if (animRow > 0) board[animRow - 1][col] = "-";
                    printBoard();
                    try 
                    {
                        Thread.sleep(100); // 100 ms delay for animation
                    } 
                    catch (InterruptedException e) 
                    {
                        Thread.currentThread().interrupt();
                    }
                    // Clear console (works in many terminals)
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                }
                break;
            }
        }
    }

    // Use this in takeTurn instead of direct placement:
    public void takeTurn(int col) {
        String piece = (turn % 2 == 0) ? red : yellow;
        animateDrop(col, piece);
        turn++;
    }

    public boolean checkRow() 
    {
        for (String[] board1 : board) {
            boolean redWin = true;
            boolean yellowWin = true;
            for (int col = 0; col < board[0].length; col++) {
                if (!board1[col].equals(red)) {
                    redWin = false;
                }
                if (!board1[col].equals(yellow)) {
                    yellowWin = false;
                }
            }
            if (redWin || yellowWin) 
            {
                return true;
            }
        }
        return false;
    }

    public boolean checkCol()
    {
        for (int col = 0; col < board[0].length; col++) 
        {
            boolean redWin = true;
            boolean yellowWin = true;
            for (String[] board1 : board) {
                if (!board1[col].equals(red)) {
                    redWin = false;
                }
                if (!board1[col].equals(yellow)) {
                    yellowWin = false;
                }
            }
            if (redWin || yellowWin) 
            {
                return true;
            }
        }
        return false;
    }

    public boolean checkDiag()
    {
        // Check main diagonal
        boolean redWin = true;
        boolean yellowWin = true;
        for (int i = 0; i < board.length; i++) {
            if (!board[i][i].equals(red)) {
                redWin = false;
            }
            if (!board[i][i].equals(yellow)) {
                yellowWin = false;
            }
        }
        if (redWin || yellowWin) {
            return true;
        }

        // Check anti-diagonal
        redWin = true;
        yellowWin = true;
        for (int i = 0; i < board.length; i++) {
            if (!board[i][board.length - 1 - i].equals(red)) {
                redWin = false;
            }
            if (!board[i][board.length - 1 - i].equals(yellow)) {
                yellowWin = false;
            }
        }
        return redWin || yellowWin;
    }

    public boolean checkDraw() 
    {
        for (int row = 0; row < board.length; row++) 
        {
            for (int col = 0; col < board[0].length; col++) 
            {
                if (board[row][col].equals("-")) 
                {
                    return false; // Found an empty slot, not a draw
                }
            }
        }
        // Board is full, check if there is no winner
        return !checkWin();
    }

    public boolean checkWin() {
        // Check for 4 in a row horizontally, vertically, and diagonally
        for (int row = 0; row < board.length; row++) 
        {
            for (int col = 0; col < board[0].length; col++) 
            {
                String cell = board[row][col];
                if (cell.equals("-")) continue;
                // Horizontal
                if (col <= board[0].length - 4) 
                {
                    if (cell.equals(board[row][col+1]) && cell.equals(board[row][col+2]) && cell.equals(board[row][col+3])) 
                    {
                        return true;
                    }
                }
                // Vertical
                if (row <= board.length - 4) 
                {
                    if (cell.equals(board[row+1][col]) && cell.equals(board[row+2][col]) && cell.equals(board[row+3][col])) 
                    {
                        return true;
                    }
                }
                // Diagonal down-right
                if (row <= board.length - 4 && col <= board[0].length - 4) 
                {
                    if (cell.equals(board[row+1][col+1]) && cell.equals(board[row+2][col+2]) && cell.equals(board[row+3][col+3])) 
                    {
                        return true;
                    }
                }
                // Diagonal down-left
                if (row <= board.length - 4 && col >= 3) 
                {
                    if (cell.equals(board[row+1][col-1]) && cell.equals(board[row+2][col-2]) && cell.equals(board[row+3][col-3])) 
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void takeRedTurn(Scanner input) {
        System.out.println("Red's turn");
        
        int c = input.nextInt();
        while (!pickLocation(c)) {
            System.out.println("Invalid location. Try again.");
            c = input.nextInt();
        }
        takeTurn(c);
        printBoard();
    }

    public void takeYellowTurn(Scanner input) {
        System.out.println("Yellow's turn");
        
        int c = input.nextInt();
        while (!pickLocation(c)) {
            System.out.println("Invalid location. Try again.");
            c = input.nextInt();
        }
        takeTurn(c);
        printBoard();
    }

    public void startPlayer(User currentUser, Scanner input)
    {
        clearBoard();
        clearTurn();
        printBoard();
        while(!checkWin())
        {
            if(getTurn() % 2 == 0)
            {
                System.out.println("Red's turn");                
                int c = input.nextInt();
                while(!pickLocation(c))
                {
                    System.out.println("Invalid location. Try again");
                    c = input.nextInt();
                }
                takeTurn(c);
                printBoard();
            }
            else
            {
                System.out.println("Yellow's turn");
                
                int c = input.nextInt();
                while(!pickLocation(c))
                {
                    System.out.println("Invalid location. Try again");
                    c = input.nextInt();
                }
                takeTurn(c);
                printBoard();
            }

            if(checkDraw())
            {                
                System.out.println("Neither of you are world class material! SO SAD...");
                System.out.println("You can cover it up by playing again, you know");
                break;
            }

            if(checkWin())
            {
                if ((getTurn() - 1) % 2 == 0) 
                {
                    System.out.println(getRedWin());
                    currentUser.giveWin();
                } 
                else 
                {
                    System.out.println(getYellowWin());
                }
                break;
            }
        }
    }

    public void startComputer(User currentUser, Scanner input)
    {
        clearBoard();
        clearTurn();
        printBoard();
        while(!checkWin() && !checkDraw())
        {
            if(getTurn() % 2 == 0)
            {
                System.out.println("Red's turn");
                
                int c = input.nextInt();
                while(!pickLocation(c))
                {
                    System.out.println("Invalid location. Try again");
                    c = input.nextInt();
                }
                takeTurn(c);
                printBoard();
            }
            else
            {
                System.out.println("Yellow's turn: ");
                int c = (int) (Math.random() * 4);
                while (!pickLocation(c)) 
                {
                    c = (int) (Math.random() * 4);
                }
                takeTurn(c);
                printBoard();
            }
        }
        // After loop, check for win or draw and print appropriate message
        if(checkWin())
        {
            if ((getTurn() - 1) % 2 == 0) 
            {
                System.out.println(getRedWin());
                currentUser.giveWin();
            } 
            else 
            {
                System.out.println(getYellowWin());
            }
        }
        else if(checkDraw())
        {
            System.out.println("Neither of you are world class material! SO SAD...");
            System.out.println("You can cover it up by playing again, you know");
        }
    }
}
