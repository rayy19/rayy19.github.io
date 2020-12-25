/*
* Date: March 7, 2020
* Name: Rayyan Amir
* Teacher: Mr. Ho
* Description: Nim Game Assignment
*/

//P1: Start & initilize Code
import java.util.Scanner;
import java.util.Random;
class NimGame{
  public static void main(String[] args){
    int total_items = 0;
    int turn = 0;
    
//P2: Create Name System
    Scanner myScanner = new Scanner (System.in);
    System.out.println("Welcome to Nim Game");
    System.out.println("User 1, enter your name:");
    String user_one = myScanner.nextLine();
    System.out.println("User 2, enter your name:");
    String user_two = myScanner.nextLine();
    
//P3: Create Pile Randomizer
    Random rand = new Random(); 
    int pile_a = rand.nextInt(9) + 1;
    int pile_b = rand.nextInt(9) + 1;
    int pile_c = rand.nextInt(9) + 1;
    
//P4: Create the total items counter
    total_items = pile_a + pile_b + pile_c;
 
    while (true){
//P5: User 1 Interface & Scenerios are below  
    if(turn %2 == 0){
     int temp_a = pile_a;
     int temp_b = pile_b;
     int temp_c = pile_c;
     
     /* This is the code outputting the # of items in each pile in columns.
     *It does this by taking the intial values of pile a b and c and printing each item in 1 row 
     *Once all three piles items have been outputted in the row, the println(""); function at the bottom of this code skips to the nextline
     *where the pile items are outputted once again. The code will do this until the "temp" values of the piles are equal to 0.
     */
     while(temp_a>0 || temp_b>0||temp_c>0){
       if(temp_a>0){
        System.out.print("*");
        temp_a--;
       } else{
        System.out.print(" "); 
       }
       if(temp_b>0){
        System.out.print("*");
        temp_b--;
       } else{
        System.out.print(" "); 
       }
       if(temp_c>0){
        System.out.print("*");
        temp_c--;
       } else{
        System.out.print(" "); 
       }
       System.out.println("");
     }    
      System.out.println("A" + "B" + "C");
      
//User 1 interface: Picking a pile     
      System.out.println(user_one + "," + " Choose a pile:");
      String reader = myScanner.nextLine();
      while (pile_a <= 0 && reader.equals("A")){
        System.out.println("Pile A is taken. Try another pile.");
        reader = myScanner.nextLine();
      }
      while (pile_b <= 0 && reader.equals("B")){
         System.out.println("Pile B is taken. Try another pile.");
         reader = myScanner.nextLine();
      }
      while (pile_c <= 0 && reader.equals("C")){
        System.out.println("Pile C is taken. Try another pile.");
        reader = myScanner.nextLine();
      }
 
// Scenerio 1: user 1 picks pile a and removes a #
      if (reader.equals("A")){
        System.out.println("How many to remove from pile A");
        int pile_1 = myScanner.nextInt();
        while ((pile_1<=0) || (pile_1>pile_a)){
        System.out.println("Illegal Move. Try Again.");
        pile_1 = myScanner.nextInt();
        } 
        int p1 = pile_a;
        pile_a = pile_a - pile_1;
        total_items = pile_a + pile_b + pile_c;
        while ((pile_a<=0) && (pile_b<=0) && (pile_c<=0)){
        System.out.println("You must leave behind 1 item. Try again.");
        pile_1 = myScanner.nextInt();
        pile_a = p1;
        pile_a = pile_a - pile_1;
        total_items = pile_a + pile_b + pile_c;
        } 
        turn++;
      }
      
// Scenerio 2: User 1 picks pile b and removes a #
      else if (reader.equals("B")){
        System.out.println("How many to remove from pile B");
        int pile_2 = myScanner.nextInt();
        while ((pile_2<=0) || (pile_2>pile_b)){
        System.out.println("Illegal Move. Try Again.");
        pile_2 = myScanner.nextInt();
        } 
        int p2 = pile_b;
        pile_b = pile_b - pile_2;
        total_items = pile_a + pile_b + pile_c;
        while ((pile_a<=0) && (pile_b<=0) && (pile_c<=0)){
        System.out.println("You must leave behind 1 item. Try again.");
        pile_2 = myScanner.nextInt();
        pile_b = p2;
        pile_b = pile_b - pile_2;
        total_items = pile_a + pile_b + pile_c;
        } 
        turn++;
      }
      
// Scenerio 3: User 1 picks pile c & removes a #
      else if (reader.equals("C")){
        System.out.println("How many to remove from pile C");
        int pile_3 = myScanner.nextInt();
        while ((pile_3<=0) || (pile_3>pile_c)){
        System.out.println("Illegal Move. Try Again.");
        pile_3 = myScanner.nextInt();
        } 
        int p3 = pile_c;
        pile_c = pile_c - pile_3;
        total_items = pile_a + pile_b + pile_c;
        while ((pile_a<=0) && (pile_b<=0) && (pile_c<=0)){
        System.out.println("You must leave behind 1 item. Try again.");
        pile_3 = myScanner.nextInt();
        pile_c = p3;
        pile_c = pile_c - pile_3;
        total_items = pile_a + pile_b + pile_c;
        } 
        turn++;
      }    
 // Part 6: Exiting the game (the end) 
      if (total_items == 1){
      System.out.println(user_two + ", " + "you must take the last remaining counter, " + "so you lose. " + user_one + " wins!");
      break;
      }
    }
//P5: User 2 interface & Scenerios are below
    else if (turn%2 != 0){
     int temp_a = pile_a;
     int temp_b = pile_b;
     int temp_c = pile_c;
    
     // Explain How this loop is working     
     while(temp_a>0 || temp_b>0||temp_c>0){
       if(temp_a>0){
        System.out.print("*");
        temp_a--;
       } else{
        System.out.print(" "); 
       }
       if(temp_b>0){
        System.out.print("*");
        temp_b--;
       } else{
        System.out.print(" "); 
       }
       if(temp_c>0){
        System.out.print("*");
        temp_c--;
       } else{
       System.out.print(" "); 
       }
       System.out.println("");
     }
       System.out.println("A" + "B" + "C");
      
//User 2 interface: Picking a pile  
      System.out.println(user_two + "," + " Choose a pile:");
      String reader = myScanner.nextLine();
      
      while (pile_a <= 0 && reader.equals("A")){
        System.out.println("Pile A is taken. Try another pile.");
        reader = myScanner.nextLine();
      }
      while (pile_b <= 0 && reader.equals("B")){
         System.out.println("Pile B is taken. Try another pile.");
         reader = myScanner.nextLine();
      }
      while (pile_c <= 0 && reader.equals("C")){
        System.out.println("Pile C is taken. Try another pile.");
        reader = myScanner.nextLine();
      }
     
// Scenerio 1: user 2 picks pile a and removes a #
      if (reader.equals("A")) {
        System.out.println("How many to remove from pile A");
        int pile_4 = myScanner.nextInt();
        while ((pile_4<=0) || (pile_4>pile_a)){
        System.out.println("Illegal Move. Try Again.");
        pile_4 = myScanner.nextInt();
        }
        int p4 = pile_a;
        pile_a = pile_a - pile_4;
        total_items = pile_a + pile_b + pile_c;
        while ((pile_a<=0) && (pile_b<=0) && (pile_c<=0)){
        System.out.println("You must leave behind 1 item. Try again.");
        pile_4 = myScanner.nextInt();
        pile_a = p4;
        pile_a = pile_a - pile_4;
        total_items = pile_a + pile_b + pile_c;
        } 
        turn++;
      }
// Scenerio 2: User 2 picks pile b and removes a #
      else if (reader.equals("B")){
        System.out.println("How many to remove from pile B");
        int pile_5 = myScanner.nextInt();
        while ((pile_5<=0) || (pile_5>pile_b)){
        System.out.println("Illegal Move. Try Again.");
        pile_5 = myScanner.nextInt();
        }
        int p5 = pile_b;
        pile_b = pile_b - pile_5;
        total_items = pile_a + pile_b + pile_c;
        while ((pile_a<=0) && (pile_b<=0) && (pile_c<=0)){
        System.out.println("You must leave behind 1 item. Try again.");
        pile_5 = myScanner.nextInt();
        pile_b = p5;
        pile_b = pile_b - pile_5;
        total_items = pile_a + pile_b + pile_c;
        } 
        turn++;
      }
// Scenerio 3: User 2 picks pile c & removes a #
      else if (reader.equals("C")){
        System.out.println("How many to remove from pile C");
        int pile_6 = myScanner.nextInt();
        while ((pile_6<=0) || (pile_6>pile_c)){
        System.out.println("Illegal Move. Try Again.");
        pile_6 = myScanner.nextInt();
        } 
        int p6 = pile_c;
        pile_c = pile_c - pile_6;
        total_items = pile_a + pile_b + pile_c;
        while ((pile_a<=0) && (pile_b<=0) && (pile_c<=0)){
        System.out.println("You must leave behind 1 item. Try again.");
        pile_6 = myScanner.nextInt();
        pile_c = p6;
        pile_c = pile_c - pile_6;
        total_items = pile_a + pile_b + pile_c;
        } 
        turn++;
      }
// Part 6: Exiting the game (the end) 
      if (total_items == 1){
        System.out.println(user_one + ", " + "you must take the last remaining counter, " + "so you lose. " + user_two + " wins!");
        break;
    }
   }
  }
 }
}