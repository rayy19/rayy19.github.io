/*
* Date: June 1, 2020
* Name: Rayyan Amir
* Teacher: Mr. Ho
* Description: Weather Analysis Assignment
* NOTE: This is my attempt at the level 4 version
*/



import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.*;


class WeatherAnalysis{
  
  //In order to run my code, I've called all of my methods in the main method
  public static void main(String[] args) {  
    januaryData();
    feburaryData();
    marchData();
    aprilData();
    mayData();
  }
  
  //METHOD 1 STARTS HERE
  ////This method is for the month of January
  //In order to access each files data, I've seperated them into their own methods
  //My goal is that each time, I call the methods, the data will be outputted onto the same csv file
  public static void januaryData(){
    
//P1: Declare a file to read 
    File janFile = new File("en_climate_hourly_ON_6158409_01-2020_P1H.csv");

//Below is the file that I will be writing my data too
    File file = new File("monthDataAnalysis.csv");
    try{
    if(!file.exists()){
        file.createNewFile();
      }
    
    //In order to have all months data show on a csv file, I had to append the csv file
    //To do this, the FileWriter function appends the file through its boolean condition (true)
    //Along with that, the bufferedwriter was used as a pramaeter for the printwriter 
    //The printwriter function will allow me to essentially write my data into the file
    
      FileWriter janWriter = new FileWriter(file, true);
      BufferedWriter janBuffer = new BufferedWriter(janWriter);  
      PrintWriter janInput = new PrintWriter(janBuffer);
    try{
    Scanner reader = new Scanner(janFile);
    
//P2: Assign max, min and average values for each day, week and month in Jan
    
    //The variables below are used to calculate the average temperature, Max & Min values of the days & week/month of Jan
    double total = 0;
    double janTemp = 0;
    double janDailyMin = 25.00;
    double janDailyMax = 0.0;
    double janMonthMin = 25.00;
    double janMonthMax = 0.0;
    double bTemp = 0.0;
    double janWeekAverage = 0.0;
    double janWeekMin = 25.00;
    double janWeekMax = 0.0;
    
    //Below are the headings that will be used when displaying data
    janInput.print("Month\t");
    janInput.print("Day\t");
    janInput.print("Time\t");
    janInput.print("Temp\t");
    janInput.print("Weather Alert\n");
    
    
//P3: Read the file and receive an Input
      
    //In the below while loop, a scanner is being used to read the file line by line
    //While the file has lines to read, the code under the while loop will run
    while(reader.hasNext()){
      //The code below is used to skip the first line in the file
      //This will allow us to organize our processed data more easily
      reader.next();
      
      //Below, all the information from the file is being stored within the fileData variable
      //In order to seperate the files content, the .split function is being used to seperate the files contents at every comma
      //The split values are then stored within the values array
      //The temp variable is being used to store all of the temperatures of January
      String janData = reader.next();
      String quoteRemoval = janData.replaceAll("\"", "");
      try{
      String[] janValues = quoteRemoval.split(",");
      double temp = Double.parseDouble(janValues[5]);
     
//P4: Find Max,Min & Avg of file
      
      //In this for loop, the loop has been set so that it will go through the 31 days of Jan
      //As it outputs the 31 days of Jan, it seperates each day at the end of the "23:00" mark
      //To find the average of each day, all of the termperatures of each day are added to the bTemp variable and are divded by 24 each time
      for(int i = 1; i<=31; i = i+1){
          janInput.print(janValues[2] + "\t");
          janInput.print(janValues[3] + "\t");
          janInput.print(janValues[4] + "\t");
          janInput.print(temp);
          bTemp = bTemp + temp;
          janWeekAverage = janWeekAverage + temp;
          
          //To find for the extreme weather alert of Jan, I took the max and min temps of Jan and found their average (-2.3)
          //From this value, I gave a +- 5 leeway and if any values were less than or greater than the new number, they were...
          //considered low and high temps accordingly
          if(temp>2.7){
           janInput.print("\tHigh\n"); 
          }
          else if (temp<-7.3){
           janInput.print("\tLow\n"); 
          }
          else{
           janInput.print("\tNormal\n"); 
          }
          
          //To find the max and min values, a reference variable stored within janDailyMin and janDailyMax is compared to each days temp of Jan 
          //Each time a value is less than or greater than the janDailyMin and janDailyMax found, the variables becomes the new number
          //This pattern repeats until all values of the temperature have been gone through
          
          if(temp<janDailyMin)
          janDailyMin = temp;
          
          //Similar to how the daily min/max work, the monthly min/max work by comparing the temps of each day in the month of January and takes the lowest..
          //and highest temp from each day up until the end of the month (31 days)
          
          if(temp<janMonthMin)
          janMonthMin = temp;
          
          if (temp>janDailyMax)
          janDailyMax = temp;
          
          if(temp>janMonthMax)
          janMonthMax = temp;
          
          ////Similar to how the daily min/max work, the weekly min/max work by comparing the temp of each day in a week and takes the lowest..
          //and highest temp from each day up until the end of the week (7 days)
          
          if(temp<janWeekMin)
          janWeekMin = temp;
          
          if (temp>janWeekMax)
          janWeekMax = temp;
                
          //In the below if statement, each day of Jan is sorted and seperated from each other by the end of 24 hours
          if(janValues[4].equals("23:00")){
           janInput.println("\nMin Temp for " + janValues[2] + " " + janValues[3] + " is " + (janDailyMin) + "°C ");
           janInput.println("Max Temp for " + janValues[2] + " " +  janValues[3] + " is " + (janDailyMax)+ "°C ");
           janInput.println("Avg Temp for " + janValues[2] + " " + janValues[3] + " is " + (bTemp/24) + "°C " + "\n"); 
           
           //Once one day of Jan has been found, the JanDailyMin/Max as well as bTemp resets so it can find the new info of a different day
           janDailyMin = 25.0;
           janDailyMax = 0.0;
           bTemp = 0;
          }
          
          //Similarly to how  the above if statement seperates each day, the below if statement seperates each week of Jan
          if(janValues[3].equals("07") && janValues[4].equals("23:00") || janValues[3].equals("14") && janValues[4].equals("23:00") || janValues[3].equals("21") && janValues[4].equals("23:00")
            || janValues[3].equals("28") && janValues[4].equals("23:00")){
            janInput.println("Min for week is " + janWeekMin + "°C ");
            janInput.println("Max for week is " + janWeekMax + "°C ");
            janInput.println("Temp for week is " + (janWeekAverage/168) + "°C " + "\n");
            
            //Once one week of Jan has been found, the JanWeekMin/Max resets so it can find the new info of a different day
            janWeekMin = 25.0;
            janWeekMax = 0.0;
            janWeekAverage = 0.0; 
          }
          break;
      }  

      //Below is the code to find for the month average
      //To find the average, the total takes all of the temperatures of May and divides it by the number of temperatures it takes in
      total += temp;
      janTemp++; 
      } catch (ArrayIndexOutOfBoundsException e){ //This try catch block is being used to make sure that an indexoutofbounds exception is dismissed
       janInput.print("");                      //If an error occurs during the try catch block then the code will not execute 
      }
    }
    reader.close();
    janInput.println("January Month Max Temp is " + janMonthMax + "°C ");
    janInput.println("January Month Min Temp is " + janMonthMin + "°C ");
    janInput.println("January Month Average is " + (total/janTemp) + "°C " + "\n");
    } catch (IOException e){ //This try catch block is being used to make sure that an IOexception of the file is dismissed
     e.printStackTrace();    //If an error occurs during the try catch block then the code will not execute
    } 
    janInput.close();
    } catch (IOException ne){
     ne.printStackTrace(); 
    }   
  }
  
  //METHOD 2 STARTS HERE
  //This method is for the month of Feburary
  //Using the exact same logic as the january method, the remaining methods have been created in the same way
  public static void feburaryData(){
    //P1: Declare a file to read 
    File marchFile = new File("en_climate_hourly_ON_6158409_02-2020_P1H.csv");
    File file = new File("monthDataAnalysis.csv");
    try{
    if(!file.exists()){
        file.createNewFile();
      }
      
      //As mentioned in the January Method, the below codes help to append and write to a csv file
      //Refer to January Method for more info
      FileWriter febWriter = new FileWriter(file, true);
      BufferedWriter febBuffer = new BufferedWriter(febWriter);  
      PrintWriter febInput = new PrintWriter(febBuffer);
  
    try{
    Scanner reader = new Scanner(marchFile);
    
//P2: Assign max, min and average values for each day, week and month in Feb
    
    //The variables below are used to calculate the average temperature, Max & Min values of the days & week/month of Feb
    double total = 0;
    double febTemp = 0;
    double febDailyMin = 25.00;
    double febDailyMax = 0.0;
    double febMonthMin = 25.00;
    double febMonthMax = 0.0;
    double bTemp = 0.0;
    double febWeekAverage = 0.0;
    double febWeekMin = 25.00;
    double febWeekMax = 0.0;
    
    
    //Below are the headings that will be used when displaying data
    febInput.print("Month\t");
    febInput.print("Day\t");
    febInput.print("Time\t");
    febInput.print("Temp\t");
    febInput.print("Weather Alert\n");
    
//P3: Read the file and receive a febInput
      
    while(reader.hasNext()){
      reader.next();
      String febData = reader.next();
      String quoteRemoval = febData.replaceAll("\"", "");
      try{
      String[] febValues = quoteRemoval.split(",");
      double temp = Double.parseDouble(febValues[5]);
     
//P4: Find Max,Min & Avg of file
     
      //An explanation of how this loop works has been given in the January Method
      for(int i = 1; i<=29; i = i+1){
          febInput.print(febValues[2] + "\t");
          febInput.print(febValues[3] + "\t");
          febInput.print(febValues[4] + "\t");
          febInput.print(temp);
          bTemp = bTemp + temp;
          febWeekAverage = febWeekAverage + temp;
          
          //To find for the extreme weather alert of Feb, I took the max and min temps of Feb and found their average (-6.8)
          //From this value, I gave a +- 5 leeway and if any values were less than or greater than the new number, they were...
          //considered low and high temps accordingly
          if(temp>-1.8){
           febInput.print("\tHigh\n"); 
          }
          else if (temp<-11.8){
           febInput.print("\tLow\n"); 
          }
          else{
           febInput.print("\tNormal\n"); 
          }
          
          //Below is the code that compares the max/min values fo each day,week and month
          //Refer to Jan method for more info
          if(temp<febDailyMin)
          febDailyMin = temp;
          
          if(temp<febMonthMin)
          febMonthMin = temp;
          
          if (temp>febDailyMax)
          febDailyMax = temp;
          
          if(temp>febMonthMax)
          febMonthMax = temp;
          
          if(temp<febWeekMin)
          febWeekMin = temp;
          
          if (temp>febWeekMax)
          febWeekMax = temp;
                
          
          //This if statement is used to seperate each day of Feb
          if(febValues[4].equals("23:00")){
           febInput.println("\nMin Temp for " + febValues[2] + " " + febValues[3] + " is " + (febDailyMin) + "°C ");
           febInput.println("Max Temp for " + febValues[2] + " " +  febValues[3] + " is " + (febDailyMax) + "°C ");
           febInput.println("Avg Temp for " + febValues[2] + " " + febValues[3] + " is " + (bTemp/24) + "°C " + "\n"); 
           //After each day of Feb, all of the daily info is reset
           febDailyMin = 25.0;
           febDailyMax = 0.0;
           bTemp = 0;
          }
          //This if statement is used to seperate each week of Feb
          if(febValues[3].equals("07") && febValues[4].equals("23:00") || febValues[3].equals("14") && febValues[4].equals("23:00") || febValues[3].equals("21") && febValues[4].equals("23:00")
            || febValues[3].equals("28") && febValues[4].equals("23:00")){
            febInput.println("Min for week is " + febWeekMin + "°C ");
            febInput.println("Max for week is " + febWeekMax + "°C ");
            febInput.println("Temp for week is " + (febWeekAverage/168) + "°C " + "\n");
            //After each week of Feb, all of the weekly info is reset
            febWeekMin = 25.0;
            febWeekMax = 0.0;
            febWeekAverage = 0.0; 
          }
          break;
      }  

      //Below is the code to find for the month average
      //To find the average, the total takes all of the temperatures of Feb and divides it by the number of temperatures it takes in
      total += temp;
      febTemp++; 
      } catch (ArrayIndexOutOfBoundsException e){ //This try catch block is being used to make sure that an indexoutofbounds exception is dismissed
       febInput.print("");                      //If an error occurs during the try catch block then the code will not execute 
      }
    }
    reader.close();
    //Below is where all the monthly info of Feb is printed
    febInput.println("Feburary Month Max Temp is " + febMonthMax + "°C ");
    febInput.println("Feburary Month Min Temp is " + febMonthMin + "°C ");
    febInput.println("Feburary Month Average is " + (total/febTemp) + "°C " + "\n");
    } catch (IOException e){ //This try catch block is being used to make sure that an IOexception of the file is dismissed
     e.printStackTrace();    //If an error occurs during the try catch block then the code will not execute
    } 
    febInput.close();
    } catch (IOException ne){
     ne.printStackTrace(); 
    }    
  }
 
//METHOD 3 STARTS HERE
//This method is for the month of March
//As mentioned above, this method has the same logics as the one above  
  public static void marchData(){
    //P1: Declare a file to read 
    File marchFile = new File("en_climate_hourly_ON_6158409_03-2020_P1H.csv");
    File file = new File("monthDataAnalysis.csv");
    try{
    if(!file.exists()){
        file.createNewFile();
      }
    
      //Code to append and write to a csv file is below
      FileWriter marWriter = new FileWriter(file, true);
      BufferedWriter marBuffer = new BufferedWriter(marWriter);   
      PrintWriter marInput = new PrintWriter(marBuffer);
      
    try{
    Scanner reader = new Scanner(marchFile);
    
//P2: Assign max, min and average values for each day, week and month in Mar
    
    //The variables below are used to calculate the average temperature, Max & Min values of the days & week/month of Mar
    double total = 0;
    double marTemp = 0;
    double marDailyMin = 25.00;
    double marDailyMax = 0.0;
    double marMonthMin = 25.00;
    double marMonthMax = 0.0;
    double bTemp = 0.0;
    double marWeekAverage = 0.0;
    double marWeekMin = 25.00;
    double marWeekMax = 0.0;
    
    //Below are the headings that will be used when displaying data
    marInput.print("Month\t");
    marInput.print("Day\t");
    marInput.print("Time\t");
    marInput.print("Temp\t");
    marInput.print("Weather Alert\n");
    
//P3: Read the file and receive an input
    while(reader.hasNext()){
      reader.next();
      String marData = reader.next();
      String quoteRemoval = marData.replaceAll("\"", "");
      try{
      String[] marValues = quoteRemoval.split(",");
      //int date = Integer.parseInt(marValues[3]);
      double temp = Double.parseDouble(marValues[5]);
     
//P4: Find Max,Min & Avg of file
      for(int i = 1; i<=31; i = i+1){
          marInput.print(marValues[2] + "\t");
          marInput.print(marValues[3] + "\t");
          marInput.print(marValues[4] + "\t");
          marInput.print(temp);
          bTemp = bTemp + temp;
          marWeekAverage = marWeekAverage + temp;
          
              
          //To find for the extreme weather alert of Mar, I took the max and min temps of Mar and found their average (1.9)
          //From this value, I gave a +- 5 leeway and if any values were less than or greater than the new number, they were...
          //considered low and high temps accordingly
          if(temp>6.9){
           marInput.print("\tHigh\n"); 
          }
          else if (temp<-3.1){
           marInput.print("\tLow\n"); 
          }
          else{
           marInput.print("\tNormal\n"); 
          }
          
          //Below is the code to compare the daily, weekly & monthly max/min values of May
          //Refer to jan Method for more info
          if(temp<marDailyMin)
          marDailyMin = temp;
          
          if(temp<marMonthMin)
          marMonthMin = temp;
          
          if (temp>marDailyMax)
          marDailyMax = temp;
          
          if(temp>marMonthMax)
          marMonthMax = temp;
          
          if(temp<marWeekMin)
          marWeekMin = temp;
          
          if (temp>marWeekMax)
          marWeekMax = temp;
                
          //This if statement seperates the daily info of Mar
          if(marValues[4].equals("23:00")){
           marInput.println("\nMin Temp for " + marValues[2] + " " + marValues[3] + " is " + (marDailyMin) + "°C ");
           marInput.println("Max Temp for " + marValues[2] + " " +  marValues[3] + " is " + (marDailyMax) + "°C ");
           marInput.println("Avg Temp for " + marValues[2] + " " + marValues[3] + " is " + (bTemp/24) + "°C " + "\n");
           //After the end of each day, Mar info is reset
           marDailyMin = 25.0;
           marDailyMax = 0.0;
           bTemp = 0;
          }
          
          //This if statement seperates the weekly info of Mar
          if(marValues[3].equals("07") && marValues[4].equals("23:00") || marValues[3].equals("14") && marValues[4].equals("23:00") || marValues[3].equals("21") && marValues[4].equals("23:00")
            || marValues[3].equals("28") && marValues[4].equals("23:00")){            
            marInput.println("Min for week is " + marWeekMin + "°C ");
            marInput.println("Max for week is " + marWeekMax + "°C ");
            marInput.println("Temp for week is " + (marWeekAverage/168) + "°C " + "\n");   
            //After each week of Mar, Mar weekly info is reset
            marWeekMin = 25.00;
            marWeekMax = 0.0;
            marWeekAverage = 0.0; 
          }
          break;
      }  

      //Below is the code to find for the week/month average
      //To find the average, the total takes all of the temperatures of May and divides it by the number of temperatures it takes in
      total += temp;
      marTemp++; 
      } catch (ArrayIndexOutOfBoundsException e){ //This try catch block is being used to make sure that an indexoutofbounds exception is dismissed
       marInput.print("");                      //If an error occurs during the try catch block then the code will not execute 
      }
    }
    reader.close();
    //Below is where all the monthly info of Mar is printed
    marInput.println("March Month Max Temp is " + marMonthMax + "°C ");
    marInput.println("March Month Min Temp is " + marMonthMin + "°C ");
    marInput.println("March Month Average is " + (total/marTemp) + "°C " + "\n");
    } catch (IOException e){ //This try catch block is being used to make sure that an IOexception of the file is dismissed
     e.printStackTrace();    //If an error occurs during the try catch block then the code will not execute
    } 
    marInput.close();
    } catch (IOException ne){
     ne.printStackTrace(); 
    }
  }
  
  //METHOD 4 STARTS HERE
  //As the name suggests, this method is for the month of April
  public static void aprilData(){
   //P1: Declare a file to read 
    File mayFile = new File("en_climate_hourly_ON_6158409_04-2020_P1H.csv");
    //This is the file that Aprils content will be printed too
    File file = new File("monthDataAnalysis.csv");
    try{
    if(!file.exists()){
        file.createNewFile();
      }
    
      //Below are the codes that are being used to append and write to a csv file accordingly
      FileWriter aprWriter = new FileWriter(file, true);
      BufferedWriter aprBuffer = new BufferedWriter(aprWriter);   
      PrintWriter aprInput = new PrintWriter(aprBuffer);
    try{
    Scanner reader = new Scanner(mayFile);
    
//P2: Assign max, min and average values for each day, week and month in Apr
    
    //The variables below are used to calculate the average temperature, Max & Min values of the days & week/month of Apr
    double total = 0;
    double aprilTemp = 0;
    double aprilMins = 25.00;
    double aprilMaxs = 0.0;
    double aprilMin = 25.00;
    double aprilMax = 0.0;
    double bTemp = 0.0;
    double aprWeekAverage = 0.0;
    double aprWeekMin = 25.00;
    double aprWeekMax = 0.0;
    
    //Below are the headings that will be used when displaying data
    aprInput.print("Month\t");
    aprInput.print("Day\t");
    aprInput.print("Time\t");
    aprInput.print("Day Temp\t");
    aprInput.print("Weather Alert\t");
    
//P3: Read the file and receive an input
      
    while(reader.hasNext()){
      reader.next();
      String aprData = reader.next();
      String quoteRemoval = aprData.replaceAll("\"", "");
      try{
      String[] aprValues = quoteRemoval.split(",");
      double temp = Double.parseDouble(aprValues[5]);
     
//P4: Find Max,Min & Avg of file
      for(int i = 1; i<=30; i = i+1){
          aprInput.print(aprValues[2] + "\t");
          aprInput.print(aprValues[3] + "\t");
          aprInput.print(aprValues[4] + "\t");
          aprInput.print(temp);
          bTemp = bTemp + temp;
          aprWeekAverage = aprWeekAverage + temp;
          
          //To find for the extreme weather alert of Apr, I took the max and min temps of Apr and found their average (5.1)
          //From this value, I gave a +- 5 leeway and if any values were less than or greater than the new number, they were...
          //considered low and high temps accordingly
          if(temp>10.1){
           aprInput.print("\tHigh\n"); 
          }
          else if (temp<0.1){
           aprInput.print("\tLow\n"); 
          }
          else{
           aprInput.print("\tNormal\n"); 
          }
          
          //Below are the codes being used to find the daily, weekly, and monthly max/min of Apr
          //Refer to Jan method for more info
          if(temp<aprilMins)
          aprilMins = temp;
          
          if(temp<aprilMin)
          aprilMin = temp;
          
          if (temp>aprilMaxs)
          aprilMaxs = temp;
          
          if(temp>aprilMax)
          aprilMax = temp;
          
          if(temp<aprWeekMin)
          aprWeekMin = temp;
          
          if (temp>aprWeekMax)
          aprWeekMax = temp;
                
          //Below is the if statement being used to find tte daily info of Apr     
          if(aprValues[4].equals("23:00")){
           aprInput.println("\nMin Temp for " + aprValues[2] + " " + aprValues[3] + " is " + (aprilMins) + "°C ");
           aprInput.println("Max Temp for " + aprValues[2] + " " +  aprValues[3] + " is " + (aprilMaxs) + "°C ");
           aprInput.println("Avg Temp for " + aprValues[2] + " " + aprValues[3] + " is " + (bTemp/24) + "°C " + "\n"); 
           //After each day of Apr, Apr daily info is reset
           aprilMins = 25.0;
           aprilMaxs = 0.0;
           bTemp = 0;
          }
          //Below is the if statement being used to find the weekly info of Apr
          if(aprValues[3].equals("07") && aprValues[4].equals("23:00") || aprValues[3].equals("14") && aprValues[4].equals("23:00") || aprValues[3].equals("21") && aprValues[4].equals("23:00")
            || aprValues[3].equals("28") && aprValues[4].equals("23:00")){
            aprInput.println("Min for week is " + aprWeekMin + "°C ");
            aprInput.println("Max for week is " + aprWeekMax + "°C ");
            aprInput.println("Temp for week is " + (aprWeekAverage/168) + "°C " + "\n");
            //After each week of Apr, Apr weekly info is reset
            aprWeekMin = 25.00;
            aprWeekMax = 0.0;
            aprWeekAverage = 0.0; 
          }
          break;
      }  

      //Below is the code to find for the week/month average
      //To find the average, the total takes all of the temperatures of May and divides it by the number of temperatures it takes in
      total += temp;
      aprilTemp++; 
      } catch (ArrayIndexOutOfBoundsException e){ //This try catch block is being used to make sure that an indexoutofbounds exception is dismissed
       aprInput.print("");                      //If an error occurs during the try catch block then the code will not execute 
      }
    }
    reader.close();
    //Below is where the Apr month data is printed
    aprInput.println("April Month Max Temp is " + aprilMax + "°C ");
    aprInput.println("April Month Min Temp is " + aprilMin + "°C ");
    aprInput.println("April Month Average is " + (total/aprilTemp) + "°C " + "\n");
    } catch (IOException e){ //This try catch block is being used to make sure that an IOexception of the file is dismissed
     e.printStackTrace();    //If an error occurs during the try catch block then the code will not execute
    } 
    aprInput.close();
    } catch (IOException ne){
     ne.printStackTrace(); 
    }   
  }
    
  //METHOD 5 STARTS HERE
  //Below is the method that will be used to recieve data input from the May file
  //NOTE: A void method is being used for all of these methods because it will perform the data inputting tasks and then terminate
  public static void mayData(){
  
//P1: Declare a file to read 
    File mayFile = new File("en_climate_hourly_ON_6158409_05-2020_P1H.csv");
    File file = new File("monthDataAnalysis.csv");
     try{
      if(!file.exists()){
        file.createNewFile();
      }
      FileWriter mayWriter = new FileWriter(file, true);
      BufferedWriter mayBuffer = new BufferedWriter(mayWriter);  
      PrintWriter mayInput = new PrintWriter(mayBuffer);
    try{
    Scanner myScanner = new Scanner(mayFile);
    
//P2: Assign max, min and average values for each day, week and month in May
    
    //The variables below are used to calculate the average temperature, Max & Min values of the days & week/month of May
    double sum = 0;
    double hourTemp = 0;
    double mayDailyMin = 25.00;
    double mayDailyMax = 0.0;
    double mayMonthMin = 25.00;
    double mayMonthMax = 0.0;
    double aTemp = 0.0;
    
    //Below are the headings that will be used when displaying data
    mayInput.print("Month\t");
    mayInput.print("Day\t");
    mayInput.print("Time\t");
    mayInput.print("Day Temp\t");
    mayInput.print("Weather Alert\n");
    
//P3: Read the file and receive an input
      
    while(myScanner.hasNext()){
      myScanner.next();
      String info = myScanner.next();
      String quoteSeperator = info.replaceAll("\"", "");
      try{
      String[] mayValues = quoteSeperator.split(",");
      //int date = Integer.parseInt(mayValues[3]);
      double temp = Double.parseDouble(mayValues[5]);
     
//P4: Find Max,Min & Avg of file
      for(int i = 1; i<=7;i = i+1){
          mayInput.print(mayValues[2] + "\t");
          mayInput.print(mayValues[3] + "\t");
          mayInput.print(mayValues[4] + "\t");
          mayInput.print(temp);
          aTemp = aTemp + temp;
          
          //To find for the extreme weather alert of May, I took the max and min temps of May and found their average (10.2)
          //From this value, I gave a +- 5 leeway and if any values were less than or greater than the new number, they were...
          //considered low and high temps accordingly
          if(temp>15.2){
           mayInput.print("\tHigh\n"); 
          }
          else if (temp<5.2){
           mayInput.print("\tLow\n"); 
          }
          else{
           mayInput.print("\tNormal\n"); 
          }
          
          //Below is the code to find for the daily and monthly Max/Min of May
          //Refer to Jan method for more info
          if(temp<mayDailyMin)
          mayDailyMin = temp;
          
          if(temp<mayMonthMin)
          mayMonthMin = temp;
          
          if (temp>mayDailyMax)
          mayDailyMax = temp;
          
          if(temp>mayMonthMax)
          mayMonthMax = temp;
                
          //This if statement sorts the daily info of May
          if(mayValues[4].equals("23:00")){
           mayInput.println("\nMin value for " + mayValues[2] + " " + mayValues[3] + " is " + (mayDailyMin) + "°C ");
           mayInput.println("Max value for " + mayValues[2] + " " +  mayValues[3] + " is " + (mayDailyMax) + "°C ");
           mayInput.println("Avg Temp for " + mayValues[2] + " " + mayValues[3] + " is " + (aTemp/24) + "°C " + "\n"); 
           //After each day of May, the daily info is reset
           mayDailyMin = 25.0;
           mayDailyMax = 0.0;
           aTemp = 0;
          }
          break;
      }  

      //Below is the code to find for the week/month average
      //To find the average, the sum takes all of the temperatures of May and divides it by the number of temperatures it takes in
      sum += temp;
      hourTemp++; 
      } catch (ArrayIndexOutOfBoundsException e){ //This try catch block is being used to make sure that an indexoutofbounds exception is dismissed
       mayInput.print("");                      //If an error occurs during the try catch block then the code will not execute 
      }
    }
    myScanner.close();
    mayInput.println("May Week & Month Max value is " + mayMonthMax + "°C ");
    mayInput.println("May Week & Month Min value is " + mayMonthMin + "°C ");
    mayInput.println("May Week & Month Average is " + (sum/hourTemp) + "°C " + "\n");
    } catch (IOException e){ //This try catch block is being used to make sure that an IOexception of the file is dismissed
     e.printStackTrace();    //If an error occurs during the try catch block then the code will not execute
    } 
    mayInput.close();
    System.out.println("File is ready and is named monthDataAnalysis.csv");
    } catch (IOException e){
       e.printStackTrace(); 
      }       
  }  
  
}