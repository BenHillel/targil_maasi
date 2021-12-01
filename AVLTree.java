
/**
 *
 * AVLTree
 *
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {
	private IAVLNode root;
	private int length;
	
	public AVLTree() {
		this.root = new AVLNode();
		this.length = 0;
	}
  /**
   * public boolean empty()
   *
   * Returns true if and only if the tree is empty.
   *
   */
  public boolean empty() {
    return !getRoot().isRealNode();
  }

 /**
   * public String search(int k)
   *
   * Returns the info of an item with key k if it exists in the tree.
   * otherwise, returns null.
   */
  public String search(int k)
  {
	  // binary search
	  IAVLNode x = getRoot();
	  while (x != null) {
		  if (x.getKey() == k) {
			  return x.getValue();
		  }
		  else if (x.getKey() > k) {
			  x = x.getLeft();
		  }
		  else {
			  x = x.getRight();
		  }
	  }
	  return null;
  }

  /**
   * public int insert(int k, String i)
   *
   * Inserts an item with key k and info i to the AVL tree.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {
	   // initialize node
	   IAVLNode node = new AVLNode(k, i);
	   
	   // insert node to tree
	   if (empty()) {
		   this.root = node;
		   return 0;
	   }
	   
	   int result = treeInsert(this.getRoot(), node);
	   
	   // node already exists
	   if (result == -1) {
		   return -1;
	   }
	   
	   this.length++;
	   
	   // rebalance
	   return rebalance(node);
   }


  /**
   * public int delete(int k)
   *
   * Deletes an item with key k from the binary tree, if it is there.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k was not found in the tree.
   */
   public int delete(int k)
   {
	   IAVLNode node = treeSearch(k);
	   if (node == null) {
		   return -1;
	   }
	   node = treeDelete(node);
	   this.length--;
	   
	   
	   return delRebalance(node);	// to be replaced by student code
   }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    */
   public String min()
   {
	   IAVLNode x = getRoot();
	   while (x.getLeft().isRealNode()) {
		   x = x.getLeft();
	   }
	   return x.getValue();
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
   public String max()
   {
	   IAVLNode x = getRoot();
	   while (x.getRight().isRealNode()) {
		   x = x.getRight();
	   }
	   return x.getValue();
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()
  {
	  IAVLNode[] array = new IAVLNode[size()];
	  int index = 0;
	  
	  inOrder(getRoot(), array, index);
	  
	  int[] keys = new int[size()];
	  for (int i = 0; i < keys.length; i++) {
		  keys[i] = array[i].getKey();
	  }
	  
	  return keys;
  }

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray()
  {
	  IAVLNode[] array = new IAVLNode[size()];
	  int index = 0;
	  
	  inOrder(getRoot(), array, index);
	  
	  String[] values = new String[size()];
	  for (int i = 0; i < values.length; i++) {
		  values[i] = array[i].getValue();
	  }
	  
	  return values;
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    */
   public int size()
   {
	   return this.length;
   }
   
   /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    */
   public IAVLNode getRoot()
   {
	   return this.root;
   }
   
   /**
    * public AVLTree[] split(int x)
    *
    * splits the tree into 2 trees according to the key x. 
    * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
    * 
	* precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
    * postcondition: none
    */   
   public AVLTree[] split(int x)
   {
	   return null; 
   }
   
   /**
    * public int join(IAVLNode x, AVLTree t)
    *
    * joins t and x with the tree. 	
    * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	*
	* precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
    * postcondition: none
    */   
   public int join(IAVLNode x, AVLTree t)
   {
	   return -1;
   }
   
   // insert the node in the correct position
   private int treeInsert(IAVLNode startNode, IAVLNode newNode) {
	   IAVLNode parent = treePosition(startNode, newNode.getKey());
	   
	   // node already exists
	   if (newNode.getKey() == parent.getKey()) {
		   return -1;
	   }
	   
	   // insert the node
	   newNode.setParent(parent);
	   if (newNode.getKey() < parent.getKey()) {
		   parent.setLeft(newNode);
	   }
	   else {
		   parent.setRight(newNode);
	   }
	   return 1;
   }
   
   // find the position of the parent of the node to be inserted
   private IAVLNode treePosition(IAVLNode startNode, int key) {
	   IAVLNode parent = null;
	   while (startNode.isRealNode()) {
		   parent = startNode;
		   if (key == startNode.getKey()) {
			   return startNode;
		   }
		   else if (key < startNode.getKey() ) {
			   startNode = startNode.getLeft();
		   }
		   else {
			   startNode = startNode.getRight();
		   }
	   }
	   return parent;
   }
   
   // @return the parent of the node to be deleted
   // @post the tree without the node to be deleted 
   private IAVLNode treeDelete(IAVLNode node) {
	   // node is a leaf
	   if (node.getHeight() == 0) {
		   if (node.getParent().getLeft() == node) {
			   node.getParent().setLeft(new AVLNode());
		   }
		   else {
			   node.getParent().setRight(new AVLNode());
		   }
		   return node.getParent();
		   

	   }
	   // node has one child
	   else if (hasOneChild(node) != null) {
		   IAVLNode child = hasOneChild(node);
		   IAVLNode parent = node.getParent();
		   if (parent.getLeft() == node) {
			   parent.setLeft(child);
		   }
		   else {
			   parent.setRight(child);
		   }
		   child.setParent(parent);
		   return node.getParent();
	   }
	   // node has two children
	   else {
		   IAVLNode successor = min(node.getRight());
		   treeDelete(successor);
		   
		   successor.setLeft(node.getLeft());
		   successor.setRight(node.getRight());
		   successor.setParent(node.getParent());
		   node.getRight().setParent(successor);
		   node.getLeft().setParent(successor);
		   if (node.getParent().getLeft() == node) {
			   node.getParent().setLeft(node);
		   }
		   else {
			   node.getParent().setRight(node);
		   }
		   successor.setHeight(length);
		   return successor;
	   }
   }
   
   private IAVLNode min(IAVLNode node) {
	   if (!node.getLeft().isRealNode()) {
		   return node;
	   }
	   
	   return min(node.getLeft());
   }
   
   private IAVLNode hasOneChild(IAVLNode node) {
	   if (node.getRight().isRealNode() && !node.getLeft().isRealNode()) {
		   return node.getRight();
	   }
	   else if (!node.getRight().isRealNode() && node.getLeft().isRealNode()) {
		   return node.getLeft();
	   }
	   return null;
   }
   
   private int rebalance(IAVLNode curr) {
	   int counter = 0;
	   while (curr.getParent() != null) {
		   IAVLNode parent = curr.getParent();
		   if (parent.getHeight() - curr.getHeight() == 0) {
			   // case 1
			   if (Math.abs(getBalanceFactor(parent)) == 1) {
				   promote(parent);
				   counter++;
				   curr = curr.getParent();
				   continue;
			   }
			   
			   // cases 2 + 3
			   if (Math.abs(getBalanceFactor(parent)) == 2) {
				   // we are left child
				   if (parent.getLeft() == curr) {
					   if (getBalanceFactor(curr) == -1) {
						   // case 2
						   // rotate right
						   rotateRight(curr);
						   // demote parent
						   demote(parent);
						   counter += 2;
					   }
					   else if (getBalanceFactor(curr) == 1) {
						   // case 3
						   // demote curr, parent
						   demote(curr);
						   demote(parent);
						   // promote curr.right
						   promote(curr.getRight());
						   // double rotation (LR)
						   rotateLeft(curr.getRight());
						   rotateRight(curr.getParent());
						   counter += 5;
					   }
				   }
				   else if (parent.getRight() == curr) {
					   if (getBalanceFactor(curr) == 1) {
						   // case 2
						   // rotate left
						   rotateLeft(curr);
						   // demote parent
						   demote(parent);
						   counter += 2;
					   }
					   else if (getBalanceFactor(curr) == -1) {
						   // case 3
						   // demote curr, parent
						   demote(curr);
						   demote(parent);
						   // promote curr.left
						   promote(curr.getLeft());
						   // double rotation (LR)
						   rotateRight(curr.getLeft());
						   rotateLeft(curr.getParent());
						   counter += 5;
					   }
				   }
				   break;
			   }
		   }
		   else {
			   break;
		   }
	   }
	   return counter;
   }
   
   private int delRebalance(IAVLNode curr) {
	   int counter = 0;
	  while(curr != null) { 
		  //if the rank differences are 2,2 we demote the parent
		  if(rankDiff(curr.getLeft()) == 2 && rankDiff(curr.getRight()) == 2) {
			  demote(curr);
			  counter++;
			  curr = curr.getParent();
			  continue;
		  }
		  //If the rank differences are 3, 1 
		  if(getBalanceFactor(curr) == 2) {
			  IAVLNode rightSon = curr.getRight();
			  //Case 2: if the rank differences of the right child are the same
			  if(getBalanceFactor(curr.getRight()) == 0) {
				  rotateLeft(rightSon);
				  demote(curr);
				  promote(rightSon);
				  counter += 3;
				  break; // problem solved!
			  }
			  //Case 3: if the rank differences of the right child are 2, 1
			  else if(getBalanceFactor(curr.getRight()) == 1) {
				  rotateLeft(rightSon);
				  demote(curr,2);
				  counter += 2;
				  curr = rightSon.getParent();
				  continue; // problem not necessarily solved :( 
			  }
			  //Case 4: if the rank differences of the right child are 1, 2
			  else if(getBalanceFactor(curr.getRight()) == -1){
				  IAVLNode grandson = rightSon.getLeft();
				  rotateRight(grandson);
				  rotateLeft(grandson);
				  demote(curr,2);
				  demote(rightSon);
				  promote(grandson);
				  counter += 5;
				  curr = grandson.getParent();
				  continue; // problem not necessarily solved :(
			  }
		  }
		  
		  //If the rank differences are 1, 3 
		  if(getBalanceFactor(curr) == 2) {
			  IAVLNode leftSon = curr.getLeft();
			  //Case 2: if the rank differences of the right child are the same
			  if(getBalanceFactor(leftSon) == 0) {
				  rotateRight(leftSon);
				  demote(curr);
				  promote(leftSon);
				  counter += 3;
				  break; // problem solved!
			  }
			  //Case 3: if the rank differences of the right child are 1, 2
			  else if(getBalanceFactor(leftSon) == -1) {
				  rotateRight(leftSon);
				  demote(curr,2);
				  counter += 2;
				  curr = leftSon.getParent();
				  continue; // problem not necessarily solved :( 
			  }
			  //Case 4: if the rank differences of the right child are 2, 1
			  else if(getBalanceFactor(leftSon) == 1){
				  IAVLNode grandson = leftSon.getRight();
				  rotateLeft(grandson);
				  rotateRight(grandson);
				  demote(curr,2);
				  demote(leftSon);
				  promote(grandson);
				  counter += 5;
				  curr = grandson.getParent();
				  continue; // problem not necessarily solved :(
			  }
		  }
	  }
	  return counter;
   }
   
   
   private IAVLNode treeSearch(int k) {
 	  // binary search
 	  IAVLNode x = getRoot();
 	  while (x != null) {
 		  if (x.getKey() == k) {
 			  return x;
 		  }
 		  else if (x.getKey() > k) {
 			  x = x.getLeft();
 		  }
 		  else {
 			  x = x.getRight();
 		  }
 	  }
 	  return null;
   }
   
   private void promote(IAVLNode node) {
	   promote(node,1);
   }
   
   private void demote(IAVLNode node) {
	   demote(node,1);
   }
   
   private void promote(IAVLNode node,int n) {
	   node.setHeight(node.getHeight()+n);
   }
   
   private void demote(IAVLNode node,int n) {
	   node.setHeight(node.getHeight()-n);
   }
   
   private int getBalanceFactor(IAVLNode node) {
	   return node.getLeft().getHeight() - node.getRight().getHeight();
   }
   
   private int rankDiff(IAVLNode node) {
	   return node.getParent().getHeight() - node.getHeight();
   }
   // rotate the tree around the given node and doesn't update the heights 
   private void rotateRight(IAVLNode node) {
	   IAVLNode parent = node.getParent();
	   parent.setLeft(node.getRight());
	   node.getRight().setParent(parent);
	   //if parent is the tree root
	   if(parent.getParent() == null) {
		   this.root = node;
		   node.setParent(null);
	   }
	   // if the parent is a left child
	   else if(parent.getParent().getLeft() == parent) {
		   parent.getParent().setLeft(node);
		   node.setParent(parent.getParent());
	   }
	   // if the parent is a right child
	   else {
		   parent.getParent().setRight(node);
		   node.setParent(parent.getParent());
	   }
	   node.setRight(parent);
	   parent.setParent(node);
   }
   
   
   // rotate the tree around the given node and doesn't update the heights 
   private void rotateLeft(IAVLNode node) {
	   IAVLNode parent = node.getParent();
	   parent.setRight(node.getLeft());
	   node.getLeft().setParent(parent);
	   //if parent is the tree root
	   if(parent.getParent() == null) {
		   this.root = node;
		   node.setParent(null);
	   }
	   // if the parent is a left child
	   else if(parent.getParent().getLeft() == parent) {
		   parent.getParent().setLeft(node);
		   node.setParent(parent.getParent());
	   }
	   // if the parent is a right child
	   else {
		   parent.getParent().setRight(node);
		   node.setParent(parent.getParent());
	   }
	   node.setLeft(parent);
	   parent.setParent(node);
   }
   
   private void inOrder(IAVLNode x, IAVLNode[] array, int index) {
	   if (!x.isRealNode()) {
		   return;
	   }
	   
	   inOrder(x.getLeft(), array, index);
	   array[index++] = x;
	   inOrder(x.getRight(), array, index);
   }
   
   /** 
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 */
	public interface IAVLNode{	
		public int getKey(); // Returns node's key (for virtual node return -1).
		public String getValue(); // Returns node's value [info], for virtual node returns null.
		public void setLeft(IAVLNode node); // Sets left child.
		public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.
		public void setRight(IAVLNode node); // Sets right child.
		public IAVLNode getRight(); // Returns right child, if there is no right child return null.
		public void setParent(IAVLNode node); // Sets parent.
		public IAVLNode getParent(); // Returns the parent, if there is no parent return null.
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.
    	public void setHeight(int height); // Sets the height of the node.
    	public int getHeight(); // Returns the height of the node (-1 for virtual nodes).
	}

   /** 
    * public class AVLNode
    *
    * If you wish to implement classes other than AVLTree
    * (for example AVLNode), do it in this file, not in another file. 
    * 
    * This class can and MUST be modified (It must implement IAVLNode).
    */
  public class AVLNode implements IAVLNode{
	  private int key;
	  private String info;
	  private IAVLNode right;
	  private IAVLNode left;
	  private IAVLNode parent;
	  private int height;
	  
	  // creates a virtual node
	  public AVLNode() {
		  this.key = -1;
		  this.height = -1;
	  }
	  
	  // creates a real node
	  public AVLNode(int k, String i) {
		  this.key = k;
		  this.info = i;
		  this.left = new AVLNode();
		  this.right = new AVLNode();
		  this.height = 0;
	  }
	  
		public int getKey()
		{
			return this.key;
		}
		public String getValue()
		{
			return this.info;
		}
		public void setLeft(IAVLNode node)
		{
			this.left = node;
		}
		public IAVLNode getLeft()
		{
			return this.left;
		}
		public void setRight(IAVLNode node)
		{
			this.right = node;
		}
		public IAVLNode getRight()
		{
			return this.right;
		}
		public void setParent(IAVLNode node)
		{
			this.parent = node;
		}
		public IAVLNode getParent()
		{
			return this.parent;
		}
		public boolean isRealNode()
		{
			return this.key != -1;
		}
	    public void setHeight(int height)
	    {
	      this.height = height;
	    }
	    public int getHeight()
	    {
	      return this.height;
	    }
  }
}
  
