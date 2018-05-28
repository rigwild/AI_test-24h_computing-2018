package org.kaleeis_bears.reseau_24h;

import org.kaleeis_bears.reseau_24h.Action;
import org.kaleeis_bears.reseau_24h.util.ArbreDesCheminsMinimaux;
import org.kaleeis_bears.reseau_24h.util.Grille;
import org.kaleeis_bears.reseau_24h.util.Graphe;

public class EtatJeu {

	public final int numeroEquipe, nombreEquipe, longueur, largeur;
	public final Grille grille;
	public final Graphe graphe;
	public final Equipe nous;
	public final Equipe[] equipes;

	public EtatJeu(String donnes) {
		String[] splt = donnes.split("_");
		this.numeroEquipe = new Integer(splt[0]);
		this.nombreEquipe = new Integer(splt[1]);
		
		String[] grille = splt[2].split(",");
		String[] dimensions = grille[0].split(":");
		this.longueur = new Integer(dimensions[0]);
		this.largeur = new Integer(dimensions[1]);

		this.grille = new Grille(this.longueur, this.largeur, new boolean[] { true, true, true, true, true, false, true, false });
		String typeCase;
		for (int y = 0; y < this.largeur; y++)
			for (int x = 0; x < this.longueur; x++)
			{
				typeCase = grille[y + 1].substring(x, x + 1);
				this.grille.set(x, y, (
					typeCase.equals("X") ? 5 : (
					typeCase.equals(".") ? 6 :
					new Integer(typeCase)
				)));
			}

		this.equipes = new Equipe[this.nombreEquipe];
		Equipe equipe;
		Joueur joueur;
		for (int i = 3; i < 3 + this.nombreEquipe; i++)
		{
			equipe = new Equipe(this.longueur, splt[i]);
			for (int j = 0; j < equipe.joueurs.length; j++)
			{
				joueur = equipe.joueurs[j];
				this.grille.setJoueurSurCase(joueur.noCase,true);
			}
			for (int j = 0; j < equipe.casesDepart.length; j++)
				this.grille.set(equipe.casesDepart[j][0], equipe.casesDepart[j][1], 8);
			this.equipes[i - 3] = equipe;
		}
		this.nous = this.equipes[this.numeroEquipe];

		this.graphe = this.grille.graphe(true);
	}

	public int[] getCasesDepart() {
		int[] cases = new int[this.nous.casesDepart.length];
		for (int i = 0; i < cases.length; i++)
			cases[i] = this.nous.casesDepart[i][1] * this.largeur + this.nous.casesDepart[i][0];
		return cases;
	}

	public ArbreDesCheminsMinimaux getDistances(int racine) {
		return this.graphe.dijkstra(racine);
	}

	public int getPlusProche(int racine, int ...types) {
		return this.getPlusProche(this.getDistances(racine), types);
	}
	public int getPlusProche(ArbreDesCheminsMinimaux arbre, int ...types) {
		return arbre.getSommetPlusProche(this.grille.getObjets(types));
	}

	public int[] getLesPlusProche(ArbreDesCheminsMinimaux arbre, int ...types) {
		return arbre.trierParDistance(this.grille.getObjets(types));
	}

	public Action getDirectionPlusProche(int depart, int voisin, boolean lancer) {
		if (depart == voisin)
			return Action.RIEN;
		else if (voisin == this.grille.getHaut(depart))
			return lancer ? Action.LANCER_NORD : Action.BOUGER_NORD;
		else if (voisin == this.grille.getBas(depart))
			return lancer ? Action.LANCER_SUD : Action.BOUGER_SUD;
		else if (voisin == this.grille.getGauche(depart))
			return lancer ? Action.LANCER_EST : Action.BOUGER_EST;
		else
			return lancer ? Action.LANCER_OUEST : Action.BOUGER_OUEST;
	}
	public Action getDirectionPlusProche(ArbreDesCheminsMinimaux arbre, boolean lancer, int ...types) {
		int plusProche = this.getPlusProche(arbre, types);
		int[] chemin = arbre.getChemin(plusProche);
		return this.getDirectionPlusProche(chemin[0], chemin[chemin.length == 1 ? 0 : 1], lancer);
	}
}