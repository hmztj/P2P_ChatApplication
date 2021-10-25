/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JTextArea;

    /**
     * Handles new incoming connections, Calls the SignUp method if the connected user requests for it, and validates their 
     * ID's uniqueness.  
     */
    class PeerHandler extends Thread {

        private Socket s = null;
        private JTextArea display;
        private PrintWriter out;
        private Scanner in;
        private Server server;
        private String input, ID, port, IP;
        private ArrayList<String> IDs;

        //Conctructor
        public PeerHandler(Socket s, JTextArea display, Server server, ArrayList<String> IDs) {

            this.s = s;
            this.server = server;
            this.display = display;
            this.IDs = IDs;

        }

        /**
         * Checks if the new peer has requested for a signup, otherwise just adds the new user to the list and send out
         * a notification to previously connected users that there is a new peer available
         */
        @Override
        public void run() {

            try {
                while (true) {

                    in = new Scanner(s.getInputStream());
                    this.out = new PrintWriter(s.getOutputStream(), true);

                    input = in.nextLine();

                    //checks if user requested signup
                    if (input.startsWith("SIGNUP")) {

                        signUp();

                        
                    } 
                    //checks if User has already Signed Up.
                    else if (input.startsWith("N")) {

                        ID = input.substring(1, input.indexOf("P"));

                        port = input.substring(input.indexOf("P") + 1, input.indexOf("I"));
                        IP = input.substring(input.indexOf("I") + 1);

                        //tell you, who has connected to you by displaying details on textArea
                        display.append("User[" + ID + "] -> port: " + port + ", IP: " + IP + " has connected to your port\n");

                        //if its a new user, tell everyone else that it is available to connect
                        if (!IDs.contains(ID)) {

                            IDs.add(ID);
                            this.setName(ID);
                            server.getPeerDetails().add("ID: "+ID, "Port: "+port, "IP: "+IP);
                            server.sendMessage("New User[" + ID + "] available -> port: " + port + ", IP: " + IP);

                        }

                    }

                }

            } catch (IOException ioe) {

                System.out.println(ioe);
            }

        }

        /**
         * keep prompting the user for an id untill a unqique one is received.
         */
        public void signUp() {

            while (true) {

                this.out.println("SUBMITID");

                ID = in.nextLine();
                if (ID == null) {
                    return;
                }
                if (!ID.isEmpty() && !IDs.contains(ID)) {

                    this.setName(ID);
                    IDs.add(ID);
                    this.out.println("IDACCEPTED" + ID);
                    break;

                }
            }

            input = in.nextLine();

            port = input.substring(input.indexOf("P") + 1, input.indexOf("I"));

            IP = input.substring(input.indexOf("I") + 1);

            server.getPeerDetails().add("ID: "+ID, "Port: "+port, "IP: "+IP);
            display.append("User[" + ID + "] -> port: " + port + ", IP: " + IP + " has connected to your port\n");
            server.sendMessage("New User[" + ID + "] available -> port: " + port + ", IP: " + IP);

        }

        //returns the printWriter for this specific thread
        public PrintWriter getPrintWriter() {

            return out;

        }
        

    }