//PLAN: My goal is to use inheritance to have the player class be the only ones who have an energy limit
//NOTE: Currently there is no inheritnace in actual code so need to include the player and enemy classes

public class Player extends Pokemon{
 
  private int energy = 50;
  
  public Player(String data){
    super(data);
  }
  
  public int getEnergy(){
    return energy;
  }
  
  public void setEnergy(int x){
    energy = x;
  }
  
}