/*
  Pokemon.java
  Group: Zhiyang and Rayyan
  Teacher: Mr Chu
  Date: January 24, 2021
  Assignment: Final Project (Pokemon Class)
*/

/// Imports everything
import java.util.*;

class Pokemon{
  
  //Fields
  public static String[] types = {"Fire","Water","Grass"};
  private String pkmName;
  private String pkmType;
  private int numAtks;
  public ArrayList<Move> moves;
  private String weakness;
  private String resistance;
  
  //Constructor
  public Pokemon(String data){
    
    String[] dataArr = data.split(",");

    pkmName = dataArr[0];
    pkmType = dataArr[2];
    resistance = dataArr[3];
    weakness = dataArr[4];
    numAtks = Integer.parseInt(dataArr[5]);
    
    moves = new ArrayList<Move>();

    for(int i=0;i<numAtks;i++){
      Move temp = new Move(dataArr, i*3);
      moves.add(temp);
    }
  }
  
  //Gets pokemon name
  public String getPkmName(){
   return pkmName; 
  }
  
  //Gets the move attributes a pokemon is about to use 
  //It does this by getting the move being used from the arraylist and then getting its attributes(getAtk)
  public String[] getMove(int index){
    return (moves.get(index)).getMoveAttributes();
  }
  
    //Returns just one move Name
  public String getMoveName(int index){
   return (moves.get(index)).getMoveName();
  }
  
  //Determines the # of moves a pkmn has(all of them have 4)
  public int getNumAtks(){
   return numAtks; 
  }
  
  //Used to get the pkm type (Will be important when doing weakness)
  public String getType(){
   return pkmType; 
  }
  
  //Gets resistance of pokemon
  public String getResistance(){
   return resistance; 
  }
  
  //Gets weakness of pokemon
  public String getWeakness(){
   return weakness; 
  }
  
  //Gets the energy of the move
  public int getMoveEnergy(int index){
    return (moves.get(index)).getMoveCost();
  }
}