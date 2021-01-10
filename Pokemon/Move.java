//PLAN: This class is used to get the moves the pokemon use

public class Move{

  //Fields
private String atkName;
private int dmg;
private int energy;
private String[] atk = new String[3];

//Constructor: The parameters are being used to get the pkmnmoves
//NOTE: The location parameter is used to determine the location of where the user wanted move is in the file

public Move(String[] tempStorage, int location){
 int numOfAtk = Integer.parseInt(tempStorage[4]);
 atkName = tempStorage[5+location];
 energy = Integer.parseInt(tempStorage[6+location]);
 dmg = Integer.parseInt(tempStorage[7+location]);
 atk[0] = atkName;
 atk[1] = ""+energy;
 atk[2] = ""+dmg;
}
  
//Gets all the possible move
public String[] getAtk(){
  return atk;
}

//Gets move name
public String getAtkName(){
  return atkName;
}

//Ignore Method for now but it gets the energy cost of the move
public int getEnergy(){
 return energy; 
}
  
}