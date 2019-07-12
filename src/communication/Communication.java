/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;


/**
 * Lance l'application en exécutant une instance unique du server
 *
 * @author JBQ and Camille
 */
public class Communication {
    private static Server serverChat;
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        serverChat = new Server();
        
    }

}
