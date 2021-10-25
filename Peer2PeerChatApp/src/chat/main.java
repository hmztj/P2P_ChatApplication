/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.IOException;


/**
 *
 * @author hamza
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
       GUI view = new GUI();
       Server server = new Server();
       
       Controller controller = new Controller(view,server);
       
    }
    
}
