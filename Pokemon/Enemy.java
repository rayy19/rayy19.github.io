/* 
    Enemy.java
    Group: Zhiyang and Rayyan
    Teacher: Mr Chu
    Date: January 24, 2021
    Assignment: Final Project (Enemy Class)
*/

public class Enemy extends Item {
    // Constructor
    public Enemy(String data){
        super(data);
    }
      
    // Method for toString
    public String toString(){
        String temp = getPkmName();
        this.getPkmName();
        return temp;
    }
}
