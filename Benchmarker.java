//benchmarker
//This class is purposed with creating three different forms of datastructures, adding the same data to them and comparing the operation time between them
//342 DataStructures
//Logan Miller
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Benchmarker {

private String inputFileName; //war and peace
private String text; //holds file contents
private MyLinkedList listOfWords; //linked list for test
private MyBinarySearchTree treeOfWords; //binary tree for test
private MyBinarySearchTree balancedTreeOfWords; //avl tree for test
private ArrayList nonSeperators; //holds valid characters
private HashMap<Character, Integer> Chrmap; //used for 
private char[] textArray;
private String check;
private int totalWords;

   //Construtor method setting fields to be used later
   public Benchmarker() throws IOException {

      inputFileName = "./WarAndPeace.txt";
      
      readInputFile();
      
      //set valid character 
      Chrmap = new HashMap<Character, Integer>();
      check = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'"; //provided valid characters
      for (int j = 0; j < check.length(); j++) {
         Chrmap.put(check.charAt(j), 0);
      }
      
      textArray = text.toCharArray();
      
      //Linkedlist test
      listOfWords = new MyLinkedList();
      loadList();
      
      //binary search tree test
      treeOfWords = new MyBinarySearchTree();
      loadTree();
      
      //AVL tree test
      balancedTreeOfWords = new MyBinarySearchTree();
      loadBalancedTree();
      
      System.out.println(this.toString());
      
   }
   
   private void readInputFile() throws IOException { //reads input file and sets the contents to the text field

      int charCount = 0;
      char temp;
      int tempInt;
      StringBuilder str = new StringBuilder();
      long startTime = System.nanoTime();
      try {
         FileReader file = new FileReader(inputFileName);
         BufferedReader br = new BufferedReader(file);
         tempInt = br.read();
         while (tempInt != -1) {
            temp = (char) tempInt;
            str.append(temp); 
            charCount++;
            tempInt = br.read();
         }
         text = str.toString();
      }
      catch (FileNotFoundException e) {
         System.out.println("File not found");
      
      }

      
      //outputs operation times and test results
      long endTime = System.nanoTime();
      double time = ((endTime - startTime)/1000000.0);
      System.out.println("Reading input file " + inputFileName + "... " + charCount + " characters in " + time + " milliseconds" );
      System.out.print("\n");
      
   }
 
   
   
   private void loadList() throws IOException {   //adds all unique words into the linked list 
      StringBuilder adder = new StringBuilder();   
      int wrd = 0;
      long sTime = System.nanoTime();
      char tempchr;
      listOfWords.comparison = 0;
      for (int i = 0; i < textArray.length; i++) {
         tempchr = textArray[i];
         if (Chrmap.containsKey(tempchr)) {
            adder.append(tempchr);
         }
         else {  
            if (adder.toString().length() > 0) {
               totalWords++;
               if (!(listOfWords.contains(adder.toString()))) { //test if word is unique
                  listOfWords.add(adder.toString()); //add to list
               }
            } 
            adder = new StringBuilder();
         }
      }
      
      //output operation time and test results
      long eTime = System.nanoTime();
      double time = ((eTime - sTime)/1000000.0);
      System.out.println("Adding unique words to a linked list... " + "in " + time + " milliseconds.");
      System.out.println("The linked list made " + listOfWords.comparison + " comparisons.");
      System.out.println("The linked list has a length of " + listOfWords.size()); 
      System.out.print("\n");
   }
   
   
   private void loadTree() throws IOException{ //adds all unique words to binary search tree

      StringBuilder adder = new StringBuilder();
      int compare = 0;
      long sTime = System.nanoTime();
 
      char tempchr;
      treeOfWords.compare = 0;
      for (int i = 0; i < textArray.length; i++) {
         tempchr = textArray[i];
         if (Chrmap.containsKey(tempchr)) {
            adder.append(tempchr);
         }
         else {
            if (adder.toString().length() > 0) {
               if (!(treeOfWords.contains(adder.toString()))) { //check for uniqueness
                  treeOfWords.add(adder.toString()); //add to tree
               }
            }
            adder = new StringBuilder();
         }
      }
      
      
      //output operation time and test results
      long eTime = System.nanoTime();
      double time = ((eTime - sTime)/1000000.0); 
      System.out.println("Adding unique words to a binary search tree... " + "in " + time + " milliseconds.");
      System.out.println("The binary tree made " + treeOfWords.compare + " comparisons.");
      System.out.println("The binary tree has a depth of " + treeOfWords.getHeight());  
      System.out.println("\n");
         
   }
   
   
   private void loadBalancedTree() throws IOException{ //add unique words to the AVL tree
      balancedTreeOfWords.setBalancing(true);
      StringReader textRead = new StringReader(text);
      BufferedReader br = new BufferedReader(textRead);    
      StringBuilder adder = new StringBuilder();
      
      int compare = 0;
      long sTime = System.nanoTime();
 
      int temp = br.read();
      char tempchr;
      for (int i = 0; i < textArray.length; i++) {
         tempchr = textArray[i];
         if (Chrmap.containsKey(tempchr)) {
            adder.append(tempchr);
         }
         else {
            if (adder.toString().length() > 0) {
               if (!(balancedTreeOfWords.contains(adder.toString()))) { //uniqueness check
                  balancedTreeOfWords.add(adder.toString()); //adds to the AVL
               }
            }
            adder = new StringBuilder();
         }
      }
      
      long eTime = System.nanoTime();
      double time = ((eTime - sTime)/1000000.0);
      
      //outputs operation time and test results
      System.out.println("Adding unique words to AVL tree .... in " + time + " milliseconds");
      System.out.println("The AVL Tree made " + balancedTreeOfWords.compare + " comparisons.");
      System.out.println("The AVL Tree has a depth of " + balancedTreeOfWords.getHeight());
      System.out.println("The AVL Tree made " + balancedTreeOfWords.rotations + " rotations");
   }
   
   
   public String toString() { //outputs total word count  
      return ("\n" + listOfWords.size() + " Unique words \n" + totalWords + " Total words");  
   }
}

//the graveyard of text that doesn't work properly
      
      /*for (int i = 0; i < text.length(); i++) {
         temp = br.read();
      
         if (br.read() != " ") {
            if ( !(listOfWords.contains(adder))) {
               listOfWords.add(adder);
               compare++;

            }
             adder = new StringBuilder();
         } 
         else {
            adder.append(temp);
         }
      } */



/*while (temp != -1) {
         tempchr = (char) temp;
         
         if (check.indexOf(tempchr) != -1) {
            adder.append(tempchr);
         }
         else {
            if ( !(listOfWords.contains(adder.toString(), compare)) ) {
               listOfWords.add(adder.toString());
            }
            adder = new StringBuilder();
         }
         
         temp = br.read();
      }*/
      
      
      /*for (int i = 0; i < text.length(); i++) {
         tempchr = textArray[i];
         if (Chrmap.containsKey(tempchr)) {
            adder.append(tempchr);
         }
         else {
            if (!(listOfWords.contains(adder.toString()))) { 
               listOfWords.add(adder.toString());

            }
            adder = new StringBuilder();
         }
      }*/