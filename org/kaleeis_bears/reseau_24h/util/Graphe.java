package org.kaleeis_bears.reseau_24h.util;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Graphe {
	
	public final int ordre;
	private final int[][] liaisons;
	private Object[] sommets;

	public Graphe(int ordre) {
		this.ordre = ordre;
		this.liaisons = new int[ordre][ordre];
		for (int x = 0; x < this.ordre; x++)
			for (int y = 0; y < this.ordre; y++)
				this.liaisons[x][y] = Integer.MAX_VALUE;
	}
	public Graphe(int[][] matriceAdjacence) {
		if (matriceAdjacence.length != matriceAdjacence[0].length)
			throw new IllegalArgumentException("Le paramètre « matriceAdjacence » doit être une matrice carrée.");
		this.ordre = matriceAdjacence.length;
		this.liaisons = matriceAdjacence;
	}
	public Graphe(int ordre, int[][] arcs) {
		this(ordre);
		if (arcs[0].length != 3)
			throw new IllegalArgumentException("Le paramètre « arcs » doit être un tableau de dimensions N × 3.");
		for (int i = 0; i < arcs.length; i++)
			this.setArc(arcs[i][0], arcs[i][1], arcs[i][2]);
	}
	public Graphe(Object[] sommets) {
		this(sommets.length);
		this.sommets = sommets;
	}

	public int getNoSommet(Object obj) {
		if (sommets == null)
			throw new IllegalStateException("Ce graphe n'associe pas d'objet aux sommets. Utilisez directement leur indice.");
		for (int i = 0; i < this.sommets.length; i++)
			if (obj.equals(this.sommets[i]))
				return i;
		throw new NoSuchElementException("Le sommet « " + obj + " » ne fait pas partie de ce graphe.");
	}

	public boolean arcExiste(int depart, int arrivee) {
		return this.liaisons[depart][arrivee] != Integer.MAX_VALUE;
	}
	public boolean arcExiste(Object depart, Object arrivee) {
		return this.arcExiste(this.getNoSommet(depart), this.getNoSommet(arrivee));
	}
	public int getPoids(int depart, int arrivee) {
		return this.liaisons[depart][arrivee];
	}
	public int getPoids(Object depart, Object arrivee) {
		return this.getPoids(this.getNoSommet(depart), this.getNoSommet(arrivee));
	}
	
	public Object[][] getSuccesseurs() {
		ArrayList<Object[]> sommets = new ArrayList<>();
		for (int i = 0; i < this.ordre; i++)
			sommets.add(this.getSuccesseurs(i));
		return sommets.toArray(new Object[0][0]);
	}
	public Object[] getSuccesseurs(int depart) {
		ArrayList<Object> successeurs = new ArrayList<>();
		for (int i = 0; i < this.ordre; i++)
			if (this.liaisons[depart][i] != Integer.MAX_VALUE)
				successeurs.add(this.sommets[i]);
		return successeurs.toArray();
	}
	public Object[] getSuccesseurs(Object depart) {
		return this.getSuccesseurs(this.getNoSommet(depart));
	}

	public Object[][] getPredecesseurs() {
		ArrayList<Object[]> sommets = new ArrayList<>();
		for (int i = 0; i < this.ordre; i++)
			sommets.add(this.getPredecesseurs(i));
		return sommets.toArray(new Object[0][0]);
	}
	public Object[] getPredecesseurs(int arrivee) {
		ArrayList<Object> predecesseurs = new ArrayList<>();
		for (int i = 0; i < this.ordre; i++)
			if (this.liaisons[i][arrivee] != Integer.MAX_VALUE)
				predecesseurs.add(this.sommets[i]);
		return predecesseurs.toArray();
	}
	public Object[] getPredecesseurs(Object arrivee) {
		return this.getPredecesseurs(this.getNoSommet(arrivee));
	}

	public void setArc(int depart, int arrivee, int poids) {
		if (poids < 0)
			throw new IllegalArgumentException("Le paramètre « poids » doit être supérieur ou égal à 0. Utilisez « retirerArc » pour retirer un arc.");
		this.liaisons[depart][arrivee] = poids;
	}
	public void setArc(Object depart, Object arrivee, int poids) {
		this.setArc(this.getNoSommet(depart), this.getNoSommet(arrivee), poids);
	}
	public void setDoubleArc(int depart, int arrivee, int poids) {
		this.setArc(depart, arrivee, poids);
		this.setArc(arrivee, depart, poids);
	}
	public void setDoubleArc(Object depart, Object arrivee, int poids) {
		this.setDoubleArc(this.getNoSommet(depart), this.getNoSommet(arrivee), poids);
	}
	public void retirerArc(int depart, int arrivee) {
		this.liaisons[depart][arrivee] = Integer.MAX_VALUE;
	}
	public void retirerArc(Object depart, Object arrivee) {
		this.retirerArc(this.getNoSommet(depart), this.getNoSommet(arrivee));
	}

	public ArbreDesCheminsMinimaux dijkstra(int racine) {
		int[] distance = new int[this.ordre], pere = new int[this.ordre];
		boolean[] aChoisir = new boolean[this.ordre];
		Arrays.fill(distance, Integer.MAX_VALUE);
		Arrays.fill(pere, -1);
		distance[racine] = 0;
		aChoisir[racine] = true;
		// System.out.println("DEBUObject DIJKSObjectRA");
		int x = racine, y, min = 0;
		while (x != -1)
		{
			// System.out.println("x = " + this.sommets[x]);
			for (y = 0; y < this.ordre; y++)
				if (this.liaisons[x][y] != Integer.MAX_VALUE)
				{
					// System.out.print("  y = " + this.sommets[y]);
					if (distance[y] > distance[x] + this.liaisons[x][y])
					{
						pere[y] = x;
						distance[y] = distance[x] + this.liaisons[x][y];
						aChoisir[y] = true;
						// System.out.println(" (ajouté)");
					}
					// else
						// System.out.println();
				}
			aChoisir[x] = false;
			x = -1;
			for (y = 0; y < this.ordre; y++)
				if (aChoisir[y] == true && (x == -1 || distance[y] < min))
				{
					min = distance[y];
					x = y;
				}
		}
		// System.out.println("FIN DIJKSObjectRA");
		return new ArbreDesCheminsMinimaux(distance, pere, this.sommets);
	}
	public ArbreDesCheminsMinimaux dijkstra(Object racine) {
		return this.dijkstra(this.getNoSommet(racine));
	}
}