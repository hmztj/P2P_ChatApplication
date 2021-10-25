/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

public class StringNode {
  // Instance variables:
  private final String[] element = new String[3];
  private StringNode next;
  /** Creates a node with null references to its element and next node. */
  public StringNode() {
    this("","","",null);
  }
  /** Creates a node with the given element and next node.
   * @param ID @param port @param IP @param n */
  public StringNode(String ID, String port,String IP, StringNode n) {
    
      element[0] = ID;
      element[1] = port;
      element[2] = IP;
     
     
    
      next = n;
  }
  // Accessor methods:
  public String[] getElement() {
    
      return element; 
      
  }
  public StringNode getNext() { 
    return next;
  }
 
  public void setNext(StringNode newNext) {
    next = newNext; 
  }
}

