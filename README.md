# 🌍 Symbiose

**Symbiose** est un jeu de gestion stratégique au tour par tour développé en Java. Prenez la tête d'une métropole en plein essor et relevez le défi ultime : atteindre l'indépendance énergétique sans provoquer l'effondrement écologique de votre planète. 

Le jeu propose une simulation environnementale poussée (automate cellulaire, propagation de la pollution, météo dynamique) générée sur une grille hexagonale.

---

## 📖 Table des matières
1. [Documentation Utilisateur (Comment jouer ?)](#-documentation-utilisateur)
2. [Documentation Technique (Architecture & Code)](#-documentation-technique)
3. [Installation et Lancement](#-installation-et-lancement)

---

## 🎮 Documentation Utilisateur

### 🎯 Le But du Jeu
Votre objectif est d'atteindre une **production d'énergie de 10 000 unités** pour assurer l'avenir de votre ville. 
Mais attention : chaque infrastructure construite a un impact sur l'environnement. Si vous dépassez les **Limites Planétaires** (pollution, déforestation, chute de la biodiversité), la nature s'effondrera et la partie sera perdue.

### ⚙️ Mécaniques de base
* **Les Tours et les Actions :** Vous disposez de **3 actions par tour** (construire, détruire, etc.). Une fois vos actions épuisées, cliquez sur "Fin de Tour" pour laisser le temps s'écouler.
* **L'Économie :** Vous devez gérer deux flux principaux :
  * 🧱 **Ressources :** Nécessaires pour construire (CapEx) et entretenir (OpEx) vos bâtiments. Produites par les *Exploitations*.
  * ⚡ **Énergie :** Produite par vos centrales et énergies renouvelables. Elle permet à la ville de se développer.

### 🗺️ L'Écosystème
La carte est vivante. Elle est composée de plusieurs biomes :
* 🌲 **Forêt :** Le poumon de la carte. Elle absorbe la pollution de l'air. Si la pollution ambiante est trop forte, les arbres meurent.
* 💧 **Lac :** Possède une capacité d'auto-épuration, mais l'eau peut devenir toxique si trop de déchets y sont déversés.
* 🌾 **Plaine :** Zone propice à la construction, sensible à l'érosion et aux tempêtes.

*Note : La météo (vent, soleil, pluie) est dynamique, se déplace sur la carte et influence directement le rendement de vos énergies renouvelables !*

### 🏗️ Les Infrastructures
* **Ville :** Le cœur de votre partie. Consomme beaucoup de ressources, mais évolue au niveau 2 si vous atteignez 5 000 d'énergie !
* **Exploitation :** Indispensable. Construit sur les forêts ou les lacs, elle génère les matériaux de base au prix d'une forte pollution.
* **Éolienne & Panneau Solaire :** Énergies vertes. Coûteuses à installer mais propres. Leur rendement dépend de la météo locale.
* **Hydrolienne :** À placer sur les lacs. Excellent rendement grâce aux courants, mais dangereuse pour la faune aquatique.
* **Centrale (Charbon / Uranium) :** Puissance colossale, mais attention au désastre écologique ou au coût d'entretien exorbitant.

### 🛑 Conditions de Défaite
* **Banqueroute :** Vos ressources ou votre énergie tombent en négatif (défaut d'entretien).
* **Limites Planétaires franchies :**
  * Taux de Forêt moyen inférieur à 15%
  * Pollution atmosphérique moyenne supérieure à 80%
  * Qualité de la Vie Sauvage inférieure à 20%
* **Temps écoulé :** Nombre de tours limite atteint sans avoir gagné.

---

## 💻 Documentation Technique

Symbiose a été conçu avec une architecture Orientée Objet stricte en Java, mettant l'accent sur la modularité et l'encapsulation.

### 🛠️ Technologies
* **Langage :** Java (JDK 17+)
* **Interface Graphique :** Java Swing
* **Moteur de Rendu :** Java2D personnalisé (Moteur de grille hexagonale via `HexGridApp`)

### 🏗️ Architecture et Design Patterns

Le projet est divisé en plusieurs couches logiques :

#### 1. Le Moteur de Jeu (`Partie.java`, `Carte.java`)
Gère l'orchestration des tours, les conditions de victoire/défaite et l'économie globale. La `Carte` génère la grille de manière procédurale (répartition pondérée des biomes) et gère les moyennes écologiques (Limites Planétaires).

#### 2. L'Automate Cellulaire (`Case.java`)
La grille n'est pas un simple tableau de données. Chaque `Case` :
* Connaît ses 6 voisines (via algorithme de détection de voisinage spatial).
* Applique un algorithme de **diffusion environnementale** à chaque fin de tour : la pollution ruisselle d'une case à l'autre et les forêts saines rayonnent sur les cases polluées adjacentes.

#### 3. L'Héritage des Terrains (`TypeTerrain.java`)
Utilisation du polymorphisme. `Foret`, `Plaine` et `Lac` héritent d'une classe abstraite. Chaque terrain possède sa propre implémentation de la méthode `fin_tour(Case c)` pour simuler la résilience naturelle (ex: la forêt calcule son taux d'absorption de CO2 en fonction de son recouvrement).

#### 4. Le Système de Construction (`Construction.java`)
Architecture hiérarchique :
* `Construction` (Abstraite) définit les attributs universels (Coût, Entretien, Impact écologique).
* `ProdEnergie` hérite de Construction et ajoute des mécaniques de rendement (`produireEnergie(Case c)`).
* Les sous-classes (`Eolienne`, `Centrale`, `Ville`...) implémentent leurs formules spécifiques dans `BilanTour(Case c)`.

#### 5. Météo Dynamique (`Meteo.java`)
Un système probabiliste basé sur les chaînes de Markov. La météo d'une case a tendance à s'aligner sur la météo de ses voisines, créant des "fronts" météorologiques naturels (tempêtes, fronts nuageux) qui affectent directement la physique des bâtiments (vent pour les éoliennes, soleil pour les panneaux).

### 🎨 Gestion de l'Interface Graphique
* Séparation du modèle et de la vue : Les fenêtres contextuelles de gestion (`Plaine.java`, etc.) modifient les états du jeu et appellent des écouteurs (`notifyMapChanged`) pour mettre à jour la vue de la grille (`HexGridApp.java`).
* Les sprites sont chargés dynamiquement et appliqués sur des polygones mathématiques générés à la volée.

---

## 🚀 Installation et Lancement

1. **Prérequis :** Assurez-vous d'avoir Java (JDK 17 ou supérieur) d'installé sur votre machine.
2. **Cloner le dépôt :**
   ```bash
   git clone [https://github.com/TitouanRd/Symbiose](https://github.com/TitouanRd/Symbiose)
   cd Symbiose
