//main
//this class is the top level design for the benchmarker class
//342 Data Structures
//Logan Miller
import java.util.ArrayList;
import java.io.IOException;

public class Main {

   public static void main(String[] args) {
   
      try {
         //Benchmarker handles output and rest of function calls
         Benchmarker bench = new Benchmarker(); //create new benchmark test
      }
      catch (IOException ex) {
         System.out.println("IO ERROR");
      }  
   }  
}
