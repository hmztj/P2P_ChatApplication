/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

/**
 * Controller, controlls the interaction between the Server and client state of the application, and connects the two
 * with GUI class, Acts a bridge between gui and model classes for a smooth data manipulation and user interaction
 * 
 */

public class Controller {

    private GUI view;
    private Server server;
    private Peers peer;

    private String targetIP, targetPort, serverIP, serverPort, ID;

    private ArrayList<String> IDs = new ArrayList<>();

    private ArrayList<Peers> peers = new ArrayList<>();
    private int check = 0;

    private Socket socket;
    private PrintWriter out;
    private Scanner in;
    

    /**
     * takes gui and server parameters in the constructor to be able to call necassary methods from them to make the
     * interaction possible, calls all the actionlistener method from gui and executes the actionperformed method on
     * the user selected component.
     */
    public Controller(GUI view, Server server) {

        this.view = view;
        this.server = server;

        this.view.addPingButtonListener(new PingButtonListener());
        this.view.addSendListener(new sendListener());
        this.view.addServerButtonListener(new serverButtonListener());
        this.view.addaddUserListener(new addUserButtonListener());
        this.view.listUserListener(new List());

    }

    //executes if ping button is pressed
    class PingButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            /**
             * checks the returned value of the method and uses to display a message
             */
            if (server.threadCheck() != null) {

                server.sendMessage(server.threadCheck().getName() + " has Left");
                view.displayErrorMessage(server.threadCheck().getName() + " is Offline");
                server.getPeers().remove(server.threadCheck());
                

            } else {

                view.displayErrorMessage("All Connected Users Are Online");
            }
            

        }

    }

    /**
     * calls for the details of the connected users from server and display them on textArea.
     */
    class List implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            StringNode element = server.getPeerDetails().head;
            element = element.getNext();

            for(int n = 0; n < server.getPeerDetails().size(); n++){
            
            
                view.getChatArea().append(Arrays.toString(element.getElement())+"\n");
                
            
            }
            
            
        }

    }

    /**
     * add a new user to the list, who you want to receive messages from.
     * calls the signup method, if you have not been able to  connect with anyone before.
     * spinoff a new thread to be able to receive messages from all the peers in your list.
     */
    class addUserButtonListener implements ActionListener {

        /**
         * requests for a signup if you are connecting to someone for the first time
         * 
         */
        public void signUp() throws IOException {

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new Scanner(socket.getInputStream());
            String line;

            out.println("SIGNUP");

            while (in.hasNextLine()) {
                line = in.nextLine();

                if (line.startsWith("SUBMITID")) {
                    ID = view.getID();
                    out.println(ID);
                } else if (line.startsWith("IDACCEPTED")) {

                    server.getIDs().add(ID);

                    //sets the app title to your id, port and IP
                    view.setAppTitle("[" + ID + ": " + serverPort + " | " + serverIP + "]");
                    break;

                }
            } 

        }

        @Override
        public void actionPerformed(ActionEvent e) {

            /**
             * this condition checks if the start button has been pressed or not.
             * note: you need to press the start button to initialize your listening socket 
             * before you connect to anyone else.
             */
            if (check == 1) {
                try {
                    

                    targetPort = view.getPort();
                    targetIP = view.getTargetIP();

                    socket = new Socket(targetIP, Integer.parseInt(targetPort));

                    /**
                     * checks if your list is empty, then call for signup otherwise else block is executed.
                     */
                    if (peers.isEmpty()) {

                        signUp();
                        peer = new Peers(socket, view.getChatArea(), server.getIDs(), serverPort, serverIP, ID);
                        

                        peers.add(peer);
                        peer.start();

                    } else {

                        peer = new Peers(socket, view.getChatArea(), server.getIDs(), serverPort, serverIP, ID);
                       
                        peers.add(peer);

                        peer.start();

                    }

                } catch (IOException ex) {

                    System.out.println(ex);
                    view.displayErrorMessage("NO ACTIVE SERVER FOUND ON THIS PORT");
                    actionPerformed(e);

                }
            } else {
                view.displayErrorMessage("PLEASE Press The START Button");
            }
        }

    }

    /**
     * get the user method from gui and pass it to sendMessage method from server class to broadcast your message
     * to all connected users.
     * also display your own message in your textArea
     */
    class sendListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

          
            
            SimpleDateFormat date = new SimpleDateFormat("E dd/MM/yy HH-mm");
            
            view.getChatArea().append("You: "+ view.getMessage()+" - ["+date.format(new Date())+"]"+"\n");
            server.sendMessage(ID + ": " + view.getMessage()+" - ["+date.format(new Date())+"]");
            view.setMessage();

        }

    }

    /**
     * Start the server thread to be able to receive incoming connections from new users.
     * takes all the necessary variables from gui and pass them to server via accessors
     */
    class serverButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {

                check = 1;

                serverPort = view.getPort();
                serverIP = view.getServerIP();
               

                view.setAppTitle(serverPort + "/" + serverIP);
               
                server.setPort(Integer.parseInt(serverPort));
                server.setDisplay(view.getChatArea());
                server.setIDs(IDs);

                server.start();

            } catch (UnknownHostException ex) {

            }

        }

    }

}
