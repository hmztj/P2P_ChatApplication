/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextArea;

/** 
 * Server listens for new incoming connections, and initializes a new thread for each one of them, and contains a method 
 * which is used to broadcast messages to everyone in the list.
 * Acts as a database for a peer itself.
 */

class Server extends Thread {
    
    // Variable declarations
    private JTextArea display;
    private int port;
    private ArrayList<String> IDs;
    private PeerHandler peer;
    private ArrayList<PeerHandler> peers = new ArrayList<>();
    private SLinkedList peerDetails = new SLinkedList();

    //Accesors(setter and getters) for setting up the variable values
    public void setDisplay(JTextArea display) {
        this.display = display;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ArrayList<String> getIDs() {
        return IDs;
    }

    public void setIDs(ArrayList<String> IDs) {
        this.IDs = IDs;
    }
    
    //returns peers' details(ID, Port, IP)
    public SLinkedList getPeerDetails(){
    
        return peerDetails;
    
    }
    
    // returns peers thread list
    public ArrayList<PeerHandler> getPeers() {

        return peers;

    }

    /**
     * Spinoff A new Thread to handle a new incoming connection from a peer.Adds the new peer into the peers ArrayList
     * 
     */
    @Override
    public void run() {
        display.append("Server starting\n");
        try {

            ServerSocket ss = new ServerSocket(port);

            //keep accepting new incoming connections
            while (true) {

                Socket mySocket = ss.accept();
                
                //spinoff a new thread for each new connection.
                peer = new PeerHandler(mySocket, display, this, IDs);
                peer.start();
                
                //adds the peer to the list
                peers.add(peer);

            }

        } catch (IOException ioe) {

            System.out.println(ioe);
        }
        display.append("Server closing\n");
    }


    /**
     * Coordinator function, checks for all the thread states and returns the terminated thread or null if all threads
     * are running.
     */ 
    public PeerHandler threadCheck() {

        for (PeerHandler cliento : peers) {

            if ("TERMINATED".equals(String.valueOf(cliento.getState()))) {

                return cliento;

            }

        }
        return null;

    }

    /**
     * gets the printWriter for all the peers' threads in the list and 
     * send out the message passed to it as a String arguement.
     */
    void sendMessage(String message) {
        try {
            peers.forEach(t -> t.getPrintWriter().println(message));

        } catch (Exception e) {
        }

    }



}
