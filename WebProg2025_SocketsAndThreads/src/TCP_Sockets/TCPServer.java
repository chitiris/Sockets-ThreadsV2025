package TCP_Sockets;  // Δηλώνει ότι το αρχείο ανήκει στο πακέτο TCP_Sockets

import java.io.*;     // Εισάγει τις κλάσεις για είσοδο/έξοδο δεδομένων (Input/Output Streams)
import java.net.*;    // Εισάγει τις κλάσεις για δικτυακό προγραμματισμό (Sockets, ServerSocket κ.λπ.)

public class TCPServer {
	public static void main (String argv[]) throws Exception
	{
		// Δηλώνουμε δύο συμβολοσειρές:
		// - clientSentence: το μήνυμα που θα λάβουμε από τον client
		// - capitalizedSentence: το μήνυμα που θα στείλουμε πίσω (σε κεφαλαία)
		String clientSentence;
		String capitalizedSentence;

		// Δημιουργούμε αντικείμενο ServerSocket στη θύρα 6789
		// Ο server "ακούει" σε αυτήν τη θύρα και περιμένει αιτήματα σύνδεσης
		ServerSocket welcomeSocket = new ServerSocket(6789);

		// Ο server θα λειτουργεί συνεχώς, δέχοντας πολλαπλές συνδέσεις
		while(true) {
			// Η μέθοδος accept() περιμένει (μπλοκάρει) μέχρι να συνδεθεί κάποιος client
			Socket connectionSocket = welcomeSocket.accept();

			// Εμφανίζει μήνυμα με τη διεύθυνση IP και τη θύρα του client που συνδέθηκε
			System.out.println("Connected to " + connectionSocket.getInetAddress() + ":" + connectionSocket.getPort());

			// Δημιουργούμε ρεύμα εισόδου (InputStream) για να διαβάζουμε δεδομένα από τον client
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

			// Δημιουργούμε ρεύμα εξόδου (OutputStream) για να στέλνουμε δεδομένα πίσω στον client
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

			// Διαβάζουμε μια γραμμή κειμένου που έστειλε ο client
			clientSentence = inFromClient.readLine();

			// Εμφανίζουμε στην κονσόλα τι μήνυμα λάβαμε από τον client
			System.out.println("FROM client: " + clientSentence);

			// Μετατρέπουμε το μήνυμα σε κεφαλαία γράμματα και προσθέτουμε χαρακτήρα αλλαγής γραμμής ('\n')
			capitalizedSentence = clientSentence.toUpperCase() + '\n';

			// Στέλνουμε πίσω το επεξεργασμένο μήνυμα στον client
			outToClient.writeBytes(capitalizedSentence);
		}
	}
}
