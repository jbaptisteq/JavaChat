/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Génère kes instances de Client lors de la connexion d'un utilisateur
 * @author JBQ and Camille
 */
public class ClientCommunication extends Thread{
    private Socket sClient;
    private PrintWriter pw = null;
    private String pseudo = null;
    private String message = null;
    private String destinataire = null;
    private ArrayList<ClientCommunication> users;
    private PrintWriter pwLog = null;
   
    
    public ClientCommunication(Socket soc, ArrayList<ClientCommunication> users) {
        sClient = soc;
        this.users = users;
    }
    
    @Override
    public void run(){
        OutputStream os = null;
        // Printer du logger
        try {
            pwLog = new PrintWriter(new FileWriter("myLog.txt"), true);
        } catch (IOException ex) {
            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            // Initialisation et saisie du pseudonyme
            InputStream is = sClient.getInputStream();
            os = sClient.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            pw = new PrintWriter(os);
            pw.println("Bienvenue sur le Chat, Entrez votre pseudonyme : ");
            pw.flush();           
            this.setPseudo(br.readLine());
            pseudo = getPseudo();
            pw.println("Votre pseudo est " + pseudo);
            pw.flush();
            
            // Gestion de la discussion
            while(true) {
                pw.println("Destinataire ou All : ");
                pw.flush();
                destinataire = br.readLine();
                pw.println("Votre message : ");
                pw.flush();
                message = br.readLine();
                parler(destinataire, pseudo, message);
            }
        } catch (IOException | NullPointerException ex) {
            Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                os.close();
            } catch (IOException | NullPointerException ex) {
                Logger.getLogger(ClientCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Envoie les entrées de discussion dans un fichier myLog.txt
     * @param log
     * @throws IOException 
     */
    private void logger (String log) throws IOException {
        Date date = new Date();
        pwLog.println(date + log);
        pwLog.flush();
    }
    
    /**
     * Gère l'envoie de message à un ou plusieurs utilisateurs
     * @param destinataire
     * @param pseudo
     * @param message
     * @throws IOException 
     */
    public void parler(String destinataire, String pseudo, String message) throws IOException{
        boolean cible = false;
        // Envoi à tout les utilisateurs
        if (destinataire.equals("All") | destinataire.equals("all") ) {
            for(ClientCommunication listenMessage : users){
                listenMessage.pw.println("<" + pseudo + "> " + message);
                listenMessage.pw.flush();
                String log = ("<" + pseudo + "> All >" + message);
                logger(log);
            }
        } else {
            // Envoi à un seul utilisateur
            for(ClientCommunication listenMessage : users){
                if (listenMessage.getPseudo().equals(destinataire) && cible == false) {
                    listenMessage.pw.println("<[Private]" + pseudo + "> " + message);
                    listenMessage.pw.flush();
                    cible = true;
                    String log = ("<[Private]" + pseudo + "> <" + destinataire + " > " + message);
                    logger(log);
                }
            }
            // Gestion d'une erreur de saisie utilisateur inexistant
            if (cible == false) {
                this.pw.println("Utilisateur " + destinataire + " Inexistant !!");
                this.pw.flush();
            }
        }
    }
    
    /**
     * @return the pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @param pseudo the pseudo to set
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
