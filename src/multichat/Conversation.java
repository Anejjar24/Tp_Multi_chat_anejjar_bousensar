package multichat;
import java.io.*;
import java.net.*;
import java.util.List;

/*
public class Conversation extends Thread{
	private Socket socket;
    private int nb_client;
    private List<Conversation> clients;
    private PrintWriter sortie;

    public Conversation(Socket socket, int nb_client, List<Conversation> clients) {
        this.socket = socket;
        this.nb_client = nb_client;
        this.clients = clients;
    }

    public void run() {
        try {
            // Flux pour la communication
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            BufferedReader entree = new BufferedReader(new InputStreamReader(in));
            sortie = new PrintWriter(out, true);

            // Envoyer la liste des clients connectés
            envoyerListeClients();

            // Boucle pour gérer les messages
            String message;
            while ((message = entree.readLine()) != null) {
                System.out.println("Client " + nb_client + " a envoyé: " + message);

                if (message.startsWith("liste")) {
                    envoyerListeClients(); // Envoyer la liste des clients connectés
                } else if (message.contains(":")) {
                    // Format "numéro: message"
                    String[] parts = message.split(":", 2);
                    int numeroClient = Integer.parseInt(parts[0].trim());
                    String messageAEnvoyer = parts[1].trim();
                    envoyerMessageAClient(numeroClient, messageAEnvoyer);
                }
            }

            // Fermeture de la connexion
            sortie.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void envoyerListeClients() {
        StringBuilder liste = new StringBuilder("Clients connectés: ");
        for (Conversation client : clients) {
            liste.append("Client ").append(client.nb_client).append(", ");
        }
        sortie.println(liste.toString());
    }

    private void envoyerMessageAClient(int numeroClient, String message) {
        for (Conversation client : clients) {
            if (client.nb_client == numeroClient) {
                client.sortie.println("Message de Client " + nb_client + ": " + message);
                return;
            }
        }
        sortie.println("Client " + numeroClient + " non trouvé.");
    }
}
*/
/*
public class Conversation extends Thread {
    private Socket socket;
    private int nb_client;
    private List<Conversation> clients;
    private PrintWriter sortie;

    public Conversation(Socket socket, int nb_client, List<Conversation> clients) {
        this.socket = socket;
        this.nb_client = nb_client;
        this.clients = clients;
    }

    public void run() {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            BufferedReader entree = new BufferedReader(new InputStreamReader(in));
            sortie = new PrintWriter(out, true);

            // Envoyer la liste des clients connectés
            envoyerListeClients();

            // Boucle pour traiter les messages
            String message;
            while ((message = entree.readLine()) != null) {
                System.out.println("Client " + nb_client + " a envoyé: " + message);

                if (message.startsWith("liste")) {
                    envoyerListeClients(); // Renvoyer la liste des clients connectés
                } else if (message.contains(":")) {
                    // Format "numéro: message"
                    String[] parts = message.split(":", 2);
                    int numeroClient = Integer.parseInt(parts[0].trim());
                    String messageAEnvoyer = parts[1].trim();
                    envoyerMessageAClient(numeroClient, messageAEnvoyer);
                } else {
                    // Broadcasting the message to all clients
                    broadcastMessage("Client " + nb_client + ": " + message);
                }
            }

            sortie.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void envoyerListeClients() {
        StringBuilder liste = new StringBuilder("Clients connectés: ");
        for (Conversation client : clients) {
            liste.append("Client ").append(client.nb_client).append(", ");
        }
        sortie.println(liste.toString());
    }

    private void envoyerMessageAClient(int numeroClient, String message) {
        for (Conversation client : clients) {
            if (client.nb_client == numeroClient) {
                client.sortie.println("Message de Client " + nb_client + ": " + message);
                return;
            }
        }
        sortie.println("Client " + numeroClient + " non trouvé.");
    }

    private void broadcastMessage(String message) {
        for (Conversation client : clients) {
            client.sortie.println(message);
        }
    }
}
*/

public class Conversation extends Thread {
    private Socket socket;
    private int nb_client;
    private List<Conversation> clients;
    private PrintWriter sortie;

    public Conversation(Socket socket, int nb_client, List<Conversation> clients) {
        this.socket = socket;
        this.nb_client = nb_client;
        this.clients = clients;

        try {
            // Initialisation du flux de sortie ici
            OutputStream out = socket.getOutputStream();
            sortie = new PrintWriter(out, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            InputStream in = socket.getInputStream();
            BufferedReader entree = new BufferedReader(new InputStreamReader(in));

            // Envoyer la liste des clients connectés au démarrage de la connexion
            envoyerListeClients();

            // Boucle pour traiter les messages
            String message;
            while ((message = entree.readLine()) != null) {
                System.out.println("Client " + nb_client + " a envoyé: " + message);

                if (message.startsWith("liste")) {
                    envoyerListeClients(); // Renvoyer la liste des clients
                } else if (message.contains(":")) {
                    // Format "numéro: message"
                    String[] parts = message.split(":", 2);
                    int numeroClient = Integer.parseInt(parts[0].trim());
                    String messageAEnvoyer = parts[1].trim();
                    envoyerMessageAClient(numeroClient, messageAEnvoyer);
                } else {
                    // Broadcasting the message to all clients
                    broadcastMessage("Client " + nb_client + ": " + message);
                }
            }

            sortie.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour envoyer la liste des clients connectés
    public void envoyerListeClients() {
        if (sortie != null) {
            StringBuilder liste = new StringBuilder("Clients connectés: ");
            for (Conversation client : clients) {
                liste.append("Client ").append(client.nb_client).append(", ");
            }
            sortie.println(liste.toString());
        }
    }

    private void envoyerMessageAClient(int numeroClient, String message) {
        for (Conversation client : clients) {
            if (client.nb_client == numeroClient) {
                client.sortie.println("Message de Client " + nb_client + ": " + message);
                return;
            }
        }
        sortie.println("Client " + numeroClient + " non trouvé.");
    }

    private void broadcastMessage(String message) {
        for (Conversation client : clients) {
            client.sortie.println(message);
        }
    }
}