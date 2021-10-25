/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GUITest {


    @Before
    public void setUp() throws Exception {
        System.out.println("Before test cases...");

    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Runs after all test cases...");
    }

    @Test
    public void getServerIP() throws UnknownHostException {
        GUI cv = new GUI();
        String ip = cv.getServerIP();
        InetAddress host = InetAddress.getLocalHost();
        assertEquals(host.getHostAddress(), ip);
    }



    @Test
    public void getServerPort() {
        GUI cv = new GUI();
        String port = JOptionPane.showInputDialog("Port: ");
        assertTrue(port.equals(cv.getPort()));

    }

    @Test
    public void getMessage() {

    }

    @Test
    public void setMessage() {
    }

    @Test
    public void getTargetPort() {
        GUI test = new GUI();
        String port = JOptionPane.showInputDialog("PORT Where you want to connect: ");
        assertEquals(port, test.getPort());
    }

    @Test
    public void getID() {
        GUI test = new GUI();
        String ID = JOptionPane.showInputDialog("Enter ID: ");
        assertEquals(ID, test.getID());
    }



    @Test
    public void getTargetIP() {
        GUI test = new GUI();
        String IP = test.getTargetIP();
        assertEquals("localhost", IP);
    }
}