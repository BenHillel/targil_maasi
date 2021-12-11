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
	
	// creates an empty tree.
	public AVLTree() {
		this.root = new AVLNode();
		this.length = 0;
	}
	
	// creates a tree with root node.
	public AVLTree(IAVLNode root) {
		this.root = root;
		this.root.setParent(null);
		this.length = this.root.getSize();
	}
	
  /**
   * public boolean empty()
   *
   * Returns true if and only if the tree is empty.
   *
   */
  public boolean empty() {
	// COMPLEXITY: O(1).
    return !this.getRoot().isRealNode();
  }

 /**
   * public String search(int k)
   *
   * Returns the info of an item with key k if it exists in the tree.
   * otherwise, returns null.
   */
  public String search(int k)
  {
	  // COMPLEXITY: O(log(n)).
	  
	  // finds node with key k.
	  // treeSearch complexity: O(log(n)).
	  IAVLNode x = treeSearch(k);
	  
	  return x == null ? null : x.getValue();
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
	   // COMPLEXITY: O(log(n))
	   
	   // create new node.
	   IAVLNode node = new AVLNode(k, i);
	   
	   // insert node as root.
	   if (empty()) {
		   this.root = node;
		   this.length++;
		   return 0;
	   }
	   
	   // regular BST insert.
	   // treeInsert complexity: O(log(n)).
	   int result = treeInsert(this.getRoot(), node);
	   
	   // node already exists in tree.
	   if (result == -1) {
		   return -1;
	   }
	   
	   this.length++;
	   
	   // rebalance after insertion.
	   // rebalance complexity: O(log(n)).
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
	   // COMPLEXITY: O(log(n)).
	   
	   // find node to delete.
	   IAVLNode node = treeSearch(k);
	   
	   // node not found in tree.
	   if (node == null) {
		   return -1;
	   }
	   
	   // regular BST delete
	   // treeDelete worst case complexity (when finding successor): O(log(node.height)).
	   node = treeDelete(node);
	   this.length--;
	   
	   if (node == null) {
		   return 0;
	   }
	   
	   // rebalance tree after deletion.
	   // delRebalance complexity: O(log(n)).
	   return delRebalance(node);
   }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    */
   public String min()
   {
	   // COMPLEXITY: O(log(n)).
	   
	   if (this.empty()) {
		   return null;
	   }
	   
	   // find node with smallest key.
	   // min complexity: O(log(n)) (worst case min is with depth=height of tree).
	   IAVLNode minNode = min(this.getRoot());
	   
	   return minNode.getValue();
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
   public String max()
   {
	   // COMPLEXITY: O(log(n)).
	   
	   if (this.empty()) {
		   return null;
	   }
	   
	   // find node with largest key.
	   // max complexity: O(log(n)) (worst case max is leaf with depth=height of tree).
	   IAVLNode maxNode = max(this.getRoot());
	   
	   return maxNode.getValue();
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()
  {
	  // COMPLEXITY: O(n).
	  
	  IAVLNode[] array = new IAVLNode[size()];
	  int[] index = new int[1];
	  index[0] = 0;
	  
	  // inOrder complexity: O(n). We visit all nodes in the tree.
	  inOrder(getRoot(), array, index);
	  
	  // copy n keys to array. complexity: O(n).
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
	  // COMPLEXITY: O(n).
	  
	  IAVLNode[] array = new IAVLNode[size()];
	  int[] index = new int[1];
	  index[0] = 0;
	  
	  // inOrder complexity: O(n). We visit all nodes in the tree.
	  inOrder(getRoot(), array, index);
	  
	  // copy n values to array. complexity: O(n).
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
	   // COMPLEXITY: O(1).
	   
	   return this.length;
   }
   
   /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    */
   public IAVLNode getRoot()
   {
	   // COMPLEXITY: O(1).
	   
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
	   // NEED TO FINISH.
	   
	   
	   IAVLNode xNode = treeSearch(x);
	   
	   IAVLNode xLeft = xNode.getLeft();
	   IAVLNode xRight = xNode.getRight();
	   
	   AVLTree leftTree = new AVLTree(xLeft);
	   AVLTree rightTree = new AVLTree(xRight);
	   
	   IAVLNode parent = xNode.getParent();
	   
	   while (parent != null) {
		   IAVLNode next = parent.getParent();
		   if (parent.getKey() < xNode.getKey()) {
			  AVLTree tempTree = new AVLTree(parent.getLeft());
			  IAVLNode joinNode = new AVLNode(parent.getKey(), parent.getValue());
			  leftTree.join(joinNode, tempTree);
		   }
		   else {
			   AVLTree tempTree = new AVLTree(parent.getRight());
			   IAVLNode joinNode = new AVLNode(parent.getKey(), parent.getValue());
			   rightTree.join(joinNode, tempTree);
		   }
		   xNode = parent;
		   parent = next;
	   }
	   
	   return new AVLTree[] {leftTree, rightTree};
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
	   // COMPLEXITY: O(|tree.rank - t.rank| + 1).
	   
	   int returnValue = Math.abs(this.getRank() - t.getRank())+1;
	   
	   //determine if t's nodes are larger or smaller than x
	   if(t.getRoot().getKey() < x.getKey()) { // t < x 
		   if(t.getRoot().getHeight() < this.getRoot().getHeight()) { //rank(t) < rank(this)
			   IAVLNode tRoot = t.getRoot();
			   IAVLNode joined = this.getRoot();
			   int tRootHeight = tRoot.getHeight();
			   if(t.empty()) { //insert x in this 
				   this.treeInsert(this.getRoot(),x);
			   } 
			   else { // if t is not empty here, then this is not empty as well
				   tRoot.setParent(x);
				   x.setLeft(tRoot);
				   x.setHeight(tRoot.getHeight()+1);
				   //find node in this (its most left branch) with the same height as tRoot (or one less)
				   while(joined.isRealNode() && joined.getHeight() != tRootHeight && joined.getHeight() != tRootHeight-1) {
					   joined = joined.getLeft();
				   }
				   //insert x between joined and its parent
				   joined.getParent().setLeft(x);
				   x.setParent(joined.getParent());
				   joined.setParent(x);
				   x.setRight(joined);
			   }
			   //Do rebalancing from x
			   this.rebalance(x);
			   this.length += t.size()+1;
			   return returnValue;
		   }
		   else{// rank(t) >= rank(this)
			   if(this.empty()) {
				   if(t.empty()) { //if this.size <= t.size then t could be empty only if this is empty
					   //if both trees are empty then we only insert x
					   this.root = x;
					   this.length++;
					   return 1;
				   }
				   else {// only this is empty
					   //set this to be t
					   this.root = t.getRoot();
					   this.length = t.size();
					   //then insert x
					   this.treeInsert(this.getRoot(),x);
					   this.rebalance(x);
					   return returnValue;
				   }
			   }
			   else { //None of the trees are empty
				   //Here we do the same as before but here t and this switched places
				   //thus, after we finish we will change the root of this to the root of t
				   IAVLNode root = this.getRoot();
				   IAVLNode joined = t.getRoot();
				   int rootHeight = root.getHeight();
				   
				   root.setParent(x);
				   x.setRight(root);
				   x.setHeight(root.getHeight()+1);
				   //find node in t (its most right branch) with the same height as root (or one less)
				   while(joined.isRealNode() && joined.getHeight() != rootHeight && joined.getHeight() != rootHeight-1) {
					   joined = joined.getRight();
				   }
				   if(joined.getParent() == null) {//then add x as the root
					   x.setLeft(joined);
					   joined.setParent(x);
					   this.root = x;
					   this.length += t.length+1;  
				   }else {
					   //insert x between joined and its parent
					   joined.getParent().setRight(x);
					   x.setParent(joined.getParent());
					   joined.setParent(x);
					   x.setLeft(joined);
					   
					   this.root = t.getRoot();
					   this.length += t.length+1;
					   this.rebalance(x);
				   }

			   }
			   return returnValue;
		   }
	   }
	   else {// x < t
		   // here we perform the same actions but we flip all of the directions
		   if(t.getRoot().getHeight() < this.getRoot().getHeight()) { //rank(t) < rank(this)
			   IAVLNode tRoot = t.getRoot();
			   IAVLNode joined = this.getRoot();
			   int tRootHeight = tRoot.getHeight();
			   if(t.empty()) { //insert x in this 
				   this.treeInsert(this.getRoot(),x);
			   } 
			   else { // if t is not empty here, then this is not empty as well
				   tRoot.setParent(x);
				   x.setRight(tRoot);
				   x.setHeight(tRoot.getHeight()+1);
				   //find node in this (its most right branch) with the same height as tRoot (or one less)
				   while(joined.isRealNode() && (joined.getHeight() != tRootHeight || joined.getHeight() != tRootHeight-1)) {
					   joined = joined.getRight();
				   }
				   //insert x between joined and its parent
				   joined.getParent().setRight(x);
				   x.setParent(joined.getParent());
				   joined.setParent(x);
				   x.setLeft(joined);
			   }
			   //Do rebalancing from x
			   this.rebalance(x);
			   this.length += t.size()+1;
			   return returnValue;
		   }
		   else{// rank(t) >= rank(this)
			   if(this.empty()) {
				   if(t.empty()) { //if this.size <= t.size then t could be empty only if this is empty
					   //if both trees are empty then we only insert x
					   this.root = x;
					   this.length++;
					   return 1;
				   }
				   else {// only this is empty
					   //set this to be t
					   this.root = t.getRoot();
					   this.length = t.size()+1;
					   //then insert x
					   this.treeInsert(this.getRoot(),x);
					   this.rebalance(x);
					   return returnValue;
				   }
			   }
			   else { //None of the trees are empty
				   //Here we do the same as before but here t and this switched places 
				   //thus, after we finish we will change the root of this to the root of t
				   IAVLNode root = this.getRoot();
				   IAVLNode joined = t.getRoot();
				   int rootHeight = root.getHeight();
				   
				   root.setParent(x);
				   x.setLeft(root);
				   x.setHeight(root.getHeight()+1);
				   //find node in t (its most right branch) with the same height as root (or one less)
				   while(joined.isRealNode() && (joined.getHeight() != rootHeight || joined.getHeight() != rootHeight-1)) {
					   joined = joined.getLeft();
				   }
				   if(joined.getParent() == null) {
					   x.setRight(joined);
					   joined.setParent(x);
					   this.root = x;
					   this.length += t.length+1;
				   }else {
					   //insert x between joined and its parent
					   joined.getParent().setLeft(x);
					   x.setParent(joined.getParent());
					   joined.setParent(x);
					   x.setRight(joined);
				   
					   this.root = t.getRoot();
					   this.length += t.length+1;
					   this.rebalance(x);
				   }
			   }
			   return returnValue;
		   }
	   }
	   
   }
   
   //================================================================== ||
   //==========================OUR_FUNCTIONS=========================== ||
   //================================================================== \/
   
   // finds node with key k.
   // returns null if there is no such key in the tree.
   // complexity: O(log(n)).
   private IAVLNode treeSearch(int k) {	   
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
   
   // insert the node in the correct position.
   // complexity: O(log(n)).
   private int treeInsert(IAVLNode startNode, IAVLNode newNode) {
	   IAVLNode parent = treePosition(startNode, newNode.getKey());
	   
	   // node already exists.
	   if (newNode.getKey() == parent.getKey()) {
		   return -1;
	   }
	   
	   // insert the node.
	   newNode.setParent(parent);
	   if (newNode.getKey() < parent.getKey()) {
		   parent.setLeft(newNode);
	   }
	   else {
		   parent.setRight(newNode);
	   }
	   return 1;
   }
   
   // find the position of the parent of the node to be inserted.
   // complexity: O(log(n)).
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
   
   // return the parent of the node to be deleted.
   // post the tree without the node to be deleted.
   // complexity: O(log(node.height)).
   private IAVLNode treeDelete(IAVLNode node) {
	   IAVLNode parent = node.getParent();
	   
	   // node to delete is a leaf.
	   if (node.isLeaf()) {
		   if (parent == null) {
			   this.root = null;
		   }
		   else {
			   if (parent.getKey() > node.getKey()) {
				   parent.setLeft(new AVLNode());
				   parent.getLeft().setParent(parent);
			   }
			   else { 
				   parent.setRight(new AVLNode());
				   parent.getRight().setParent(parent);
			   }
		   }
		   return parent;
	   }
	   
	   // node to delete has one child.
	   else if (node.isUnary() != null) {
		   IAVLNode child = node.isUnary();
		   if (parent == null) {
			   this.root = child;
			   child.setParent(null);
		   }
		   else {
			   if (parent.getKey() > node.getKey()) {
				   parent.setLeft(child);
			   }
			   else {
				   parent.setRight(child);
			   }
			   child.setParent(parent);
		   }
		   return parent;
	   }
	   
	   // node to delete has two children.
	   else {
		   // finding successor takes O(node.height);
		   IAVLNode successor = min(node.getRight());
		   
		   // successor is right child of node to delete.
		   if (successor == node.getRight()) {
			   successor.setLeft(node.getLeft());
			   successor.getLeft().setParent(successor);
			   if (parent == null) {
				   this.root = successor;
				   successor.setParent(null);
			   }
			   else {
				   successor.setParent(parent);
				   if (parent.getKey() > node.getKey()) {
					   parent.setLeft(successor);
				   }
				   else {
					   parent.setRight(successor);
				   }
			   }
			   successor.setHeight(node.getHeight());
			   return successor;
		   }
		   
		   // successor is a child in the right subtree.
		   else {
			   IAVLNode sucParent = successor.getParent();
			   if (successor.isLeaf()) {
				   sucParent.setLeft(new AVLNode());
				   sucParent.getLeft().setParent(sucParent);
			   }
			   else {
				   successor.getRight().setParent(sucParent);
				   sucParent.setLeft(successor.getRight());
			   }
			   successor.setRight(node.getRight());
			   successor.setLeft(node.getLeft());
			   successor.getRight().setParent(successor);
			   successor.getLeft().setParent(successor);
			   if (parent == null) {
				   successor.setParent(null);
				   this.root = successor;
			   }
			   else {
				   successor.setParent(parent);
				   if (parent.getKey() > node.getKey()) {
					   parent.setLeft(successor);
				   }
				   else {
					   parent.setRight(successor);
				   } 
			   }
			   successor.setHeight(node.getHeight());
			   return sucParent;  
		   }
	   }
   }
   
   // finds node with minimal key.
   // complexity: O(log(n)).
   private IAVLNode min(IAVLNode node) {
	   if (!node.getLeft().isRealNode()) {
		   return node;
	   }
	   
	   return min(node.getLeft());
   }
   
   // finds node with maximal key.
   // complexity: O(log(n)).
   private IAVLNode max(IAVLNode node) {
	   if (!node.getRight().isRealNode()) {
		   return node;
	   }
	   
	   return min(node.getRight());
   }

   // rebalance tree from curr node up to the root.
   // complexity: O(log(n)).
   private int rebalance(IAVLNode curr) {
	   int counter = 0;
	   
	   // going up to the root.
	   while (curr.getParent() != null) {
		   IAVLNode parent = curr.getParent();
		   if (parent.getHeight() - curr.getHeight() == 0) {
			   // case 1.
			   if (Math.abs(getBalanceFactor(parent)) == 1) {
				   parent.promote();
				   counter++;
				   curr = curr.getParent();
				   continue;
			   }
			   
			   // cases 2 + 3.
			   if (Math.abs(getBalanceFactor(parent)) == 2) {
				   // we are left child.
				   if (parent.getLeft() == curr) {
					   if (getBalanceFactor(curr) == -1) {
						   // case 2
						   rotateRight(curr);
						   parent.demote();
						   counter += 2;
					   }
					   else if (getBalanceFactor(curr) == 1) {
						   // case 3.
						   curr.demote();
						   parent.demote();
						   curr.getRight().promote();
						   rotateLeft(curr.getRight());
						   rotateRight(curr.getParent());
						   counter += 5;
					   }
					   else if(getBalanceFactor(curr) == 0) {
							// special case, could happen only after - join().
							parent.demote();
							rotateRight(curr);
							continue;
										   
						}
				   }
				   // we are right child.
				   else if (parent.getRight() == curr) {
					   if (getBalanceFactor(curr) == 1) {
						   // case 2.
						   rotateLeft(curr);
						   parent.demote();
						   counter += 2;
					   }
					   else if (getBalanceFactor(curr) == -1) {
						   // case 3.
						   curr.demote();
						   parent.demote();
						   curr.getLeft().promote();
						   rotateRight(curr.getLeft());
						   rotateLeft(curr.getParent());
						   counter += 5;
					   }
					   else if(getBalanceFactor(curr) == 0) {
							// special case, could happen only after - join()
							parent.demote();
							rotateLeft(curr);
							continue;
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
   
   
   // rebalance tree after deletion of node curr.
   // complexity: O(log(n)).
   private int delRebalance(IAVLNode curr) {
	   int counter = 0;
	   
	   // going up to the root.
	   while (curr != null) {
		   int bf = curr.getBalanceFactor();
		   if (bf == -2) {
			   IAVLNode right = curr.getRight();
			   if (right.getBalanceFactor() == -1 || right.getBalanceFactor() == 0) {
				   rotateLeft(right);
				   curr.updateHeight();
				   right.updateHeight();
				   curr = right.getParent();
				   counter += 3;
			   }
			   else if (right.getBalanceFactor() == 1) {
				   IAVLNode son = right.getLeft();
				   rotateRight(son);
				   rotateLeft(son);
				   right.updateHeight();
				   curr.updateHeight();
				   son.updateHeight();
				   curr = son.getParent();
				   counter += 5;
			   }
		   }
		   else if (bf == 2) {
			   IAVLNode left = curr.getLeft();
			   if (left.getBalanceFactor() == -1) {
				   IAVLNode son = left.getRight();
				   rotateLeft(son);
				   rotateRight(son);
				   left.updateHeight();
				   curr.updateHeight();
				   son.updateHeight();
				   curr = son.getParent();
				   counter += 5;
			   }
			   else if (left.getBalanceFactor() == 1 || left.getBalanceFactor() == 0) {
				   rotateRight(left);
				   curr.updateHeight();
				   left.updateHeight();
				   curr = left.getParent();
				   counter += 3;
			   }
		   }
		   else {
			   // need to update height of node.
			   if (!curr.isHeightUpdated()) {
				   curr.updateHeight();
				   counter++;
			   }
			   curr = curr.getParent();
		   }
	   }
	   return counter;
   }
   
   private int getBalanceFactor(IAVLNode node) {
	   return node.getRight().getHeight() - node.getLeft().getHeight();
   }

   
   // rotate right around node and it's parent.
   // complexity: O(1) (pointers).
   private void rotateRight(IAVLNode node) {
	   IAVLNode parent = node.getParent();
	   parent.setLeft(node.getRight());
	   node.getRight().setParent(parent);
	   //if parent is the tree root.
	   if(parent.getParent() == null) {
		   this.root = node;
		   node.setParent(null);
	   }
	   // if the parent is a left child.
	   else if(parent.getParent().getLeft() == parent) {
		   parent.getParent().setLeft(node);
		   node.setParent(parent.getParent());
	   }
	   // if the parent is a right child.
	   else {
		   parent.getParent().setRight(node);
		   node.setParent(parent.getParent());
	   }
	   node.setRight(parent);
	   parent.setParent(node);
   }
   
   // rotate left around node and it's parent.
   // complexity: O(1) (pointers).
   private void rotateLeft(IAVLNode node) {
	   IAVLNode parent = node.getParent();
	   parent.setRight(node.getLeft());
	   node.getLeft().setParent(parent);
	   //if parent is the tree root.
	   if(parent.getParent() == null) {
		   this.root = node;
		   node.setParent(null);
	   }
	   // if the parent is a left child.
	   else if(parent.getParent().getLeft() == parent) {
		   parent.getParent().setLeft(node);
		   node.setParent(parent.getParent());
	   }
	   // if the parent is a right child.
	   else {
		   parent.getParent().setRight(node);
		   node.setParent(parent.getParent());
	   }
	   node.setLeft(parent);
	   parent.setParent(node);
   }
   
   // in order scan of the tree.
   // complexity: O(n).
   private void inOrder(IAVLNode x, IAVLNode[] array, int[] index) {
	   if (!x.isRealNode()) {
		   return;
	   }
	   
	   inOrder(x.getLeft(), array, index);
	   array[index[0]] = x;
	   index[0]++;
	   inOrder(x.getRight(), array, index);
   }
   
   // get the rank of the tree.
   // complexity: O(1).
   public int getRank() {
	   return this.getRoot().getHeight();
   }
   
   
   // TO BE REMOVED!!!!!
   public void print(IAVLNode node, int level) { /////aux tester func
		if (node == null) {
			return;
		}
		//System.out.println("Level: " + level + " , " + node.getKey() + " , " + node.getValue());
		if ((node.getKey() != -1) && ((node.getBalanceFactor() >= 2) || (node.getBalanceFactor() <= -2))) {
			System.out.println("node" + node.getKey() + " is unbalanced. BF is " + node.getBalanceFactor() + ". sons are "
					+ node.getLeft().getKey() + " rank=" + node.getLeft().getHeight() + " " + node.getRight().getKey() + " rank=" + node.getRight().getHeight()
					+ "*********");
		} else if (((node.getRight() != null) && (node.getRight().getKey() < node.getKey()) && (node.getRight().getKey() != -1))
				|| ((node.getLeft() != null) && (node.getLeft().getKey() > node.getKey()) && (node.getLeft().getKey() != -1))) {
			System.out.println("node" + node.getKey() + "is not in order********");
		} else if ((node.getHeight() == -1) && ((node.getRight() != null) || (node.getLeft() != null))) {
			System.out.println("virtual son of" + node.getParent().getKey() + " has a son*********");
		} else if ((node.getRight() != null) && (node.getRight().getParent() != node)
				|| ((node.getLeft() != null) && (node.getLeft().getParent() != node))) {
			System.out.println("one of" + node.getKey() + "sons is not connected to him*********");
		} else if ((node.getParent() != null) && (node.getParent().getRight() != node) && (node.getParent().getLeft() != node)) {
			System.out.println("node" + node.getKey() + " is not connected to parent********");
		} else if ((node.getHeight() != -1) && (node.getHeight() != (Math.max(node.getLeft().getHeight(), node.getRight().getHeight()) + 1))) {
			System.out.println("node" + node.getKey() + " rank is not adjusted, his rank is" + node.getHeight());
			System.out.println("sons are" + node.getLeft().getKey() + " rank " + node.getLeft().getHeight() + " and " + node.getRight().getKey()
					+ " rank " + node.getRight().getHeight());
		}

		if (node.getLeft() != null)
			print((AVLNode) (node.getLeft()), level + 1);
		if (node.getRight() != null)
			print((AVLNode) node.getRight(), level + 1);
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
    	public int getBalanceFactor(); // Returns the balance factor of the node.
    	public int getSize(); // Returns the size of the subtree that the node is it's root.
    	public boolean isLeaf(); // Returns if the node is a leaf.
    	public IAVLNode isUnary(); // Returns unary child if node has one child.
    	public void promote(int num); // Promotes node's rank by num.
    	public void demote(int num); // Demotes node's rank by num.
    	default void promote() {
    		promote(1);
    	}
    	default void demote() {
    		demote(1);
    	}
    	public int rankDiffRight(); // Returns the rank difference with right child.
    	public int rankDiffLeft(); // Returns the rank difference with left child.
    	public boolean isHeightUpdated(); // Returns if height field is updated according to children.
    	public void updateHeight(); // Updates the height according to children.
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
		  
		  //set the virtual nodes parent to this node
		  this.left.setParent(this);
		  this.right.setParent(this);
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
	    public int getBalanceFactor() {
	    	return  (this.getLeft() == null ? 0 : this.getLeft().getHeight()) - (this.getRight() == null ? 0 : this.getRight().getHeight());
	    }  
	    public int getSize() {
	    	if (!this.isRealNode()) {
	    		return 0;
	    	}
    		int leftSize = 0, rightSize = 0;
    		if (this.getLeft().isRealNode()) {
    			leftSize = this.getLeft().getSize();
    		}
    		if (this.getRight().isRealNode()) {
    			rightSize = this.getRight().getSize();
    		}
    		return leftSize + rightSize + 1;
	    }
    	public boolean isLeaf() {
    		return !this.getLeft().isRealNode() && !this.getRight().isRealNode();
    	}
    	public IAVLNode isUnary() {
    		if (this.right.isRealNode() && !this.left.isRealNode()) {
			   return this.right;
		    }
		    else if (!this.right.isRealNode() && this.left.isRealNode()) {
			   return this.left;
		    }
		    return null;
    	}
    	public void promote(int num) {
    		this.setHeight(this.getHeight()+num);
    	}
    	public void demote(int num) {
    		this.setHeight(this.getHeight()-num);
    	}
    	public int rankDiffRight() {
    		return this.getHeight() - this.right.getHeight();
    	}
    	public int rankDiffLeft() {
    		return this.getHeight() - this.left.getHeight();
    	}
    	public boolean isHeightUpdated() {
    		return this.height == Math.max(this.getLeft().getHeight(), this.getRight().getHeight()) + 1;
    	}
    	public void updateHeight() {
    		this.setHeight(Math.max(this.getLeft().getHeight(), this.getRight().getHeight()) + 1);
    	}
  }
}
  
