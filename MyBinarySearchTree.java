//Binary Search Tree
//This class holds the implementations of the AVL and binary search tree for testing in the
//342 Data Structures
//Logan Miller

public class MyBinarySearchTree<T extends Comparable<T>> {

   private Node root; //root of huffman/AVL tree
   private int size; //# of nodes in tree
   private boolean balancing; //int value representing the balance of children nodes
   public int compare; //# of comparisons
   public int rotations; //# of rotations 
   
   private class Node {
      public T item;
      public Node left; //children nodes
      public Node right;

      public int height; // largest # of children on left or right branches
      public int balanceFactor; 
   
      public String toString() {
         return ("node " + item);
      }
   }
   
   
   
   public void setBalancing(boolean bool) {//sets balancing field for AVL
      this.balancing = bool;
   }
   
   
   public void add(T item) {//recursive call to adding to the root
      add(item, root);
   }
   
   private Node add(T item, Node subTree) { //recursively traverses tree add new node to proper location
      if (root == null) {
         root = new Node();
         root.item = item;
      }
      else {
         if (subTree.item.compareTo(item) > 0) { //traversese to the left when the item is less
            if (subTree.left == null) {//if null, place new node here
               Node leftA = new Node();
               leftA.item = item;
               subTree.left = leftA;            
            }
            else{
              subTree.left = add(item, subTree.left);//when not null add to the leftnode
            }
         }
         else { //traverses to the right when the item is more
            if (subTree.right == null) { //if null, place new node here 
               Node rightA = new Node();
               rightA.item = item;
               subTree.right = rightA;
            }
            else{
               subTree.right = add(item, subTree.right);//when not null add to the rightnode
            }  
         }
         
         update(subTree);//update ndoes height and balance factors of these nodes
         if (balancing){//if the tree is a self balancing, rebalance after every add
            if (subTree == root) {
               root = rebalance(subTree);
            }
            else {
               subTree = rebalance(subTree);
            }
            
         }      
      }
      return subTree;
   }
       
   public void remove(T item) {
      root = remove(item, root);
   }
   
   
   private Node remove(T item, Node subTree) { //traveres down to find the item in the tree, removes it if found
      if (root != null) {
         if (root.item.compareTo(item) > 1) {
            if(subTree.left != null) {
               subTree = remove(item, subTree.left);
            }
         }
         else if (root.item.compareTo(item) < -1) {
            if(subTree.right != null) {
               subTree = remove(item, subTree.right);
            }
         }
         else if (root.item.compareTo(item) == 1) {
            subTree = subTree.right;   
         }    
      } 
      return subTree;  
   }
   
   
   public void update(Node node) {//update the passed node's height and balance factor
      int L = -1;
      int R = -1;
      if (node.right != null){
        R = node.right.height;      
      }
      if (node.left != null) {
         L = node.left.height;
      }
      node.height = Math.max(L, R) + 1;
      node.balanceFactor = L - R;
   }
   
   public boolean contains(T item) { //recursive call for contains
     return contains(item, root, false);
   }
   
   private boolean contains(T item, Node subTree, boolean flag) { //contains call that cycles down the tree to see if the item data exists in the tree
      if (subTree == null) {
         return false;
      }
      if (subTree.item.compareTo(item) == 0) {
         flag = true;
      }
      else {
         if (subTree.item.compareTo(item) > 0) {
            if (subTree.left != null) {
            flag = contains(item, subTree.left, flag); 
            }
         }
         else {        
            if (subTree.right != null) {
               flag = contains(item, subTree.right, flag);
            }
         }
      }
      compare++;//iterate compares
      return flag;
   }
   
   public int getHeight() {//returns height
      return root.height;
   }   
   
   public int height() { //recursive height call
      return height(this.root);
   }
   
   public int height(Node rootN) {//finds the maximum height of the roots child nodes
      if (rootN == null) {
         return -1;
      }
      return 1 + Math.max(height(rootN.left), height(rootN.right));
   }
   
   public int size(Node node) {//returns the number of elements in the tree
      if (node == null) {
         return 0;
      }
      else {
         return (size(node.left) + 1 + size(node.right));
      }
   }
   
   public boolean isEmpty() { //checks the tree for contents
      if (root == null) {
         return true;
      }
      else {
         return false;
      }
   }
   
   public void updateNodes(Node node) { // recursive update of height and balance factor 
      int Rh = -1;
      int Lh = -1;
      if (node.right != null) {
         updateNodes(node.right);
         Rh = height(node.right);
      }
      if (node.left != null) {
         updateNodes(node.left);
         Lh = height(node.left);
      }
      

      node.height = Math.max(Rh, Lh) + 1;
      node.balanceFactor = Lh - Rh;
      
      
   }
   
   private boolean updateStats(Node node) { //check the entire tree, breaks and returns true as soon as a imbalanced node is found
      boolean flag = false;
      if (node.balanceFactor > 1 || node.balanceFactor < -1) {
         return true;
      }
      if (node.left != null) {
         flag =  updateStats(node.left);
      }
      if (!flag) {
         if (node.right != null) {
            return updateStats(node.right);
         }
      }
      return flag;
      
   }
   
   private Node rotateRight(Node node) { //rotates node.right up to balance the contents of the tree
      Node rightNode = node.right;
      if (rightNode.balanceFactor == 1) { //RL and LR check 
         rightNode = rotateLeft(rightNode);
         rightNode.right.height = 0;
         rightNode.right.balanceFactor = 0; 
      }
      //update stats post rotate
      node.height = 0;
      node.balanceFactor = 0;
      node.right = rightNode.left;
      rightNode.left = node;
      if (rightNode.right == null) {
         rightNode.balanceFactor = rightNode.left.height;
         rightNode.height = rightNode.left.height;
      }
      else {
         rightNode.balanceFactor = rightNode.left.height - rightNode.right.height;
         rightNode.height = Math.max(rightNode.right.height, rightNode.left.height) + 1;      
      }
      rotations++;      
      return rightNode;
   }
   
   private Node rotateLeft(Node node) {// rotates node.left up to balance the contents of the tree
      Node leftNode = node.left;
      if (leftNode.balanceFactor == -1) { // RL and LR check 
         leftNode = rotateRight(leftNode);
         leftNode.left.height = 0;
         leftNode.left.balanceFactor = 0;
      }
      //update stats post rotate
      node.height = 0;
      node.balanceFactor = 0;
      node.left = leftNode.right;
      leftNode.right = node;
      if (leftNode.left == null) {
         leftNode.balanceFactor = -1 * leftNode.right.height;
         leftNode.height = leftNode.right.height;
      }
      else {
         leftNode.balanceFactor = leftNode.left.height - leftNode.right.height; 
         leftNode.height = Math.max(leftNode.left.height, leftNode.right.height) + 1;     
      }
      rotations++;
      

      return leftNode;
   }
   
   public void rebalance() { //recursive call for rebalance
      this.root = rebalance(this.root);
   }
   
   
   private Node rebalance(Node node) { //checks the current node for imbalance and calls rotate accordingly
      if (node.balanceFactor > 1 ) {
         node = rotateLeft(node);
         updateNodes(node);
      }
      else if (node.balanceFactor < -1) {
         node = rotateRight(node);
         updateNodes(node);
      }
      
      return node;
   }
  
  public String toString() { //recursive printout call
      return toString(this.root);
  }
  
   
  public String toString(Node node) { //returns pre-order traversal printout
      if (node == null) {
         return "";
      }
      else {
         if (node.left != null) {
           return toString(node.left);
         }
         if (node.right != null) {
           return toString(node.right);  
         }
         
      }
      return (node.item  + " ");
      
      
   }

}



/*if (root.balanceFactor > 1) {
              root.left = rebalance(root.left);
           }
           else {
              root.right = rebalance(root.right);
           }*/
           
           
           
           /*if (node.balanceFactor > 1 || node.balanceFactor < -1) {
         if (node.balanceFactor > 1) {
            node = rotateLeft(node);
         }
         else {
            node = rotateRight(node);
         }     
      }*/
      
      
      
      
      /*Node rightNode = node.right;
      if (rightNode.left != null && rightNode.right == null) {
         rightNode.right = rightNode.left;
         rightNode.left = null;
         Node temp = rightNode.right;
         Node temp2 = rightNode;
         rightNode = temp;
         rightNode.right = temp2;
         node.right = null;
      }
      else {
         node.right = rightNode.left;
      }
      rightNode.left = node;
      node = rightNode;
      node.height = height(node);
      
      this.rotations++;  
      return rightNode;*/
      
      
      
      /*Node leftNode = node.left;      
      if (leftNode.right != null && leftNode.left == null) {
         node.left = leftNode.right;
         T hold1 = leftNode.item;
         T hold2 = leftNode.right.item;
         leftNode.item = hold2;
         leftNode.right.item = hold1;
         node.left = null;
      }
      else {
         node.left = leftNode.right;
      }
      
      leftNode.right = node;
      node = leftNode;
      node.height = (height(node));
      
      this.rotations++;
      return leftNode;*/
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      /*if (node.balanceFactor < -1) {
         if (node.right.balanceFactor > 1 || node.right.balanceFactor < -1) {
            node.right = rebalance(node.right);
         }
         else {
            node = rotateRight(node);
         }
      }
      else if (node.balanceFactor > 1) {
         if (node.left.balanceFactor < -1 || node.left.balanceFactor > 1) {
            node.left = rebalance(node.left);
         }
         else {
            node = rotateLeft(node);
         }
      }
      else {
         if (node.right != null) {
            node.right = rebalance(node.right);         
         }
         
         if (node.left != null) {
            node.left = rebalance(node.left);         
         }    
      }  
      return node; */