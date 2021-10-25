/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JTextArea;

/**
 * Handles the new connection made with another peer, receives messages from the connected peers.
 * send out your own id, port and ip in the constructor to new peers to let them know about your details.
 * 
 */
public class Peers extends Thread {

    private Scanner in;
    private JTextArea display;
    private PrintWriter out;
    private String serverPort, serverIP, ID, myID;
    private Socket socket;
    private ArrayList<String> IDs = new ArrayList<>();
    private File msgFile;

    public Peers(Socket socket, JTextArea display, ArrayList<String> IDs, String serverPort, String serverIP, String myID) throws IOException {

        this.socket = socket;
        this.IDs = IDs;
        this.serverPort = serverPort;
        this.serverIP = serverIP;
        this.myID = myID;
        this.display = display;
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
        
        msgFile = new File("src/chat/"+myID+".txt");
        
        //sends your ID, Port, IP to the connected peer. so they can connect back with you.
        out.println("N" + myID + "P" + serverPort + "I" + serverIP);

    }

    /**
     * receives messages from the peers. check for a new notification, if a notification contains a new user then
     * it is displayed on the textArea, so you can add them to the list.
     */
    @Override
    public void run() {

        boolean flag = true;
        while (flag) {
            try {

                String input = in.nextLine();

                if (input != null) {

                    //checks for a new notification
                    if (input.startsWith("New")) {

                        ID = input.substring(input.indexOf("[") + 1, input.indexOf("]"));
                        
                        // check if the user is not in your list already
                        if (!IDs.contains(ID) || IDs.isEmpty()) {

                            IDs.add(ID);
                            display.append(input + "\n");

                        }

                    } else {

                        display.append(input + "\n");
                        storeMessages(input, msgFile);
                    }
                }

            } catch (Exception e) {
                flag = false;
                interrupt();
            }
        }
    }
    
    /**
     * Stores All the messages received from peers. 
     */
    void storeMessages(String message, File msgFile) throws IOException{
    
        
        
        if(msgFile.exists()==true){
        
            FileWriter writer = new FileWriter(msgFile, true);
            writer.write(message+"\n");
            writer.close();
            
        }
        else{
        
            msgFile = new File("src/chat/"+myID+".txt");
            FileWriter writer = new FileWriter(msgFile, true);
            writer.write(message+"\n");
            writer.close();
            
        
        }
       
        
    
    }
}
