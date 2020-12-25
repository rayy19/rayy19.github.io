/* Rayyan Amir
 * December 20, 2020
 * BlackJack: Hand Class
 */
 

import java.util.*;

public class Hand{
  
  //OBJECT VARIABLE
  private ArrayList<Card> cardList;
  private int totalValue;
  private boolean isDealer;
  
  //CONSTRUCTOR METHOD
  public Hand(boolean inputDealer){
    cardList = new ArrayList<Card>(); 
    isDealer = inputDealer; 
  }
  
  //OBJECT METHODS
  //Below I use the draw method from my deck class (acts like the parameter) to add a card into my hand
  public void addCard(Card a){
    cardList.add(a); 
  }
  
  //The below method will be used when a round of blackjack is over...
  //to reset dealer and player hand
  public void removeAll(){
    cardList.clear();
  }
  
  //Below method is used to print out the card object
  public String toString(){
    String temp = "";
    for(int i=0;i<this.cardList.size();i++){
      temp += "\n" + cardList.get(i).toString();
    }
    return temp;
  }
  
  //Below is where I determine my hand value
  //To do this, I correspond the card rank to its value (i.e. rank 2 = value 2)
  //In the case of (Jack,King etc.), I set their value to be 10
  //For aces, to determine wheter its 1 or 11 I first determine how many aces I have...
  //I then check to see what the current total is (if currenttotal>10, ace = 1)
  //Otherwise ace will equal 11
  public int value(){
    int totalValue = 0;
    int aces = 0;
    for(int i=0;i<cardList.size();i++){
      int tempVal = cardList.get(i).rankNum();
      if(tempVal==10 || tempVal==11||tempVal==12||tempVal==13){
        totalValue += 10; 
      }
      else if(tempVal==1){
        aces+=1; 
      }
      else{
        totalValue += tempVal;
      }
    }
    
    for(int j=0;j<aces;j++){
      if(totalValue>10){
        totalValue += 1; 
      }
      else{
        totalValue += 11; 
      }
    }
    return totalValue;
  }
  
  //Below is where I hide 1 card for computer and show all for user
  //To do this, if we are looking at the dealer hand(true), we will print...
  //Hidden and then show the rest of the hand whereas if we are in player deck(false)...
  //We will show all cards
  public void printHand(boolean findDealer){
    for(int c=0;c<cardList.size();c++){
      if(c==0 && findDealer==true){
        System.out.println("\t[Hidden]"); 
      }
      else{
        System.out.println("\tCard: " + cardList.get(c).toString()); 
      }
    }
  }
  
}