import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

// --- 1. CLASSE DE BASE HEXAGONE ---
class HexagonTile {
    protected double radius;
    protected Point2D.Double position;
    protected Color baseColor;
    protected int highlightTick = 0;
    protected final int maxHighlightTicks = 15;
    protected final int highlightOffset = 5;

    public HexagonTile(double radius, Point2D.Double position, Color color) {
        this.radius = radius;
        this.position = position;
        this.baseColor = color;
    }

    public void update() {
        if (highlightTick > 0) highlightTick--;
    }

    public double getMinimalRadius() {
        return radius * Math.cos(Math.toRadians(30));
    }

    public Point2D.Double getCentre() {
        return new Point2D.Double(position.x, position.y + radius);
    }

    public Polygon getPolygon() {
        Polygon p = new Polygon();
        double x = position.x;
        double y = position.y;
        double hr = radius / 2.0;
        double mr = getMinimalRadius();

        // Sommets Pointy Top (ton calcul Python original)
        p.addPoint((int) x, (int) y);
        p.addPoint((int) (x - mr), (int) (y + hr));
        p.addPoint((int) (x - mr), (int) (y + 3 * hr));
        p.addPoint((int) x, (int) (y + 2 * radius));
        p.addPoint((int) (x + mr), (int) (y + 3 * hr));
        p.addPoint((int) (x + mr), (int) (y + hr));
        return p;
    }

    public Color getHighlightColor() {
        int offset = highlightTick * highlightOffset;
        int r = Math.min(255, baseColor.getRed() + offset);
        int g = Math.min(255, baseColor.getGreen() + offset);
        int b = Math.min(255, baseColor.getBlue() + offset);
        return new Color(r, g, b);
    }

    public void render(Graphics2D g2d) {
        Polygon poly = getPolygon();
        g2d.setColor(getHighlightColor());
        g2d.fillPolygon(poly);
        g2d.setColor(new Color(0, 0, 0, 50)); // Bordure discrète
        g2d.drawPolygon(poly);
    }

    public void triggerHighlight() {
        this.highlightTick = maxHighlightTicks;
    }
}

// --- 2. CLASSE HEXAGONE PLAT ---
class FlatTopHexagonTile extends HexagonTile {
    public FlatTopHexagonTile(double radius, Point2D.Double position, Color color) {
        super(radius, position, color);
    }

    @Override
    public Point2D.Double getCentre() {
        return new Point2D.Double(position.x + radius / 2.0, position.y + getMinimalRadius());
    }

    @Override
    public Polygon getPolygon() {
        Polygon p = new Polygon();
        double x = position.x;
        double y = position.y;
        double hr = radius / 2.0;
        double mr = getMinimalRadius();

        p.addPoint((int) x, (int) y);
        p.addPoint((int) (x - hr), (int) (y + mr));
        p.addPoint((int) x, (int) (y + 2 * mr));
        p.addPoint((int) (x + radius), (int) (y + 2 * mr));
        p.addPoint((int) (x + 3 * hr), (int) (y + mr));
        p.addPoint((int) (x + radius), (int) y);
        return p;
    }
}

// --- 3. APPLICATION PRINCIPALE ---
public class HexGridApp extends JPanel {
    private List<HexagonTile> hexagons;
    private Point mousePos = new Point(0, 0);
    private static final Random RANDOM = new Random();

    public HexGridApp() {
        setBackground(Color.BLACK);
        // On génère la grille avec la logique Python
        hexagons = initHexagons(15, 10, true); 

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mousePos = e.getPoint();
            }
        });

        // Loop de 50 FPS
        new Timer(20, e -> {
            for (HexagonTile h : hexagons) h.update();
            repaint();
        }).start();
    }

    private List<HexagonTile> initHexagons(int numX, int numY, boolean flatTop) {
        List<HexagonTile> list = new ArrayList<>();
        double radius = 30.0;
        
        // On commence un peu en dehors de l'écran (comme ton -50, -50)
        HexagonTile leftmost = createHex(new Point2D.Double(50, 50), radius, flatTop);
        
        for (int y = 0; y < numY; y++) {
            if (y > 0) {
                Polygon poly = leftmost.getPolygon();
                int index = (y % 2 == 1 || flatTop) ? 2 : 4;
                leftmost = createHex(new Point2D.Double(poly.xpoints[index], poly.ypoints[index]), radius, flatTop);
            }
            
            HexagonTile current = leftmost;
            list.add(current);
            
            for (int x = 0; x < numX; x++) {
                double px = current.position.x;
                double py = current.position.y;
                Point2D.Double nextPos;

                if (flatTop) {
                    if (x % 2 == 1) {
                        nextPos = new Point2D.Double(px + radius * 1.5, py - current.getMinimalRadius());
                    } else {
                        nextPos = new Point2D.Double(px + radius * 1.5, py + current.getMinimalRadius());
                    }
                } else {
                    nextPos = new Point2D.Double(px + current.getMinimalRadius() * 2, py);
                }
                current = createHex(nextPos, radius, flatTop);
                list.add(current);
            }
        }
        return list;
    }

    private HexagonTile createHex(Point2D.Double pos, double r, boolean flat) {
        Color c = new Color(100, 180, 220); // même couleur pour tous les hexagones
        
        return flat ? new FlatTopHexagonTile(r, pos, c) : new HexagonTile(r, pos, c);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (HexagonTile h : hexagons) {
            h.render(g2d);
            // Si la souris est proche du centre (collision)
            if (h.getCentre().distance(mousePos) < h.getMinimalRadius()) {
                h.triggerHighlight();
                // Highlight aussi les voisins proches
                for(HexagonTile n : hexagons) {
                    if (h.getCentre().distance(n.getCentre()) < h.getMinimalRadius() * 2.1) {
                        n.triggerHighlight();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Hex Grid");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new HexGridApp());
        f.setSize(800, 600);
        f.setVisible(true);
    }
}