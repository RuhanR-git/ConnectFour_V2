import java.util.ArrayList;
import java.util.Scanner;

public class ConnectFourTester 
{ 
    public static void main(String[] args) 
    {
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_RESET = "\u001B[0m"; 
        
        try (Scanner input = new Scanner(System.in)) {

            ConnectFour board = new ConnectFour();
            
            User Newton = new User("Newton", 123);
            User Archi = new User("Archi", 456);
            User Vinci = new User("Vinci", 789);
            
            ArrayList<User> userList = new ArrayList<>();
            userList.add(Newton);
            userList.add(Archi);
            userList.add(Vinci);
            
            Server server = new Server();
            
            for (User u : userList) 
            {
                server.addUserMap(u);
                server.addUserArray(u);
            }
            
            User currentUser = null;
            boolean loggedIn = false;
            boolean playAgain = true;
            while (playAgain) 
            {
                if (!loggedIn) 
                {
                    System.out.println("Hello and Welcome to Sticky Connect 4, you scaredy cat, of course normal connect four was too much for you ;D");
                    System.out.println(ANSI_RED + """
                     ██████╗ ██████╗ ███╗   ██╗███╗   ██╗███████╗ ██████╗████████╗
                    ██╔════╝██╔═══██╗████╗  ██║████╗  ██║██╔════╝██╔════╝╚══██╔══╝
                    ██║     ██║   ██║██╔██╗ ██║██╔██╗ ██║█████╗  ██║        ██║   
                    ██║     ██║   ██║██║╚██╗██║██║╚██╗██║██╔══╝  ██║        ██║   
                    ╚██████╗╚██████╔╝██║ ╚████║██║ ╚████║███████╗╚██████╗   ██║   
                     ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝  ╚═══╝╚══════╝ ╚═════╝   ╚═╝""" + ANSI_RESET);
                    System.out.println(ANSI_YELLOW + """
                    ███████╗ ██████╗  ██╗   ██╗ ██████╗ 
                    ██╔════╝██╔═══██╗ ██║   ██║ ██╔══██╗
                    █████╗  ██║   ██║ ██║   ██║ ██████╔╝
                    ██╔══╝  ██║   ██║ ██║   ██║ ██╔══██╗
                    ██║     ╚██████╔╝ ╚██████╔╝ ██║  ██║
                    ╚═╝      ╚═════╝   ╚═════╝  ╚═╝  ╚═╝""" + ANSI_RESET);
                    
                    System.out.println("Have you been here before or you want the roller coaster?");
                    String x = input.nextLine();
                    if(x.equalsIgnoreCase("before")) 
                    {
                        System.out.println("Alright, put in your username...NOW");
                        String username = input.nextLine();
                        System.out.println("Mediocre Work! Now put in your password even faster!");
                        int password = input.nextInt();
                        input.nextLine(); // Consume the leftover newline character
                        currentUser = server.validateLogin(username, password);
                        if (currentUser != null) 
                        {
                            loggedIn = true;
                            System.out.println("I hope our accountant doesn't chew you up, anyways let the stickity begin!");
                        } else 
                        {
                            System.out.println("Invalid username or password. Goodbye!");
                            continue;
                        }
                    } 
                    else if(x.equalsIgnoreCase("coaster")) 
                    {
                        System.out.println("Give me recalling factor and your camp #");
                        String user = input.nextLine();
                        int pass = input.nextInt();
                        input.nextLine();
                        currentUser = new User(user, pass);
                        server.addUserMap(currentUser);
                        server.addUserArray(currentUser);
                        System.out.println("Good boy, now I can just log you in but I'm too lazy to do that so you get to go to Sign-in!");
                        System.out.println("Alright, put in your username...NOW");
                        String username = input.nextLine();
                        System.out.println("Mediocre Work! Now put in your password even faster!");
                        int password = input.nextInt();
                        input.nextLine();
                        currentUser = server.validateLogin(username, password);
                        if (currentUser != null) 
                        {
                            loggedIn = true;
                            System.out.println("I hope our accountant doesn't chew you up, anyways let the stickity begin!");
                        } 
                        else 
                        {
                            System.out.println("Invalid username or password. Goodbye!");
                            continue;
                        }
                    }
                }
                System.out.println("Please select who you would like to play against: (player or computer)");
                String opponentType = input.nextLine();
                switch (opponentType) 
                {
                    case "player" -> board.startPlayer(currentUser, input);
                    case "computer" -> board.startComputer(currentUser, input);
                    default -> System.out.println("Invalid option. Exiting.");
                }
                
                
                boolean inMenu = true;
                System.out.println("Would you like to play again? You can also view your record and leaderboard, simply ask for them.\nYou can also go back to the login page.");
                while (inMenu) 
                {
                    String again = input.nextLine().trim().toLowerCase();

                    if (again.isEmpty()) {
                        // If the input is empty (e.g., leftover newline), skip this iteration
                        continue;
                    }
                    if (again.equals("no")) 
                    {
                        playAgain = false;
                        inMenu = false;
                    } 
                    else if (again.equals("record")) 
                    {
                        System.out.println(currentUser.recordString());
                        System.out.println("Would you like to play again? You can also view your leaderboard, or go back to the login page");
                    } 
                    else if (again.equals("leaderboard")) 
                    {
                        server.updateUser(currentUser); // Ensure latest stats are in the server
                        server.sort();
                        System.out.println(server.leaderboard());
                        System.out.println("Would you like to play again? You can also go back to the login page");
                    } 
                    else if (again.equals("yes")) 
                    {
                        inMenu = false;
                    }
                    else if (again.equals("log out"))
                    {
                        loggedIn = false;
                        inMenu = false;
                    }
                    else 
                    {
                        System.out.println("Invalid option. Please type 'yes', 'no', 'record', 'leaderboard', or 'log out'");
                    }
                }
            }
        }
    }  
}