import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //User nach der Anzahl der Schritte fragen
        Scanner scanner = new Scanner(System.in);
        System.out.print("Wie viele Schritte soll die Linie machen (z.B. 100)? ");
        int schritte = scanner.nextInt();
        scanner.close();

        //Fenster erstellen
        JFrame fenster = new JFrame("Zufälliger Pfad (Random Walk)");
        fenster.setSize(600, 600);
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setLocationRelativeTo(null);

        //Animations-Fläche hinzufügen
        ZufallsPfadFläche fläche = new ZufallsPfadFläche(schritte);
        fenster.add(fläche);

        fenster.setVisible(true);
    }
}

class ZufallsPfadFläche extends JPanel {
    private int maxSchritte;
    private int aktuellerSchritt = 0;

    // Liste des Linenwegs
    private ArrayList<Point> pfad = new ArrayList<>();
    private Random random = new Random();
    private int schrittLänge = 15;

    public ZufallsPfadFläche(int maxSchritte) {
        this.maxSchritte = maxSchritte;
        this.setBackground(Color.BLACK);

        pfad.add(new Point(300, 300));

        // Ein Timer für die intervalle nach dem ein neuer schritt gemacht wird
        Timer timer = new Timer(50, e -> {
            if (aktuellerSchritt < this.maxSchritte) {
                macheZufälligenSchritt();
                aktuellerSchritt++;
                repaint();
            } else {
                ((Timer)e.getSource()).stop();
                System.out.println("Ziel erreicht!");
            }
        });
        timer.start();
    }

    private void macheZufälligenSchritt() {
        //Letzter Punkt an dem die Line ist
        Point letzterPunkt = pfad.get(pfad.size() - 1);
        int x = letzterPunkt.x;
        int y = letzterPunkt.y;

        //RIchtung Zufälligwählen
        int richtung = random.nextInt(4);
        switch (richtung) {
            case 0: y -= schrittLänge; break; // Hoch
            case 1: y += schrittLänge; break; // Runter
            case 2: x -= schrittLänge; break; // Links
            case 3: x += schrittLänge; break; // Rechts
        }

        //Neuer Schritt
        pfad.add(new Point(x, y));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.GREEN); // Eine neongrüne Linie (Matrix-Style)

        // Die "Schleife" zeichnet nun alle bisherigen Linienstücke
        for (int i = 0; i < pfad.size() - 1; i++) {
            Point p1 = pfad.get(i);
            Point p2 = pfad.get(i + 1);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}