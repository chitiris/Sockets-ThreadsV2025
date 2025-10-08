package TCP_Sockets;  // Δηλώνει ότι το αρχείο ανήκει στο πακέτο TCP_Sockets

import java.io.*;      // Εισάγει κλάσεις για είσοδο/έξοδο δεδομένων (streams, readers, writers)
import java.net.*;     // Εισάγει κλάσεις για δικτυακό προγραμματισμό (Socket, ServerSocket κ.λπ.)

public class TCPClient {
	public static void main (String argv[]) throws Exception
	{
		// Δημιουργούμε μεταβλητές τύπου String για το μήνυμα προς αποστολή
		// και για την απάντηση που θα λάβουμε από τον server
		String sentence;
		String modifiedSentence;

		// Δημιουργούμε αντικείμενο BufferedReader για να διαβάζουμε γραμμές κειμένου από το πληκτρολόγιο (stdin)
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

		// Δημιουργούμε TCP σύνδεση με τον server στη διεύθυνση "localhost" και στη θύρα 6789
		Socket clientSocket = new Socket("localhost", 6789);

		// Εμφανίζουμε μήνυμα επιβεβαίωσης ότι η σύνδεση έγινε επιτυχώς
		System.out.println("Connected to " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

		// Δημιουργούμε ρεύμα εξόδου (output stream) για αποστολή δεδομένων προς τον server
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

		// Δημιουργούμε ρεύμα εισόδου (input stream) για λήψη δεδομένων από τον server
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		// Διαβάζουμε από τον χρήστη μια γραμμή κειμένου
		sentence = inFromUser.readLine();

		// Στέλνουμε το μήνυμα στον server μέσω του output stream
		// Το '\n' (newline) χρησιμοποιείται ώστε ο server να γνωρίζει ότι τελείωσε η γραμμή
		outToServer.writeBytes(sentence + '\n');

		// Περιμένουμε να λάβουμε την απάντηση του server (readLine μπλοκάρει μέχρι να έρθουν δεδομένα)
		modifiedSentence = inFromServer.readLine();

		// Εμφανίζουμε στην κονσόλα την απάντηση που στείλε ο server
		System.out.println("FROM SERVER: " + modifiedSentence);

		// Κλείνουμε τη σύνδεση με τον server για να απελευθερωθούν οι πόροι του συστήματος
		clientSocket.close();
	}
}
