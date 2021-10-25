/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

public class SLinkedList {

    protected StringNode head;

    public SLinkedList() {
        head = new StringNode();
    }

    //adds different calculated values from methods at assigned space.
    public void add(String ID, String port, String IP) {
        StringNode tail;
        tail = head;

        while (tail.getNext() != null) {
            tail = tail.getNext();
        }
        //insert new node at end of list
        tail.setNext(new StringNode(ID, port, IP, null));

    }
    
    
    public int size()
{
    int size = 0;
    StringNode CurrNode = head;
    while(CurrNode.getNext() != null)
    {
        CurrNode = CurrNode.getNext();
        size++;     
    }
    return size;
}
    

    //add a new node after position of curnode
    public boolean isEmpty() {
        return head == null;
    }

    //clears out the whole list.
    public void clearList() {
        
        head.setNext(null);
       
    }

   
}

