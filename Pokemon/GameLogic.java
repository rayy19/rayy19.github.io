import java.util.*;
import java.io.*;

public class GameLogic{
  
  //Fields - You can think of them as if they are parameters(thats esentially how I used them)
  static Scanner myScanner = new Scanner(System.in);
  private static ArrayList<Pokemon> allPkm;
  private static ArrayList<Pokemon> playerPkm;
  private static ArrayList<Pokemon> enemyPkm;
  private static String currentPkm;
  private static int index;
  private static String userName;
  private static boolean playerSkip = false;
  private static boolean enemySkip = false;
  
  //For now the main method willl be in this class
  //Hopefully we can put it in its own driver class later
  public static void main(String[] args) throws Exception{
    //Initalizing pokemon arrayLists
    allPkm = new ArrayList<Pokemon>();
    playerPkm = new ArrayList<Pokemon>();
    enemyPkm = new ArrayList<Pokemon>();
    
    //Do the below methods
    welcomeMsg();
    readFile();
    selectPokemon();
    createEnemies();
    
    //Just shuffles the order of the computer pokemon
    Collections.shuffle(enemyPkm);
    
    //Determines who starts first
    int round = 1;
    if(firstTurn()==1){
      System.out.println("-->You start");
      while(gameValidation()){
        if(round==1){
          playerChoosePkm(); 
        }
        //Once player chooses their pokemon put enemy pokemon on field
        enemySelection();
        //This is where user is asked to attack or use item
        playerInterface();
        //This is where the battle logistics (i.e. how much Hp remains is shown)
        calculateDmg();
        if(gameValidation()&&enemySkip==false){
          enemyMove();
          calculateDmg();
        }
        enemySkip = false;
        round++;
      }
    }
    //Same logic as above except if enemy goes first do user actions after
    else{
      System.out.println("\n-->Enemy Starts");
      while(gameValidation()){
        if(round==1){
          playerChoosePkm(); 
        }
        enemySelection();
        enemyMove();
        calculateDmg();
        if(gameValidation() && playerSkip == false){
          playerInterface();
          calculateDmg();
        }
        playerSkip = false;
        round++;
      }
    }
    finishGame();
  }
  
  //Welcome message to get userName
  public static void welcomeMsg(){
    boolean nameSet = false;
    System.out.println("\nWelcome to the Pokemon Game!");
    
    //Get Player Name
    do{
      System.out.println("What is your name");
      userName = myScanner.next();
      System.out.println("Your name is " + userName + ".\nIs that correct?");
      System.out.println("(1) Yes");
      System.out.println("(2) No, I want to change my name");
      int input = myScanner.nextInt();
      if(input==1){
        nameSet = true; 
      }
    } while(!nameSet);    
  }
  
  //This is where all the pokemon are initilzed/created
  public static void readFile() throws Exception{
    try{
      Scanner fileRead = new Scanner(new File("Pokemon.csv"));
      fileRead.nextLine();
      while(fileRead.hasNext()){
        Pokemon temp = new Pokemon(fileRead.nextLine());  
        allPkm.add(temp);
      }
    } catch(InputMismatchException ex){
      
    }
  }
  
  //This is where user decides which pokemons they want
  public static void selectPokemon(){
    Scanner myReader = new Scanner(System.in);
    displayPokemon();
    while(true){
      int choice = 0; 
      //Where user inputs what pkmn they want
      if(playerPkm.size()<3){
        System.out.println("\nEnter Pokemon " + (playerPkm.size()+1));
        choice = myReader.nextInt();
      }
      //Once the pokemon picks three pkmn, code breaks
      else{
        break; 
      }
      //Below are the validation conditions
      if(choice<allPkm.size() && choice >= 0 && !(playerPkm.contains(allPkm.get(choice)))){
        playerPkm.add(allPkm.get(choice));
        System.out.println("-->You selected " + allPkm.get(choice).getPkmName() + "!");
      }
      else{
        System.out.println("Invalid selection. Input again"); 
      }
    }
  }
  
  //Below is where the enimes are created
  //They are created by determining what leftover pokemon are left after user selects
  public static void createEnemies(){
    for(int i=0; i<allPkm.size();i++){
      if(!(playerPkm.contains(allPkm.get(i)))){
        enemyPkm.add(allPkm.get(i)); 
      }
    }
  }
  
  //In this method, we just go through the whole Pokemon arraylist and print them out
  public static void displayPokemon(){
    //NOTE: The reason A few pokemon are not alligned is because the typing does not have same amount of letters
    System.out.println();
    for(int i=0; i<allPkm.size(); i++){
      Pokemon temp = allPkm.get(i);
      System.out.println(i + ". " + temp.getPkmName() + "\tType: " + temp.getType() + "\tHp: " + temp.getHp() + "\tDefense: " + temp.getDefense());
    }
  }
  
  //In this method, we go through the player pokemon and print them all out
  public static void displayPlayerPkm(){
    System.out.println();
    for(int i=0;i<playerPkm.size();i++){
      Pokemon temp = playerPkm.get(i);
      System.out.println(i+ ". " + temp.getPkmName() + "\tType: " + temp.getType() + "\tHp: " + temp.getHp() + "\tDefense: " + temp.getDefense());
    }
  }
  
  //A random number is generated and depending on the number, the user or player will go first
  public static int firstTurn(){
    Random rand = new Random();
    int num = rand.nextInt(2)+1;
    return num;
  }
  
  //Just makes sure game can continue(user and computer have more than 0 pkmn available)
  public static boolean gameValidation(){
    if(playerPkm.size() == 0){
      return false; 
    }
    if(enemyPkm.size()==0){
     return false; 
    }
    return true;
  }
  
  //This method is where user selects what pkmn to bring into battle
  public static void playerChoosePkm(){
    displayPlayerPkm();
    System.out.println("\n-->" + userName + " has " + playerPkm.size() + " pokemon remaining");
    System.out.println();
    System.out.println(userName + " , enter the pokemon # you would like to bring in");
    while(true){
      int input = myScanner.nextInt(); 
      //The input gets pkmn to bring in and makes sure that user does not input any wrong #s
      if(input>=0 && input<playerPkm.size()){
        currentPkm = playerPkm.get(input).getPkmName();
        index = input;
        System.out.println("\n-->" + userName + " sends out " + currentPkm);
        break;
      }
      else{
        System.out.println("Invalid input"); 
      }
    }
  }
  
  //Generates what pkmn you are battling
  public static void enemySelection(){
    System.out.println("\n-->You are battling " + enemyPkm.get(0).getPkmName());
  }
  
  //This is where user decides to attack or use item
  public static void playerInterface(){
    System.out.println("\n-->" + userName + ", " + currentPkm + " is currently in battle. What do you do");
    System.out.println("\t1: Attack");
    System.out.println("\t2: Use Item");
    int input = 0;
    while(true){
      input = myScanner.nextInt();
      if(input>0&&input<=2){
        //This code just makes sure user put in valid value and will break out of the while true loop
        break; 
      }
      else{
        System.out.println("Invalid Input");
      }
    }
    //depending on the user Choice, the method they would like to do will run
    if(input==1){
      atkSelection(); 
    }
    //NOTE: This is where you will put your item method
    else if(input ==2){
      //itemSelection; 
    }
  }
  
  //In this method, we print out all avialble moves for the pokemon and let them pick one
  public static void atkSelection(){
    //NOTE: the index is being used to keep track of what pkmn is in play right now
    for(int i=0;i<playerPkm.size();i++){
      if(playerPkm.get(i).getPkmName()==currentPkm){
        index = i; 
      }
    }
    
    System.out.println("-->" + userName + " decided to attack. What move would you like to use");    
    //Print out the pkmn moves
    for(int i=0;i<playerPkm.get(index).getNumAtks();i++){
      String[] temp = playerPkm.get(index).getMove(i);
      System.out.println(i+": " + temp[0]);
    }
    //Where user selets what move to use
    while(true){
      int atkChoice = myScanner.nextInt(); 
      //If invalid move this loop win run
      if(atkChoice<0||atkChoice>playerPkm.get(index).getNumAtks()){
        System.out.println("Invalid Command"); 
      }
      //Otherwise this oe will run
      else{
        System.out.println(playerPkm.get(index).getPkmName() + " used " + playerPkm.get(index).getMoveName(atkChoice)+"!");
        useAtk(playerPkm.get(index).getMove(atkChoice),"Enemy");
        break;
      }
    }
  }
  
  //In this method, depending on who the target is, the user or computer will attack
  public static void useAtk(String[] move, String target){
    if(target == "enemy"){
      rawPlayerAtk(move); 
    }
    else if(target == "player"){
      rawEnemyAtk(move); 
    }
  }
  
  //This is where the end result of the atk is dealt with (i.e. the pkmn health is shown here)
  //If pkmn faints, it will also be printed here
  public static void calculateDmg(){
    //NOTE: Index just refers to the pkmn we are using
    if(playerPkm.get(index).getHp()<=0){
      System.out.println(playerPkm.get(index).getPkmName()+" has fainted! Who will replace them?");
      playerPkm.remove(index);
      //Prints out avaiable players
      for(int i=0;i<playerPkm.size();i++){
        System.out.println(i +". " + playerPkm.get(i).getPkmName());
      }
      playerSkip = true;
      //As long as computer and player have pokemon then ask user what pokemon to bring in afer pkmn faints
      if(gameValidation()){
        while(true){
          System.out.println(userName + " What pokemon do you bring in?");
          int response = myScanner.nextInt();
          if(response >= 0 && response<playerPkm.size()){
            index = response;
            currentPkm = playerPkm.get(index).getPkmName();
            System.out.println("\n-->" + userName + " selected " + currentPkm);
            break;
          }
          else{
            System.out.println("Invalid Input"); 
          }
        }
      }
    }
    //Prints how muhc Hp user pokemon has
    else{
      System.out.println("--->" + playerPkm.get(index).getPkmName() + " has " + playerPkm.get(index).getHp() + " HP");
    }
    //If enemy pkmn has less than 0 Hp say they fainted
    if(enemyPkm.get(0).getHp() <=0){
      enemySkip = true;
      System.out.println(enemyPkm.get(0).getPkmName() + " fainted!" );
      enemyPkm.remove(0);
    }
    //Print what Hp enemy pokemon has if alive
    else{
      System.out.println("\n-->" + enemyPkm.get(0).getPkmName() + " has " + enemyPkm.get(0).getHp() + " Hp"); 
    }
  }
  
  //Prints out the enemyMove
  public static void enemyMove(){
    int atkIndex = enemyPkm.get(0).getNumAtks();
    Random rand =  new Random();
    int randomMove = rand.nextInt(atkIndex)+0;
    System.out.println("-->" + enemyPkm.get(0).getPkmName() + " used " + enemyPkm.get(0).getMoveName(randomMove)+"!");
    useAtk(enemyPkm.get(0).getMove(randomMove),"player");
  }
  
  //This is where the Hp is taken away for the ENEMY (aka player attacks)
  public static void rawPlayerAtk(String[] move){
    int dmg = 0;
    dmg = Integer.parseInt(move[2]);
    System.out.println("\n-->The attack Hit!");
    System.out.println("-->The atk did " + dmg + " damage");
    //This is where Hp is taken away - I think issue is happening here
    int currentHp = enemyPkm.get(0).getHp();
    enemyPkm.get(0).setHp(currentHp - dmg);
  }
  
  //This is where Hp is taken for the PLAYER (aka enemy Attacks)
  public static void rawEnemyAtk(String[] move){
    int dmg = 0;
    dmg = Integer.parseInt(move[2]);
    System.out.println("\nThe attack Hit!");
    System.out.println("-->The atk did " + dmg + " damage");
    //This is where Hp is taken away - I think Issue is happening here
    int currentHp = playerPkm.get(0).getHp();
    playerPkm.get(0).setHp(currentHp - dmg);
  }
  
  //Checks to see if anyone wins
  public static void finishGame(){
    if(enemyPkm.size()==0){
      System.out.println();
      System.out.println(userName + " wins"); 
    }
    else if(playerPkm.size()==0){
      System.out.println("\nYou lose. Computer Wins"); 
    }
  }
  
}