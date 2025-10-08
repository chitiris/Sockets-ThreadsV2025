package UDP_time_server;  // Δηλώνει ότι το αρχείο ανήκει στο πακέτο UDP_time_server

import java.io.*;         // Εισάγει βασικές κλάσεις εισόδου/εξόδου
import java.net.*;        // Εισάγει κλάσεις για δικτυακό προγραμματισμό (DatagramSocket, DatagramPacket)

public class TimeClient {
	
    // Συνάρτηση που εκτελεί την επικοινωνία με τον time server
	public static void timeclient(DatagramSocket serversoc, InetAddress hostaddress, int port) {
	  
	  // Δηλώνουμε ένα DatagramPacket για αποστολή/λήψη δεδομένων
	  DatagramPacket packet;

	  // Δημιουργούμε πίνακα bytes που θα σταλεί (αίτημα) και θα δεχτεί την απάντηση
	  byte[] message = new byte[256];

	  try {
	      // Δημιουργούμε το UDP packet με:
	      // - το περιεχόμενο (message)
	      // - το μέγεθος (256 bytes)
	      // - τη διεύθυνση του server (hostaddress)
	      // - και τη θύρα (port)
	      packet = new DatagramPacket(message, 256, hostaddress, port);

	      System.out.println("Sending the request for the time...");

	      // Στέλνουμε το αίτημα στον server μέσω του socket
	      serversoc.send(packet);

	      // Αναμονή για την απάντηση από τον server
	      System.out.println("Waiting for reply...");
	      serversoc.receive(packet);

	      // Διαβάζουμε την απάντηση από τα δεδομένα του packet
	      // (χρησιμοποιούμε τη βοηθητική συνάρτηση data() για μετατροπή byte[] -> String)
	      String mesg = data(packet.getData()).toString();

	      // Εμφανίζουμε στην κονσόλα την ώρα που έστειλε ο server
	      System.out.println("Time at server location " + packet.getAddress() + ":" + packet.getPort() + " is " + mesg );
	  } catch(Exception exc) {
	      // Αν προκύψει οποιοδήποτε σφάλμα (π.χ. δικτύου), το εμφανίζουμε
	      System.out.println("Error : " + exc.toString());
	  }
	} // End of timeclient()

	
	// Συνάρτηση που μετατρέπει έναν πίνακα bytes σε συμβολοσειρά (StringBuilder)
	// μέχρι να φτάσει σε byte με τιμή 0 (null terminator)
	public static StringBuilder data(byte[] a) 
	{ 
	    if (a == null) 
	        return null; 
	    StringBuilder ret = new StringBuilder(); 
	    int i = 0; 
	    while (a[i] != 0) 
	    { 
	        ret.append((char) a[i]); 
	        i++; 
	    } 
	    return ret; 
	} 
	
	
	// Κύρια συνάρτηση main() — σημείο εκκίνησης του προγράμματος
	public static void main(String args[]) {
	  
	  // Δηλώνουμε μεταβλητές για τη διεύθυνση του server και τη θύρα
	  InetAddress hostAddress;
	  int portnum;

	  // Δηλώνουμε ένα DatagramSocket (UDP socket)
	  DatagramSocket serversoc;

	  // Αρχικοποίηση δικτύου και εκτέλεση αιτήματος
	  try {
	      // Παίρνουμε από τη γραμμή εντολών τη διεύθυνση του server (args[0])
	      // π.χ. "127.0.0.1" για τοπική εκτέλεση
	      hostAddress = InetAddress.getByName(args[0]);

	      // Θύρα στην οποία "ακούει" ο time server
	      portnum = 8505;

	      // Δημιουργούμε το socket του client (χωρίς να ορίσουμε συγκεκριμένη θύρα)
	      serversoc = new DatagramSocket();

	      // Καλούμε τη συνάρτηση που υλοποιεί τη λογική επικοινωνίας
	      timeclient(serversoc, hostAddress, portnum);

	      // Κλείνουμε το socket για να απελευθερωθούν οι πόροι
	      if (serversoc != null) 
	    	  serversoc.close();
	  } catch (UnknownHostException uhe) {
	      // Αν η διεύθυνση του server δεν είναι έγκυρη
	      System.out.println("Unknown host : " + uhe.toString());
	  } catch (SocketException exc) {
	      // Αν υπάρξει πρόβλημα στη δημιουργία του socket
	      System.out.println("Error : " + exc);
	  }
	} // End of main()
}

/*
ΟΔΗΓΙΕΣ ΕΚΤΕΛΕΣΗΣ ΣΤΟ ECLIPSE
1) Κάνε δεξί κλικ στο αρχείο σου TimeClient.java.
2) Επίλεξε:Run As → Run Configurations...
3) Στο αριστερό πάνελ επίλεξε τη ρύθμιση για το πρόγραμμά σου (ή φτιάξε νέα με το κουμπί “New launch configuration”).
4) Πήγαινε στην καρτέλα Arguments.
5) Στο πεδίο Program arguments, γράψε:127.0.0.1 (ή άλλη IP, αν ο server τρέχει αλλού).
6) Πάτα Apply → Run.
*/