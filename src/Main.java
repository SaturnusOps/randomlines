import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // User input um die linen züge zubestimmen
        Scanner scanner = new Scanner(System.in);
        System.out.print("Wie lang soll die Line gezogen weden:");
        int linenlänge = scanner.nextInt();
        scanner.close();

        // die richtung zufällig die Zahl bestimmen
        Random random = new Random();
        int richtung= random.nextInt(4);

        // Ein Fenster Öffnen
        JFrame fenster = new JFrame(randomlines);
        fenster.setSize(600, 600);
        fenster.setDefailtCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setLocationRelativeTo(null);
        // im fenster zeichnen
        ZeichnenFläche fläche = new ZeichenFläche(Länge, richtung);
        fenster.add(fläche);
        // Fenster sichtbar machen
        fenster.setVisible(true);
    }
}