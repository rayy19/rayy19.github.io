/*
  GameLogic.java
  Group: Zhiyang and Rayyan
  Teacher: Mr Chu
  Date: January 24, 2021
  Assignment: Final Project (GameLogic Class)
*/

// Imports Everything
import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class GameLogic {
  public static void main(String[] args) throws Exception {
    // These are parameter game variables
    ArrayList<Pokemon> allPkm = new ArrayList<Pokemon>(); // Will be used to display all pokemon at start
    ArrayList<Player> tempPlayerPkm = new ArrayList<Player>(); // Will be used to initally set all pokemon as players
    ArrayList<Player> playerPkm = new ArrayList<Player>(); // Stores all user selected pokemon into this array by taking players from above arraylist
    ArrayList<Enemy> tempEnemyPkm = new ArrayList<Enemy>(); // Will be used to store the users selected pokemon in order to remove it from enemyPkm arraylist
    ArrayList<Enemy> enemyPkm = new ArrayList<Enemy>(); // Will store remaining pokemon after user selection, will take enemies from above arraylist
    ArrayList<Item> item = new ArrayList<Item>(); // Will be used for the Item selection
    // NOTE: Reason these things are in an array is so that I can pass by refrence
    // values (aka get/use values from multiple methods)
    String[] currentPkm = new String[1];
    int[] index = new int[1];
    Boolean[] playerSkip = new Boolean[1];
    playerSkip[0] = false;
    Boolean[] enemySkip = new Boolean[1];
    enemySkip[0] = false;

    // Welcoming Message
    System.out.println();
    System.out.println("Welcome to the Pokemon Game!");
    
    // Below is where we begin calling methods/ the game
    String[] userName = new String[1];
    userName[0] = playerUsername();

    // The three files below simply get the pokemon/player/enemy
    readFile(allPkm);
    getPlayer(tempPlayerPkm);
    getEnemy(enemyPkm);
    getItem(item);

    selectPokemon(tempPlayerPkm, playerPkm, allPkm, enemyPkm, tempEnemyPkm); // Pick three pokemon from the 6
    Collections.shuffle(enemyPkm); // Shuffle enemy pkmn array

    int round = 1;
    System.out.println();
    System.out.println("Game Begin!");
    System.out.println("Randomizing Turns...");
    System.out.println();
    TimeUnit.SECONDS.sleep(1); // Delay
    
    // Using the first turn randomizer method if #=1 then player starts
    // If #=2 then enemy starts
    if (firstTurn() == 1) {
      System.out.println("You Start!");
      TimeUnit.SECONDS.sleep(1); // Delay
      while (gameValidation(playerPkm, enemyPkm)) {
        if (round == 1) {
          playerChoosePkm(playerPkm, enemyPkm, currentPkm, index, userName); // Let user choose pkmn to bring in
        }
        System.out.println();
        enemySelection(enemyPkm);
        playerInterface(playerPkm, enemyPkm, index, userName, currentPkm); // choose item or attack
        calculateDmg(playerPkm, enemyPkm, playerSkip, enemySkip, currentPkm, index, userName); // determine if fainted or not
        if (gameValidation(playerPkm, enemyPkm) && enemySkip[0] == false) {
          enemyMove(playerPkm, enemyPkm, index); // Enemy uses move
          calculateDmg(playerPkm, enemyPkm, playerSkip, enemySkip, currentPkm, index, userName); // Determine if fainted or not
        }
        enemySkip[0] = false; // set enemySkip to false meaning dont skip turn
        round++; // Increment round so the computer can go
      }
    } else {
      System.out.println("Enemy Starts!");
      TimeUnit.SECONDS.sleep(1); // Delay
      while (gameValidation(playerPkm, enemyPkm)) {
        if (round == 1) {
          playerChoosePkm(playerPkm, enemyPkm, currentPkm, index, userName); // Let user choose pkmn to bring
        }
        System.out.println();
        enemySelection(enemyPkm); // Says what pokemon enemy brings in
        enemyMove(playerPkm, enemyPkm, index); // Enemy uses move
        calculateDmg(playerPkm, enemyPkm, playerSkip, enemySkip, currentPkm, index, userName); // Determines if fainted or not
        if (gameValidation(playerPkm, enemyPkm) && playerSkip[0] == false) {
          playerInterface(playerPkm, enemyPkm, index, userName, currentPkm); // Lets user choose item or attack
          calculateDmg(playerPkm, enemyPkm, playerSkip, enemySkip, currentPkm, index, userName); // Determine if fainted or not
        }
        playerSkip[0] = false; // Set skip to false meaning no need to skip turn
        round++; // Increment round so user can go
      }
      endGame(playerPkm, enemyPkm, userName); // Checks if game is over
    }

    System.out.println();
    playerChoosePkm(playerPkm, enemyPkm, currentPkm, index, userName); // Pick one to bring on field
    playerInterface(playerPkm, enemyPkm, index, userName, currentPkm);
    atkSelection(playerPkm, enemyPkm, userName, currentPkm, index);
  }

  // Method 1: In this method, we allow user to enter their name and confirm their name
  public static String playerUsername() {
    // Initializes the Scanner
    Scanner myScan = new Scanner(System.in);
    // Variables
    String userName = "";

    // Try catch in order to handle InputMisMatchException
    try {
      // Asks the player for its name
      System.out.println();
      System.out.println("What is your name?");
      System.out.print("= ");
      userName = myScan.nextLine();
      System.out.println();

      // Asks the User if they want to keep their userName or make a new one
      System.out.println("Your name is " + userName + ", is that correct?");
      System.out.println("1. Yes");
      System.out.println("2. No, I want to change my name");
      System.out.print("= ");
      int input = myScan.nextInt();

      // If the user chooses to change their name
      if (input == 2) {
        userName = playerUsername();
      }
      // If they put an invalid input
      else if (input != 1 && input != 2) {
        System.out.println();
        System.out.println("Invalid input, please try again");
        userName = playerUsername();
      }
    } catch (InputMismatchException e) {
      System.out.println();
      System.out.println("Invalid input, please try again");
      userName = playerUsername();
    }
    
    // If they choosed to keep their userName
    return userName;
  }

  // Method 2: In this method we read our file to get all our pokemon
  public static void readFile(ArrayList<Pokemon> allPkm) throws Exception {
    // This will be used to display the pokemon for the user to select from
    try {
      Scanner fileRead = new Scanner(new File("Pokemon.csv"));
      fileRead.nextLine();
      while (fileRead.hasNext()) {
        // Below is where the pokemon object is created
        // The idea is that the pokemon object is being read from the file...
        // and that object is put into arraylist
        Pokemon temp = new Pokemon(fileRead.nextLine());
        allPkm.add(temp);
      }
    } catch (InputMismatchException ex) {

    }
  }

  // Method 3: In this method, we temporarily set all pokemon to be players
  public static void getPlayer(ArrayList<Player> tempPlayerPkm) throws Exception {
    // The reason for this is that when user selects, we can simply add the user
    // choice pokemon into the playerPkm array
    try {
      Scanner readFile = new Scanner(new File("Pokemon.csv"));
      readFile.nextLine();
      while (readFile.hasNext()) {
        // Just like how the pokemon object is created, the player object is created in
        // the same way
        Player temp = new Player(readFile.nextLine());
        tempPlayerPkm.add(temp);
      }
    } catch (InputMismatchException ne) {

    }
  }

  // Method 4: In this method, we temporiraily set all pokemon to be Enemies
  public static void getEnemy(ArrayList<Enemy> enemyPkm) throws Exception {
    // The reason for this is that after the user picks their pokemon, the
    // remiaining pokemon will become enimies
    try {
      Scanner readFile = new Scanner(new File("Pokemon.csv"));
      readFile.nextLine();
      while (readFile.hasNext()) {
        // Just like how player object is created, the enemy object is created in the
        // same way
        Enemy temp = new Enemy(readFile.nextLine());
        enemyPkm.add(temp);
      }
    } catch (InputMismatchException ne) {

    }
  }

  // Method 5: This method will be used for the Item Interface/Mechanic
  public static void getItem(ArrayList<Item> item) throws Exception {
    try {
      Scanner readFile = new Scanner(new File("Pokemon.csv"));
      readFile.nextLine();
      while (readFile.hasNext()) {
        Item temp = new Item(readFile.nextLine());
        item.add(temp);
      }
    } catch (InputMismatchException ne) {

    }
  }

  // Method 6: In this method, we display all pokemon for user to pick from
  public static void displayPokemon(ArrayList<Player> tempPlayerPkm) {
    // NOTE: The allPkm array is only used for display purposes and will NOT be used
    // in terms of actually battling and using items
    System.out.println("Pokemon Menu");
    System.out.println();
    for (int i = 0; i < tempPlayerPkm.size(); i++) {
      Player temp = tempPlayerPkm.get(i);
      System.out.println("|\t" + i + ". " + temp.getPkmName() + "\t | \t Type: " + temp.getType() + "\t | \t Health: " + temp.getHp() + "\t | \t Defense: " + temp.getDefense() + "\t |");
    }
  }

  // Method 7: In this method, we will add the user selected pokemon into the playerPkm arraylist
  public static void selectPokemon(ArrayList<Player> tempPlayerPkm, ArrayList<Player> playerPkm,
      ArrayList<Pokemon> allPkm, ArrayList<Enemy> enemyPkm, ArrayList<Enemy> tempEnemyPkm) {
    // We will get the player from the tempPlayerPkm arraylist
    System.out.println();
    displayPokemon(tempPlayerPkm);

    // Used to continously let the user choose a pokemon until they have filled the arraylist to 3 pokemon
    while (true) {
      // This try catch is used to catch IndexOutOfBoundsException when a user enters a value greater than the arraylist
      try {
        // This try catch is used to catch InputMisMatchException error when a user inputs a String instead of an int
        try {
          // Initializes the Scanner
          Scanner myScan = new Scanner(System.in);

          // Variables
          int choice = 0;

          // If the player arraylist is equal to 3, then stop the selection process
          if (playerPkm.size() == 3) {
            break;
          }

          // Asks the user to select a pokemon
          System.out.println();
          System.out.println("Enter Pokemon # you wish to select: Pokemon #" + (playerPkm.size() + 1));
          System.out.print("= ");
          choice = myScan.nextInt();

          // This if statement makes sure the choice is a legal move (i.e. not a pokemon in list or not a pokemon out of bounds)
          if (!(playerPkm.contains(tempPlayerPkm.get(choice))) && choice < tempPlayerPkm.size() && choice >= 0) {
            // This is where we add the player into the playerPkm arraylist
            playerPkm.add(tempPlayerPkm.get(choice));
            // This is where we store the players choice of pokemon into a tempEnemyPkm arraylist (to be used above after the 3 choices from the player)
            Enemy temp = enemyPkm.get(choice);
            tempEnemyPkm.add(temp);
            System.out.println("You selected " + tempPlayerPkm.get(choice).getPkmName() + "!");
          }
          // If the choice is outside of the arraylist
          else {
            System.out.println("Invalid Input, please try again");
          }
          
        } catch (InputMismatchException e) {
          System.out.println("Invalid Input, please try again");
        }
      } catch (IndexOutOfBoundsException e) {
        System.out.println("Invalid Input, please try again");
      }
    }

    // In this section, we remove the pokemons that the user has chosen from the enemyPkm arraylist in order...
    // ... for the enemy to have the remaining 3 pokemon the user didnt choose
    // Goes though the tempEnemyPkm array and compares it with the enemyPkm
    for (int i = 0; i < tempEnemyPkm.size(); i++) {
      for (int j = 0; j < enemyPkm.size(); j++) {
        // If any of the pokemon in tempEnemyPkm array is in the enemyPkm, remove it from enemyPkm
        if (tempEnemyPkm.get(i).equals(enemyPkm.get(j))) {
          enemyPkm.remove(j);
        }
      }
    }

    // If the enemyPkm exceeds 3 pokemon, remove any excess pokemon (Occurs when its not in chronological order, ie user didnt eneter 1,2,3 or 2,3,4, instead something liek 1,3,4)
    if (enemyPkm.size() > 3) {
      for (int i = 3; i < enemyPkm.size(); i++) {
        enemyPkm.remove(i);
      }
    }
  } 
  
  // Method 8: In this method, we are printing out all of the users avialable pokemon
  public static void displayPlayerPkm(ArrayList<Player> playerPkm, String[] userName) {
    System.out.println();
    System.out.println(userName[0].toUpperCase() + "'S POKEMON");
    System.out.println();
    for (int i = 0; i < playerPkm.size(); i++) {
      Player temp = playerPkm.get(i);
      System.out.println("|\t" + i + ". " + temp.getPkmName() + "\t | \t Type: " + temp.getType() + "\t | \t Health: " + temp.getHp() + "\t | \t Energy: " + temp.getTotalPkmEnergy() + "\t |");
    }
    System.out.println();
  }

  // In this method, we are randomizing a # b/t 1 and 2 and the # will determine who goes first
  // 1 = user, 2 = enemy
  public static int firstTurn() {
    Random rand = new Random();
    int num = rand.nextInt(2) + 1;
    return num;
  }

  // In this method, we are just making sure that both the player and enemy have pokemon avialable
  public static boolean gameValidation(ArrayList<Player> playerPkm, ArrayList<Enemy> enemyPkm) {
    if (playerPkm.size() == 0 && enemyPkm.size() == 0) {
      return false;
    }
    return true;
  }

  // In this method, the user decides who to bring into battle
  public static void playerChoosePkm(ArrayList<Player> playerPkm, ArrayList<Enemy> enemyPkm,
      String[] currentPkm, int[] index, String[] userName) {
    
    // Call the display method
    displayPlayerPkm(playerPkm, userName);

    // Tell user how many pokemon they have left
    System.out.println("You have " + playerPkm.size() + " Pokemon remaining");
    System.out.println();

    // In this while loop, the user will select one pokemon to bring out
    while (true) {
      // This try catch is used to catch the InputMisMatchException, where the user enters a String instead of an int
      try {
        // This try catch is used to catch the NoSuchElementException, where the user enters a number larger than the arraylist
        try {
          // Imports the Scanner
          Scanner myScan = new Scanner(System.in);

          // Asks the user what pokemon they want to bring in
          System.out.println("Enter the Pokemon # you would like to bring in:");
          System.out.print("= ");
          int input = myScan.nextInt();

          // If user inputs legal move, then the pokemon will beceom current pokemon
          // battle and will join battle
          if (input >= 0 && input < playerPkm.size()) {
            currentPkm[0] = playerPkm.get(input).getPkmName();
            index[0] = input;
            System.out.println("-->" + userName[0] + " sends out " + currentPkm[0]);
            break;
          }
          // otherwise loop will reloop
          else {
            System.out.println("Invalid Input, please try again");
            System.out.println();
          }
          
        } catch (NoSuchElementException e) {
          System.out.println("Invalid Input, please try again");
          System.out.println();
        }
      } catch (InputMismatchException e) {
        System.out.println("Invalid Input, please try again");
        System.out.println();
      }
    }
  }

  // In this method we are simply introducing the enemy pokemon
  public static void enemySelection(ArrayList<Enemy> enemyPkm) throws InterruptedException {
    // Do this in an array because each time we need to introduce a new enemy we can
    // use this method
    System.out.println("Enemy Pokemon: " + enemyPkm.get(0).getPkmName());
    TimeUnit.SECONDS.sleep(1); // Delay
  }

  // This method acts as the user interface, it is where user can choose to take item or attack
  public static void playerInterface(ArrayList<Player> playerPkm, ArrayList<Enemy> enemyPkm, int[] index, String[] userName, String[] currentPkm) throws InterruptedException {
    // Variables
    int input = 0;
    
    // While true statement in order to catch any invalid inputs
    while (true) {
      // This try catch is used to catch the InputMisMatchException, where the user enters a String instead of an int
      try {
        // Initializes the Scanner
        Scanner myScan = new Scanner(System.in);

        // Shows the user the avaliable menu
        System.out.println();
        System.out.println("Menu");
        System.out.println("1: Attack");
        System.out.println("2: Use Item");
        System.out.print("= ");
        input = myScan.nextInt();
        // If user inputs a valid move, break out of the loop
        if (input > 0 && input <= 2) {
          break;
        }
        // Otherwise continue to ask user for their choice
        else {
          System.out.println("Invalid Input, please try again");
        }
      } catch (InputMismatchException e) {
        System.out.println("Invalid Input, please try again");
      }
    }

    // If user selects to attack then the atttack selection method will be called
    if (input == 1) {
      atkSelection(playerPkm, enemyPkm, userName, currentPkm, index);
    }
    // If user selects to use an item, then the item selection method will be called
    // NOTE: This is where your method will go
    else if (input == 2) {
      // itemSelection; //NOTE: YOU WILL PUT YOUR METHOD HERE!
      itemSelection(playerPkm, enemyPkm, userName, currentPkm, index);
    }
  }

  // Method for itemSelection
  public static void itemSelection(ArrayList<Player> playerPkm, ArrayList<Enemy> enemyPkm,
      String[] userName, String[] currentPkm, int[] index) {
    // Determines which pokemon is currently in battle
    for (int i = 0; i < playerPkm.size(); i++) {
      if (playerPkm.get(i).getPkmName() == currentPkm[0]) {
        index[0] = i; // the index variable finds the index of what pokemon that is onField
      }
    }

    // Variables
    int ans = 0;

    // This try catch is used to catch the InputMisMatchException, where the user enters a String instead of an int
    try {
      // Initalizes the Scanner
      Scanner myScan = new Scanner(System.in);

      // Menu
      System.out.println();
      System.out.println("Item Menu");
      System.out.println("1. Defense Boost");
      System.out.println("2. Energy Boost");
      System.out.println("3. Health Boost");
      System.out.print("= ");
      ans = myScan.nextInt();

      // defBoost
      if (ans == 1) {
        // Initializes the Randomizer
        Random rand = new Random();
        // Chooses a random number to increase the pokemons defense by (2-4)
        int defBoostNum = rand.nextInt(3) + 2;
        // Initializes the def stat
        int def = 0;

        // Gets the current pokemon's defense and adds the defense boost onto the
        // overall defense
        int tempDef = playerPkm.get(index[0]).getDefense();
        def = tempDef + defBoostNum;
        playerPkm.get(index[0]).setDefense(def);

        // Gives a print statement to the user
        System.out.println("-->" + currentPkm[0] + " has his defense boosted by an additional " + defBoostNum + "!");
      }
      // energyBoost
      else if (ans == 2) {
        // Just set the pokemon energy to the maximum energy
        playerPkm.get(index[0]).setPkmEnergy(50);
        System.out.println("-->You now have max energy!");
      }

      // hpBoost
      else if (ans == 3) {
        // Initializes the Randomizer
        Random rand = new Random();
        // Chooses a random number to increase the pokemons hp by (10-20)
        int hpBoostNum = rand.nextInt(11) + 10;
        // Initializes the hp stat
        int hp = 0;

        // Gets the current HP and the MaximumHP of the pokemon
        int tempHP = playerPkm.get(index[0]).getHp();
        int maxHP = playerPkm.get(index[0]).getMaxHp();

        // Checks if the current HP plus the hpBoost would exceed or directly equal the
        // pokemons MaximumHP
        if ((tempHP + hpBoostNum) >= maxHP) {
          // Just set the pokemon HP to the MaximumHP
          playerPkm.get(index[0]).setHp(maxHP);
          System.out.println("-->" + currentPkm[0] + " has healed to max HP!");
        }
        // Else, if it doesn't exceed the pokemons MaximumHP, just add it like you
        // regularly would
        else {
          // Adds the hpBoost
          hp = tempHP + hpBoostNum;
          playerPkm.get(index[0]).setHp(hp);
          System.out.println("-->" + currentPkm[0] + " has healed by " + hpBoostNum + " HP!");
        }
      }

      // Integrity
      else {
        System.out.println("Invalid answer, please try again");
        itemSelection(playerPkm, enemyPkm, userName, currentPkm, index);
      }
    } catch (InputMismatchException e) {
      System.out.println("Invalid answer, please try again");
      itemSelection(playerPkm, enemyPkm, userName, currentPkm, index);
    }
  }

  // In this method, we allow user to attack. We do this by determining what pokemon is on battle and then displaying their moves
  public static void atkSelection(ArrayList<Player> playerPkm, ArrayList<Enemy> enemyPkm,
      String[] userName, String[] currentPkm, int[] index) throws InterruptedException {
    
    // This is where we determine what pokemon is in battle
    for (int i = 0; i < playerPkm.size(); i++) {
      if (playerPkm.get(i).getPkmName() == currentPkm[0]) {
        index[0] = i; // the index variable finds the index of what pokemon that is onField
      }
    }

    // In this while loop, user selects what move to select
    while (true) {
      // This try catch is used to catch the InputMisMatchException, where the user enters a String instead of an int
      try {
        // This try catch is used to catch the IndexOutOfBoundsException, where the user enters a number larger than the arraylist
        try {
          // Initializes the Scanner
          Scanner myScan = new Scanner(System.in);

          // This is where all the moves of the pokemon are printed out
          System.out.println();
          System.out.println(currentPkm[0].toUpperCase() + " MOVES:");
          System.out.println();
          for (int i = 0; i < playerPkm.get(index[0]).getNumAtks(); i++) {
            String[] temp = playerPkm.get(index[0]).getMove(i);
            System.out.println("|\t" + i + ". " + temp[0] + "\t    | \t  Damage: " + temp[1] + "\t | \t Energy: " + temp[2]  + "\t |");
          }
          System.out.println();
          System.out.println(currentPkm[0] + " has " + playerPkm.get(index[0]).getTotalPkmEnergy() + " energy left.");
          System.out.println(userName[0] + ", what move should " + currentPkm[0] + " use?");
          System.out.print("= ");

          int attackChoice = myScan.nextInt();
          // If illegal move, reloop
          if (attackChoice < 0 || attackChoice > playerPkm.get(index[0]).getNumAtks()) { // safety net
            System.out.println("Invalid Input, please try again");
          }
          // If user does not have enough energy to use a move
          else if (playerPkm.get(index[0]).getTotalPkmEnergy() < playerPkm.get(index[0]).getMoveEnergy(attackChoice)) { // if there's not enough energy
            System.out.println("Not enough energy. Use another move or use energy boost");
            playerInterface(playerPkm, enemyPkm, index, userName, currentPkm); //FORCE User to go back into the game interface
            break;
          }
          // If user selects a legal move, then they will use it and have their energy
          // decrease
          else {
            System.out.println("\nPLAYER ATTACK");
            System.out.println(playerPkm.get(index[0]).getPkmName() + " used " + playerPkm.get(index[0]).getMoveName(attackChoice));
            TimeUnit.SECONDS.sleep(1); // Delay
            int temp = playerPkm.get(index[0]).getTotalPkmEnergy(); // used to subract energy
            playerPkm.get(index[0]).setPkmEnergy(temp - playerPkm.get(index[0]).getMoveEnergy(attackChoice));
            useAtk(playerPkm, enemyPkm, playerPkm.get(index[0]).getMove(attackChoice), "enemy", index); // using the attack
            break;
          }
        } catch (IndexOutOfBoundsException e) {
          System.out.println("Invalid Input, please try again");
        }
      } catch (InputMismatchException e) {
        System.out.println("Invalid Input, please try again");
      }
    }
  }

  // In this method, we simply attack the opposing side, we do this by calling a method that will deal the dmg
  public static void useAtk(ArrayList<Player> playerPkm, ArrayList<Enemy> enemyPkm, String[] move, String target,
      int[] index) {
    if (target == "enemy") {
      playerAtk(playerPkm, enemyPkm, move, index);
    } else if (target == "player") {
      enemyAtk(playerPkm, enemyPkm, move, index);
    }
  }

  // In this method, we determine if the user or enemy pokemon fainted or is alive
  public static void calculateDmg(ArrayList<Player> playerPkm, ArrayList<Enemy> enemyPkm,
      Boolean[] playerSkip, Boolean[] enemySkip, String[] currentPkm, int[] index, String[] userName) {
    // If they faint we will allow them to bring a new pokemon in and use it (this will skip their turn)
    // Otherwise we will print the pokemon health

    // In this if statement, we see if user faints
    if (playerPkm.get(index[0]).getHp() <= 0) {
      System.out.println("\n" + playerPkm.get(index[0]).getPkmName() + " has fainted.");
      // If the game is already over, end the game
      if (playerPkm.size() == 0) {
        endGame(playerPkm, enemyPkm, userName); // Runs the endGame method to see if the game has already ended
      }
      System.out.println("Who would replace them?");
      playerPkm.remove(index[0]);
      endGame(playerPkm, enemyPkm, userName); // Runs the endGame method to see if the game has already ended
      System.out.println("\n" + userName[0].toUpperCase() + " AVAILABLE POKEMON");

      // If the pokemon fainted, we remove it above and then print out reminaing pokemon
      for (int i = 0; i < playerPkm.size(); i++) {
        System.out.println(i + ". " + playerPkm.get(i).getPkmName());
      }
      playerSkip[0] = true; // Skips the player turn once they bring in pokemon
      if (gameValidation(playerPkm, enemyPkm)) { // If game is true aka is not finished and both sides have poekmon available
        // This while loop lets user pick their pokemon and makes sure they make no illegal moves
        while (true) {
          // This try catch is used to catch InputMisMatchException, if the user enters a String instead of an int
          try {
            // Initializes the Scanner
            Scanner myScan = new Scanner(System.in);

            System.out.println();
            System.out.println(userName[0] + " what pokemon do you bring in");
            System.out.print("= ");
            int response = myScan.nextInt();
            // If move is legal, we execute this code
            if (response >= 0 && response < playerPkm.size()) {
              index[0] = response;
              currentPkm[0] = playerPkm.get(index[0]).getPkmName();
              System.out.println(userName[0] + " chooses " + currentPkm[0]);
              break;
            }
            // Otherwise we reloop
            else {
              System.out.println("Invalid Input, please try again");
            }
          } catch (InputMismatchException e) {
            System.out.println("Invalid Input, please try again");
          }
        }
      }
    }
    // If user Pokemon does have Hp (greater than 0) we will print their stats
    else {
      System.out.println("\nYOUR POKEMON:");
      System.out.println("|\t" + playerPkm.get(index[0]).getPkmName().toUpperCase() + "\t | \t   HP: " + playerPkm.get(index[0]).getHp() + " \t | \t DEFENSE: " + playerPkm.get(index[0]).getDefense() + " \t | \t ENERGY: " + playerPkm.get(index[0]).getTotalPkmEnergy() + "\t |");
    }

    // Just like we did above for user, we will now check if computer fainted
    if (enemyPkm.get(0).getHp() <= 0) {
      enemySkip[0] = true; // Skips enemy turn when they bring in new pokemon
      System.out.println();
      System.out.println("\n" + enemyPkm.get(0).getPkmName() + " fainted");
      enemyPkm.remove(0); // removing the first pokemon in the arraylist
      endGame(playerPkm, enemyPkm, userName); // Runs the endGame method to see if the game has already ended
      System.out.println("\n -->Enemy sends out " + enemyPkm.get(0).getPkmName());
    }
    // If current pokemon is still alive, we will print their stats
    else {
      System.out.println();
      System.out.println("COMPUTER POKEMON:");
      System.out.println("|\t" + enemyPkm.get(0).getPkmName() + "\t | \t   HP: " + enemyPkm.get(0).getHp() +  " \t | \t DEFENSE: " + enemyPkm.get(0).getDefense() + " \t | \t         \t |");
    }
  }

  public static void enemyMove(ArrayList<Player> playerPkm, ArrayList<Enemy> enemyPkm, int[] index)
      throws InterruptedException {
    // System for Swapping
    // Initializes the Randomizer
    Random rand = new Random();
    // Chooses a random number (1-6)
    int chance = rand.nextInt(6) + 1;

    // Everytime its the Enemies turn, there is a 1/6 chance of the AI switching
    // pokemon
    // This chance will only switch if the current enemy pokemon is uneffective at
    // fighting the player pokemon aka (Grass vs Fire)
    if (chance == 1) {
      // If the current pokemon is effective against the player pokemon, dont switch
      // (So we use the normal attack code from the below else statement)
      if (playerPkm.get(index[0]).getWeakness() == enemyPkm.get(0).getType()) {
        // In this method, since we do not have to worry about energy, we can simply
        // just let computer pick a move
        // To do this, we randomize a # b\t 0-3 and depending on that number, a move
        // will be used
        int atkIndex = enemyPkm.get(0).getNumAtks();
        int randomMove = rand.nextInt(atkIndex) + 0;
        System.out.println("\nENEMY ATTACK");
        System.out.println("--> " + enemyPkm.get(0).getPkmName() + " used " + enemyPkm.get(0).getMoveName(randomMove));
        TimeUnit.SECONDS.sleep(1); // Delay
        useAtk(playerPkm, enemyPkm, enemyPkm.get(0).getMove(randomMove), "player", index);
      }
      // If not, then the pokemon will switch
      else {
        // If the enemy only has one pokemon left, dont switch
        if (enemyPkm.size() > 1) {
          System.out.println("\nENEMY TURN");
          System.out.println("\nEnemy decides to switch pokemon!");
          TimeUnit.SECONDS.sleep(1); // Delay
          // Saves the current pokemon on the field to the variable "tempPkm"
          Enemy tempPkm = enemyPkm.get(0);
          System.out.println(tempPkm + " has returned");
          // Removes the current pokemon, resulting in it being a new pokemon as the enemy
          // pokemon is always whatever is at index 0 of the Arraylist
          enemyPkm.remove(0);
          // Then, we add the pokemon back into the ArrayList, resulting in it being the
          // last pokemon in the ArrayList.
          enemyPkm.add(tempPkm);
          // Displays the new pokemon to the user
          System.out.println("\n--> Enemy sends out " + enemyPkm.get(0).getPkmName());
        }
      }
    }

    // Else, run the regular attack code
    else {
      // In this method, since we do not have to worry about energy, we can simply
      // just let computer pick a move
      // To do this, we randomize a # b\t 0-3 and depending on that number, a move
      // will be used
      int atkIndex = enemyPkm.get(0).getNumAtks();
      int randomMove = rand.nextInt(atkIndex) + 0;
      System.out.println("\nENEMY ATTACK");
      System.out.println("--> " + enemyPkm.get(0).getPkmName() + " used " + enemyPkm.get(0).getMoveName(randomMove));
      TimeUnit.SECONDS.sleep(1); // Delay
      useAtk(playerPkm, enemyPkm, enemyPkm.get(0).getMove(randomMove), "player", index);
    }
  }

  // This is where the Hp is taken away for the ENEMY (aka player attacks)
  public static void playerAtk(ArrayList<Player> playerPkm, ArrayList<Enemy> enemyPkm, String[] move, int[] index) {

    int dmg = 0;

    // System for Critial Hit/Miss
    // Initializes the Randomizer
    Random rand = new Random();
    // Chooses a random number (1-6)
    int chance = rand.nextInt(6) + 1;

    // For every hit that is done, there is a 1/6 chance of it being a critical hit
    if (chance == 1) {
      dmg = Integer.parseInt(move[2]) * 2 - enemyPkm.get(index[0]).getDefense();
      System.out.println("--> Critical Hit!");

      // If the attack is efffective/not effective, it stacks on
      if (enemyPkm.get(0).getResistance().equals(playerPkm.get(index[0]).getType())) {
        dmg = (dmg / 2) - enemyPkm.get(index[0]).getDefense();
        System.out.println("--> It's not very effective...");
      } else if (enemyPkm.get(0).getWeakness().equals(playerPkm.get(index[0]).getType())) {
        dmg = (dmg * 2) - enemyPkm.get(index[0]).getDefense();
        System.out.println("--> It's super effective!");
      }
    }
    // For every hit that is done, there is a 1/6 chance of it being a complete miss
    else if (chance == 2) {
      System.out.println("--> Miss!");
    }
    // If it did not land that 1/6 chance for a critical hit, run the normal attack code
    else {
      if (enemyPkm.get(0).getResistance().equals(playerPkm.get(index[0]).getType())) {
        dmg = (Integer.parseInt(move[2]) / 2) - enemyPkm.get(index[0]).getDefense();
        System.out.println("--> It's not very effective...");
      } else if (enemyPkm.get(0).getWeakness().equals(playerPkm.get(index[0]).getType())) {
        dmg = (Integer.parseInt(move[2]) * 2) - enemyPkm.get(index[0]).getDefense();
        System.out.println("--> It's super effective!");
      } else {
        dmg = Integer.parseInt(move[2]) - enemyPkm.get(index[0]).getDefense();
        System.out.println("--> Hit!");
      }
    }

    // If the damage is negative, set it to 0
    if (dmg <= 0) {
      dmg = 0;
    }
    
    System.out.println();
    System.out.println("--> The atk did " + dmg + " damage!");

    // This is where Hp is taken away 
    int currentHp = enemyPkm.get(0).getHp();
    enemyPkm.get(0).setHp(currentHp - dmg);
  }

  // This is where Hp is taken away from the player
  public static void enemyAtk(ArrayList<Player> playerPkm, ArrayList<Enemy> enemyPkm, String[] move, int[] index) {

    int damage = 0; // always starts an attack at 0

    // System for Critial Hit/Miss
    // Initializes the Randomizer
    Random rand = new Random();
    // Chooses a random number (1-6)
    int chance = rand.nextInt(6) + 1;

    // For every hit that is done, there is a 1/6 chance of it being a critical hit
    if (chance == 1) {
      damage = Integer.parseInt(move[2]) * 2 - enemyPkm.get(index[0]).getDefense();
      System.out.println("\n--> Critical Hit!");

      // If the attack is efffective/not effective, it stacks on
      if (playerPkm.get(index[0]).getResistance().equals(enemyPkm.get(0).getType())) { // if resistance
        damage = (damage / 2) - playerPkm.get(index[0]).getDefense();
        System.out.println("--> It's not very effective...");
      } else if (playerPkm.get(index[0]).getWeakness().equals(enemyPkm.get(0).getType())) { // if weakness
        damage = (damage * 2) - playerPkm.get(index[0]).getDefense();
        System.out.println("--> It's Super Effective!");
      }
    }
    // For every hit that is done, there is a 1/6 chance of it being a complete miss
    else if (chance == 2) {
      System.out.println("--> Miss!");
    }
    // If it did not land that 1/6 chance for a critical hit, run the normal attack code
    else {
      if (playerPkm.get(index[0]).getResistance().equals(enemyPkm.get(0).getType())) { // if resistance
        damage = (Integer.parseInt(move[2]) / 2) - playerPkm.get(index[0]).getDefense();
        System.out.println("--> It's not very effective...");
      } // end if
      else if (playerPkm.get(index[0]).getWeakness().equals(enemyPkm.get(0).getType())) { // if weakness
        damage = (Integer.parseInt(move[2]) * 2) - playerPkm.get(index[0]).getDefense();
        System.out.println("--> It's Super Effective!");
      } // end else if
      else { // regular attack
        damage = Integer.parseInt(move[2]) - playerPkm.get(index[0]).getDefense();
        System.out.println("--> Hit!");
      } // end else
    }

    // If the damage is negative, set it to 0
    if (damage <= 0) {
      damage = 0;
    }
    
    System.out.println();
    System.out.println("--> The attack did " + damage + " damage!");

    int currHp = playerPkm.get(index[0]).getHp();
    playerPkm.get(index[0]).setHp(currHp - damage); 
  }// end rawEnemyAttack

  public static void endGame(ArrayList<Player> playerPkm, ArrayList<Enemy> enemyPkm, String[] userName) {
    if (enemyPkm.size() == 0) { // all enemies are dead
      System.out.println("Congratulations " + userName[0] + ", you win!");
      System.exit(1);
    } else if (playerPkm.size() == 0) { // all players are dead
      System.out.println(userName[0] + ", you lost. Better luck next time!");
      System.exit(1);
    }
  }
}