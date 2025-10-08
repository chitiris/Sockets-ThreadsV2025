package UDP_Sockets;  // Δηλώνει ότι το αρχείο ανήκει στο πακέτο UDP_Sockets

import java.io.*;     // Εισάγει κλάσεις για είσοδο/έξοδο (π.χ. BufferedReader)
import java.net.*;    // Εισάγει κλάσεις για επικοινωνία μέσω δικτύου (UDP sockets κ.λπ.)

public class UDPClient {
    public static void main(String args[]) throws Exception 
    { 
        // Δημιουργούμε αντικείμενο BufferedReader για να διαβάζουμε δεδομένα από το πληκτρολόγιο (stdin)
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); 
  
        // Δημιουργούμε UDP socket στην πλευρά του πελάτη (client)
        DatagramSocket clientSocket = new DatagramSocket(); 
  
        // Ορίζουμε τη διεύθυνση IP του server — εδώ είναι το ίδιο μηχάνημα (localhost)
        InetAddress IPAddress = InetAddress.getByName("localhost"); 
  
        // Δημιουργούμε πίνακες bytes για αποστολή και λήψη δεδομένων
        byte[] sendData = new byte[1024]; 
        byte[] receiveData = new byte[1024]; 
  
        // Ζητάμε από τον χρήστη να εισάγει ένα μήνυμα
        System.out.println("Enter a sentence: ");
        String sentence = inFromUser.readLine();  // Διαβάζουμε τη γραμμή κειμένου από το πληκτρολόγιο
      
        // Μετατρέπουμε το string σε bytes (ώστε να μπορεί να σταλεί μέσω UDP)
        sendData = sentence.getBytes();       

        // Δημιουργούμε UDP πακέτο με τα δεδομένα, τη διεύθυνση IP και την πόρτα του server (9876)
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876); 
  
        // Στέλνουμε το πακέτο μέσω του socket στον server
        clientSocket.send(sendPacket); 
  
        // Δημιουργούμε πακέτο για να λάβουμε την απάντηση από τον server
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
  
        // Περιμένουμε να λάβουμε δεδομένα από τον server (blocking call)
        clientSocket.receive(receivePacket); 
      
        // Μετατρέπουμε τα bytes της απάντησης σε κείμενο (μέχρι το πρώτο byte 0)
        String modifiedSentence = data(receivePacket.getData()).toString();

        // Εμφανίζουμε την απάντηση του server στην κονσόλα
        System.out.println("FROM SERVER:" + modifiedSentence); 

        // Κλείνουμε το socket για να ελευθερωθούν οι πόροι του συστήματος
        clientSocket.close(); 
    } 
    
    // Συνάρτηση που μετατρέπει έναν πίνακα bytes σε συμβολοσειρά (μέχρι να βρει το byte 0)
    public static StringBuilder data(byte[] a) 
    { 
        if (a == null) 
            return null; 
        StringBuilder ret = new StringBuilder(); 
        int i = 0; 
        // Προσθέτει κάθε χαρακτήρα στο StringBuilder μέχρι να βρει το byte 0 (δηλαδή «τέλος δεδομένων»)
        while (a[i] != 0) 
        { 
            ret.append((char) a[i]); 
            i++; 
        } 
        return ret;  // Επιστρέφει τη συμβολοσειρά
    } 
}
