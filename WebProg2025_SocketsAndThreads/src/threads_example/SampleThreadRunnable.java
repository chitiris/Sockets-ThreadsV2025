package threads_example;

//Η κλάση SampleThreadRunnable υλοποιεί το interface Runnable
//Αυτό σημαίνει ότι μπορεί να εκτελεστεί από ένα Thread
public class SampleThreadRunnable implements Runnable {

 // Η μέθοδος run() περιέχει τον κώδικα που θα εκτελέσει το thread
 @Override
 public void run() {
     // Εμφανίζει ποιο thread "τρέχει" αυτή τη στιγμή
     System.out.println(Thread.currentThread().getName() + " is under Running...");

     // Επαναλαμβάνει την εκτέλεση 100 φορές για παράδειγμα
     for (int i = 1; i <= 100; i++) {
         // Εμφανίζει το όνομα του thread και την τρέχουσα τιμή του μετρητή
         System.out.println(Thread.currentThread().getName() + "  i=" + i);
     }
 }
}
