package threadsandsockets;

//Εισαγωγές απαραίτητων βιβλιοθηκών για δικτύωση, είσοδο/έξοδο και πολυνηματικότητα
import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;

//Κύρια κλάση του προγράμματος - ο εξυπηρετητής (server)
public class EchoServer {
	public static void main(String args[])
 {
     // Προσπάθεια εκκίνησης του server
     try {
         // Δημιουργούμε ένα αντικείμενο ServerSocket που "ακούει" στη θύρα 8205
         // Η θύρα (port) είναι σαν μια "πρίζα" όπου συνδέονται οι clients
         ServerSocket serversoc = new ServerSocket(8205);

         // Ο server εκτελείται συνεχώς (άπειρος βρόχος)
         // και περιμένει αιτήματα από πελάτες (clients)
         while (true) {
             // Όταν ένας client συνδεθεί, η μέθοδος accept() "ξεμπλοκάρει"
             // και επιστρέφει ένα νέο Socket που αντιπροσωπεύει τη σύνδεση με τον συγκεκριμένο client
             Socket incoming = serversoc.accept();

             // Για κάθε νέο client δημιουργούμε ένα ξεχωριστό νήμα (thread)
             // ώστε ο server να μπορεί να εξυπηρετεί πολλούς clients ταυτόχρονα
             EchoThread et = new EchoThread(incoming);

             // Εκκίνηση του νέου thread
             et.start();
         }

     // Αν προκύψει κάποιο σφάλμα κατά τη δικτύωση
     } catch (IOException e) {
         System.out.println("Error : " + e.getMessage());
     }
 } // Τέλος της main
} // Τέλος της κλάσης EchoServer


//Η κλάση EchoThread επεκτείνει την Thread
//Δηλαδή κάθε αντικείμενο αυτής της κλάσης είναι ένα ξεχωριστό νήμα
class EchoThread extends Thread
{
 // Το socket που συνδέει τον server με τον συγκεκριμένο client
 Socket s;

 // Constructor – αρχικοποιεί το socket με το αντικείμενο που δόθηκε από τον server
 EchoThread(Socket s)
 {
     this.s = s;
 }

 // Η μέθοδος run() είναι αυτή που εκτελείται όταν ξεκινά το thread
 public void run() 
 {
     boolean finished = false;   // Μεταβλητή για να ελέγχουμε πότε θα σταματήσει το thread
     try {
         // Δημιουργία ροών εισόδου και εξόδου για επικοινωνία με τον client
         // Input stream: για να διαβάζουμε δεδομένα που στέλνει ο client
         DataInputStream in = new DataInputStream(s.getInputStream());

         // Output stream: για να στέλνουμε δεδομένα πίσω στον client
         PrintStream out = new PrintStream(s.getOutputStream());

         // Εμφάνιση πληροφοριών για τον client που μόλις συνδέθηκε
         System.out.println("Client from : " +
             s.getInetAddress() + " port " + s.getPort());

         // Επανάληψη μέχρι ο client να στείλει τη λέξη "quit"
         while(!finished) {
             // Διαβάζουμε μία γραμμή κειμένου από τον client
             String st = in.readLine();

             // Στέλνουμε πίσω στον client την ίδια γραμμή (echo)
             out.println(st);

             // Εμφανίζουμε τη γραμμή και στην κονσόλα του server
             System.out.println(st);

             // Αν ο client στείλει τη λέξη "quit", τερματίζουμε το thread
             if (st.equals("quit")) {
                 finished = true;
                 System.out.println("Thread exiting...");
             }
         }

     // Αν παρουσιαστεί σφάλμα (π.χ. αποσύνδεση client)
     } catch (IOException e) {
         System.out.println("Error : " + e.getMessage());
     }

     // Στο τέλος, κλείνουμε πάντα τη σύνδεση (socket)
     finally {
         try {
             if (s != null) 
                 s.close();
         } catch(Exception e) {
             System.out.println("Error : " + e.getMessage());
         }
     }
 } // Τέλος της run()
} // Τέλος της κλάσης EchoThread
