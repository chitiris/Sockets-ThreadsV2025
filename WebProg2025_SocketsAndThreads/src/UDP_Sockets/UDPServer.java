package UDP_Sockets;  // Δηλώνει ότι το αρχείο ανήκει στο πακέτο UDP_Sockets

import java.io.*;     // Εισάγει κλάσεις για είσοδο/έξοδο (Input/Output)
import java.net.*;    // Εισάγει κλάσεις για δικτυακό προγραμματισμό (sockets, πακέτα κ.λπ.)

public class UDPServer {
    public static void main(String args[]) throws Exception 
    { 
        // Δημιουργία UDP socket για τον server που "ακούει" στη θύρα 9876
        DatagramSocket serverSocket = new DatagramSocket(9876); 
  
        // Ο server θα τρέχει συνεχώς, περιμένοντας αιτήματα από clients
        while(true) 
        { 
            // Δημιουργία πινάκων byte για λήψη και αποστολή δεδομένων
            byte[] receiveData = new byte[1024]; 
            byte[] sendData  = new byte[1024]; 
           
            // Δημιουργία UDP πακέτου για να δεχτεί δεδομένα από τον client
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
           
            // Ο server περιμένει να λάβει ένα πακέτο από κάποιον client (blocking call)
            serverSocket.receive(receivePacket);

            // Μετατρέπει τα bytes που έλαβε σε κείμενο μέχρι να βρει το byte 0
            String sentence = data(receivePacket.getData()).toString();

            // Εμφανίζει στην κονσόλα το μήνυμα που έλαβε και το μήκος του
            System.out.println("Received sentence=" + sentence + " sentence.length()=" + sentence.length());

            // Παίρνει τη διεύθυνση IP του client (ώστε να ξέρει πού να στείλει την απάντηση)
            InetAddress IPAddress = receivePacket.getAddress(); 
           
            // Παίρνει και τον αριθμό της θύρας του client
            int port = receivePacket.getPort(); 

            // Μετατρέπει το μήνυμα σε κεφαλαία γράμματα (ως απάντηση)
            String capitalizedSentence = sentence.toUpperCase(); 

            // Μετατρέπει ξανά το αποτέλεσμα σε bytes για αποστολή μέσω UDP
            sendData = capitalizedSentence.getBytes(); 

            // Δημιουργεί νέο UDP πακέτο με τα δεδομένα προς αποστολή, τη διεύθυνση και τη θύρα του client
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port); 

            // Στέλνει το πακέτο πίσω στον client
            serverSocket.send(sendPacket); 
        } 
    } 
	
    // Συνάρτηση που μετατρέπει έναν πίνακα bytes σε StringBuilder, μέχρι να συναντήσει byte 0 (δηλαδή "τέλος")
	public static StringBuilder data(byte[] a) 
    { 
        if (a == null) 
            return null; 
        StringBuilder ret = new StringBuilder(); 
        int i = 0; 
        // Διαβάζει byte προς byte και τα μετατρέπει σε χαρακτήρες
        while (a[i] != 0) 
        { 
            ret.append((char) a[i]); 
            i++; 
        } 
        return ret;  // Επιστρέφει το τελικό StringBuilder που περιέχει το μήνυμα
    } 
} 
