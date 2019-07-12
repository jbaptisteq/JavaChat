/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author JBQ
 */
public class Client {
        
    private BufferedReader br;
//    private String pseudo = null;
      
    public Client(String host, int port, String p) {
        try {
            Socket s = new Socket(host, port);
//            pseudo = p;
            InputStream is =  s.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }
    
    public void affiche() {
        while (true) {
            try {
                System.out.println(br.readLine());
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String localhost, int port, String pseudo) {
        Client c = new Client(localhost, port, pseudo);
        c.affiche();
    }
   
//   /**
//     * @return the pseudo
//     */
//    public String getPseudo() {
//        return pseudo;
//    }
//
//    /**
//     * @param pseudo the pseudo to set
//     */
//    public void setPseudo(String pseudo) {
//        this.pseudo = pseudo;
//    }
   
    
}
