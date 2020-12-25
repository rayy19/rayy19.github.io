/* Rayyan Amir
 * December 20, 2020
 * BlackJack: Card Class
 */
 
public class Card{
  
  //OBJECT VARIABLE
  private int suitType;
  private int rankType;

  //CONSTRUCTOR METHOD
  //it takes a rank and suit as its parameter  
  public Card(int inputRank, int inputSuit){
   rankType = inputRank;
   suitType = inputSuit;
  }
  
  //OBJECT METHODS
  //In this method, I return the suit of the cards
  public String suit(){
    if(suitType==1){
      return "Spades";
    }
    else if(suitType==2){
     return "Hearts"; 
    }
    else if(suitType==3){
     return "Clubs"; 
    }
    else if(suitType==4){
     return "Diamonds"; 
    }
    return "";
  }
    
  //In this method, I return the rank of the card
  public String rank(){
    if(rankType==1){
     return "Ace"; 
    }
    else if(rankType==2){
     return "Two"; 
    }
    else if(rankType==3){
     return "Three"; 
    }
    else if(rankType==4){
     return "Four"; 
    }
    else if(rankType == 5){
     return "Five"; 
    }
    else if(rankType == 6){
     return "Six"; 
    }
    else if(rankType==7){
     return "Seven"; 
    }
    else if(rankType ==8){
     return "Eight"; 
    }
    else if(rankType==9){
     return "Nine";
    }
    else if(rankType==10){
     return "Ten"; 
    }
    else if(rankType==11){
     return "Jack"; 
    }
    else if(rankType==12){
     return "Queen"; 
    }
    else if(rankType==13){
     return "King"; 
    }
    return "";
  }
  
  //In this method, I return the rank and suit of the card
  public String toString(){
   return rank() + " of " + suit(); 
  }
  
  //In this method, I return the rank type of the card
  //This method is important because I udse it to determine the card value in my Hand class
  public int rankNum(){
   return rankType; 
  }
  
}