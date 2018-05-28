package org.kaleeis_bears.reseau_24h.versions_ia;

import java.util.List;
import java.util.ArrayList;

import org.kaleeis_bears.reseau_24h.Action;
import org.kaleeis_bears.reseau_24h.EtatJeu;
import org.kaleeis_bears.reseau_24h.BaseIA;
import org.kaleeis_bears.reseau_24h.util.Tableau;
import org.kaleeis_bears.reseau_24h.util.ArbreDesCheminsMinimaux;

public class IA_Noob extends BaseIA {
	public IA_Noob(String hote, int port, boolean debug) throws Exception {
		super(hote, port, debug);

		debug("IA de type « IA_Noob » créé (serveur : " + hote + ":" + port + ").");
	}

	public Action[] jouer(EtatJeu etat) {
		debug(etat.grille.toString('0','1','2','3','4','#','.','j'));
		return new Action[] {
			this.Quater(etat),
			this.lanceur1(etat),
			this.lanceur2(etat)
		};
	}

	private Action Quater(EtatJeu etat){
		int departX = etat.nous.casesDepart[0][0];
		int departY = etat.nous.casesDepart[0][1];

		int largeur = etat.grille.largeur;

		int numCase = etat.nous.joueurs[0].noCase;
		int[] chataigneProche = etat.grille.getObjets(4);

		int objetPorte = etat.nous.joueurs[0].inventaire;
		ArbreDesCheminsMinimaux arbreOrigine = etat.getDistances(etat.grille.getCase(departX,departY));
		if(objetPorte == 4){
			int[] voisins = etat.grille.getVoisins(numCase,false);
			voisins = arbreOrigine.trierParDistance(voisins);
			for(int i = voisins.length - 1;i>=0;i--){
				if(arbreOrigine.estAccessible(voisins[i])){
					return  etat.getDirectionPlusProche(numCase,voisins[i],true);
				}
			}
			

		}
		debug("case " + etat.grille.get(numCase) );
		if(etat.grille.get(numCase) == 4){
			debug("OK je prends en " + numCase);
			return Action.CHANGER_OBJET;
		}

		debug("des chantaignes dans ma partie " + chataigneProche.length);
		chataigneProche = arbreOrigine.getSommetDistanceEntre(0,largeur-3,chataigneProche);
		if(chataigneProche.length != 0){
			debug("des chantaignes dans ma partie " + chataigneProche.length);
			ArbreDesCheminsMinimaux arbrePerso = etat.getDistances(numCase);
			chataigneProche = arbrePerso.trierParDistance(chataigneProche);
			

			int action[] = arbrePerso.getChemin(chataigneProche[0]);
			if (action.length > 1)
			{
				debug("action 0 " + action[1] + " num case " + numCase);
				return etat.getDirectionPlusProche(numCase,action[1],false);
			}
		}
		else
			return this.lanceur2(etat);

		

		return Action.RIEN;
	}

	private Action lanceur1(EtatJeu etat){
		int departX = etat.nous.casesDepart[1][0];
		int departY = etat.nous.casesDepart[1][1];

		int largeur = etat.grille.largeur;

		int numCase = etat.nous.joueurs[1].noCase;
		int[] chataigneProche = etat.grille.getObjets(0, 1, 2, 3);

		int objetPorte = etat.nous.joueurs[1].inventaire;
		ArbreDesCheminsMinimaux arbreOrigine = etat.getDistances(etat.grille.getCase(departX,departY));
		if(objetPorte >= 0 && objetPorte < 4){
			int[] voisins = etat.grille.getVoisins(numCase,false);
			voisins = arbreOrigine.trierParDistance(voisins);
			for(int i = 0;i<voisins.length;i++){
				if(arbreOrigine.estAccessible(voisins[i])){
					return etat.getDirectionPlusProche(numCase,voisins[i],true);
				}
			}
			

		}
		debug("case " + etat.grille.get(numCase));
		if(etat.grille.get(numCase) >= 0 && etat.grille.get(numCase) < 4){
			debug("OK je prends en " + numCase);
			return Action.CHANGER_OBJET;
		}

		debug("des fruits dans ma partie " + chataigneProche.length);
		// chataigneProche = arbreOrigine.getSommetDistanceEntre(0,largeur-3,chataigneProche);
		if(chataigneProche.length != 0){
			debug("des chantaignes dans ma partie " + chataigneProche.length);
			ArbreDesCheminsMinimaux arbrePerso = etat.getDistances(numCase);
			chataigneProche = arbrePerso.trierParDistance(chataigneProche);
			

			int action[] = arbrePerso.getChemin(chataigneProche[0]);
			if (action.length > 1)
			{
				debug("action 0 " + action[1] + " num case " + numCase);
				return etat.getDirectionPlusProche(numCase,action[1],false);
			}
		}

		

		return Action.RIEN;
	}
	private Action lanceur2(EtatJeu etat){
		int departX = etat.nous.casesDepart[2][0];
		int departY = etat.nous.casesDepart[2][1];

		int largeur = etat.grille.largeur;

		int numCase = etat.nous.joueurs[2].noCase;
		int[] chataigneProche = etat.grille.getObjets(0, 1, 2, 3);

		int objetPorte = etat.nous.joueurs[2].inventaire;
		ArbreDesCheminsMinimaux arbreOrigine = etat.getDistances(etat.grille.getCase(departX,departY));
		if(objetPorte >= 0 && objetPorte < 4){
			int[] voisins = etat.grille.getVoisins(numCase,false);
			voisins = arbreOrigine.trierParDistance(voisins);
			for(int i = 0;i<voisins.length;i++){
				if(arbreOrigine.estAccessible(voisins[i])){
					return etat.getDirectionPlusProche(numCase,voisins[i],true);
				}
			}
			

		}
		debug("case " + etat.grille.get(numCase));
		if(etat.grille.get(numCase) >= 0 && etat.grille.get(numCase) < 4){
			debug("OK je prends en " + numCase);
			return Action.CHANGER_OBJET;
		}

		debug("des fruits dans ma partie " + chataigneProche.length);
		chataigneProche = arbreOrigine.getSommetDistanceEntre(0,largeur-3,chataigneProche);
		if(chataigneProche.length != 0){
			debug("des chantaignes dans ma partie " + chataigneProche.length);
			ArbreDesCheminsMinimaux arbrePerso = etat.getDistances(numCase);
			chataigneProche = arbreOrigine.trierParDistance(chataigneProche);
			

			int action[] = arbrePerso.getChemin(chataigneProche[0]);
			if (action.length > 1)
			{
				debug("action 0 " + action[1] + " num case " + numCase);
				return etat.getDirectionPlusProche(numCase,action[1],false);
			}
		}

		

		return Action.RIEN;
	}
	public Action lanceur2_tmp(EtatJeu etat) {
		int posLanceur = etat.nous.joueurs[2].noCase;

		// check si il y a un fruit sur ma case
		if (etat.grille.get(posLanceur) < 4) {
			return Action.CHANGER_OBJET;
		}


		List<Integer> zoneReception = new ArrayList<Integer>();
		List<Integer> fruitReception = new ArrayList<Integer>();

		// Récupérer la zone la plus en coin
		int departX, departY;
		
		if (
			etat.nous.casesDepart[0][0] == 1 ||
			etat.nous.casesDepart[1][0] == 1 ||
			etat.nous.casesDepart[2][0] == 1
		) {
			departX = 1;
		} else {
			departX = etat.grille.longueur - 1;
		}
		if (
			etat.nous.casesDepart[0][1] == 1 ||
			etat.nous.casesDepart[1][1] == 1 ||
			etat.nous.casesDepart[2][1] == 1
		) {
			departY = 1;
		} else {
			departY = etat.grille.largeur - 1;
		}

		zoneReception.add(etat.grille.getCase(departX, departY));

		int voisins[] = etat.grille.getVoisins(etat.grille.getCase(departX, departY), false);

		for(int caseS : voisins) {
			if (etat.grille.estTraversable(caseS) || etat.grille.estJoueurSurCase(caseS)) {
				zoneReception.add(caseS);
			}
		}

		// récupère la zoneReception
		int moveX, moveY;
		if (departX == 1) {
			moveX = 1;
		} else {
			moveX = -1;
		}

		if (departY == 1) {
			moveY = 1;
		} else {
			moveY = -1;
		}

		int caseActuelle;
		for (int i = 0; i < 4; i++) {
			caseActuelle = zoneReception.get(1) + moveX * (i + 1);
			if (caseActuelle < 0 || !etat.grille.estTraversable(caseActuelle)) {
				break;
			}
			if (!zoneReception.contains(caseActuelle))
				zoneReception.add(caseActuelle);
		}
		for (int i = 0; i < 4; i++) {
			caseActuelle = zoneReception.get(1) + moveY * (i + 1) * etat.grille.longueur;
			if (caseActuelle < 0 || !etat.grille.estTraversable(caseActuelle)) {
				break;
			}
			if (!zoneReception.contains(caseActuelle))
				zoneReception.add(caseActuelle);
		}
		for (int i = 0; i < 4; i++) {
			caseActuelle = zoneReception.get(2) + moveX * (i + 1);
			if (caseActuelle < 0 || !etat.grille.estTraversable(caseActuelle)) {
				break;
			}
			if (!zoneReception.contains(caseActuelle))
				zoneReception.add(caseActuelle);
		}
		for (int i = 0; i < 4; i++) {
			caseActuelle = zoneReception.get(2) + moveY * (i + 1) * etat.grille.longueur;
			if (caseActuelle < 0 || !etat.grille.estTraversable(caseActuelle)) {
				break;
			}
			if (!zoneReception.contains(caseActuelle))
				zoneReception.add(caseActuelle);
		}

		
		
		// check si j'ai un fruit sur moi
		if (etat.nous.joueurs[2].inventaire < 4 && etat.nous.joueurs[2].inventaire >= 0) {
			// Faire une fonction pour savoir si on peut lancer a partir d'une case et d'une zone de reception
			int posLances[] = new int[4];
			for (int i = 0; i < 5; i++) {
				if (!etat.grille.estTraversable(posLanceur + i)) {
					posLances[0] = posLanceur + (i-1);
					break;
				}
				if (i == 4) {
					posLances[0] = posLanceur + (i);
				}
			}
			for (int i = 0; i < 5; i++) {
				if (!etat.grille.estTraversable(posLanceur - i)) {
					posLances[1] = posLanceur - (i-1);
					break;
				}
				if (i == 4) {
					posLances[1] = posLanceur - (i);
				}
			}
			for (int i = 0; i < 5; i++) {
				if (!etat.grille.estTraversable(posLanceur + i * etat.grille.longueur)) {
					posLances[2] = posLanceur + (i-1) * etat.grille.longueur;
					break;
				}
				if (i == 4) {
					posLances[2] = posLanceur + (i) * etat.grille.longueur;

				}
			}
			for (int i = 0; i < 5; i++) {
				if (!etat.grille.estTraversable(posLanceur - i * etat.grille.longueur)) {
					posLances[3] = posLanceur - (i-1) * etat.grille.longueur;
					break;
				}
				if (i == 4) {
					posLances[3] = posLanceur - (i) * etat.grille.longueur;

				}
			}
			int tmp = -1;
			int zoneDepart[] = new int[3];
			zoneDepart[0] = etat.nous.casesDepart[0][0] + etat.nous.casesDepart[0][1] * etat.grille.longueur;
			zoneDepart[1] = etat.nous.casesDepart[1][0] + etat.nous.casesDepart[1][1] * etat.grille.longueur;
			zoneDepart[2] = etat.nous.casesDepart[2][0] + etat.nous.casesDepart[2][1] * etat.grille.longueur;

			for (int pos : posLances) {
				tmp = Tableau.tableauNombreVersListe(zoneDepart).indexOf(new Integer(pos));

				if (tmp != -1) {
					break;
				}
			}
			///////////////

			if (tmp != -1) {
				int posLanceurX = etat.grille.getX(posLanceur);
				int posLanceurY = etat.grille.getY(posLanceur);

				int tmpX = etat.grille.getX(tmp);
				int tmpY = etat.grille.getY(tmp);

				if (tmpX == posLanceurX) {
					if (tmpY > posLanceurY) {
						return Action.LANCER_SUD;
					} else {
						return Action.LANCER_NORD;
					}
				} else {
					if (tmpX > posLanceurX) {
						return Action.LANCER_EST;
					} else {
						return Action.LANCER_OUEST;
					}
				}
			} else {
				// check les cases adjascentes si c'est possible de lancer
				for (int voisin : etat.grille.getVoisins(posLanceur, false)) {
					tmp = etat.grille.getLance(voisin, Tableau.tableauNombreVersListe(zoneDepart));
					if (tmp != -1) {
						break;
					}
				}
				if (tmp != -1) {
					return etat.getDirectionPlusProche(posLanceur, tmp, false);
				} else {
					return Action.LANCER_OUEST;
				}
			}
		}

		// cherche tous les fruits de la zone
		for (int i : zoneReception) {
			if (etat.grille.get(i) < 4) {
				fruitReception.add(i);
			}
		}

		// for (int i : zoneReception) {
		// 	System.out.print(" " + i);
		// }
		// System.out.println("");

		// cherche le fruit le plus proche
		if (fruitReception.size() > 0) {

			int plusCourt = fruitReception.get(0);

			ArbreDesCheminsMinimaux arbreChemin = etat.graphe.dijkstra(posLanceur);

			for (int i : fruitReception) {
				if (arbreChemin.getDistance(i) < arbreChemin.getDistance(plusCourt)) {
					plusCourt = i;
				}
			}
	
			// aller vers i
			int[] destination = arbreChemin.getChemin(plusCourt);
			
			if (destination.length > 1)
			{
			debug("" + destination[1]);
				return etat.getDirectionPlusProche(posLanceur, destination[1], false);
			}
		}
			return Action.RIEN;
		

	}
}