/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.awt.*;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.*;

public class GUI extends JFrame {

   
    private JTextField typingArea = new JTextField(40);
    private JTextArea chatArea = new JTextArea();
    private JScrollPane chatScroll = new JScrollPane(chatArea);

    private JPanel topPanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    private JButton ping = new JButton("Ping");
    private JButton addUser = new JButton("Connect");
    private JButton list = new JButton("Show List");

    private JButton serverButton = new JButton("Start");

    /**
     * Constructs the GUI with the given components
     */
    public GUI() {

        super("MyChat");

        this.setLayout(new BorderLayout());
        this.setSize(480, 450);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        topPanel.setLayout(new FlowLayout());
        topPanel.add(serverButton);
        topPanel.add(addUser);
        topPanel.add(ping);
        topPanel.add(list);
      
        
        
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(chatScroll, BorderLayout.CENTER);
        this.getContentPane().add(typingArea, BorderLayout.SOUTH);

    }
    
    /**
     * Start of accessors/ 
     */
    public String getServerIP() throws UnknownHostException{
    
        InetAddress host = InetAddress.getLocalHost();
        return host.getHostAddress();
    }
    
    public void setAppTitle(String e){
    
        this.setTitle(e);
    
    }


    public JTextArea getChatArea() {

        return chatArea;

    }

    
    public String getMessage() {

        return typingArea.getText();

    }

    public void setMessage() {

        typingArea.setText("");

    }

    public String getPort() {
        return JOptionPane.showInputDialog(
                this,
                "Port No:",
                "Port",
                JOptionPane.PLAIN_MESSAGE
        );
    }
    
    public String getID() {
        return JOptionPane.showInputDialog(
                this,
                "ID No:",
                "ID",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    public String getTargetIP() {

        return JOptionPane.showInputDialog(
                this,
                "IP",
                "IP number",
                JOptionPane.PLAIN_MESSAGE
        );
    }
     /**
     * End of accessors/ 
     */

    
    /**
     * ActionListener methods for required components
     * @param listenForMethod 
     */
    void addPingButtonListener(ActionListener listenForMethod) {

        ping.addActionListener(listenForMethod);

    }

    
    void addServerButtonListener(ActionListener listenForCLearButton) {

        serverButton.addActionListener(listenForCLearButton);

    }

    
    void addSendListener(ActionListener listenForCalcButton) {

        typingArea.addActionListener(listenForCalcButton);

    }
    void addaddUserListener(ActionListener listenForCalcButton) {

        addUser.addActionListener(listenForCalcButton);

    }
    
    void listUserListener(ActionListener listenForCalcButton) {

        list.addActionListener(listenForCalcButton);

    }
    
    /**
     * generic method to display any notification or error message
     * @param errorMessage 
     */
    void displayErrorMessage(String errorMessage) {

        JOptionPane.showMessageDialog(this, errorMessage);

    }

}
