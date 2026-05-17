import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // User nach der Anzahl der Schritte fragen
        Scanner scanner = new Scanner(System.in);
        System.out.print("Wie viele Schritte soll die Linie machen (z.B. 100)? ");
        int schritte = scanner.nextInt();
        scanner.close();

        // Fenster erstellen
        JFrame fenster = new JFrame("Zufälliger Pfad (Random Walk)");
        fenster.setSize(1000, 1000); // 1000x1000 Pixel
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setLocationRelativeTo(null);

        // Animations-Fläche hinzufügen (Klassenname ohne Umlaut)
        ZufallsPfadFlaeche flaeche = new ZufallsPfadFlaeche(schritte);
        fenster.add(flaeche);

        fenster.setVisible(true);
    }
}


class ZufallsPfadFlaeche extends JPanel {
    private int maxSchritte;
    private int aktuellerSchritt = 0;

    // Liste des Linienwegs
    private ArrayList<Point> pfad = new ArrayList<>();
    private Random random = new Random();
    private int schrittLaenge = 15;

    public ZufallsPfadFlaeche(int maxSchritte) {
        this.maxSchritte = maxSchritte;
        this.setBackground(Color.BLACK);


        pfad.add(new Point(500, 500));

        // Ein Timer für die Intervalle, nach denen ein neuer Schritt gemacht wird
        Timer timer = new Timer(50, e -> {
            if (aktuellerSchritt < this.maxSchritte) {
                macheZufaelligenSchritt();
                aktuellerSchritt++;
                repaint();
            } else {
                ((Timer)e.getSource()).stop();
                System.out.println("Ziel erreicht!");
            }
        });
        timer.start();
    }

    private void macheZufaelligenSchritt() {
        // Hol den letzten Punkt
        Point letzterPunkt = pfad.get(pfad.size() - 1);
        int nextX = letzterPunkt.x;
        int nextY = letzterPunkt.y;

        // Richtung würfeln
        int richtung = random.nextInt(4);
        switch (richtung) {
            case 0: nextY -= schrittLaenge; break; // Hoch
            case 1: nextY += schrittLaenge; break; // Runter
            case 2: nextX -= schrittLaenge; break; // Links
            case 3: nextX += schrittLaenge; break; // Rechts
        }

        // Aktuelle Fenstergröße
        int breite = getWidth();
        int hoehe = getHeight();
        if (breite <= 0) breite = 1000;
        if (hoehe <= 0) hoehe = 1000;

        //durchlaufen lassen

        // Wenn links raus -> rechts rein
        if (nextX < 0) {
            nextX = breite + nextX;
        }
        // Wenn rechts raus -> links rein
        else if (nextX > breite) {
            nextX = nextX - breite;
        }

        // Wenn oben raus -> unten rein
        if (nextY < 0) {
            nextY = hoehe + nextY;
        }
        // Wenn unten raus -> oben rein
        else if (nextY > hoehe) {
            nextY = nextY - hoehe;
        }

        pfad.add(new Point(nextX, nextY));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.GREEN);


        for (int i = 0; i < pfad.size() - 1; i++) {
            Point p1 = pfad.get(i);
            Point p2 = pfad.get(i + 1);

            int abstandX = Math.abs(p1.x - p2.x);
            int abstandY = Math.abs(p1.y - p2.y);

            if (abstandX <= schrittLaenge * 2 && abstandY <= schrittLaenge * 2) {
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }
}