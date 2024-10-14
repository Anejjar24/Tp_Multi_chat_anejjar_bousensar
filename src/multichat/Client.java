package multichat;
import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args) {
        int port = 1000;
        InetAddress address;
        try {
            // Création de la socket pour se connecter au serveur
            address = InetAddress.getByName("localhost");
            Socket soc = new Socket(address, port);

            // Flux pour envoyer et recevoir les messages
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            OutputStream out = soc.getOutputStream();
            PrintWriter sortie = new PrintWriter(out, true);
            BufferedReader entree = new BufferedReader(new InputStreamReader(soc.getInputStream()));

            // Thread pour écouter les messages du serveur
            new Thread(() -> {
                try {
                    String rep;
                    while ((rep = entree.readLine()) != null) {
                        System.out.println(rep);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Boucle pour envoyer les messages
            String data;
            while ((data = br.readLine()) != null) {
                sortie.println(data);
            }

            // Fermeture de la connexion
            soc.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
