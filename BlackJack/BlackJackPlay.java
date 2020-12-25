/* Rayyan Amir
 * December 20, 2020
 * BlackJack Game: BlackJack Class
 */

import java.util.*;

public class BlackJackPlay{
  
  public static void main(String[] args){
    //Main Method: In my Main method, I run the user interface for blackjack
    //The user interface comprises of three parts; the bet method, the user turn, and the continue option
    
    //Below is where I make my user interface/game variables 
    //NOTE: the amount is in an aray because I use the array to pass by refrence the changed values
    
    Scanner myScanner = new Scanner(System.in);
    int[] amount = new int[1];
    amount[0] = 100;
    boolean playGame = true;

    System.out.println("Welcome to BlackJack");
    
    //In this while loop, the user interface continues as long as playGame is true and user has more than $0   
    while(playGame && amount[0]>0){   
      
      //Below is where I make more game variables
      //As the name apply, each game variable serves an important role
      //(I.e. the deck variable is important because that is where I draw my cards from)
      
      Deck deck = new Deck();
      deck.shuffle();
      Hand playerHand = new Hand(false);
      Hand dealerHand = new Hand(true);
      int bet = getBet(amount, myScanner);
        
      //Below is where I add two cards for the dealer and player each round
      playerHand.addCard(deck.draw());
      playerHand.addCard(deck.draw());
      
      dealerHand.addCard(deck.draw());
      dealerHand.addCard(deck.draw());
      
      //Below is where I print the dealer and player hand     
      System.out.println("Player Hand: ");
      playerHand.printHand(false);
      System.out.println("\tCurrent Value: " + playerHand.value());
      
      System.out.println("\nDealer Hand: ");
      dealerHand.printHand(true); 
      
      //Below is where the player turn method is run
      //NOTE: The player turn goes first and then is the dealer
      
      System.out.println("\nPlayer Turn: ");
      playerTurn(playerHand, dealerHand, deck, myScanner, amount, bet);
      
      //Below is where the dealer turn method is run
      //NOTE: This method only runs if the player turn didnt end the round
      //(I.e. if player didnt bust and decided to stand, then it'll run)
      
      if(playerHand.value()<21){
        System.out.println("\nDealer Turn: ");
        dealerTurn(playerHand, dealerHand, deck, amount, bet);
      }
      
      //If the amount drops below 0 after the round..
      //User will not be asked to continue the game and will be exited from loop
      
      if(amount[0]<=0){
        playGame = false;
        break;
      }
      
      //Below is the interface for where user is asked to continue
      //If user continues, the playGame will stay true and run
      //Otherwise, it'll turn false and user will exit loop
      
      System.out.println("\nRound over. (1)Play again or (2)end");
      int result = myScanner.nextInt();
      while (result>2||result<0){
       System.out.println("Invalid command. Select play again or end");
       result = myScanner.nextInt();
      }
      if(result==1){
       playGame = true; 
      }
      else if(result == 2){
       playGame = false; 
      }    
    }
    //Closing messages
    System.out.println("\n>You finished playing BlackJack");
    System.out.println(">You take home $" + amount[0]);  
  }
  
  public static int getBet(int[] amount, Scanner myScanner){
    //In this method, I get the bet amoutn from the 0
    //NOTE: If user inputs an invalid amount, then user will be asked to reinput a number
    System.out.println("\n>You have $" + amount[0]);
    System.out.println("How much would you like to bet");
    int bet = myScanner.nextInt();
    while(bet>amount[0]||bet<=0){
      System.out.println("Invalid Command");
      bet = myScanner.nextInt();
    }
    System.out.println("You bet $" + bet);
    return bet;
  }
  
  public static void playerTurn(Hand playerHand, Hand dealerHand, Deck deck, Scanner myScanner, int[] amount, int bet){
    //In this method, the user is asked wheter they want to hit or stand
    //Since a while true loop is used, the code will continue to run until it is broken out of
    //NOTE: To understand the possible options user has, I will refer to them as "Scenerios"
    
    while(true){       
      //SCENERIO 1: If user draws blackjack from their two cards, the loop will break
      if(playerHand.value()==21){
        System.out.println("\nYou win!");
        System.out.println("You got $" + bet);
        amount[0] = amount[0] + bet;
        break; 
      }
      else{
        //Below is where user is asked to hit or stand
        System.out.println("\nWould you like to (1)Hit or (2)Stand");
        int response = myScanner.nextInt();
        
        //The below if statement is if user hits
        //NOTE: In both scenerios that can occur, the player turn loop will be broken out of
        
        if(response==1){
          playerHand.addCard(deck.draw());
          System.out.println(">You drew a card");
          System.out.println("\nPlayer Hand: ");
          playerHand.printHand(false);
          System.out.println("\tCurrent Value: " + playerHand.value()); 
          
          //SCENERIO 2: If user draws a card and they go over 21, then they lose the round
          if(playerHand.value()>21){
            System.out.println("\n>Bust. Currently Valued at " + playerHand.value()); 
            System.out.println("You lost $" + bet);
            amount[0] = amount[0] - bet;
            System.out.println("You are now left with $" + amount[0]);
            break;
          }
          //SCENERIO 3: If user hits 21 after drawing a card they win
          else if(playerHand.value()==21){
            System.out.println("\n>You hit blackjack and win!");
            System.out.println("You got $" + bet);
            amount[0] = amount[0] + bet;
            System.out.println("You are now left with $" + amount[0]);
            break; 
          }
        }
        
        //The below statement is if user stands
        //NOTE: If user chooses to stand, the loop will be broken out of
        else if(response == 2){
          System.out.println(">You stand");
          break; 
        }
        //The below statement is run if user does not hit or stand
        else{
          System.out.println("You must hit or stand"); 
        }
      }    
    }
  }
  
  public static void dealerTurn(Hand playerHand, Hand dealerHand, Deck deck, int[] amount, int bet){  
    //In this method, the dealer can eithre hit or stand
    //Similar to player turn, since a while true loop is used, the code will continue to run...
    //until it is broken out of
    //NOTE: To understand the possible options dealer has, I will refer to them as "Scenerios"
    
    System.out.println("\nDealer Hand: ");
    dealerHand.printHand(true); 
      
    while(true){       
      //In the below statement, the dealer will continue to hit as long as their value is less than 17
      while(dealerHand.value()<17){
        dealerHand.addCard(deck.draw()); 
        System.out.println("\n>Dealer drew a card");
        System.out.println("\nDealer Hand: ");
        dealerHand.printHand(true);
      }
      
      //SCENERIO 1: If dealer hand becomes greater than 21, then dealer busts and loses the round
      if(dealerHand.value() > 21){
        System.out.println("\n>Dealer busted");
        System.out.println("You win the round and got $" + bet);
        amount[0] = amount[0] + bet;
        System.out.println("You are now left with $" + amount[0]);
        break;
      }
      //SCENERIO 2: If dealer hits black jack, then they win and round ends
      else if(dealerHand.value()==21){
        System.out.println("\n>Dealer hit BlackJack");
        System.out.println("Dealer wins the round and you lose $" + bet);
        amount[0] = amount[0] - bet;
        System.out.println("You are now left with $" + amount[0]);
        break;
      }
      //SCENERIO 3: If dealer value is equal or greater than player (the values will be less than 21)...
      //Then the dealer is considered the winner and round will end
      else if(dealerHand.value()>=playerHand.value()){
        System.out.println("\n>Dealer stands and has a value of " + dealerHand.value());
        System.out.println(">Therefore, his value is equal or greater than yours");
        System.out.println("Dealer wins the round and you lose $" + bet);
        amount[0] = amount[0] - bet;
        System.out.println("You are now left with $" + amount[0]);
        break;
      }              
    }    
  }
   
}