package org.kaleeis_bears.reseau_24h.util;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Grille {

	private final int[] grille;
	private final boolean[] cases, joueurs;

	public final int longueur, largeur, aire;

	public Grille(int[][] grille) {
		this(grille, new boolean[] { true, false });
	}
	public Grille(int[][] grille, boolean[] typesTraversables) {
		this(grille.length, grille[0].length, typesTraversables);
		for (int x = 0; x < this.longueur; x++)
			for (int y = 0; y < this.largeur; y++)
				this.set(x, y, grille[y][x]);
				// this.grille[Grille.getCase(x, y, this.longueur)] = grille[x][y];
	}
	public Grille(int longueur, int largeur) {
		this(longueur, largeur, new boolean[] { true, false });
	}
	public Grille(int longueur, int largeur, boolean[] typesTraversables) {
		this.longueur = longueur;
		this.largeur = largeur;
		this.aire = longueur * largeur;
		this.grille = new int[this.aire];
		this.joueurs = new boolean[this.aire];
		this.cases = typesTraversables;
	}

	public boolean estSurLaGrille(int noCase) {
		return noCase < this.aire;
	}
	public boolean estSurLaGrille(int x, int y) {
		return (x >= 0 && x < this.longueur) && (y >= 0 && y < this.largeur);
	}

	public boolean estJoueurSurCase(int noCase) {
		return this.joueurs[noCase];
	}
	public void setJoueurSurCase(int noCase, boolean surCase) {
		this.joueurs[noCase] = surCase;
	}

	public int getCase(int x, int y) {
		if (!this.estSurLaGrille(x, y))
			throw new IndexOutOfBoundsException("(" + x + ", " + y + ") est en dehors de la grille.");
		return getCase(x, y, this.longueur);
	}
	public static int getCase(int x, int y, int longueur) {
		return y * longueur + x;
	}

	public int getX(int noCase) {
		return getX(noCase, this.longueur);
	}
	public static int getX(int noCase, int longueur) {
		return noCase % longueur;
	}

	public int getY(int noCase) {
		return getY(noCase, this.longueur);
	}
	public static int getY(int noCase, int longueur) {
		return noCase / longueur;
	}

	public int get(int noCase) {
		return this.grille[noCase];
	}
	public int get(int x, int y) {
		return this.get(this.getCase(x, y));
	}
	public void set(int noCase, int valeur) {
		this.grille[noCase] = valeur;
	}
	public void set(int x, int y, int valeur) {
		this.set(this.getCase(x, y), valeur);
	}

	public int[] getObjets(int ...valeurs) {
		return Tableau.chercherTous(this.grille, x -> Tableau.position(valeurs, x) != -1);
	}

	public boolean estTraversable(int noCase) {
		return !this.estJoueurSurCase(noCase) && (this.grille[noCase] >= 0 && this.grille[noCase] < this.cases.length ? this.cases[this.grille[noCase]] : true);
	}
	public boolean estTraversable(int x, int y) {
		return this.estTraversable(this.getCase(x, y));
	}

	public int getHaut(int noCase) {
		return noCase - this.longueur;
	}
	public int getBas(int noCase) {
		return noCase + this.longueur;
	}
	public int getDroite(int noCase) {
		return noCase - 1;
	}
	public int getGauche(int noCase) {
		return noCase + 1;
	}

	public int[] getVoisins(int noCase, boolean boucler) {
		LinkedList<Integer> voisins = new LinkedList<>();
		int x = this.getX(noCase), y = this.getY(noCase);
		if (x > 0 || boucler) voisins.add(this.getDroite(noCase));
		if (y > 0) voisins.add(this.getHaut(noCase));
		if (x < this.longueur - 1) voisins.add(this.getGauche(noCase));
		if (y < this.largeur - 1) voisins.add(this.getBas(noCase));
		return Tableau.listeVersTableauNombre(voisins);
	}

	public Graphe graphe(boolean isolerMurs) {
		Graphe g = new Graphe(this.aire);
		for (int noCase = 0; noCase < this.aire; noCase++)
			if (isolerMurs || this.estTraversable(noCase))
				for (int enfant : this.getVoisins(noCase, false))
					if (this.estTraversable(enfant))
						g.setArc(noCase, enfant, 1);
		return g;
	}
	public Graphe graphe() {
		return this.graphe(false);
	}

	public Graphe graphe(int[] sommets) {
		int i, actuel;
		int[] grilleSommets = new int[this.grille.length]
			, historique, voisins;
		Tableau.remplir(grilleSommets, -1);
		for (i = 0; i < sommets.length; i++)
			grilleSommets[sommets[i]] = i;
		Graphe g = new Graphe(sommets.length);
		for (i = 0; i < sommets.length; i++)
		{
			LinkedList<Integer> file = new LinkedList<>();
			historique = new int[this.grille.length];
			Tableau.remplir(historique, Integer.MAX_VALUE);
			historique[this.grille[i]] = 0;
			file.add(this.grille[i]);
			while (file.size() != 0)
			{
				actuel = file.remove();
				voisins = this.getVoisins(actuel, false);
				for (int enfant : voisins)
					if (historique[enfant] == Integer.MAX_VALUE &&
						this.estTraversable(enfant))
					{
						historique[enfant] = historique[actuel] + 1;
						file.add(enfant);
						if (grilleSommets[enfant] != -1)
							g.setDoubleArc(grilleSommets[enfant], i, historique[enfant]);
					}
			}
		}
		return g;
	}

	public String toString() {
		return toString('.', '#');
	}
	public String toString(char ...affichage) {
		String result = "";
		for (int noCase = 0; noCase < this.aire; noCase++)
		{
			System.out.print(this.grille[noCase] >= 0 && this.grille[noCase] < affichage.length ? affichage[this.grille[noCase]] : "" + this.grille[noCase]);
			if (this.getX(noCase + 1) == 0 && this.getY(noCase + 1) != this.largeur)
				System.out.println();
		}
		return result;
	}


	public int getLance(int posLanceur, List<Integer> zoneReception) {
		int posLances[] = new int[4];
		for (int i = 0; i < 4; i++) {
			if (!this.estTraversable(posLanceur + i) || i == 3) {
				posLances[0] = posLanceur;
				break;
			}
		}
		for (int i = 0; i < 4; i++) {
			if (!this.estTraversable(posLanceur - i) || i == 3) {
				posLances[1] = posLanceur;
				break;
			}
		}
		for (int i = 0; i < 4; i++) {
			if (!this.estTraversable(posLanceur + i * this.largeur) || i == 3) {
				posLances[2] = posLanceur;
				break;
			}
		}
		for (int i = 0; i < 4; i++) {
			if (!this.estTraversable(posLanceur - i * this.largeur) || i == 3) {
				posLances[3] = posLanceur;
				break;
			}
		}
		int tmp = -1;

		for (int pos : posLances) {
			tmp = zoneReception.indexOf(pos);

			if (tmp != -1) {
				break;
			}
		}

		return tmp;
	}
}