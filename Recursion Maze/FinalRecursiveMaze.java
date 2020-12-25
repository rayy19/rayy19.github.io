/* Rayyan Amir
 * Recursive Maze Assignment
 * Teacher: Mr.Chu
 * October 23, 2020
 */

import java.util.*;
import java.io.*;


class FinalRecursiveMaze{
  
  public static void main(String[] args) throws Exception{
    //In my main method I will have the code continue until all of the 6 mazes have been run through and solved
    //To do this, I have used a while loop to loop through the 6 files and call those 6 files in methods each time
    
    System.out.println("NOTE: 0 repersents the solvable path\n");   
    System.out.print("Og Maze");
    System.out.print("\t" + "Solved or Unsolved Maze\n");
    
    //Below is my while loop that runs through the 6 files
    //Once it has gone through one file, it increments by one each time
    
    int counter = 1;
    while(counter<=6){
      String fileNames = "Maze"+counter+".txt";
      ArrayList<String> mazeReader = new ArrayList<String>();
      
      //NOTE: Since I don't originally know my 2d Array lengths, I return the...
      //"InitiateMaze" method into the 2d Arrays. This allows me to determine the size of all the mazes
      
      String[][] mazeGrid = initiateMaze(fileNames,mazeReader);
      String[][] solveGrid = initiateMaze(fileNames,mazeReader);
      System.out.println();
      
      //Note: Similar to the above code, since I need to know the starting pt,
      //I return the "startMaze" method into the below variables
      
      int startRow = startMaze(mazeGrid)[0];
      int startColumn = startMaze(mazeGrid)[1];
      
      //Below are the maze conditions
      //If the maze is able to be solved (aka solveMaze is true)...
      //then the msg it can be solved will show
      //Otherwise maze will be unsolvable
      
      if(solveMaze(mazeGrid,startRow,startColumn)){
        System.out.print(fileNames + " is Solvable and is shown on the right\n");
      }
      else{
        System.out.print(fileNames + " is not solvable\n");
      }
      
      //Below is where the method that displays the maze is being called  
      
      displayMaze(mazeGrid,solveGrid); 
      System.out.println();
      counter++;
    }
    
  }
  
  public static String[][] initiateMaze(String fileNames, ArrayList<String>mazeReader) throws Exception{
     //Method #1: In this method, I am initilizing the mazes each time and storing them into a string 2d array...
    //called mazeGrid (this 2d Array is in the main method and will be used as pass by refrence so other methods can use the same value)
    
    
    //Below is the line of code that will help me identify the rows and columns in each maze
    //To do this, I run the length of the maze file and each time I go over to a new line, I add a row...
    //and each time a value is split, its value will count towards a column
    //NOTE: I add the values of the maze to an arraylist(mazeReader) as I don't know the initial amount of values
    
    File mazeFile = new File(fileNames);
    Scanner myScanner = new Scanner(mazeFile);
    int row = 0;
    int column = 0;
    while(myScanner.hasNext()){
     String data = myScanner.nextLine();
     row++;
     String[] values = data.split("");
     mazeReader.add(data);
     column = values.length;  
    }
    myScanner.close();
    
    //Below is where I began to populate the array with values
    //To do this, I run the row length of the maze and insert the values inside of the mazeReader...
    //in each position of all rows of the maze
    
    String[][] maze = new String[row][column];
    for (int i =0;i<maze.length;i++){
     String getRow = mazeReader.get(i);     
     maze[i] = getRow.split("");
    }
    return maze;
  }
    
  public static void displayMaze(String[][] mazeGrid, String[][] solveGrid){
    //Method #2: Below is where I display the original maze and solution maze side by side
    //To do this, I use a simple nested for loop to print out the rows and columns of my 2 arrays
    //NOTE: To prevent them from printing as one, I space the two columns out by tabbing it (\t)
    
    for(int row =0; row<solveGrid.length;row++){
      for(int column =0; column<solveGrid[0].length;column++){
       System.out.print(solveGrid[row][column]); 
      }
      System.out.print("\t");
      for(int j = 0; j<mazeGrid[0].length;j++){
       System.out.print(mazeGrid[row][j]); 
      }
      System.out.println();
    }
  }
  
  public static int[] startMaze(String[][] mazeGrid){
      //Method #3: Below is the code that I use to determine where the starting pts of the maze are...
      //This is important to know because this will help the computer identify where to start and...
      //formulate a path that it can use to get to the end from the start
      
      //To find the starting length, I run the length of the maze and if at any pt the maze has an S...
      //That point will be stored within my array and will be returned into the startRow,startColumn variables... 
      //in my main method
    
      int[] startPt = new int[2];
      for(int findRow =0; findRow<mazeGrid.length;findRow++){
        for(int findColumn =0;findColumn<mazeGrid[findRow].length;findColumn++){
          if(mazeGrid[findRow][findColumn].equals("S")){
           startPt[0] = findRow;
           startPt[1] = findColumn;
           break;
          }
        }        
      }
     return startPt; 
    }
  
  public static boolean solveMaze(String[][] solveGrid, int startRow, int startColumn){
    //Method #4: Below is my code where I use recursion to solve the maze
    //To do this, I set my base case to be if I can reach the goal and have my recursive cases repeat themselves...
    //until they reach base case
    
    //The below if statement is used to make sure that the maze pts being checked are in bounds
    //If they are then the method will return true and will be good to go
    
    if(startRow>=0&& startRow<solveGrid.length && startColumn>=0 && startColumn<solveGrid[0].length){
      
      //This below if statement makes sure the computer stays on path (I.e. is not touching #) 
      if(solveGrid[startRow][startColumn].equals(".") || solveGrid[startRow][startColumn].equals("S") || solveGrid[startRow][startColumn].equals("G")){
         
         //Base Case - Did I reach Finish
         if(solveGrid[startRow][startColumn].equals("G")){
           return true;
         }
        
       //Recursive Cases are below
       else{     
         //The below code checks for the path to take by changing current values that aren't an S to an O
         if( !(solveGrid[startRow][startColumn].equals("S"))){
          solveGrid[startRow][startColumn] = "0"; 
        }
         
         //The below code checks whether the current points below point has been tried
         if(solveMaze(solveGrid, startRow -1,startColumn)){
           return true;
         }
         
         //The below code checks whether the current points above point has been tried
         if(solveMaze(solveGrid, startRow+1,startColumn)){
           return true; 
         }
         
         //The below code checks whether the current points left point has been tried
         if(solveMaze(solveGrid,startRow,startColumn-1)){
           return true;
         }
         
         //The below code checks whether the current points right point has been tried
         if(solveMaze(solveGrid,startRow,startColumn+1)){
           return true; 
         }
         
         //The below code checks for if the path does not work
         //If it does not work, then the value will stay a .
         
         else{
           if(!(solveGrid[startRow][startColumn].equals("S"))){
             solveGrid[startRow][startColumn] = "."; 
           }
         } 
       }
      }
     }
    return false;
  }
}