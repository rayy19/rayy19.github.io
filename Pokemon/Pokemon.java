import java.util.*;

//PLAN: This class is the pokemon constructor it is what is used to make the pokemon
//This class esentailly holds all th eimportant attributes of the pokemon (i.e. health)

//NOTE: When adding weakness and resistance, add fields called weakness and resistance and intilize them in class
//(You might have to add the resistance to the csv file)

public class Pokemon{
  
  //Fields
  public static String[] types = {"Fire", "Water", "Grass"};  
  private String pkmName;
  private int hp;
  private int maxHp;
  private String pkmType;
  private int numAtks;
  public ArrayList<Move>moves;
  private int defense;
  
  //Constructor
  //The idea here is that the data paramater is a string reading from all of the file
  //It then splits the data and initializes each characteristic (i.e. gives the pkmn name)
  public Pokemon(String data){
   String[] dataArr = data.split(",");
   pkmName = dataArr[0];
   hp = Integer.parseInt(dataArr[1]);
   maxHp = hp;
   pkmType = dataArr[2];
   numAtks = Integer.parseInt(dataArr[4]);
   defense = Integer.parseInt(dataArr[17]);
   
   moves = new ArrayList<Move>();
   for(int i=0;i<numAtks;i++){
    Move temp = new Move(dataArr, i*3);
    moves.add(temp);
   }
  }
  
  //Getters and Setters are below
  
  //Gets pokemon game
  public String getPkmName(){
   return pkmName; 
  }
  
  //Gets pokemon Hp
  public int getHp(){
   return hp; 
  }
  //Sets Pokemon Hp (will prolly be useful when doing items)
  public void setHp(int newHp){
    hp = newHp;
  }
  
  //returns the maxHp(will also prolly be important when doing items)
  public int getMaxHp(){
   return maxHp; 
  }
  
  //Gets all the moves associated with a pokemon
  public String[] getMove(int index){
    return (moves.get(index)).getAtk();
  }
  
  //Returns just one move Name
  public String getMoveName(int index){
   return (moves.get(index)).getAtkName();
  }
  
  //Determines the # of moves a pkmn has(all of them have 4)
  public int getNumAtks(){
   return numAtks; 
  }
  
  //Determines defense stat(may also be useful when doing items)
  public int getDefense(){
   return defense; 
  }
  
  //Used to get the pkm type (Will be important when doing weakness)
  public String getType(){
   return pkmType; 
  }
  
  //NOTE: Will be used to get the move enrgy but ignore this method for now
  public int getMoveEnergy(int index){
    return (moves.get(index)).getEnergy();
  }
}