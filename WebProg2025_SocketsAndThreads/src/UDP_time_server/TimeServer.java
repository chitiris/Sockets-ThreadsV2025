package UDP_time_server;  // Δηλώνει ότι το αρχείο ανήκει στο πακέτο UDP_time_server

import java.io.*;         // Εισάγει τις βασικές κλάσεις εισόδου/εξόδου
import java.net.*;        // Εισάγει τις κλάσεις για δικτυακό προγραμματισμό (UDP sockets, packets κ.λπ.)
import java.util.*;       // Εισάγει τις κλάσεις για ημερομηνίες και ώρα (Calendar, Date κ.λπ.)

public class TimeServer {
    public static void main(String args[]) {
        // Δημιουργούμε ένα DatagramSocket για επικοινωνία μέσω UDP
        DatagramSocket s = null;

        // Δημιουργούμε ένα DatagramPacket για λήψη αιτήματος και αποστολή απάντησης
        DatagramPacket packet = null;

        // Δημιουργούμε buffer (πίνακα bytes) για να αποθηκεύσουμε τα δεδομένα που θα λάβουμε
        byte[] buf = new byte[256];

        // Δημιουργούμε το αρχικό packet για λήψη δεδομένων
        packet = new DatagramPacket(buf, buf.length);

        // Δημιουργούμε socket στη θύρα 8505 για να "ακούμε" αιτήματα πελατών
        try {
            s = new DatagramSocket(8505);
        } catch(Exception e) {
            System.out.println("Error : " + e.toString());
        }

        // Ο server παραμένει ενεργός συνεχώς, περιμένοντας αιτήματα
        while (true) {
            // Περιμένουμε να λάβουμε ένα πακέτο από κάποιον client
            try {
                s.receive(packet);
            } catch (Exception e) {
                System.out.println("Error : " + e.toString());
            }

            // Παίρνουμε πληροφορίες για τον client που έστειλε το αίτημα
            InetAddress cl = packet.getAddress();   // Διεύθυνση IP του client
            int port = packet.getPort();            // Θύρα του client

            // Εμφανίζουμε στην κονσόλα πληροφορίες για το αίτημα
            System.out.println("Client from " + cl.getHostAddress() 
                + ":" + port + " requested the time.");

            // Δημιουργούμε αντικείμενο Calendar για να πάρουμε την τρέχουσα ημερομηνία και ώρα
            Calendar rightNow = Calendar.getInstance();

            // Συνθέτουμε μια συμβολοσειρά με την τρέχουσα ημερομηνία και ώρα σε μορφή: ΗΗ/ΜΜ/ΕΕΕΕ-ΩΩ:ΛΛ:ΔΔ
            String localtime = "" + rightNow.get(Calendar.DATE) + "/"
                + (rightNow.get(Calendar.MONTH) + 1) + "/"
                + rightNow.get(Calendar.YEAR) + "-"
                + rightNow.get(Calendar.HOUR) + ":"
                + rightNow.get(Calendar.MINUTE) + ":"
                + rightNow.get(Calendar.SECOND);

            // Μετατρέπουμε το string (localtime) σε πίνακα bytes για αποστολή μέσω UDP
            buf = localtime.getBytes();

            // Δημιουργούμε νέο packet με τα δεδομένα της απάντησης, τη διεύθυνση και τη θύρα του client
            try {
                packet = new DatagramPacket(buf, buf.length, cl, port);

                // Στέλνουμε το packet πίσω στον client
                s.send(packet);
            } catch (Exception e) {
                System.out.println("Error : " + e.toString());
            }

            // Ενημερώνουμε ότι ολοκληρώθηκε η εξυπηρέτηση του client
            System.out.println("Client served...");
        }
    }
}
