package threadsandsockets;

import java.io.*;
import java.net.*;

// Κλάση του προγράμματος-πελάτη (Client) που επικοινωνεί με τον EchoServer
public class EchoClient {
    
    // Η μέθοδος echoclient() διαχειρίζεται την επικοινωνία με τον server
    // sin: είσοδος από το socket (ό,τι στέλνει ο server)
    // sout: έξοδος προς το socket (ό,τι στέλνει ο client στον server)
    public static void echoclient(BufferedReader sin, DataOutputStream sout) throws IOException
	{
	    // Ροή εισόδου από το πληκτρολόγιο (ό,τι γράφει ο χρήστης)
	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
	    
	    // Ροή εξόδου προς τον server
	    PrintStream out = new PrintStream(sout);
	    
	    String line;
	    boolean finished = false; // Μεταβλητή ελέγχου για τερματισμό του client
	    
	    // Ο client εκτελείται σε βρόχο μέχρι να σταλεί ή να ληφθεί το "quit"
	    while(!finished) {
	        line = "";
	        
	        // 1️⃣ Ανάγνωση από το πληκτρολόγιο και αποστολή στον server
	        try {
	        	line = in.readLine();  // Διαβάζει μια γραμμή από το χρήστη
	            out.println(line);      // Την στέλνει στον server μέσω socket
	        } catch(IOException e) {
	            System.out.println(e.getMessage());
	        }
	        
	        // 2️⃣ Ανάγνωση από το socket (δηλαδή η απάντηση του server)
	        try {
	            line = sin.readLine();     // Περιμένει απάντηση από τον server
	            System.out.println(line);  // Την εμφανίζει στην οθόνη
	            
	            // Αν η απάντηση είναι "quit", τότε ο client τερματίζει
	            if (line.equals("quit")) {
	                finished = true;
	                System.out.println("Echo client terminates");
	            }
	        } catch(IOException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	}    // Τέλος της echoclient() function...
	
	
    public static void main(String[] args) 
	{
	    Socket s = null;  // Το socket που θα συνδέσει τον client με τον server
	    
	    try {
	        // Δημιουργία socket για σύνδεση με τον server
	        // args[0] είναι το όνομα ή η IP του υπολογιστή που τρέχει ο server
	        // Η θύρα (8205) πρέπει να είναι ίδια με αυτή που "ακούει" ο server
	        s = new Socket(args[0], 8205);
	        
	        // Δημιουργία ροών (streams) για ανάγνωση και αποστολή δεδομένων
	        BufferedReader sin = 
			        new BufferedReader(new InputStreamReader(s.getInputStream())); 
	        DataOutputStream sout = 
	            new DataOutputStream(s.getOutputStream());
	        
	        // Εμφάνιση μηνύματος επιτυχούς σύνδεσης
	        System.out.println("Connected to " + 
	            s.getInetAddress() + ":" + s.getPort());
	        
	        // Κλήση της μεθόδου echoclient για αλληλεπίδραση με τον server
	        echoclient(sin, sout);
	    
	    // Αν παρουσιαστεί σφάλμα (π.χ. ο server δεν είναι διαθέσιμος)
	    } catch(IOException e) {
	        System.out.println(e);
	    }
	    
	    // Τερματισμός και καθαρισμός πόρων
	    finally {
	        try {
	            if (s != null) 
	                s.close();  // Κλείνει τη σύνδεση με τον server
	        } catch(IOException e) {
	            System.out.println("Closing socket...");
	        }
	    }
	}  // Τέλος της main()
}  // Τέλος της κλάσης EchoClient
