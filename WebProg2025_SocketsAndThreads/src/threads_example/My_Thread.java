package threads_example;

//Κύρια κλάση που περιέχει τη μέθοδο main() — το σημείο εκκίνησης του προγράμματος
public class My_Thread {
 public static void main(String[] args) {

     // Δημιουργία ενός αντικειμένου της κλάσης που υλοποιεί το Runnable
     // Αυτό είναι το "έργο" (task) που θα εκτελέσουν τα threads
     SampleThreadRunnable threadObject = new SampleThreadRunnable();

     // Δημιουργία δύο αντικειμένων Thread, που θα εκτελέσουν το ίδιο task
     // Το δεύτερο όρισμα είναι το όνομα που δίνουμε στο thread (προαιρετικό αλλά βοηθά στην αναγνώριση)
     Thread thread1 = new Thread(threadObject, "Thread1");
     Thread thread2 = new Thread(threadObject, "Thread2");

     // Εμφανίζει μήνυμα ότι το πρώτο thread πρόκειται να ξεκινήσει
     System.out.println("Thread 1 about to start...");
     // Εκκίνηση του πρώτου thread (καλεί εσωτερικά τη run() σε ξεχωριστό νήμα εκτέλεσης)
     thread1.start();

     // Εμφανίζει μήνυμα ότι το δεύτερο thread πρόκειται να ξεκινήσει
     System.out.println("Thread 2 about to start...");
     // Εκκίνηση του δεύτερου thread
     thread2.start();
 }
}
