import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

// --- 1. CLASSE DE BASE HEXAGONE ---
class HexagonTile {
    protected double radius;
    protected Point2D.Double position;
    protected Color baseColor;
    protected BufferedImage sprite;
    protected BufferedImage constructionSprite;
    protected int highlightTick = 0;
    protected final int maxHighlightTicks = 15;
    protected final int highlightOffset = 5;

    public HexagonTile(double radius, Point2D.Double position, Color color, BufferedImage sprite) {
        this.radius = radius;
        this.position = position;
        this.baseColor = color;
        this.sprite = sprite;
        this.constructionSprite = null;
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


    //couleur d'un hexagone
    public Color getHighlightColor() {
        int offset = highlightTick * highlightOffset;
        int r = Math.min(255, baseColor.getRed() + offset);
        int g = Math.min(255, baseColor.getGreen() + offset);
        int b = Math.min(255, baseColor.getBlue() + offset);
        return new Color(r, g, b);
    }


    //change de sprite
    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public void setConstructionSprite(BufferedImage constructionSprite) {
        this.constructionSprite = constructionSprite;
    }

    public void render(Graphics2D g2d) {
        Polygon poly = getPolygon();

        // 1. On dessine la couleur de base (sécurité si pas d'image)
        g2d.setColor(baseColor);
        g2d.fillPolygon(poly);

        // 2. On dessine l'image (texture du terrain)
        if (sprite != null) {
            Shape previousClip = g2d.getClip();
            g2d.setClip(poly);
            Rectangle bounds = poly.getBounds();
            g2d.drawImage(sprite, bounds.x, bounds.y, bounds.width, bounds.height, null);
            g2d.setClip(previousClip);
        }

        // 3. On dessine le sprite de construction par-dessus le terrain
        if (constructionSprite != null) {
            Shape previousClip = g2d.getClip();
            g2d.setClip(poly);
            Rectangle bounds = poly.getBounds();
            g2d.drawImage(constructionSprite, bounds.x, bounds.y, bounds.width, bounds.height, null);
            g2d.setClip(previousClip);
        }

        // 4. Bordure
        g2d.setColor(new Color(255, 255, 255, 100)); // Blanc translucide
        g2d.drawPolygon(poly);
    }

    public void triggerHighlight() {
        this.highlightTick = maxHighlightTicks;
    }
}











// --- 2. CLASSE HEXAGONE PLAT ---
class FlatTopHexagonTile extends HexagonTile {
    public FlatTopHexagonTile(double radius, Point2D.Double position, Color color, BufferedImage sprite) {
        super(radius, position, color, sprite);
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
    private Map<String, BufferedImage> sprites;
    private Carte carte;
    private static final Random RANDOM = new Random();
    private int numX, numY;

    // Tableau contenant le nom exact de tes classes de construction (en minuscules)
    // pour charger les fichiers "images/exploitation.png" et "images/hydrolienne.png"
    private static final String[] TOUTES_LES_CONSTRUCTIONS = {"exploitation","eolienne","centrale","panneausolaire","ville"};

    public HexGridApp(Partie partie) {
        this.carte = partie.getCarte();
        setBackground(Color.BLACK);
        sprites = new HashMap<>();
        loadSprites(); // Charge les terrains ET les constructions au démarrage

        // On génère la grille avec les dimensions réelles de la carte
        int numRows = carte.getGrille().length;
        int numCols = carte.getGrille()[0].length;
        hexagons = initHexagons(numCols, numRows, true);
        this.numX = numCols; // largeur
        this.numY = numRows; // hauteur

        // Calculer la taille préférée basée sur la grille
        double maxX = 0, maxY = 0;
        for (HexagonTile h : hexagons) {
            Polygon p = h.getPolygon();
            for (int i = 0; i < p.npoints; i++) {
                maxX = Math.max(maxX, p.xpoints[i]);
                maxY = Math.max(maxY, p.ypoints[i]);
            }
        }
        setPreferredSize(new Dimension((int) (maxX + 50), (int) (maxY + 50)));

        // Écouteur pour les mouvements de la souris
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mousePos = e.getPoint();
            }
        });

        // Écouteur pour le clic sur un hexagone
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point clickPos = e.getPoint();
                for (HexagonTile h : hexagons) {
                    if (h.getPolygon().contains(clickPos)) {
                        int index = hexagons.indexOf(h);
                        int gridY = index / numX;
                        int gridX = index % numX;

                        // Ouvre ton menu de construction
                        carte.getGrille()[gridY][gridX].show(partie);

                        // Rafraîchit immédiatement l'image de la case après fermeture du menu
                        refresh();
                        break;
                    }
                }
            }
        });

        // Boucle de rafraîchissement (~50 FPS)
        new Timer(20, e -> {
            for (HexagonTile h : hexagons) h.update();
            repaint();
        }).start();
    }

    // charge tous les sprites disponibles du dossier images
    private void loadSprites() {
        File dir = new File("images");
        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".png"));
        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                String key = name.substring(0, name.lastIndexOf('.'));
                try {
                    BufferedImage img = ImageIO.read(file);
                    sprites.put(key, img);
                    sprites.put(key.toLowerCase(), img);
                } catch (IOException e) {
                    System.err.println("Impossible de charger " + name + ", sprite ignoré.");
                    sprites.put(key, null);
                    sprites.put(key.toLowerCase(), null);
                }
            }
        } else {
            System.err.println("Dossier images introuvable : impossible de charger les sprites.");
        }
    }

    // Méthode utilitaire pour lire et stocker les fichiers images
    private void chargerImage(String nom) {
        try {
            BufferedImage img = ImageIO.read(new File("images/" + nom.toLowerCase() + ".png"));
            sprites.put(nom.toLowerCase(), img);
            sprites.put(nom, img);
        } catch (IOException e) {
            System.err.println("Impossible de charger " + nom.toLowerCase() + ".png, utilisation d'une couleur de secours.");
            sprites.put(nom.toLowerCase(), null);
            sprites.put(nom, null);
        }
    }

    // Sélectionne dynamiquement le bon sprite pour une case
    private BufferedImage genererSpriteCase(Case caseType) {
        if (caseType == null) return null;

        // Condition : Si une construction est présente sur la case
        if (caseType.getConstruction() != null) {
            // Java trouve tout seul le nom de la classe (ex: "Exploitation" ou "Hydrolienne")
            String typeConstruction = caseType.getConstruction().getClass().getSimpleName();
            BufferedImage constructionImg = sprites.get(typeConstruction.toLowerCase());

            if (constructionImg != null) {
                return constructionImg; // On renvoie l'image du bâtiment en priorité
            }
        }

        // Sinon, on renvoie le terrain de base
        if (caseType.getTypeTerrain() != null) {
            String typeTerrain = caseType.getTypeTerrain().getClass().getSimpleName();
            return sprites.get(typeTerrain.toLowerCase());
        }

        return null;
    }

    // Initialise les hexagones en fonction de la taille de la carte et de l'écran
    private List<HexagonTile> initHexagons(int numX, int numY, boolean flatTop) {
        List<HexagonTile> list = new ArrayList<>();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth() - 100;
        double screenHeight = screenSize.getHeight() - 100;
        double radius = Math.min(screenWidth / (numX * 1.5 + 1), screenHeight / (numY * Math.sqrt(3) + 1));
        radius = Math.max(radius, 10);

        HexagonTile leftmost = createHex(new Point2D.Double(50, 50), radius, flatTop, carte.getGrille()[0][0]);

        for (int y = 0; y < numY; y++) {
            if (y > 0) {
                Polygon poly = leftmost.getPolygon();
                int index = (y % 2 == 1 || flatTop) ? 2 : 4;
                leftmost = createHex(new Point2D.Double(poly.xpoints[index], poly.ypoints[index]), radius, flatTop, carte.getGrille()[y][0]);
            }

            HexagonTile current = leftmost;
            list.add(current);

            for (int x = 1; x < numX; x++) {
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
                current = createHex(nextPos, radius, flatTop, carte.getGrille()[y][x]);
                list.add(current);
            }
        }
        return list;
    }

<<<<<<< HEAD
=======
<<<<<<< Updated upstream
=======
>>>>>>> IHM
    private BufferedImage getSpriteByType(String type) {
        if (type == null) return null;
        BufferedImage sprite = sprites.get(type);
        if (sprite == null) {
            sprite = sprites.get(type.toLowerCase());
        }
        return sprite;
    }

    private BufferedImage getTerrainSprite(Case caseType) {
        if (caseType == null || caseType.getTypeTerrain() == null) return null;
        return getSpriteByType(caseType.getTypeTerrain().getClass().getSimpleName());
    }

    private BufferedImage getConstructionSprite(Case caseType) {
        if (caseType == null || caseType.getConstruction() == null) return null;
<<<<<<< HEAD
        return getSpriteByType(caseType.getConstruction().getClass().getSimpleName());
    }
=======
        
        String constructionName = caseType.getConstruction().getClass().getSimpleName();
        String terrainName = caseType.getTypeTerrain().getClass().getSimpleName();
        
        // Chercher d'abord avec terrain_construction (ex: "eolienne_plaine")
        BufferedImage sprite = getSpriteByType(constructionName + "_" + terrainName);
        if (sprite != null) return sprite;
        
        // Sinon utiliser juste le nom de la construction
        return getSpriteByType(constructionName);
    }
>>>>>>> Stashed changes
>>>>>>> IHM

    //raffraichie la grille en fonction de la carte
    public void refresh() {
        if (carte == null || hexagons == null) return;
        for (int i = 0; i < hexagons.size(); i++) {
            int gridY = i / numX;
            int gridX = i % numX;
            Case caseType = carte.getGrille()[gridY][gridX];
            BufferedImage terrainSprite = getTerrainSprite(caseType);
            BufferedImage constructionSprite = getConstructionSprite(caseType);
            hexagons.get(i).setSprite(terrainSprite);
            hexagons.get(i).setConstructionSprite(constructionSprite);
        }
        repaint();
    }

    // Crée un objet hexagone physique pour l'affichage
    private HexagonTile createHex(Point2D.Double pos, double r, boolean flat, Case caseType) {
        BufferedImage terrainSprite = getTerrainSprite(caseType);
        BufferedImage constructionSprite = getConstructionSprite(caseType);
        Color c = new Color(0, 0, 0, 0);
        HexagonTile tile = flat ? new FlatTopHexagonTile(r, pos, c, terrainSprite) : new HexagonTile(r, pos, c, terrainSprite);
        tile.setConstructionSprite(constructionSprite);
        return tile;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dessine toutes les tuiles
        for (HexagonTile h : hexagons) {
            h.render(g2d);
<<<<<<< HEAD

            // Gère les effets de survol de la souris (Highlight)
=======
<<<<<<< Updated upstream
            // Si la souris est proche du centre (collision)
>>>>>>> IHM
            if (h.getCentre().distance(mousePos) < h.getMinimalRadius()) {
                h.triggerHighlight();
                for(HexagonTile n : hexagons) {
                    if (h.getCentre().distance(n.getCentre()) < h.getMinimalRadius() * 2.1) {
                        n.triggerHighlight();
                    }
                }
            }
=======
>>>>>>> Stashed changes
        }
    }
}
