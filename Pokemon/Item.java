/* 
    Item.java
    Group: Zhiyang and Rayyan
    Teacher: Mr Chu
    Date: January 24, 2021
    Assignment: Final Project (Item Class)
*/

public class Item extends Pokemon{
    // Fields
    private int hp;
    private int maxHp;
    private int defense;

    // Constructor
    public Item (String data) {
        super(data);

        String[] dataArr = data.split(",");
        hp = Integer.parseInt(dataArr[1]);
        maxHp = hp;
        defense = Integer.parseInt(dataArr[18]);
    }

    // Getters
    public int getHp(){
        return hp;
    }
    public int getMaxHp(){
        return maxHp; 
    }
    public int getDefense(){
        return defense; 
    }

    // Setters
    public void setHp(int newHp){
        hp = newHp;
    }
    public void setDefense(int def) {
        defense = def;
    }
}
    

