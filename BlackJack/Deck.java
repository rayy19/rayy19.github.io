/* Rayyan Amir
 * December 20, 2020
 * BlackJack: Deck Class
 */
 
import java.util.*;

public class Deck{
  
  //OBJECT VARIABLE
  private ArrayList<Card> cards;
  
  //CONSTRUCTOR METHOD
  public Deck(){
    cards = new ArrayList<Card>(); 
    
    for (int i = 1; i <= 4; i++)
    {
      for (int j = 1; j<=13; j++)
      {
        cards.add(new Card(j,i)); 
      }
    } 
  }
  
  //OBJECT METHODS
  //In the below method, I shuffle the deck
  //I do this by shuffling a tempDeck and then set the tempdeck to become the deck  
  public void shuffle(){  
    ArrayList<Card> tempDeck = new ArrayList<Card>();
    Random rand = new Random();
    int randomCardIndex = 0;
    int originalSize = cards.size();
    for(int i=0;i<originalSize; i++){
      //Generate Random Number b/t 1-52
      randomCardIndex = rand.nextInt((cards.size()-1)+1);
      tempDeck.add(cards.get(randomCardIndex));
      //Remove From Original Deck
      cards.remove(randomCardIndex);
    }
    cards = tempDeck;
  }
  
  //In the below method, I simply remove a card from the arraylist
  public Card removeCard(int i){
    return cards.remove(i); 
  }
  
  //The below method, keeps track of the deck size
  //in our case, it is used to keep track of the last card
  public int deckSize(){
    return cards.size(); 
  }
   
  //In the below method, I use the removeCard method to draw the card on top
  public Card draw(){ 
    return removeCard(deckSize()-1);
  }
  
}