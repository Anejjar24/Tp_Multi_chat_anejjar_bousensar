package multichat;
import java.net.*;
import java.util.*;

/*
public class ServeurMT  extends Thread  {

	 private static List<Conversation> clients = new ArrayList<>();
	    private static int nb_client = 0;

	    public void run() {
	        try {
	            // Création du serveur et écoute sur le port 1000
	            ServerSocket serveur = new ServerSocket(1000);
	            System.out.println("Serveur démarré sur le port 1000...");

	            while (true) {
	                // Accepter les connexions des nouveaux clients
	                Socket socket = serveur.accept();
	                nb_client++;
	                Conversation conversation = new Conversation(socket, nb_client, clients);
	                clients.add(conversation); // Ajouter à la liste des clients
	                conversation.start(); // Lancer le thread pour le client
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public static void main(String[] args) {
	        ServeurMT serveur = new ServeurMT();
	        serveur.start();
	    }
	}
*/


public class ServeurMT extends Thread {
    private static List<Conversation> clients = new ArrayList<>();
    private static int nb_client = 0;

    public void run() {
        try {
            // Créer le serveur et attendre les connexions
            ServerSocket serveur = new ServerSocket(1000);
            System.out.println("Serveur démarré sur le port 1000...");

            while (true) {
                // Accepter une connexion d'un client
                Socket socket = serveur.accept();
                nb_client++;
                Conversation conversation = new Conversation(socket, nb_client, clients);
                
                // Lancer le thread pour la nouvelle conversation
                conversation.start();
                
                // Ajouter le client à la liste des conversations après avoir démarré la thread
                clients.add(conversation);
                
                // Mettre à jour la liste des clients pour tous les clients connectés
                mettreAJourListeClients();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServeurMT serveur = new ServeurMT();
        serveur.start();
    }

    // Méthode pour mettre à jour la liste des clients connectés pour tous les clients
    private static void mettreAJourListeClients() {
        for (Conversation client : clients) {
            client.envoyerListeClients();
        }
    }
}
