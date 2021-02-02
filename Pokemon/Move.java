

class Move{
  
  //Fields repersenting pokemon attributes
  private String moveName;
  private int moveEnergyCost;
  private int moveDmg;
  //Attack attributes refers to the atack name, energy and damage of move
  //[0] = name, [1] = energy, [2] = damage
  private String[] moveAttributes = new String[3];
  
//How the constructor Works: The move takes two parameters moveData and location
//The moveData is the array that reads all the data from the file
//The location is used to find the index of where move name, energy and damage is located
//For example, the first moves appears at column 6, but by adding the location, we can get the 2nd move etc.
  
  public Move(String[] moveData, int location){
    int numOfAtks = Integer.parseInt(moveData[5]); //All pokemon have 4 moves
    
    //Below is where we store the attributes(are put into the array)
    moveName = moveData[6+location];
    moveEnergyCost = Integer.parseInt(moveData[7+location]);
    moveDmg = Integer.parseInt(moveData[8+location]);
    
    //Below we are storing the attribute into each index of the array
    moveAttributes[0] = moveName;
    //We are adding the comma since its a string array
    moveAttributes[1] = "" + moveEnergyCost; 
    moveAttributes[2] = ""+moveDmg;
  }
  
  //We are returning the whole array (aka all attributes)
  public String[] getMoveAttributes(){
   return moveAttributes; 
  }
  
  //Return move Name
  public String getMoveName(){
   return moveName; 
  }
  
  //Return move energy
  public int getMoveCost(){
   return moveEnergyCost; 
  }
  
  //Return how much damage move does
  public int getMoveDmg(){
    return moveDmg;
  }
  
}