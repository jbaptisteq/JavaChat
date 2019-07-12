/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Génère une instance de server
 * @author JBQ and Camille
 */
public class Server {
    private static ArrayList<ClientCommunication> clientList = new ArrayList<>();
    
    public Server() {
        try {
            ServerSocket ss = new ServerSocket(2000);
            System.out.println("Initialisation du server sur le port 2000");
            Socket sClient = null;
            
            // Boucle d'acceptation des instances d'utilisateurs
            while (true) {
                sClient = ss.accept();
                ClientCommunication cc = new ClientCommunication(sClient, clientList);
                clientList.add(cc);
                cc.start();
            }
            
        } catch (IOException | NullPointerException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
