/* 
    Player.java
    Group: Zhiyang and Rayyan
    Teacher: Mr Chu
    Date: January 24, 2021
    Assignment: Final Project (Player Class)
*/

public class Player extends Item {
    // Fields
    private int totalPkmEnergy = 50;

    // Constructor
    public Player(String data){
        super(data); 
    }
        
    // Getters
    public int getTotalPkmEnergy(){
        return totalPkmEnergy;
    }
        
    // Setters
    public void setPkmEnergy(int x){
        totalPkmEnergy = x;
    }
       
    // Method for toString
    public String toString(){
        String temp = getPkmName();
        this.getPkmName();
        return temp;
    }
}