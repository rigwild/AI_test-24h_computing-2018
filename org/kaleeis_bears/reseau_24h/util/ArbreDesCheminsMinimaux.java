package org.kaleeis_bears.reseau_24h.util;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class ArbreDesCheminsMinimaux {
	private final int[] distance, arbre;
	private Object[] sommets;

	public ArbreDesCheminsMinimaux(int[] distance, int[] arbre, Object[] sommets) {
		this.distance = distance;
		this.arbre = arbre;
		this.sommets = sommets;
	}

	public int getNoSommet(Object obj) {
		for (int i = 0; i < this.sommets.length; i++)
			if (obj.equals(this.sommets[i]))
				return i;
		throw new NoSuchElementException("Le sommet « " + obj + " » ne fait pas partie de ce graphe.");
	}

	public boolean estAccessible(int destination) {
		return this.distance[destination] != Integer.MAX_VALUE;
	}
	public boolean estAccessible(Object destination) {
		return this.estAccessible(this.getNoSommet(destination));
	}

	public int getDistance(int destination) {
		return this.distance[destination];
	}
	public int getDistance(Object destination) {
		return this.getDistance(this.getNoSommet(destination));
	}

	public int[] getSommetDistanceEntre(int min, int max) {
		LinkedList<Integer> sommets = new LinkedList<>();
		int distance;
		for (int i = 0; i < this.distance.length; i++)
		{
			distance = this.getDistance(i);
			if (distance >= min && distance < max)
				sommets.add(i);
		}
		return Tableau.listeVersTableauNombre(sommets);
	}
	public int[] getSommetDistanceEntre(int min, int max, int[] sommets) {
		LinkedList<Integer> resultat = new LinkedList<>();
		int distance;
		for (int i = 0; i < sommets.length; i++)
		{
			distance = this.getDistance(sommets[i]);
			if (distance >= min && distance < max)
				resultat.add(sommets[i]);
		}
		return Tableau.listeVersTableauNombre(resultat);
	}
	public int[] trierParDistance(int[] sommets) {
		List<Integer> aTrier = Tableau.tableauNombreVersListe(sommets);
		Collections.sort(aTrier, (a, b) -> this.getDistance(a.intValue()) - this.getDistance(b.intValue()));
		return Tableau.listeVersTableauNombre(aTrier);
	}

	public int getSommetPlusProche(int[] sommets) {
		int distance;
		int distanceMin = this.getDistance(sommets[0]),
			sommetMin = sommets[0];
		for (int i = 0; i < sommets.length; i++)
		{
			distance = this.getDistance(sommets[i]);
			if (distance < distanceMin)
			{
				distanceMin = distance;
				sommetMin = sommets[i];
			}
		}
		return sommetMin;
	}

	public int[] getChemin(int destination) {
		LinkedList<Integer> chemin = new LinkedList<>();
		do
		{
			chemin.addFirst(destination);
		} while ((destination = arbre[destination]) != -1);
		return Tableau.listeVersTableauNombre(chemin);
	}
	public int[] getChemin(Object destination) {
		return this.getChemin(this.getNoSommet(destination));
	}
}