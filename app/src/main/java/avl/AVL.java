///Author: Malaki-Jacob Taub
///Date: 7/16/2025
///Description: Contains all BST and AVL Operations
package avl;

public class AVL {

  public Node root;

  private int size;

  public int getSize() {
    return size;
  }
  public int balance(Node n){
    return getHeight(n.right) - getHeight(n.left);
  }
  /** find w in the tree. return the node containing w or
  * null if not found */
  public Node search(String w) {
    return search(root, w);
  }
  private Node search(Node n, String w) {
    if (n == null) {
      return null;
    }
    if (w.equals(n.word)) {
      return n;
    } else if (w.compareTo(n.word) < 0) {
      return search(n.left, w);
    } else {
      return search(n.right, w);
    }
  }

  /** insert w into the tree as a standard BST, ignoring balance */
  public void bstInsert(String w) {
    if (root == null) {
      root = new Node(w);
      size = 1;
      return;
    }
    bstInsert(root, w);
  }

  /* insert w into the tree rooted at n, ignoring balance
   * pre: n is not null */
  private void bstInsert(Node n, String w) {
    Node insert = new Node(w);
    Node nd;
    int h;
    if(n.word.compareTo(w)>0){
      //In the event insert goes left
      if(n.left!=null){
        bstInsert(n.left, w);
      }
      else{
        size += 1;
        n.left = insert;
        insert.parent = n;
        nd = insert;
        h = 0;
        while(nd.parent!=null){
          if(h>nd.height){
            nd.height = h;
          }
          nd=nd.parent;
          h++;
        }
      }
    }
    else if(n.word.compareTo(w)<0){
      //In the event insert goes right
      if(n.right!=null){
        bstInsert(n.right, w);
      }
      else{
        size += 1;
        n.right = insert;
        insert.parent = n;
        nd = insert;
        h = 0;
        while(nd.parent!=null){
          if(h>nd.height){
            nd.height = h;
          }
          nd=nd.parent;
          h++;
        }
      }
    }
    else{
      return;
    }
  }

  /** insert w into the tree, maintaining AVL balance
  *  precondition: the tree is AVL balanced and any prior insertions have been
  *  performed by this method. */
  public void avlInsert(String w) {
    if (root == null) {
      root = new Node(w);
      size = 1;
      return;
    }
    avlInsert(root, w);
  }
  /** Update the height of the accepted node. */
  public int getHeight(Node n){
    if(n==null){
      return -1;
    }
    if(n.left==null && n.right==null){
      return 0;
    }
    else if(n.left!=null && n.right==null){
      return 1 + n.left.height;
    }
    else if(n.right!=null && n.left==null){
      return 1 + n.right.height;
    }
    else{
      return 1 + Math.max(n.left.height, n.right.height);
    }
  }
  /* insert w into the tree, maintaining AVL balance
   *  precondition: the tree is AVL balanced and n is not null */
  private void avlInsert(Node n, String w) {
    Node insert = new Node(w);
    if(n.word.compareTo(w)>0){
      //In the event insert goes left
      if(n.left!=null){
        avlInsert(n.left, w);
      }
      else{
        n.left = insert;
        insert.parent = n;
        size += 1;
        insert.height=0;
        insert.parent.height = getHeight(n);
      }
    }
    else if(n.word.compareTo(w)<0){
      //In the event insert goes right
      if(n.right!=null){
        avlInsert(n.right, w);
      }
      else{
        n.right = insert;
        insert.parent = n;
        size += 1;
        insert.height=0;
        insert.parent.height = getHeight(n);
      }
    }
    else{
      return;
    }
    rebalance(n);
  }

  /** do a left rotation: rotate on the edge from x to its right child.
  *  precondition: x has a non-null right child */
  public void leftRotate(Node x) {
    Node y = x.right;
    x.right = y.left;
    if(y.left!=null){
      y.left.parent = x;
    }
    y.parent = x.parent;
    if(x==root){
      root = y;
    }
    else if(x==x.parent.left){
      x.parent.left = y;
    }
    else{
      x.parent.right = y;
    }
    y.left = x;
    x.parent = y;
    x.height++;
    y.height--;
    x.height = getHeight(x);
  }

  /** do a right rotation: rotate on the edge from x to its left child.
  *  precondition: y has a non-null left child */
  public void rightRotate(Node y) {
    Node x = y.left;
    y.left = x.right;
    if(x.right!=null){
      x.right.parent = y;
    }
    x.parent = y.parent;
    if(y==root){
      root = x;
    }
    else if(y==y.parent.right){
      y.parent.right = x;
    }
    else{
      y.parent.left = x;
    }
    x.right = y;
    y.parent = x;
    y.height++;
    x.height--;
    y.height = getHeight(y);
  }

  /** rebalance a node N after a potentially AVL-violoting insertion.
  *  precondition: none of n's descendants violates the AVL property */
  public void rebalance(Node n) {
    if(balance(n)<-1){
      if(balance(n.left)<0){
        rightRotate(n);
      }
      else{
        leftRotate(n.left);
        rightRotate(n);
      }
    }
    else if(balance(n)>1){
      if(balance(n.right)<0){
        rightRotate(n.right);
        leftRotate(n);
      }
      else{
        leftRotate(n);
      }
    }
    n.height = getHeight(n);
  }

  /** remove the word w from the tree */
  public void remove(String w) {
    remove(root, w);
  }

  /* remove w from the tree rooted at n */
  private void remove(Node n, String w) {
    return; // (enhancement TODO - do the base assignment first)
  }

  /** print a sideways representation of the tree - root at left,
  * right is up, left is down. */
  public void printTree() {
    printSubtree(root, 0);
  }
  private void printSubtree(Node n, int level) {
    if (n == null) {
      return;
    }
    printSubtree(n.right, level + 1);
    for (int i = 0; i < level; i++) {
      System.out.print("        ");
    }
    System.out.println(n);
    printSubtree(n.left, level + 1);
  }

  /** inner class representing a node in the tree. */
  public class Node {
    public String word;
    public Node parent;
    public Node left;
    public Node right;
    public int height;

    public String toString() {
      return word + "(" + height + ")";
    }

    /** constructor: gives default values to all fields */
    public Node() { }

    /** constructor: sets only word */
    public Node(String w) {
      word = w;
    }

    /** constructor: sets word and parent fields */
    public Node(String w, Node p) {
      word = w;
      parent = p;
    }

    /** constructor: sets all fields */
    public Node(String w, Node p, Node l, Node r) {
      word = w;
      parent = p;
      left = l;
      right = r;
    }
  }
}
