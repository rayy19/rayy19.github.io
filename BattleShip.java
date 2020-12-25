/* Rayyan Amir
 * BattleShip Assignment
 * Teacher: Mr.Chu
 * October 4, 2020
 */

import java.util.Scanner;
import java.util.Random;

class BattleShip{
 
  public static void main(String[] args){
  //In main procedure I will initalize all my variables and call my methods
  //The main method will have the game continue until the computer or a user...
  //has hit 17 points (5 + 4 + 3 + 3 + 2 = 17)
    
  char[][] userBoard = new char[10][10];
  char[][] comBoard = new char[10][10];
  int[] shoot = new int[2];
  int[] comShot = new int[2];
  int computerHit =0;
  int userHit = 0;
  
  System.out.println("Battleship game Starting");
  System.out.println("\nBoard Legend");
  System.out.println(". = Coordinate hasen't been tried");
  System.out.println("O = Ship");
  System.out.println("X = Ship Hit");
  System.out.println("* = Missed Shot");
   
  //Below is where I initialze(set com user grid to be .)... 
  //and begin my user and computer interface(get ship values and ship orientation)
  initiateUserBoard(userBoard);
  System.out.println("\nComputer is deploying its ships ");
  initiateComBoard(comBoard);
  userInterface(userBoard);
  comInterface(comBoard);
 
  //As mentioned above, the do while loop will run until com or user hits 17 points
  do{
    printUserBoard(userBoard);
    printComBoard(comBoard);
    findShot(shoot);
    findRandShot(comShot);
    //if userHitMiss is true (there was a hit), then the hit counter will be added by 1
    if(userHitMiss(comBoard,shoot)){
      userHit++;
    }
    //if userHitMiss is true (there was a hit), then the hit counter will be added by 1
    if(comHitMiss(userBoard, comShot)){
      computerHit++;
    }
  } while(userHit != 17 && computerHit != 17);
       
  //Ending game message is displayed here
    if(userHit == 17 && computerHit != 17){
      System.out.println("\nYou win.");
      System.out.println("\nFinal look at Boards");
      printUserBoard(userBoard);
      printUserBoard(comBoard);
    }
    else if(userHit != 17 && computerHit == 17){
      System.out.println("\nComputer wins");
      System.out.println("\nFinal look at Boards");
      printUserBoard(userBoard);
      printComBoard(comBoard);
    }
  }
 
  public static void userInterface(char[][] userBoard){    
  //1st Procedure: In this procedure, the user interface is activated
  //This means the user picks their ship size, starting coordinates and ship orientation here
    
    int count = 0;
    while(count<5){
      //1.Take user input and see where they will place ship
      Scanner myScanner = new Scanner(System.in);
      System.out.println("Deploy your ships: ");
      System.out.println("Enter which ship size you want to enter (5,4,3,3,2)");
      int shipSize = myScanner.nextInt();
      while(shipSize<1||shipSize>=6){
        System.out.println("Invalid. Try again");
        shipSize = myScanner.nextInt();
      }
      //2. See what the ship orientation will be
      System.out.println("How would you like to place your ship");
      System.out.println("1.Horizontal 2. Vertical");
      int shipOrientation = myScanner.nextInt();
      while(shipOrientation<=0||shipOrientation>=3){
        System.out.println("Invalid. Try again");
        shipOrientation = myScanner.nextInt();
      }
      //PositionX(Row)/Y(Column) take the starting coordinate of each boat each time
      System.out.println("Enter your starting Coordinates:");
      System.out.println("Row:");
      int positionX = myScanner.nextInt();
      System.out.println("Column: ");
      int positionY = myScanner.nextInt();
      
      //The below statement is were out of bonds overlapping is considered
      //To work The below statment checks if FALSE(Ship goes out of bounds) = FALSE (this is the useroutofbounds scenerio)...
      // OR if FALSE (ship overlaps) = FALSE (this is the usership over lapping scenerio)
      //Then proceed to the code below (Reinput the starting coordinate)
      //Depending on shipOrientation, the value of the ships will be stored slightly differently
      
      if(shipOrientation==1){
        while(shipHorizontalInBound(positionX, positionY, shipSize)==false|| horizontalOverLap(userBoard, positionX, positionY, shipSize) == false){
          System.out.println("Pick new position X");
          positionX = myScanner.nextInt();
          System.out.println("Pick new position Y");
          positionY = myScanner.nextInt();
        }
        //If ship is horizontal this is where its values are stored
        userHorizontalShip(userBoard, shipSize, positionX, positionY);
      }
     
      else if(shipOrientation==2){
        while(shipVerticalInBound(positionX, positionY, shipSize)==false||verticalOverLap(userBoard, positionX, positionY, shipSize) == false){
          System.out.println("Pick new position X");
          positionX = myScanner.nextInt();
          System.out.println("Pick new position Y");
          positionY = myScanner.nextInt();
        }
        //If ship is vertical this is where its values are stored
        userVerticalShip(userBoard, shipSize, positionX, positionY);
      }  
      count++;
    }
   
  }
 
  public static void initiateUserBoard(char[][] userBoard){
    //2nd Procedure: In this procedure, the userBoard is initilized and begins having a '.'
    //Dots repersent that a position hasen't been tried yet
    //This is not what Prints the board! it only initializes it
    
    for(int i = 0; i<10;i++){
      for(int j =0;j<10;j++){
        userBoard[i][j] = '.';
      }
    }    
  }
 
  public static void printUserBoard(char[][] userBoard){
    //3rd Procedure: In this procedure, the userBoard is printed to the screen
    //Each time a position on the grid is changed, the printBoard will recognize the change and print it out
    
    System.out.println();
    System.out.println("\tYour Board");
    System.out.println("  0 1 2 3 4 5 6 7 8 9");
    for(int i = 0; i<10;i++){
      System.out.print((i)+ "");
    for(int j =0;j<10;j++){
      System.out.print(" " + userBoard[i][j]);
    }
    System.out.println();
  }
  }
  
  public static boolean horizontalOverLap(char[][] userBoard, int positionX, int positionY, int shipSize){
    //4th method: In this procedure we check for horizontal over lap
    //To do this, we set our boolean value to be false and only change it (make it true) when user has no overlap
    //otherwise the value will return false and user will have to input new coordinates
    
    boolean checker = false;  
    for(int i = positionX; i<=positionX; i++){
      for(int j=positionY; j<positionY + shipSize; j++){
        if(userBoard[i][j]=='.'){
          checker = true;
        }
        else{
          System.out.println("Your ship is overlapping with another ship"); 
          break;
        }
      }  
    }
    return checker;
   }
  
  public static boolean verticalOverLap(char[][] userBoard, int positionX, int positionY, int shipSize){
     //5th method: In this procedure we check for vertical over lap
    //To do this, we set our boolean value to be false and only change it (make it true) when user has no overlap
    //otherwise the value will return false and user will have to input new coordinates
    
    boolean checker = false;  
    for(int i = positionX; i<positionX+shipSize; i++){
      for(int j=positionY; j<=positionY; j++){
        if(userBoard[i][j]=='.'){
          checker = true;
        }
        else{
          System.out.println("Your ship is overlapping with another ship");
          break;
        }
      }  
    }
    return checker;
  }
    
   
  public static boolean shipVerticalInBound(int positionX, int positionY, int shipSize){
    //6th method: In this procedure we check whether ship is VERTICALLY in bounds or out
    //To do this we set boolean to be false and only change it to ture if code is in bound
    //If boolean stays false, user is asked to find a new point
    
    boolean check = false;
    //Subtract -1 to account for the inital value
    if(positionX + shipSize-1<10 && (positionY<=10 && positionY>=0)){
     check = true; //Checking if its a valid position
    }
    else{
     System.out.println("Ship goes out of bounds. Re pick starting coordinates");
    }
    return check;    
  }
 
   public static boolean shipHorizontalInBound(int positionX, int positionY, int shipSize){
     //7th method: In this procedure we check whether ship is HORIZONTALLY in bounds or out
     //To do this we set boolean to be false and only change it to ture if code is in bound
     //If boolean stays false, user is asked to find a new point   
     
     boolean check = false;
     if((positionX<=10&&positionX>=0) && positionY + shipSize-1<10){
       check = true; //Checking if its a valid position
     }
     else{
       System.out.println("Ship goes out of bounds. Re pick starting coordinates");
     }
     return check;    
  }
 
  public static void userHorizontalShip(char[][] userBoard, int shipSize, int positionX,int positionY){
    //8th Procedure: In this procedure, based on the ship size, orientation (Horizontal) and user starting pt...
    //The board stores all of the ships coordinates and later prints it in the printUserBoard method
    
    for(int i=positionX;i<=positionX;i++){
      for(int j = positionY;j<shipSize+positionY;j++){
      userBoard[i][j] = '0';      
      }
    }
  }
 
  public static void userVerticalShip(char[][] userBoard, int shipSize, int positionX,int positionY){
    //9th Procedure: Just like the above procedure, based on the ship size, orientation (vertical) and user starting pt...
    //The board stores all of the ships coordinates and later prints it in the printUserBoard method
    
    for(int i =positionX;i<shipSize+positionX;i++){
      for(int j =positionY;j<=positionY;j++){
        try{
          userBoard[i][j] = '0';  
        } catch(ArrayIndexOutOfBoundsException e){
           
           }
      }    
    }
  }
     
  public static void initiateComBoard(char[][] comBoard){
    //10th Procedure: In this procedure, the userBoard is initilized and begins having a '.'
    //Dots repersent that a position hasen't been tried yet
    //Like the userBoard, this only INITIALIZES the board and does not print the board
    
    for(int i = 0; i<10;i++){
      for(int j =0;j<10;j++){
        comBoard[i][j] = '.';
      }
    }
  }
 
  public static void comInterface(char[][] comBoard){
    //11th Procedure: In this procedure, the comBoard randomizes the ship size, orientation and starting coordinates
    //In order to ensure Computer generates the proper amount of boats, I have made variables for each ship and account for...
    //each ships boat orientation
    
    Random rand = new Random();
    int boatSize1 = 5;
    int boatSize2 = 4;
    int boatSize3 = 3;
    int boatSize4 = 3;
    int boatSize5 = 2;
    int boatOrientation1 = rand.nextInt(2);
    int boatOrientation2 = rand.nextInt(2);
    int boatOrientation3 = rand.nextInt(2);
    int boatOrientation4 = rand.nextInt(2);
    int boatOrientation5 = rand.nextInt(2);
    
    //NOTE: This is where the randomized storing coordinates for comBoat are held
    //It may be possible they all line up in one row or column but they are not overlapping, just printing side by side
    //each number besides the row indicates what ship its for
    
    int randRow1 = rand.nextInt(2);
    int randColumn1 = rand.nextInt(2);
    
    //To ensure there is no overlap, I sometimes give each ship a range that it should fit in For a few boats
    //I.E. For ship 2, I want its row coordinate to be 3-4 and column to also be 3-4
    //Therefore I use the formula ((Max-Min)+1)+Min to make it this range
    
    int randRow2 = rand.nextInt((4-3)+1) + 3;
    int randColumn2 = rand.nextInt((4-3)+1) + 3;
    
    int randRow3 = rand.nextInt((7-6)+1)+6;
    int randColumn3 = rand.nextInt((7-6)+1)+6;
    
    int randRow4 = rand.nextInt((7-6)+1)+6;
    int randColumn4 = rand.nextInt(2);
    
    int randRow5 = rand.nextInt((8-7)+1)+7;
    int randColumn5 = rand.nextInt((4-3)+1) + 3;
    
    //NOTE: Below is where I STORE the computer ships and where I account for orientation
    //To do this, if oriention == 0 (horizontal) or orientation == 1 (vertical)
    //Then each computer ship is stored accordingly
    //When storing my values, I set wherever the ship coordinates are to be 0
    
    //EXTRA NOTE: I purposely stored/printed the 0 icon on computer ships so it would be easy to identify where they are
     
    //Below accounts for ship 1 orientation
    if(boatOrientation1==0){
      for(int i=randRow1;i<=randRow1;i++){
        for(int j = randColumn1;j<boatSize1+randColumn1;j++){
          comBoard[i][j] = '0';      
        }
      }
    }

    else if(boatOrientation1==1){
       for(int i =randRow1;i<boatSize1+randRow1;i++){
        for(int j =randColumn1;j<=randColumn1;j++){
          try{
            comBoard[i][j] = '0';  
          } catch(ArrayIndexOutOfBoundsException e){
           
          }
        }    
      }       
    }
    
    //Below accounts for ship 2 orientation
    if(boatOrientation2 == 0){
     for(int i=randRow2;i<=randRow2;i++){
        for(int j = randColumn2;j<boatSize2+randColumn2;j++){
          comBoard[i][j] = '0';      
        }
      }
    }
    
    else if(boatOrientation2 == 1){
      for(int i =randRow2;i<boatSize2+randRow2;i++){
        for(int j =randColumn2;j<=randColumn2;j++){
          try{
            comBoard[i][j] = '0';  
          } catch(ArrayIndexOutOfBoundsException e){
           
          }
        }    
      }   
    }
    
    //Below code accounts for ship 3 orientation
    if(boatOrientation3 == 0){
      for(int i=randRow3;i<=randRow3;i++){
        for(int j = randColumn3;j<boatSize3+randColumn3;j++){
          comBoard[i][j] = '0';      
        }
      }     
    }
    
    else if(boatOrientation3==1){
      for(int i =randRow3;i<boatSize3+randRow3;i++){
        for(int j =randColumn3;j<=randColumn3;j++){
          try{
            comBoard[i][j] = '0';  
          } catch(ArrayIndexOutOfBoundsException e){
           
          }
        }    
      }    
    }
    
    //Below code accounts for ship 4 orientation
    if(boatOrientation4==0){
       for(int i=randRow4;i<=randRow4;i++){
        for(int j = randColumn4;j<boatSize4+randColumn4;j++){
          comBoard[i][j] = '0';      
        }
      }
    }
    
    else if(boatOrientation4==1){
       for(int i =randRow4;i<boatSize4+randRow4;i++){
        for(int j =randColumn4;j<=randColumn4;j++){
          try{
            comBoard[i][j] = '0';  
          } catch(ArrayIndexOutOfBoundsException e){
           
          }
        }    
      }    
    }
    
    //Below code accounts for ship 5 orientation
    if(boatOrientation5==0){
      for(int i =randRow5;i<boatSize5+randRow5;i++){
        for(int j =randColumn5;j<=randColumn5;j++){
          try{
            comBoard[i][j] = '0';  
          } catch(ArrayIndexOutOfBoundsException e){
           
          }
        }    
      } 
    }
    
    else if(boatOrientation5==1){
       for(int i =randRow5;i<boatSize5+randRow5;i++){
        for(int j =randColumn5;j<=randColumn5;j++){
          try{
            comBoard[i][j] = '0';  
          } catch(ArrayIndexOutOfBoundsException e){
           
          }
        }    
      }    
    }
    
  }
 
  public static void printComBoard(char[][] comBoard){
    //12th Procedure: In this procedure, the comBoard is printed
    //Each time to comBoard changes, the comBoard print will also reflect those changes
    
    System.out.println();
    System.out.println("\tEnemy Board");
    System.out.println("  0 1 2 3 4 5 6 7 8 9");
    for(int i = 0; i<10;i++){
      System.out.print((i)+ "");
    for(int j =0;j<10;j++){
      System.out.print(" " + comBoard[i][j]);
    }
    System.out.println();
  }  
  }
 
  public static void findShot(int[] shoot){
    //13th Procedure: In this procedure, the procedure allows user to input where they want to hit on comBoard
    //To do this, the shoot array is used to hold the row (shoot[0]) and column (shoot[1])
    
    System.out.println("Your turn: Enter your coordinates");
    Scanner myScanner = new Scanner(System.in);
    System.out.print("Row: ");
    shoot[0] = myScanner.nextInt();
    while(shoot[0]>10||shoot[0]<-1){
      System.out.println("Invalid. Try again");
      shoot[0] = myScanner.nextInt();
    }    
    System.out.print("Column: ");
    shoot[1] = myScanner.nextInt();
    while(shoot[1]>10||shoot[1]<-1){
      System.out.println("Invalid. Try again");
      shoot[1] = myScanner.nextInt();
    }    
    System.out.println("You attacked " + shoot[0] + "," + shoot[1] + " position");
  }
   
  public static void findRandShot(int[] comShot){
      //14th Procedrure: In this procedure, the computers shot is randomized from 0-9
      //The shot will be stored in the comShot array with comShot[0] being row and comShot[1] being column
    
      Random rand = new Random();
      System.out.println("Computers Turn:");
      do{
        comShot[0] = rand.nextInt(10);
        comShot[1] = rand.nextInt(10);
      } while((comShot[0]<-1||comShot[0]>=10)||(comShot[1] < -1 || comShot[1]>=10));
     System.out.println("Computer attacked " + comShot[0] +  "," + comShot[1] + " position");          
  }
 
  public static boolean userHitMiss(char[][] comBoard, int[] shoot){
    //15th Procedure: In this procedure, we are checking if the user shot inputed has a 'O' in the comBoard
    //If it does then it will update it as a hit (X), otherwise it'll be a miss (*)  
    
    if(comBoard[shoot[0]][shoot[1]]=='0') {
            System.out.println("Computer Ship hit at " + shoot[0] + "," + shoot[1] + " position");
            comBoard[shoot[0]][shoot[1]] = 'X';
            return true;
           }
       else if (comBoard[shoot[0]][shoot[1]]=='-'){
         comBoard[shoot[0]][shoot[1]] = '*';
       }
 
       return false;
  }
 
  public static boolean comHitMiss(char[][] userBoard, int[] comShot){
    //16th Procedure: In this procedure, we are checking if the com shot inputed has a 'O' in the userBoard
    //If it does then it will update it as a hit (X), otherwise it'll be a miss (*)
    
    if(userBoard[comShot[0]][comShot[1]]=='0') {
            System.out.println("User Ship hit at " + comShot[0] + "," + comShot[1] + " position");
            userBoard[comShot[0]][comShot[1]] = 'X';
            return true;
           }
       else if(userBoard[comShot[0]][comShot[1]]!='0'){
         userBoard[comShot[0]][comShot[1]] = '*';
       }    
       return false;
  }
 
 
}